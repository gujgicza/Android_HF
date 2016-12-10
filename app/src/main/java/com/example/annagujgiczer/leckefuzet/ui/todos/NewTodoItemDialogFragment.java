package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;

import java.util.Calendar;
import java.util.Date;


public class NewTodoItemDialogFragment extends AppCompatDialogFragment implements View.OnClickListener {

    public static final String TAG = "NewTodoItemDialogFragment";

    private EditText nameEditText;
    private CategoryItem category;

    private Button datePickerButton, timePickerButton;
    private EditText dateText, timeText;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar dateTime;

    public void setCategory(CategoryItem category) {
        this.category = category;
    }

    public interface INewTodoItemDialogListener {
        void onTodoItemCreated(TodoItem newItem, Calendar calendar);
    }

    private NewTodoItemDialogFragment.INewTodoItemDialogListener newTodoListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewTodoItemDialogFragment.INewTodoItemDialogListener) {
            newTodoListener = (NewTodoItemDialogFragment.INewTodoItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the INewTodoItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.new_todo_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isValid()) {
                                    newTodoListener.onTodoItemCreated(getTodoItem(), dateTime);
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private TodoItem getTodoItem() {
        TodoItem todoItem = new TodoItem();
        todoItem.name = nameEditText.getText().toString();
        todoItem.category = category;
        todoItem.hasDone = false;
        todoItem.deadline = (dateText.getText().toString() + " " + timeText.getText().toString());

        todoItem.save();
        return todoItem;
    }

    private View getContentView() {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_todo_item, null);
        nameEditText = (EditText) contentView.findViewById(R.id.TodoItemNameEditText);
        datePickerButton = (Button) contentView.findViewById(R.id.btn_date);
        timePickerButton = (Button) contentView.findViewById(R.id.btn_time);
        dateText = (EditText) contentView.findViewById(R.id.in_date);
        timeText = (EditText) contentView.findViewById(R.id.in_time);

        datePickerButton.setOnClickListener(this);
        timePickerButton.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View v) {

        if (v == datePickerButton) {

            // Get Current Date
            dateTime = Calendar.getInstance();
            mYear = dateTime.get(Calendar.YEAR);
            mMonth = dateTime.get(Calendar.MONTH);
            mDay = dateTime.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            dateText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            dateTime.set(Calendar.YEAR, year);
                            dateTime.set(Calendar.MONTH, monthOfYear);
                            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timePickerButton) {

            // Get Current Time
            dateTime = Calendar.getInstance();
            mHour = dateTime.get(Calendar.HOUR_OF_DAY);
            mMinute = dateTime.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timeText.setText(hourOfDay + ":" + minute);
                            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            dateTime.set(Calendar.MINUTE, minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
