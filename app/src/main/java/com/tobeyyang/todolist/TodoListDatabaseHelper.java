package com.tobeyyang.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by tobeyyang on 11/7/16.
 */

public class TodoListDatabaseHelper extends SQLiteOpenHelper {

    private static TodoListDatabaseHelper dbInstance;

    private static final String DATABASE_NAME = "todoListDatabase";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_TODOLIST = "todolists";

    private static final String KEY_TODOLIST_ID = "id";
    private static final String KEY_TODOLIST_TITLE = "title";
    private static final String KEY_TODOLIST_DATE = "date";

    public static synchronized TodoListDatabaseHelper getInstance(Context context) {
        /*
        Use the application context, which will ensure that you
        don't accidentally leak an Activity's context.
        See this article for more information: http://bit.ly/6LRzfx
        */
        if (dbInstance == null) {
            dbInstance = new TodoListDatabaseHelper(context);
        }
        return dbInstance;
    }

    private TodoListDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_TODOLIST +
                "(" +
                KEY_TODOLIST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODOLIST_TITLE + " TEXT," +
                KEY_TODOLIST_DATE + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);
            onCreate(db);
        }
    }

    // Insert an item into the database
    public void addList(TodoItem todoItem) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODOLIST_DATE, todoItem.getDate() );
            values.put(KEY_TODOLIST_TITLE, todoItem.getTitle());
            db.insertOrThrow(TABLE_TODOLIST, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add list to database");
        } finally {
            db.endTransaction();
        }
    }

    public int updateList(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODOLIST_TITLE, todoItem.getTitle());
        values.put(KEY_TODOLIST_DATE, todoItem.getDate());
        // updating row
        return db.update(TABLE_TODOLIST, values, KEY_TODOLIST_ID + " = ?",
                new String[] { String.valueOf(todoItem.getID()) });
    }
    public TodoItem getList(String itemId){
        String selectQuery = "SELECT " + KEY_TODOLIST_ID + " AS _id,"+ KEY_TODOLIST_TITLE + "," + KEY_TODOLIST_DATE + " FROM " + TABLE_TODOLIST
                + " WHERE " + KEY_TODOLIST_ID + " = " + itemId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        TodoItem todoItem = new TodoItem();
        if(cursor.moveToFirst()){
            todoItem.setID( cursor.getInt(0));
            todoItem.setTitle(cursor.getString(1));
            todoItem.setDate(cursor.getString(2));
        }
        return todoItem ;
    }

    public void deleteList(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODOLIST, KEY_TODOLIST_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public Cursor getAllTodoCursor(){
        // Select All Query
        String selectQuery = "SELECT " + KEY_TODOLIST_ID + " AS _id,"+ KEY_TODOLIST_TITLE + ", " + KEY_TODOLIST_DATE + " FROM " + TABLE_TODOLIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

}
