package com.tobeyyang.todolist;

import java.util.Date;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.thickness;

/**
 * Created by tobeyyang on 11/10/16.
 */

public class TodoItem {
    int _id;
    String _title;
    String _date;
    // Empty constructor
    public TodoItem(){

    }
    // constructor
    public TodoItem(int id, String title, String date){
        this._id = id;
        this._title = title;
        this._date = date;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting ID
    public void setID(int id){
        this._id = id;
    }

    //getting title
    public String getTitle(){
        return this._title;
    }

    // setting title
    public void setTitle(String title){
        this._title = title;
    }

    //getting date
    public String getDate(){
        return this._date;
    }

    //setting date
    public void setDate(String date){
        this._date = date;
    }
}