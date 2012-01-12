package org.text;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesDatabaseManager {
	
	private Context context;
	private NotesDatabaseHelper dbHelper;
	public SQLiteDatabase db;
	
	// Datenbank-Felder
	private static final String db_table       = "notes";
	public static final String key_id          = "_id";
	public static final String key_description = "description";
	public static final String key_content     = "content";

	public NotesDatabaseManager(Context context) {
		this.context = context;
	}
	
	public NotesDatabaseManager open() throws SQLException {
		dbHelper = new NotesDatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public boolean deleteNote(long id) {
		return db.delete(db_table, key_id + "=" + id, null) > 0;
	}
	
	public boolean updateNote(long id, String description, String content) {
		ContentValues values = createContentValues(description, content);
		if (values == null)
			return false;
		
		return db.update(db_table, values, key_id + "=" + id, null) > 0;
	}
	
	public Cursor fetchAllNotes() {
		return db.query(db_table, new String[] {key_id, key_description, key_content}, null, null, null, null, null, null);
	}

	private ContentValues createContentValues(String description, String content) {
		if(description == null || description == "")
			return null;
		
		ContentValues values = new ContentValues();
		values.put(key_description, description);
		values.put(key_content, content);
		return values;
	}
	
}
