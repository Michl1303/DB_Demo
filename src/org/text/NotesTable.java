package org.text;

import android.database.sqlite.SQLiteDatabase;

public class NotesTable {

	private static final String db_create = "CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT NOT NULL, content TEXT)";
	
	public static void onCreate (SQLiteDatabase db) {
		db.execSQL(db_create);
		db.execSQL("INSERT INTO notes (description, content) VALUES ('Foo', 'Bar')");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Hier bitte im echten Leben die Datenbank nicht 
		// einfach lšschen, es sei denn, wir wollen unsere
		// User vergraulen
		db.execSQL("DROP TABLE IF EXISTS notes");
		onCreate(db);
	}	
}
