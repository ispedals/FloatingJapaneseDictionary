package pedals.is.floatingjapanesedictionary.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryOpenHelper;

public class DictionaryOpenHelperTest extends AndroidTestCase {

	public DictionaryOpenHelperTest() {
		super();
	}

	public final void testRawQuery() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary.rawQuery(
				"select distinct kanji, kana, entry from dict", null);
		assertTrue("Raw query fetched one row", c.moveToNext());
		// fail(DatabaseUtils.dumpCurrentRowToString(c));
	}

	public final void testKanjiQuery() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary.rawQuery(
				"select distinct kanji, kana, entry from dict where kanji=?",
				new String[] { "軍車" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kanji").equals("軍車"));
	}

	public final void testKanaWithKanjiQuery() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary.rawQuery(
				"select distinct kanji, kana, entry from dict where kana=?",
				new String[] { "ぐんしゃ" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kana").equals("ぐんしゃ"));
	}

	public final void testOrQuerywithKanjiOnly() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary
				.rawQuery(
						"select distinct kanji, kana, entry from dict where kanji=? or kana=?",
						new String[] { "軍車", "軍車" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kanji").equals("軍車"));
	}

	public final void testOrQuerywithKanaOnly() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary
				.rawQuery(
						"select distinct kanji, kana, entry from dict where kanji=? or kana=?",
						new String[] { "ぐんしゃ", "ぐんしゃ" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kana").equals("ぐんしゃ"));
	}

	public final void testQuerywithBothKanjiandKana() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary
				.rawQuery(
						"select distinct kanji, kana, entry from dict where kanji=? or kana=?",
						new String[] { "軍車", "ぐんしゃ" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kanji").equals("軍車"));
		assertTrue(values.getAsString("kana").equals("ぐんしゃ"));
	}

	public final void testKanaOnlyQuery() {
		DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(getContext());
		SQLiteDatabase dictionary = dictOpener.getReadableDatabase();
		Cursor c = dictionary.rawQuery(
				"select distinct kanji, kana, entry from dict where kana=?",
				new String[] { "どーなつ" });
		c.moveToNext();
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(c, values);
		assertTrue(values.getAsString("kana").equals("どーなつ"));
	}

}
