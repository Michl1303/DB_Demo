package org.text;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DBDemoActivity extends ListActivity {
	private NotesDatabaseManager dbAdapter;
	private Cursor cursor;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.notesmenue, menu);
		return true;
		
	}
	
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private static final int DELETE_ID= Menu.FIRST + 1;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent i = new Intent(this, NotesDetailActivity.class);
		i.putExtra(NotesDatabaseManager.key_id, id);
		
		startActivityForResult(i, ACTIVITY_CREATE);		
		//Toast.makeText(this,"Hallo!", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.insert:
			Intent i = new Intent(this, NotesDetailActivity.class);
			startActivityForResult(i, ACTIVITY_EDIT);		
			//Toast.makeText(this,"Hallo!", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, "Delete");
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteslist);
        dbAdapter = new NotesDatabaseManager(this);
        dbAdapter.open();
        fillData();
        registerForContextMenu(getListView());
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case DELETE_ID:
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	dbAdapter.deleteNote(info.id);
    	fillData();
    	return true;
    	}
    	return super.onContextItemSelected(item);
    }
    
    private void fillData() {
		cursor = dbAdapter.fetchAllNotes();
		startManagingCursor(cursor);
		
		String[] fields = new String[] {NotesDatabaseManager.key_description};
		int[] toGui = new int[] {R.id.label};
		
		SimpleCursorAdapter notesAdapter = new SimpleCursorAdapter(
				this, 
				R.layout.notesrow, 
				cursor, 
				fields, 
				toGui
			);
		setListAdapter(notesAdapter);
	}

	@Override
    protected void onDestroy() {
    		super.onDestroy();
    		if (dbAdapter != null)
    			dbAdapter.close();
    }
}