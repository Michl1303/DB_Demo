package org.text;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NotesDetailActivity extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	
	private NotesDatabaseManager dbm; 
	private EditText description;
	private EditText content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notesdetail);
		
		description = (EditText) findViewById(R.id.editDescription);
		content = (EditText) findViewById(R.id.editContent);
		
		dbm = new NotesDatabaseManager(this);
		dbm.open();
		
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
			Long test = extras.getLong(NotesDatabaseManager.key_id);
			Toast.makeText(this, test.toString(), Toast.LENGTH_SHORT).show();
			
			String tmp = "SELECT * FROM notes  where _id= "+test+" ;";
			Cursor result = dbm.db.rawQuery(tmp, null);
			//Cursor result = dbm.db.query("notes", null, "_id==1", null, null, null, null);
			 result.moveToFirst();
			 description.setText(result.getString(1));
			 content.setText( result.getString(2));
			 result.close();
			 
			 
			 
		}
		
		
		
	      
	}
	
	public void onBtnClick(View view)
	{
		String str_description = description.getText().toString();
		String str_content = content.getText().toString();
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
			Long test = extras.getLong(NotesDatabaseManager.key_id);
			String tmp = "UPDATE notes SET description = '"+str_description+"', content= '"+str_content+"' WHERE _id= "+test+" ;";
			 dbm.db.execSQL(tmp);
		}
		else
		{		
		 String tmp = "INSERT INTO notes (description, content) VALUES ('"+str_description+"', '"+str_content+"')";
		 dbm.db.execSQL(tmp);
		}
		
		 Intent i = new Intent(this,DBDemoActivity.class);
		 startActivity(i);
		 
		//dbm.db.execSQL("INSERT INTO notes (description, content) VALUES (" +(String) description.getText() +", "+ content.getText() +")");
		//Toast.makeText(this, "sdfjsk", Toast.LENGTH_LONG).show();
	}
}
