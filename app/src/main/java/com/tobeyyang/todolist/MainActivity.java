package com.tobeyyang.todolist;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {
    ListView lvItems;
    TodoListDatabaseHelper db;
    Cursor todoCursor;
    TodoCursorAdapter todoCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        db = TodoListDatabaseHelper.getInstance(this);
        todoCursor= db.getAllTodoCursor();
        todoCursorAdapter = new TodoCursorAdapter( this, todoCursor );
        lvItems.setAdapter( todoCursorAdapter );

        setupListViewListener();
    }

    public void addItemButtonClicked(View v) {
        TodoItem todoItem = new TodoItem(-1, "", "");
        showEditDialog(todoItem);
    }

    public void updateItemClicked(TodoItem todoItem){
        showEditDialog(todoItem );
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                if(todoCursor.moveToPosition(pos)) {
                    String deletedItemId = todoCursor.getString(todoCursor.getColumnIndex("_id"));
                    db.deleteList( deletedItemId );
                    todoCursor= db.getAllTodoCursor();
                }
                todoCursorAdapter.changeCursor(todoCursor);

                return true;
            }
        });

        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                if(todoCursor.moveToPosition(pos)) {
                    String itemId = todoCursor.getString(todoCursor.getColumnIndex("_id"));
                    TodoItem todoItem = db.getList( itemId );
                    updateItemClicked(todoItem);
                }
            }
        });
    }

    private void showEditDialog(TodoItem todoItem) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editItemDialogFragment = EditItemDialogFragment.newInstance(todoItem);
        // SETS the target fragment for use later when sending results
        editItemDialogFragment.show(fm, "fragment_edit_item");
    }

    // This is called when the dialog is completed and the results have been passed
    @Override
    public void onFinishEditDialog(TodoItem todoItem) {
        if(todoItem.getID() == -1){
            db.addList( todoItem );
        }else{
            db.updateList( todoItem );
        }
        todoCursor= db.getAllTodoCursor();
        todoCursorAdapter.changeCursor(todoCursor);
    }
}