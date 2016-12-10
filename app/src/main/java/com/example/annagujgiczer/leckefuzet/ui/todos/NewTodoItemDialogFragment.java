package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;


public class NewTodoItemDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = "NewTodoItemDialogFragment";

    private EditText nameEditText;
    private CategoryItem category;

    public void setCategory(CategoryItem category) {
        this.category = category;
    }

    public interface INewTodoItemDialogListener {
        void onTodoItemCreated(TodoItem newItem);
    }

    private NewTodoItemDialogFragment.INewTodoItemDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewTodoItemDialogFragment.INewTodoItemDialogListener) {
            listener = (NewTodoItemDialogFragment.INewTodoItemDialogListener) activity;
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
                                    listener.onTodoItemCreated(getTodoItem());
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

        todoItem.save();
        return todoItem;
    }

    private View getContentView() {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_todo_item, null);
        nameEditText = (EditText) contentView.findViewById(R.id.TodoItemNameEditText);
        return contentView;
    }
}
