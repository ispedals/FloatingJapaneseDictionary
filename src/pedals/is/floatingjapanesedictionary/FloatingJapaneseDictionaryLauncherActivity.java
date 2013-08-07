package pedals.is.floatingjapanesedictionary;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcher;
import pedals.is.floatingjapanesedictionary.downloader.DictionaryDownloaderActivity;
import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FloatingJapaneseDictionaryLauncherActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		StandOutWindow.closeAll(this, FloatingJapaneseDictionaryService.class);
		if(DictionarySearcher.dictionaryExists(this)){
			StandOutWindow.show(this, FloatingJapaneseDictionaryService.class,
					StandOutWindow.DEFAULT_ID);
		}
		else {
			Intent intent = new Intent(this, DictionaryDownloaderActivity.class);
			startActivity(intent);
		}
		finish();
	}
}
