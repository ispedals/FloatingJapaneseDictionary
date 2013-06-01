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
	
	public DictionaryEntry(String kanji, String kana, String entry, String deinflect){
		this(kanji, kana, "("+deinflect+") " + entry);
	}
	
	public DictionaryEntry(JSONObject object) throws JSONException{
		this.kanji = object.getString("kanji");
		this.kana = object.getString("kana");
		this.entry = object.getString("entry");
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
