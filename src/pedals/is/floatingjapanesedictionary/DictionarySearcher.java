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
		
		word = katakanaToHiragana(word);
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
	
	//modified from https://code.google.com/p/kurikosu/source/browse/trunk/kurikosu/src/main/java/org/kurikosu/transcription/Hiragana2Katakana.java
	private static String katakanaToHiragana(String kana) {

		final int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;
		
		String ret = "";

        for (int index = 0; index < kana.length(); index++) {

                char letter = kana.charAt(index);
                int letterValue = (int) letter;
                
                // The Unicode block for (full-width) katakana is U+30A0 ... U+30FF
                // 12448...12543; the long vowel value is 12540, which we keep
                if (letterValue >= 12448 && letterValue <= 12543 && letterValue != 12540 ){
                		ret += (char) (letterValue - HIRAGANA_KATAKANA_UNICODE_SHIFT);
        		}
                else {
                	ret +=letter;
                }
        
        }
        return ret;
	}


}
