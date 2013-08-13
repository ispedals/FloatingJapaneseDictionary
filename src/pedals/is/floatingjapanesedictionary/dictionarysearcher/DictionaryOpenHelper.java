/*
 *  Source code Copyright 2013 Balloonguy
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

    -----------------------------------------------------------------------------------
    The dictionary definitions include material from the JMdict (EDICT, etc.) dictionary
    files in accordance with the licence provisions of the Electronic Dictionaries Research Group.
    See http://www.csse.monash.edu.au/~jwb/edict.html and http://www.edrdg.org/ 
 */
package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 60;
	private static final String DATABASE_NAME = "testwords.db";

	public DictionaryOpenHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table dict (kanji TEXT, kana TEXT, entry TEXT);");
		db.execSQL("insert into dict values('軍車','ぐんしゃ','(n) tank (military vehicle)');");
		db.execSQL("insert into dict values(NULL,'どーなつ','ドーナツ /(n) doughnut/(P)/');");
		db.execSQL("insert into dict values(NULL,'ちゃんばら','(n,abbr) sword fight/sword play');");
		db.execSQL("insert into dict values('踞る','うずくまる','(v5r,vi,uk) to crouch/to squat/to cower/(P)');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS dict;");
		onCreate(db);
	}

}
