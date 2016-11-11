package com.tobeyyang.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    int editItemIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_item );
        String contentText = getIntent().getStringExtra("contentText");
        editItemIndex = getIntent().getIntExtra( "index", -1 );
        EditText titleTextView = (EditText) findViewById( R.id.titleText );
        titleTextView.append(contentText);
        titleTextView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(titleTextView, InputMethodManager.SHOW_IMPLICIT);
    }

    public void saveItem(View v){
        EditText titleTextView = (EditText) findViewById( R.id.titleText );
        String titleText = titleTextView.getText().toString();
        Intent data = new Intent();
        data.putExtra( "updatedContent", titleText );
        data.putExtra( "editItemIndex", editItemIndex );
        setResult(RESULT_OK, data );
        finish();
    }
}
