package com.tobeyyang.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class EditItemDialogFragment extends DialogFragment {
    private EditText mEditTitleText;
    private DatePicker mDatePicker;
    private int id;
    public EditItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditItemDialogFragment newInstance(TodoItem todoItem) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", todoItem.getID());
        args.putString("title", todoItem.getTitle());
        args.putString("date", todoItem.getDate());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditTitleText = (EditText) view.findViewById(R.id.txt_title);
        id = getArguments().getInt("id");
        mDatePicker = (DatePicker) view.findViewById( R.id.datePicker );
        mDatePicker.setSpinnersShown( true );
        mDatePicker.setCalendarViewShown( false );
        Button mSaveButton = (Button) view.findViewById( R.id.saveButton );
        mSaveButton.setOnClickListener( new View.OnClickListener(){
            @Override public void onClick(View v){
                EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                String date =  Integer.toString(mDatePicker.getYear()) + '/' + Integer.toString(mDatePicker.getMonth()+1) + '/' +
                        Integer.toString(mDatePicker.getDayOfMonth());
                TodoItem todoItem = new TodoItem(id ,mEditTitleText.getText().toString(), date);
                listener.onFinishEditDialog( todoItem );
                dismiss();
            }
        } );

        // Fetch arguments from bundle and set title and date
        if(id != -1){
            String title = getArguments().getString("title", "");
            String date = getArguments().getString( "date", "" );
            String[] separated = date.split("/");
            mDatePicker.updateDate(Integer.parseInt(separated[0]),Integer.parseInt(separated[1])-1,Integer.parseInt(separated[2]));
            mEditTitleText.setText( title );
        }
    }

    // Defines the listener interface
    public interface EditItemDialogListener {
        void onFinishEditDialog(TodoItem todoItem);
    }

}
