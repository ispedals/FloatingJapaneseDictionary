package pedals.is.floatingjapanesedictionary.downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcher;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DictionaryDownloaderActivity extends Activity {

	private DownloadManager manager;
	private long enqueuedID;
	private final String downloadUrl = "https://addons.mozilla.org/firefox/downloads/latest/398350/addon-398350-latest.xpi?src=ss";
	private final String downloadFileName = "dict.xpi";

	// callback for when download is complete
	// it handles extracting the sqlite database out of the xpi file
	// and placing it into the application external file directory.
	// It deletes the downloaded xpi after extraction
	// from http://blog.vogella.com/2011/06/14/android-downloadmanager-example/
	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
				Query query = new Query();
				query.setFilterById(enqueuedID);
				Cursor c = manager.query(query);
				if (c.moveToFirst()) {
					int columnIndex = c
							.getColumnIndex(DownloadManager.COLUMN_STATUS);
					if (DownloadManager.STATUS_SUCCESSFUL == c
							.getInt(columnIndex)) {
						String uriString = c
								.getString(c
										.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
						Log.d("Floating", uriString);
						Toast.makeText(context, uriString, Toast.LENGTH_LONG)
								.show();
						File dict = unpackZip(Uri.parse(uriString),
								DictionarySearcher.DICTIONARY_NAME);
						Toast.makeText(context, dict.getPath(),
								Toast.LENGTH_LONG).show();
						Log.d("Floating", dict.getPath());
						new File(Uri.parse(uriString).getPath()).delete();
					}
				}
			}
		}
	};

	@Override
	// from http://stackoverflow.com/a/3028660
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(downloadUrl));
		request.setDescription("Dictionary File");
		request.setTitle("Dictionary");
		// in order for this if to run, you must use the android 3.2 to compile
		// your app
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}
		// delete previously downloaded files
		for (File file : this.getExternalFilesDir(null).listFiles()) {
			file.delete();
		}
		request.setDestinationInExternalFilesDir(this, null, downloadFileName);

		// get download service and enqueue file
		manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		enqueuedID = manager.enqueue(request);

		registerReceiver(receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	// unzips xpi and extracts desired file to same directory as xpi
	// from
	// http://www.jondev.net/articles/Unzipping_Files_with_Android_%28Programmatically%29
	private File unpackZip(Uri uri, String desiredFile) {

		InputStream is;
		ZipInputStream zis;
		File unzippedFile = null;
		try {
			File zipFile = new File(uri.getPath());
			is = new FileInputStream(zipFile);
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				if (ze.getName().equals(desiredFile)) {

					unzippedFile = new File(zipFile.getParentFile(),
							ze.getName());
					FileOutputStream fout = new FileOutputStream(unzippedFile);

					while ((count = zis.read(buffer)) != -1) {
						fout.write(buffer, 0, count);
					}

					fout.close();
					zis.closeEntry();
					break;
				}
			}

			zis.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return unzippedFile;
	}

}
