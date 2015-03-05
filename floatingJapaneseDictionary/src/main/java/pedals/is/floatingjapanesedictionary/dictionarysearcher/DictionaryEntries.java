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
