package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import android.content.ContentValues;

public class DictionaryEntry {

	private final String kanji;
	private final String kana;
	private final String entry;

	public DictionaryEntry(String kanji, String kana, String entry) {

		this.kanji = kanji;
		this.kana = kana;
		this.entry = entry;
	}

	public DictionaryEntry(String kanji, String kana, String entry,
			String deinflect) {

		this(kanji, kana, "(" + deinflect + ") " + entry);
	}

	public DictionaryEntry(ContentValues values) {

		this(values.getAsString("kanji"), values.getAsString("kana"), values
				.getAsString("entry"));
	}

	public DictionaryEntry(ContentValues values, String deinflect) {

		this(values.getAsString("kanji"), values.getAsString("kana"), values
				.getAsString("entry"), deinflect);
	}

	public ContentValues toContentValues() {

		ContentValues ret = new ContentValues();
		ret.put("kanji", this.kanji);
		ret.put("kana", this.kana);
		ret.put("entry", this.entry);
		return ret;
	}

	public String toString() {

		if (this.kanji != null) {
			return kanji + "[" + kana + "]: " + entry;
		}
		return kana + ": " + entry;
	}

}
