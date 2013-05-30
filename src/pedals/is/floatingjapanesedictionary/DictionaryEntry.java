package pedals.is.floatingjapanesedictionary;

import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryEntry {
	
	private final String kanji;
	private final String kana;
	private final String entry;
	
	public DictionaryEntry(String kanji, String kana, String entry){
		this.kanji = kanji;
		this.kana = kana;
		this.entry = entry;
	}
	
	public JSONObject toJSON() {
		JSONObject ret = new JSONObject();
		try {
			ret.put("kanji", kanji);
			ret.put("kana", kana);
			ret.put("entry", entry);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String toString() {
		return kanji + "[" + kana + "]: " + entry;
	}

}
