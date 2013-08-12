package pedals.is.floatingjapanesedictionary;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcher;
import pedals.is.floatingjapanesedictionary.downloader.DictionaryManagerService;
import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class FloatingJapaneseDictionaryLauncherActivity extends Activity {

	private static final String TAG = "FloatingJapaneseDictionaryLauncherActivity";

	@Override
	protected void onStart() {

		super.onStart();

		if (FloatingJapaneseDictionaryService.RUNNING) {
			Log.d(TAG, "Window already running, not doing anything");
			finish();
		}

		// we have a successfully installed dictionary
		else if (DictionarySearcher.dictionaryExists(this)) {
			Log.d(TAG, "Dictionary exists, starting window");
			StandOutWindow.show(this, FloatingJapaneseDictionaryService.class,
					StandOutWindow.DEFAULT_ID);
			finish();
		}

		// we are currently downloading a dictionary
		else if (DictionaryManagerService.RUNNING) {
			Log.d(TAG, "we are currently downloading a dictionary");
			finish();
		}

		// no dictionary, prompt for download
		else {
			Log.d(TAG, "showing prompt");
			AlertDialog.Builder builder = new AlertDialog.Builder(
					FloatingJapaneseDictionaryLauncherActivity.this);

			builder.setTitle("Download Dictionary");
			builder.setMessage("A dictionary needs to be downloaded (11.2MB). If you do not download now, the app will close and you will be asked to download the dictionary the next time you start the app.");

			builder.setPositiveButton("Download Now",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Log.d(TAG, "Downloading now");
							dialog.dismiss();
							Intent intent = new Intent(
									FloatingJapaneseDictionaryLauncherActivity.this,
									DictionaryManagerService.class);
							intent.putExtra("action", "download");
							startService(intent);
							FloatingJapaneseDictionaryLauncherActivity.this
									.finish();
						}

					});

			builder.setNegativeButton("Download Later",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Log.d(TAG, "Downloading later");
							dialog.dismiss();
							FloatingJapaneseDictionaryLauncherActivity.this
									.finish();
						}
					});

			builder.create().show();

		}

	}
}
