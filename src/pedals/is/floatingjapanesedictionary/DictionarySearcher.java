package pedals.is.floatingjapanesedictionary;

import java.io.File;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DictionarySearcher {
	
	//TABLE dict (kanji TEXT, kana TEXT, entry TEXT)
	private static final String wordQuery = "select kanji, kana, entry from dict where kanji=? or kana=?";
	
	public static DictionaryEntries findWord(String word) {
		final String dictionaryPath= new File(
	    		Environment.getExternalStoragePublicDirectory(
	    				Environment.DIRECTORY_DOWNLOADS
	    			), "dict.sqlite").getAbsolutePath();
		SQLiteDatabase dictionary = null;
		DictionaryEntries entries = new DictionaryEntries();
		try {
			dictionary = SQLiteDatabase.openDatabase(dictionaryPath, null, SQLiteDatabase.OPEN_READONLY);
			Cursor c = dictionary.rawQuery(wordQuery, new String[] { word, word });
			while(c.moveToNext()) {
				DictionaryEntry entry = new DictionaryEntry(c.getString(0), c.getString(1), c.getString(2));
				entries.add(entry);
			}
		} finally { 
			if(dictionary != null){
				dictionary.close();
			}
		}
		return entries;
	}

}
