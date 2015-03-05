package pedals.is.floatingjapanesedictionary.test;

import pedals.is.floatingjapanesedictionary.downloader.DictionaryManagerService;
import android.content.Intent;
import android.test.ServiceTestCase;

public class DictionaryManagerServiceTest extends
		ServiceTestCase<DictionaryManagerService> {

	public DictionaryManagerServiceTest() {
		super(DictionaryManagerService.class);
	}

	public final void testStarts() {
		startService(new Intent(getContext(), DictionaryManagerService.class));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse("DictionaryManagerService stops when given no action",
				DictionaryManagerService.RUNNING);

	}

	public final void testDeletingNothing() {
		Intent intent = new Intent(getContext(), DictionaryManagerService.class);
		intent.putExtra("action", "reset");
		startService(intent);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(
				"DictionaryManagerService stops when told to reset and there are no files",
				DictionaryManagerService.RUNNING);

	}

}
