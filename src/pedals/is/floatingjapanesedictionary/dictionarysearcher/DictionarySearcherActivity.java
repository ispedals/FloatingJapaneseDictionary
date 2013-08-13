package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import pedals.is.floatingjapanesedictionary.FloatingJapaneseDictionaryService;
import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class DictionarySearcherActivity extends Activity {

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
				displayDefinition(result);
			}

		}

		finish();
	}

	private DictionaryEntries doQuery(String query) {

		return DictionarySearcher.findWord(this, query);
	}

	private void displaySearch(String result) {

		sendText(result, FloatingJapaneseDictionaryService.DISPLAY_SEARCH);
	}

	private void displayText(String result) {

		sendText(result, FloatingJapaneseDictionaryService.DISPLAY_TEXT);
	}

	private void displayDefinition(DictionaryEntries result) {

		int requestCode = FloatingJapaneseDictionaryService.DISPLAY_DEFINITION;
		Bundle data = new Bundle();
		data.putParcelableArrayList("DEFINITIONS",
				result.toParcelableContentValues());
		StandOutWindow.sendData(this, FloatingJapaneseDictionaryService.class,
				StandOutWindow.DEFAULT_ID, requestCode, data, null,
				StandOutWindow.DISREGARD_ID);
	}

	private void sendText(String text, int requestCode) {

		Bundle data = new Bundle();
		data.putString("TEXT", text);
		StandOutWindow.sendData(this, FloatingJapaneseDictionaryService.class,
				StandOutWindow.DEFAULT_ID, requestCode, data, null,
				StandOutWindow.DISREGARD_ID);
	}

}
