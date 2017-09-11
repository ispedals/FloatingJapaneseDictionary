/*
 *  Copyright 2013 Balloonguy
 *
 *  Searching logic strongly inspired by Rikaichan 2.0.7
 *
	---

	Rikaichan
	Copyright (C) 2005-2012 Jonathan Zarate
	http://www.polarcloud.com/

	---

	Originally based on RikaiXUL 0.4 by Todd Rudick
	http://www.rikai.com/
	http://rikaixul.mozdev.org/

	---
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
	private static final int DICTIONARY_TYPE = USING_LOCAL;
	public static final String DICTIONARY_NAME = "dict.sqlite";

	// TABLE dict (kanji TEXT, kana TEXT, entry TEXT)

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
		else if (DICTIONARY_TYPE == USING_BUILT_IN){
			DictionaryOpenHelper dictOpener = new DictionaryOpenHelper(context);
			return dictOpener.getReadableDatabase();
		}
		else {
            throw new IllegalStateException(
                    "Unhandled Dictionary Type");
        }
	}

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
				Cursor c = doQuery(dictionary, word);

				while (c.moveToNext()) {
					ContentValues values = new ContentValues();
					DatabaseUtils.cursorRowToContentValues(c, values);
					DictionaryEntry entry = new DictionaryEntry(values);
					entries.add(entry);
				}
				c.close();

				ArrayList<DeinflectorTerm> words = DeInflector.deInflect(word);
				for (DeinflectorTerm term : words) {
					c = doQuery(dictionary, term.word);
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

	private static Cursor doQuery(SQLiteDatabase dictionary, String word) {

		String wordQuery = "select distinct kanji, kana, entry from dict where";
		if (isHiragana(word)) {
			wordQuery += " kana=?";
			return dictionary.rawQuery(wordQuery, new String[] { word });
		}
		String katakanaReading = katakanaToHiragana(word);

		// was not katakana
		if (katakanaReading.equals(word)) {
			wordQuery += " kanji=?";
			return dictionary.rawQuery(wordQuery, new String[] { word });
		}

		// is katakana
		wordQuery += " kanji=? or kana=?";
		return dictionary.rawQuery(wordQuery, new String[] { word,
				katakanaReading });
	}

	public static boolean isHiragana(String word) {

		// hiragana block is U+3040..U+309F or
		// 12352...12447. We also treat the full vowel at 12540 as hiragana
		for (int i = 0; i < word.length(); i++) {

			char kana = word.charAt(i);
			int kanaValue = (int) kana;

			if (kanaValue < 12352 || (kanaValue > 12447 && kanaValue != 12540)) {
				return false;
			}
		}
		return true;
	}

	// key insight from
	// https://code.google.com/p/kurikosu/source/browse/trunk/kurikosu/src/main/java/org/kurikosu/transcription/Hiragana2Katakana.java
	// that hiragana and katakana codepoints are separated by 2 * 16
	//
	// only attempts conversion for strings that are wholly composed of katakana
	public static String katakanaToHiragana(String word) {

		// hiragana and katakana codepoints are separated by 6 * 16
		int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;

		String ret = "";

		for (int i = 0; i < word.length(); i++) {

			char kana = word.charAt(i);
			int kanaValue = (int) kana;

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
