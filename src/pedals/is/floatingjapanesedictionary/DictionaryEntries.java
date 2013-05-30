package pedals.is.floatingjapanesedictionary;

import java.util.ArrayList;

import org.json.JSONArray;

public class DictionaryEntries extends ArrayList<DictionaryEntry>{

	private static final long serialVersionUID = 2769889375257232116L;
	
	public JSONArray toJSON() {
		JSONArray ret = new JSONArray();
		
		for(DictionaryEntry entry: this){
			ret.put(entry.toJSON());
		}
		
		return ret;
	}
	
	public String toString() {
		return this.toJSON().toString();
	}

}
