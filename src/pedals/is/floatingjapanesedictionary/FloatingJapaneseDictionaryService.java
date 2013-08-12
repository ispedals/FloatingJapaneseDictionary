package pedals.is.floatingjapanesedictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntries;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntry;
import pedals.is.floatingjapanesedictionary.downloader.DictionaryManagerService;
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
			DISPLAY_SEARCH = 2;

	private static final String APP_NAME = "Floating Japanese Dictionary";
	private static final int APP_ICON = android.R.drawable.ic_menu_add;
	private File saveLocation = new File(
			Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
			"Words.txt");

	private static final String TAG = "FloatingJapaneseDictionaryService";

	private static StandOutLayoutParams closedParams;
	private static StandOutLayoutParams openedParams;

	public static boolean RUNNING;
	private static boolean CLOSED;

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
		FloatingJapaneseDictionaryService.CLOSED = false;

		final FloatingJapaneseDictionaryService thisService = this;
		searchView.setOnCloseListener(new SearchView.OnCloseListener() {

			public boolean onClose() {

				Log.d(TAG, "searchview close click");
				setClosedState(thisService, id);
				return false;
			}
		});

		searchView.setOnSearchClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View searchView) {

				Log.d(TAG, "searchview search click");
				setOpenedState(thisService, id);

			}
		});

		Log.d(TAG, "Creating window; id: " + id);
	}

	private void setClosedState(FloatingJapaneseDictionaryService thisService,
			int window_id) {

		Log.d(TAG, "setting closed state");
		transition(thisService, window_id, true);
	}

	private void setOpenedState(FloatingJapaneseDictionaryService thisService,
			int window_id) {

		Log.d(TAG, "setting open state");
		transition(thisService, window_id, false);

	}

	private void transition(FloatingJapaneseDictionaryService thisService,
			int window_id, boolean state) {

		Log.d(TAG, "transitioning");

		Window window = thisService.getWindow(window_id);

		thisService.clearText(window);

		StandOutLayoutParams params = thisService.getParams(window_id);
		if (closedParams != null && openedParams != null) {
			Log.d(TAG, "Synchronizing position: x, y " + params.x + " "
					+ params.y);
			closedParams.x = params.x;
			closedParams.y = params.y;
			openedParams.x = params.x;
			openedParams.y = params.y;
		}

		FloatingJapaneseDictionaryService.CLOSED = state;

		thisService.updateViewLayout(window_id,
				thisService.getParams(window_id));
	}

	@Override
	public boolean onClose(int id, Window window) {

		Log.d(TAG, "window closing");
		setOpenedState(this, id);
		stopSelf();
		return false;
	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) {

		return getParams(id);
	}

	public StandOutLayoutParams getParams(int id) {

		if (CLOSED) {
			return getClosedParams(id);
		}
		return getOpenedParams(id);
	}

	@Override
	public int getFlags(int id) {

		return super.getFlags(id) | StandOutFlags.FLAG_DECORATION_SYSTEM
				| StandOutFlags.FLAG_DECORATION_RESIZE_DISABLE
				| StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE;
	}

	@Override
	public void onReceiveData(int id, int requestCode, Bundle data,
			Class<? extends StandOutWindow> fromCls, int fromId) {

		Window window = getWindow(id);

		clearText(window);

		Log.d(TAG, "recieved data, window id " + id + "code " + requestCode);

		switch (requestCode) {
			case DISPLAY_DEFINITION:
				displayDefinition(window,
						data.getParcelableArrayList("DEFINITIONS"));
				break;
			case DISPLAY_SEARCH:
				displaySearch(window, data.getString("TEXT"));
			case DISPLAY_TEXT:
			default:
				displayText(window, data.getString("TEXT"));
		}

	}

	private void clearText(Window window) {

		Log.d(TAG, "clearing text");
		TextView status = (TextView) window.findViewById(R.id.status);
		ListView listView = (ListView) window.findViewById(R.id.results);
		status.setText("");
		listView.setAdapter(new ArrayAdapter<Object>(window.getContext(),
				R.layout.dictionaryentry));

	}

	private void displayDefinition(final Window window,
			ArrayList<Parcelable> arrayList) {

		Log.d(TAG, "displaying Definition");
		DictionaryEntries entries = DictionaryEntries.fromParcelable(arrayList);
		ArrayAdapter<DictionaryEntry> adapter = new ArrayAdapter<DictionaryEntry>(
				window.getContext(), R.layout.dictionaryentry, entries);
		ListView listView = (ListView) window.findViewById(R.id.results);
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
					filewriter.append("\r\n" + entry.toString());
					filewriter.close();
					Log.d(TAG,
							"item saved to " + saveLocation.getAbsolutePath());
					Toast.makeText(window.getContext(), "Saved",
							Toast.LENGTH_SHORT).show();
				}
				catch (IOException e) {
					Log.d(TAG, "Save failed");
					e.printStackTrace();
					Toast.makeText(window.getContext(), "Could not save",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
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

		List<DropDownListItem> items = new ArrayList<DropDownListItem>();
		final StandOutWindow service = this;
		items.add(new DropDownListItem(0, "Reset", new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(service,
						DictionaryManagerService.class);
				intent.putExtra("action", "reset");
				Log.d(TAG, "reset called");
				service.startService(intent);
				closeAll();
			}
		}));
		return items;
	}

	private StandOutLayoutParams getClosedParams(int id) {

		final int CLOSED_WIDTH = 200;
		final int CLOSED_HEIGHT = 128;

		if (closedParams == null) {
			closedParams = new StandOutLayoutParams(id, CLOSED_WIDTH,
					CLOSED_HEIGHT);
		}
		return closedParams;
	}

	private StandOutLayoutParams getOpenedParams(int id) {

		final int OPENED_WIDTH = 400;
		final int OPENED_HEIGHT = 400;

		if (openedParams == null) {
			openedParams = new StandOutLayoutParams(id, OPENED_WIDTH,
					OPENED_HEIGHT);
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
