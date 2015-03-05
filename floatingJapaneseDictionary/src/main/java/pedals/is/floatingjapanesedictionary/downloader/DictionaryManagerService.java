/*
 *  Copyright 2013 Balloonguy
 *  This file is part of FloatingJapaneseDictionary.

    FloatingJapaneseDictionary is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    FloatingJapaneseDictionary is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FloatingJapaneseDictionary.  If not, see <http://www.gnu.org/licenses/>.
 */
package pedals.is.floatingjapanesedictionary.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import pedals.is.floatingjapanesedictionary.FloatingJapaneseDictionaryLauncherActivity;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcher;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class DictionaryManagerService extends Service {

	// Handler that receives messages from the thread
	@SuppressLint("HandlerLeak")
	private class ServiceHandler extends Handler {

		public ServiceHandler(Looper looper) {

			super(looper);
		}

		@Override
		public void handleMessage(Message message) {

			if (message.obj == null) {
				RUNNING = false;
				stopSelf(message.arg1);
			}
			else if (message.obj.equals("reset")) {
				deleteFiles();
				RUNNING = false;
				stopSelf(message.arg1);
				return;
			}
			else if (message.obj.equals("download")) {
				// no pending download
				if (enqueuedID == -1) {
					RUNNING = true;
					deleteFiles();
					downloadDictionary();
				}
				// pending download
				else {
					RUNNING = false;
					stopSelf(message.arg1);
				}
			}
		}
	}

	private Looper looper;
	private ServiceHandler handler;

	public static String DOWNLOAD_URL = "https://addons.mozilla.org/firefox/downloads/latest/398350/addon-398350-latest.xpi?src=ss";
	private static final String DOWNLOAD_FILE_NAME = "dict.xpi";

	private static long enqueuedID = -1;

	public static boolean RUNNING = false;
	private static final String TAG = "DictionaryManagerService";

	// callback for when download is complete
	// it handles extracting the sqlite database out of the xpi file
	// and placing it into the application external file directory.
	// It deletes the downloaded xpi after extraction
	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (!intent.getAction().equals(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				return;
			}
			Log.d(TAG, "processing");

			Query query = new Query();
			query.setFilterById(enqueuedID);
			DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			Cursor cursor = manager.query(query);

			if (!cursor.moveToFirst()) {
				cursor.close();
				return;
			}

			int columnIndex = cursor
					.getColumnIndex(DownloadManager.COLUMN_STATUS);

			if (cursor.getInt(columnIndex) != DownloadManager.STATUS_SUCCESSFUL) {
				cursor.close();
				return;
			}

			String uriString = cursor.getString(cursor
					.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
			cursor.close();

			extractFile(Uri.parse(uriString),
					DictionarySearcher.DICTIONARY_NAME);

			new File(Uri.parse(uriString).getPath()).delete();

			intent = new Intent(context,
					FloatingJapaneseDictionaryLauncherActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			RUNNING = false;
			enqueuedID = -1;
			context.unregisterReceiver(receiver);

			Log.d(TAG, "stopping");
			stopSelf();

		}
	};

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		looper = thread.getLooper();
		handler = new ServiceHandler(looper);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// For each start request, send a message to start a job and deliver the
		// start ID so we know which request we're stopping when we finish the
		// job
		RUNNING = true;
		Message message = handler.obtainMessage();
		message.arg1 = startId;
		Log.d(TAG, "started with " + startId);
		Bundle extra = intent.getExtras();
		if (extra != null) {
			String action = extra.getString("action");
			message.obj = action;
			Log.d(TAG, "started with action " + action);
		}
		Log.d(TAG, "started, id is " + enqueuedID);
		handler.sendMessage(message);

		return START_NOT_STICKY;

	}

	@Override
	public void onDestroy() {

		Log.d(TAG, "being destroyed, id is " + enqueuedID);
		RUNNING = false;
		try {
			unregisterReceiver(receiver);
		}
		catch (IllegalArgumentException e) {
		}
		super.onDestroy();
	}

	private void downloadDictionary() {

		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(DOWNLOAD_URL));
		request.setDescription("Japanese-English Dictionary for Floating Japanese Dictionary");
		request.setTitle("Downloading Dictionary");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(false);
		request.setDestinationInExternalFilesDir(this, null, DOWNLOAD_FILE_NAME);

		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		enqueuedID = manager.enqueue(request);
		Log.d(TAG, "Downloading with id " + enqueuedID);

		registerReceiver(receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE), null, handler);
	}

	private void deleteFiles() {

		for (File file : this.getExternalFilesDir(null).listFiles()) {
			Log.d(TAG, "Deleting file: " + file.getAbsolutePath());
			file.delete();
		}
	}

	// unzips xpi and extracts desired file to same directory as xpi
	private File extractFile(Uri uri, String desiredFile) {

		Log.d(TAG, "Trying to extract " + desiredFile);
		File unzippedFile = null;
		try {
			File zippedFile = new File(uri.getPath());
			ZipFile zipFile = new ZipFile(zippedFile, ZipFile.OPEN_READ);
			ZipEntry entry = zipFile.getEntry(desiredFile);

			InputStream in = zipFile.getInputStream(entry);

			unzippedFile = new File(zippedFile.getParentFile(), entry.getName());
			FileOutputStream out = new FileOutputStream(unzippedFile);

			byte[] buffer = new byte[1024];
			int len;

			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			out.close();
			in.close();
			zipFile.close();

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Error while unzipping");
		}
		return unzippedFile;
	}
}
