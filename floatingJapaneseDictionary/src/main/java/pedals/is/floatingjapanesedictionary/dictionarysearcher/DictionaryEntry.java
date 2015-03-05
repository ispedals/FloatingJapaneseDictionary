/*
 *  Copyright 2013 Balloonguy
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
