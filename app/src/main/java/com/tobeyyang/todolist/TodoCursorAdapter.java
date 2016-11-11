package com.tobeyyang.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;



public class TodoCursorAdapter extends CursorAdapter {

    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate( R.layout.item_todo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView itemTitle = (TextView) view.findViewById(R.id.itemTitle);
        TextView itemDate = (TextView) view.findViewById(R.id.itemDate);
        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        // Populate fields with extracted properties
        itemTitle.setText(title);
        itemDate.setText(date);
    }
}
