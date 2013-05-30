package pedals.is.floatingjapanesedictionary;

import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.os.Bundle;

public class FloatingJapaneseDictionaryLauncherActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StandOutWindow.show(this, FloatingJapaneseDictionaryWindow.class,
				StandOutWindow.DEFAULT_ID);
		finish();
	}
}