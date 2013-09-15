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

			/*
			 * If the flag isn't set, it means that we can freely update the
			 * textfield without worrying about disturbing search on type ime
			 * composition. We need to update the textfield when a voice search
			 * occurs so that the user knows what the query was recognized as.
			 */
			if (intent.getBooleanExtra(
					FloatingJapaneseDictionaryService.SUBMITTED, true)) {
				displaySearch(query);
			}
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
