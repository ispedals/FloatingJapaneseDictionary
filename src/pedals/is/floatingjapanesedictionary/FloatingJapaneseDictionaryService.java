package pedals.is.floatingjapanesedictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntries;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntry;
import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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

	private static boolean CLOSED = false;

	private static StandOutLayoutParams closedParams;
	private static StandOutLayoutParams openedParams;

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

				setClosedState(thisService, id);
				return false;
			}
		});

		searchView.setOnSearchClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View searchView) {

				setOpenedState(thisService, id);

			}
		});
	}

	private void setClosedState(FloatingJapaneseDictionaryService thisService,
			int window_id) {

		FloatingJapaneseDictionaryService.CLOSED = true;
		Window window = thisService.getWindow(window_id);
		thisService.clearText(window);
		thisService.synchronizePosition(thisService.getParams(window_id));
		thisService.updateViewLayout(window_id,
				thisService.getParams(window_id));
	}

	private void synchronizePosition(StandOutLayoutParams params) {

		if (closedParams == null || openedParams == null) {
			return;
		}
		closedParams.x = params.x;
		closedParams.y = params.y;
		openedParams.x = params.x;
		openedParams.y = params.y;
	}

	private void setOpenedState(FloatingJapaneseDictionaryService thisService,
			int window_id) {

		FloatingJapaneseDictionaryService.CLOSED = false;
		Window window = thisService.getWindow(window_id);
		thisService.clearText(window);
		thisService.synchronizePosition(thisService.getParams(window_id));
		thisService.updateViewLayout(window_id,
				thisService.getParams(window_id));

	}

	@Override
	public boolean onClose(int id, Window window) {

		setClosedState(this, id);
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

		TextView status = (TextView) window.findViewById(R.id.status);
		ListView listView = (ListView) window.findViewById(R.id.results);
		status.setText("");
		listView.setAdapter(new ArrayAdapter<Object>(window.getContext(),
				R.layout.dictionaryentry));

	}

	private void displayDefinition(final Window window,
			ArrayList<Parcelable> arrayList) {

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
				try {
					FileWriter filewriter = new FileWriter(
							new File(
									Environment
											.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
									"Words.txt"), true);
					filewriter.append("\r\n" + entry.toString());
					filewriter.close();
					Toast.makeText(window.getContext(), "Saved",
							Toast.LENGTH_SHORT).show();
				}
				catch (IOException e) {
					Toast.makeText(window.getContext(), "Could not save",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void displayText(Window window, String text) {

		TextView status = (TextView) window.findViewById(R.id.status);
		status.setTextSize(20);
		status.setText(text);
	}

	private void displaySearch(Window window, String text) {

		SearchView searchView = (SearchView) window.findViewById(R.id.search);
		searchView.setQuery(text, false);
	}

	private StandOutLayoutParams getClosedParams(int id) {

		final int CLOSED_WIDTH = 100;
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
