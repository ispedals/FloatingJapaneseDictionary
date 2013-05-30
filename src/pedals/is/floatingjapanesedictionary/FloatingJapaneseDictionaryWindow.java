package pedals.is.floatingjapanesedictionary;

import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class FloatingJapaneseDictionaryWindow extends StandOutWindow {
	
	public static final int DISPLAY_TEXT =0, DISPLAY_DEFINITION =1, DISPLAY_ERROR =2;
	
	@Override
	public String getAppName() {
		return "Floating Japanese Dictionary";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_add;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.floatingdictionary, frame, true);
		
		SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
		SearchView searchView = (SearchView) view.findViewById(R.id.search);
	    searchView.setSearchableInfo(
    		searchManager.getSearchableInfo(
				new ComponentName("pedals.is.floatingjapanesedictionary", "pedals.is.floatingjapanesedictionary.DictionarySearcherActivity")
			)
		);

	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 300, 200,
				StandOutLayoutParams.AUTO_POSITION, StandOutLayoutParams.AUTO_POSITION);
	}

	@Override
	public int getFlags(int id) {
		return super.getFlags(id) |
				StandOutFlags.FLAG_DECORATION_SYSTEM |
				StandOutFlags.FLAG_BODY_MOVE_ENABLE |
				StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE |
				StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP |
				StandOutFlags.FLAG_ADD_FUNCTIONALITY_DROP_DOWN_DISABLE;
	}
	
	@Override
	public void onReceiveData(int id, int requestCode, Bundle data,
			Class<? extends StandOutWindow> fromCls, int fromId) {

		Window window = getWindow(id);
		switch(requestCode){
			case DISPLAY_DEFINITION:
				displayDefinition(window, data.getString("TEXT"));
				break;
			case DISPLAY_TEXT:
			default:
				displayText(window, data.getString("TEXT"));
		}
		
		
	}
	
	private void displayDefinition(Window window, String text) {
		DictionaryEntries entries = null;
		try {
			entries = DictionaryEntries.fromJSON(text);
		}
		catch (Exception e) {
			displayError(window, "definitions lost inflight");
		}
		TextView status = (TextView) window.findViewById(R.id.results);
		status.setTextSize(20);
		
		status.setText(entries.toString());
		
	}

	private void displayError(Window window, String error) {
		TextView status = (TextView) window.findViewById(R.id.results);
		status.setTextSize(20);
		status.setText("Error: " + error);		
	}

	private void displayText(Window window, String text) {
		TextView status = (TextView) window.findViewById(R.id.results);
		status.setTextSize(20);
		status.setText(text);
	}

}
