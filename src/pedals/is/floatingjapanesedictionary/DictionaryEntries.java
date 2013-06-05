package pedals.is.floatingjapanesedictionary;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class DictionaryEntries extends ArrayList<DictionaryEntry> {

	private static final long serialVersionUID = 2769889375257232116L;

	public static DictionaryEntries fromJSON(String jsonString) {

		JSONArray entries;

		try {
			entries = new JSONArray(jsonString);
		}
		catch (JSONException e) {
			e.printStackTrace();
			throw new IllegalStateException(
					"DictionaryEntries JSON string not unserializable");
		}

		DictionaryEntries coll = new DictionaryEntries();

		for (int i = 0; i < entries.length(); i++) {
			DictionaryEntry entry;
			try {
				entry = new DictionaryEntry(entries.getJSONObject(i));
			}
			catch (JSONException e) {
				e.printStackTrace();
				throw new IllegalStateException(
						"DictionaryEntry JSON string not unserializable");
			}
			coll.add(entry);
		}

		return coll;
	}

	public JSONArray toJSON() {

		JSONArray ret = new JSONArray();

		for (DictionaryEntry entry : this) {
			ret.put(entry.toJSON());
		}

		return ret;
	}

	public String toString() {

		String out = "";

		for (DictionaryEntry entry : this) {
			out += entry.toString() + "\n";
		}
		return out;
	}

}
