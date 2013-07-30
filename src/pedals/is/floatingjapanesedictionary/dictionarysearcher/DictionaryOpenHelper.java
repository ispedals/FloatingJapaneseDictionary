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
