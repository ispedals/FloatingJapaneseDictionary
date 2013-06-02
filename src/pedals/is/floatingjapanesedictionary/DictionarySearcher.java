package pedals.is.floatingjapanesedictionary;

import java.io.File;
import java.util.ArrayList;

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
		final DictionaryEntries entries = new DictionaryEntries();
		try {
			dictionary = SQLiteDatabase.openDatabase(dictionaryPath, null, SQLiteDatabase.OPEN_READONLY);
			
			while(entries.size() == 0 && word.length() > 0){
				Cursor c = dictionary.rawQuery(wordQuery, new String[] { word, katakanaToHiragana(word) });
				
				while(c.moveToNext()) {
					DictionaryEntry entry = new DictionaryEntry(c.getString(0), c.getString(1), c.getString(2));
					entries.add(entry);
				}
				c.close();
				
				//now see if the word can be deinflected
				ArrayList<DeinflectorTerm> words = DeInflector.deInflect(word);
				for(DeinflectorTerm term: words){
					c = dictionary.rawQuery(wordQuery, new String[] { term.word, term.word });
					while(c.moveToNext()) {
						DictionaryEntry entry = new DictionaryEntry(c.getString(0), c.getString(1), c.getString(2), term.reason);
						entries.add(entry);
					}
					c.close();
				}
				
				word = word.substring(0, word.length()-1);
			}
			
		} 
		finally { 
			if(dictionary != null){
				dictionary.close();
			}
		}
		return entries;
	}
	
	//modified from https://code.google.com/p/kurikosu/source/browse/trunk/kurikosu/src/main/java/org/kurikosu/transcription/Hiragana2Katakana.java
	// only attempts conversion for strings that are wholly composed of katakana
	private static String katakanaToHiragana(String word) {

		//hiragana and katakana codepoints are separted by 6 * 16
		final int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;
		
		//contains a non-katakana character, 
		//if(word.matches"\\P{IsKatakana}")) {
		//	return word;
		//}
		
		String ret = "";

        for (int i = 0; i < word.length(); i++) {

                final char kana = word.charAt(i);
                final int kanaValue = (int) kana;
                
                // The Unicode block for (full-width) katakana is U+30A0 ... U+30FF
                // 12448...12543; the long vowel value is 12540, which we keep
                if (kanaValue >= 12448 && kanaValue <= 12543){
                		ret += (char) (kanaValue - HIRAGANA_KATAKANA_UNICODE_SHIFT);
        		}
                else if (kanaValue == 12540 ){
                	ret +=kana;
                }
                else {
                	//no katakana, leave
                	return word;
                }
        
        }
        return ret;
	}


}
