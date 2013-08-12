package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import java.io.File;
import java.util.ArrayList;

import pedals.is.floatingjapanesedictionary.deinflector.DeInflector;
import pedals.is.floatingjapanesedictionary.deinflector.DeinflectorTerm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DictionarySearcher {

	private static final int USING_LOCAL = 1, USING_EXTERNAL = 2,
			USING_BUILT_IN = 3;
	public static final int DICTIONARY_TYPE = USING_LOCAL;
	public static final String DICTIONARY_NAME = "dict.sqlite";

	// TABLE dict (kanji TEXT, kana TEXT, entry TEXT)
	private static final String wordQuery = "select distinct kanji, kana, entry from dict where kanji=? or kana=?";

	@SuppressWarnings("unused")
	private static SQLiteDatabase getDatabase(Context context) {

		if (DICTIONARY_TYPE == USING_EXTERNAL) {
			File dictionaryFile = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					DICTIONARY_NAME);
			if (!dictionaryFile.exists()) {
				throw new IllegalStateException(
						"External dictionary does not exist");
			}
			String dictionaryPath = dictionaryFile.getAbsolutePath();
			return SQLiteDatabase.openDatabase(dictionaryPath, null,
					SQLiteDatabase.OPEN_READONLY);
		}
		else if (DICTIONARY_TYPE == USING_LOCAL) {
			File dictionaryFile = new File(context.getExternalFilesDir(null),
					DICTIONARY_NAME);
			if (!dictionaryFile.exists()) {
				throw new IllegalStateException(
						"Internal dictionary does not exist");
			}
			String dictionaryPath = dictionaryFile.getAbsolutePath();
			return SQLiteDatabase.openDatabase(dictionaryPath, null,
					SQLiteDatabase.OPEN_READONLY);
		}
		else {
			DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(context);
			return dictOpener.getReadableDatabase();
		}
	}

	@SuppressWarnings("unused")
	public static boolean dictionaryExists(Context context) {

		File dictionaryFile;
		if (DICTIONARY_TYPE == USING_EXTERNAL) {
			dictionaryFile = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					DICTIONARY_NAME);
		}
		else if (DICTIONARY_TYPE == USING_LOCAL) {
			dictionaryFile = new File(context.getExternalFilesDir(null),
					DICTIONARY_NAME);
		}
		else {
			return true;
		}
		return dictionaryFile.exists();
	}

	public static DictionaryEntries findWord(Context context, String word) {

		SQLiteDatabase dictionary = getDatabase(context);
		final DictionaryEntries entries = new DictionaryEntries();
		try {
			while (entries.size() == 0 && word.length() > 0) {
				Cursor c = dictionary.rawQuery(wordQuery, new String[] { word,
						katakanaToHiragana(word) });

				while (c.moveToNext()) {
					ContentValues values = new ContentValues();
					DatabaseUtils.cursorRowToContentValues(c, values);
					DictionaryEntry entry = new DictionaryEntry(values);
					entries.add(entry);
				}
				c.close();

				// now see if the word can be deinflected
				ArrayList<DeinflectorTerm> words = DeInflector.deInflect(word);
				for (DeinflectorTerm term : words) {
					c = dictionary.rawQuery(wordQuery, new String[] {
							term.word, term.word });
					while (c.moveToNext()) {
						ContentValues values = new ContentValues();
						DatabaseUtils.cursorRowToContentValues(c, values);
						DictionaryEntry entry = new DictionaryEntry(values,
								term.reason);
						entries.add(entry);
					}
					c.close();
				}

				word = word.substring(0, word.length() - 1);
			}

		}
		finally {
			if (dictionary != null) {
				dictionary.close();
			}
		}
		return entries;
	}

	// modified from
	// https://code.google.com/p/kurikosu/source/browse/trunk/kurikosu/src/main/java/org/kurikosu/transcription/Hiragana2Katakana.java
	// only attempts conversion for strings that are wholly composed of katakana
	public static String katakanaToHiragana(String word) {

		// hiragana and katakana codepoints are separted by 6 * 16
		final int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;

		String ret = "";

		for (int i = 0; i < word.length(); i++) {

			final char kana = word.charAt(i);
			final int kanaValue = (int) kana;

			// The Unicode block for (full-width) katakana is U+30A0 ... U+30FF
			// 12448...12543; the long vowel value is 12540, which we keep
			if (kanaValue == 12540) {
				ret += kana;
			}
			else if (kanaValue >= 12448 && kanaValue <= 12543) {
				ret += (char) (kanaValue - HIRAGANA_KATAKANA_UNICODE_SHIFT);
			}
			else {
				// no katakana, leave
				return word;
			}

		}
		return ret;
	}

}
