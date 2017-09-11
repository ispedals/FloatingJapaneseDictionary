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
package pedals.is.floatingjapanesedictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntries;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntry;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcherActivity;
import pedals.is.floatingjapanesedictionary.downloader.DictionaryManagerService;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class FloatingJapaneseDictionaryService extends StandOutWindow {

	public static final int DISPLAY_TEXT = 0, DISPLAY_DEFINITION = 1,
			DISPLAY_SEARCH = 2, CLEAR_TEXT = 3;
	public static final String SUBMITTED = "SUBMITTED";

	private static final String APP_NAME = "Floating Japanese Dictionary";
	private static final int APP_ICON = android.R.drawable.ic_menu_add;
	private static final File saveLocation = new File(
			Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
			"Words.txt");

	private static final String TAG = "FJDService";

	private static final int CLOSED = 0, OPENED = 1;

	private static final int CLOSED_WIDTH = 200;
	private static final int CLOSED_HEIGHT = 128;

	private static final int OPENED_WIDTH = 400;
	private static final int OPENED_HEIGHT = 400;

	private static StandOutLayoutParams closedParams;
	private static StandOutLayoutParams openedParams;

	private static final DictionaryEntries entries = new DictionaryEntries();
	private static ArrayAdapter<DictionaryEntry> adapter;

	public static boolean RUNNING = false;
	private static int windowState = OPENED;

	@Override
	public void onCreate() {

		super.onCreate();
		RUNNING = true;
		Log.d(TAG, "Created");
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		RUNNING = false;
		Log.d(TAG, "Destroyed");
	}

	@Override
	@TargetApi(11)
	public void createAndAttachView(final int id, FrameLayout frame) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.floatingdictionary, frame, true);

		SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
		SearchView searchView = (SearchView) view.findViewById(R.id.search);
		searchView
				.setSearchableInfo(searchManager
						.getSearchableInfo(new ComponentName(
								"pedals.is.floatingjapanesedictionary",
								"pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcherActivity")));
		searchView.setSubmitButtonEnabled(true);
		searchView.setIconified(false);
		FloatingJapaneseDictionaryService.windowState = OPENED;

		searchView.setOnCloseListener(new SearchView.OnCloseListener() {

			public boolean onClose() {

				Log.d(TAG, "searchview close click");
				setClosedState(id);
				return false;
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				return false;
			}

			@Override
			public boolean onQueryTextChange(String query) {

				Intent intent = new Intent(
						FloatingJapaneseDictionaryService.this,
						DictionarySearcherActivity.class);
				intent.setAction(Intent.ACTION_SEARCH);
				intent.putExtra(SearchManager.QUERY, query);
				intent.putExtra(SUBMITTED, false);
				/*
				 * SearchView itself launches the activity with
				 * FLAG_ACTIVITY_NEW_TASK.
				 * 
				 * Because multiple instances of the activity will be fired
				 * rapidly, FLAG_ACTIVITY_CLEAR_TOP will either start the
				 * activity, or kill the activity if it is running and start it
				 * again.
				 */
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
			}
		});

		searchView.setOnSearchClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View searchView) {

				Log.d(TAG, "searchview search click");
				setOpenedState(id);

			}
		});

		adapter = new ArrayAdapter<>(this,
				R.layout.dictionaryentry, entries);
		ListView listView = (ListView) view.findViewById(R.id.results);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				DictionaryEntry entry = (DictionaryEntry) parent
						.getItemAtPosition(position);
				Log.d(TAG, "item clicked " + entry.toString());
				try {
					FileWriter filewriter = new FileWriter(saveLocation, true);
					filewriter.append("\r\n").append(entry.toString());
					filewriter.close();
					Log.d(TAG,
							"item saved to " + saveLocation.getAbsolutePath());
					Toast.makeText(FloatingJapaneseDictionaryService.this,
							"Saved", Toast.LENGTH_SHORT).show();
				}
				catch (IOException e) {
					Log.d(TAG, "Save failed");
					e.printStackTrace();
					Toast.makeText(FloatingJapaneseDictionaryService.this,
							"Could not save", Toast.LENGTH_SHORT).show();
				}

			}
		});

		Log.d(TAG, "Creating window; id: " + id);
	}

	private void setClosedState(int id) {

		Log.d(TAG, "setting closed state");
		transition(id, CLOSED);
	}

	private void setOpenedState(int id) {

		Log.d(TAG, "setting open state");
		transition(id, OPENED);

	}

	private void synchronizePositions(int id, StandOutLayoutParams... params) {

		StandOutLayoutParams currentParam = getParams(id);
		Log.d(TAG, "Synchronizing position: x, y " + currentParam.x + " "
				+ currentParam.y);
		for (StandOutLayoutParams param : params) {
			if (param != null) {
				param.x = currentParam.x;
				param.y = currentParam.y;
			}
		}
	}

	private void transition(int id, int state) {

		Log.d(TAG, "transitioning");

		Window window = getWindow(id);

		clearText(window);
		synchronizePositions(id, closedParams, openedParams);

		FloatingJapaneseDictionaryService.windowState = state;

		updateViewLayout(id, getParams(id));
	}

	@Override
	public boolean onClose(int id, Window window) {

		Log.d(TAG, "window closing");
		setOpenedState(id);
		stopSelf();
		return false;
	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) {

		return getParams(id);
	}

	public StandOutLayoutParams getParams(int id) {

		if (windowState == CLOSED) {
			return getClosedParams(id);
		}
		return getOpenedParams(id);
	}

	@Override
	public int getFlags(int id) {

		return super.getFlags(id) | StandOutFlags.FLAG_DECORATION_SYSTEM
				| StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_DECORATION_MAXIMIZE_DISABLE;
	}

	@Override
	public void onReceiveData(int id, int requestCode, Bundle data,
			Class<? extends StandOutWindow> fromCls, int fromId) {

		Window window = getWindow(id);

		clearText(window);

		Log.d(TAG, "recieved data, window id " + id + " code " + requestCode);

		switch (requestCode) {
			case DISPLAY_DEFINITION:
				displayDefinition(window,
						data.getParcelableArrayList("DEFINITIONS"));
				break;
			case DISPLAY_SEARCH:
				displaySearch(window, data.getString("TEXT"));
				break;
			case DISPLAY_TEXT:
				displayText(window, data.getString("TEXT"));
				break;
			/*
			 * Right now we clear the window before processing the data. so
			 * instead of updating the UI twice, just do nothing. This should
			 * change if we do not clear the window at the beginning.
			 */
			case CLEAR_TEXT:
			default:
				// clearText(window);
		}

	}

	private void clearText(Window window) {

		Log.d(TAG, "clearing text");
		TextView status = (TextView) window.findViewById(R.id.status);
		status.setText("");
		entries.clear();
		adapter.notifyDataSetChanged();

	}

	private void displayDefinition(Window window,
			ArrayList<Parcelable> arrayList) {

		Log.d(TAG, "displaying Definition");
		entries.clear();
		entries.addAll(DictionaryEntries.fromParcelable(arrayList));
		adapter.notifyDataSetChanged();

	}

	private void displayText(Window window, String text) {

		Log.d(TAG, "Displaying text " + text);
		TextView status = (TextView) window.findViewById(R.id.status);
		status.setTextSize(20);
		status.setText(text);
	}

	private void displaySearch(Window window, String text) {

		Log.d(TAG, "Displaying search " + text);
		SearchView searchView = (SearchView) window.findViewById(R.id.search);
		searchView.setQuery(text, false);
	}

	@Override
	public List<DropDownListItem> getDropDownItems(int id) {

		List<DropDownListItem> items = new ArrayList<>();
		items.add(new DropDownListItem(0, "About", new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(
						FloatingJapaneseDictionaryService.this,
						AboutActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.d(TAG, "about called");
				FloatingJapaneseDictionaryService.this.startActivity(intent);
				closeAll();
			}
		}));
		items.add(new DropDownListItem(0, "Reset", new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(
						FloatingJapaneseDictionaryService.this,
						DictionaryManagerService.class);
				intent.putExtra("action", "reset");
				Log.d(TAG, "reset called");
				FloatingJapaneseDictionaryService.this.startService(intent);
				closeAll();
			}
		}));

		Window window = getWindow(id);
		SearchView searchview = (SearchView) window.findViewById(R.id.search);
		final String query = searchview.getQuery().toString();
		if (query.length() != 0) {
			items.add(new DropDownListItem(0, "Lookup", new Runnable() {

				@Override
				public void run() {

					Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
					intent.putExtra(SearchManager.QUERY, query);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Log.d(TAG, "search called with " + query);
					FloatingJapaneseDictionaryService.this
							.startActivity(intent);
				}
			}));
		}
		return items;
	}

	private StandOutLayoutParams getClosedParams(int id) {

		if (closedParams == null) {
			closedParams = new StandOutLayoutParams(id, CLOSED_WIDTH,
					CLOSED_HEIGHT);
		}
		closedParams.minWidth = CLOSED_WIDTH;
		closedParams.minHeight = CLOSED_HEIGHT;
		closedParams.maxWidth = CLOSED_WIDTH;
		closedParams.maxHeight = CLOSED_HEIGHT;
		return closedParams;
	}

	private StandOutLayoutParams getOpenedParams(int id) {

		if (openedParams == null) {
			openedParams = new StandOutLayoutParams(id, OPENED_WIDTH,
					OPENED_HEIGHT);
			openedParams.minWidth = OPENED_WIDTH;
			openedParams.minHeight = OPENED_HEIGHT;
		}
		return openedParams;
	}

	@Override
	public String getAppName() {

		return APP_NAME;
	}

	@Override
	public int getAppIcon() {

		return APP_ICON;
	}

}
