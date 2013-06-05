package pedals.is.floatingjapanesedictionary;

import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class DictionarySearcherActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			displaySearch(query);
			DictionaryEntries result = doQuery(query);

			if (result.isEmpty()) {
				displayText("No results");
			}
			else {
				displayDefinition(result.toJSON().toString());
			}

		}

		finish();
	}

	private DictionaryEntries doQuery(String query) {

		return DictionarySearcher.findWord(query);
	}

	private void displaySearch(String result) {

		sendText(result, FloatingJapaneseDictionaryWindow.DISPLAY_SEARCH);
	}

	private void displayText(String result) {

		sendText(result, FloatingJapaneseDictionaryWindow.DISPLAY_TEXT);
	}

	private void displayDefinition(String result) {

		sendText(result, FloatingJapaneseDictionaryWindow.DISPLAY_DEFINITION);
	}

	private void sendText(String text, int requestCode) {

		Bundle data = new Bundle();
		data.putString("TEXT", text);
		StandOutWindow.sendData(this, FloatingJapaneseDictionaryWindow.class,
				StandOutWindow.DEFAULT_ID, requestCode, data, null,
				StandOutWindow.DISREGARD_ID);
	}

}
