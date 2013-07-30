package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import java.util.ArrayList;

import android.content.ContentValues;
import android.os.Parcelable;

public class DictionaryEntries extends ArrayList<DictionaryEntry> {

	private static final long serialVersionUID = 2769889375257232116L;

	public static DictionaryEntries fromParcelable(
			ArrayList<Parcelable> parcelableEntries) {

		DictionaryEntries coll = new DictionaryEntries();
		for (Parcelable entry : parcelableEntries) {
			coll.add(new DictionaryEntry((ContentValues) entry));
		}
		return coll;
	}

	public ArrayList<ContentValues> toParcelableContentValues() {

		ArrayList<ContentValues> ret = new ArrayList<ContentValues>();
		for (DictionaryEntry entry : this) {
			ContentValues value = entry.toContentValues();
			ret.add(value);
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
