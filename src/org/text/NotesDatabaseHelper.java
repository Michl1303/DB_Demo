package org.text;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDatabaseHelper extends SQLiteOpenHelper {

	private static final int db_version = 1;
	private static final String db_name = "notes.db";
	
	public NotesDatabaseHelper(Context context) {
		super (context, db_name, null, db_version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		NotesTable.onCreate(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		NotesTable.onUpgrade(db, oldVersion, newVersion);
	}
}
