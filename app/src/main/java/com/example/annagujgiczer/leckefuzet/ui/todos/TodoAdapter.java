package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/09.
 */

public class TodoAdapter extends RecyclerView.Adapter <TodoAdapter.TodoViewHolder> {

    private final List<TodoItem> todos;
    private RecyclerView recyclerView;

    public TodoAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        todos = new ArrayList<>();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        TodoViewHolder viewHolder = new TodoViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, final int position) {

        TodoItem todo = todos.get(position);
        holder.position = position;
        holder.nameTextView.setText(todo.name);
        holder.hasDoneCheckBox.setChecked(todo.hasDone);
        holder.deadlineTextView.setText(todo.deadline);
        formatTextView(holder.nameTextView, todo.hasDone);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                        .make(recyclerView, "Biztosan törölni akarod?", Snackbar.LENGTH_LONG)
                        .setAction("Nem", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setAction("Igen", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeTodo(position);
                            }
                        })
                        .show();
            }
        });
    }

    private void formatTextView(TextView nameTextView, boolean hasDone) {
        if(hasDone) {
            nameTextView.setTextColor(Color.GRAY);
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            nameTextView.setTextColor(Color.DKGRAY);
            nameTextView.setPaintFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        int position;
        TextView nameTextView;
        TextView deadlineTextView;
        CheckBox hasDoneCheckBox;
        Button removeButton;

        public TodoViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.TodoItemNameTextView);
            hasDoneCheckBox = (CheckBox) itemView.findViewById(R.id.TodoItemCheckBox);
            deadlineTextView = (TextView) itemView.findViewById(R.id.TodoItemDeadlineTextView);
            removeButton = (Button) itemView.findViewById(R.id.TodoItemRemoveButton);

            hasDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateTodoItem(position, nameTextView, isChecked);
                }
            });
        }
    }

    private void updateTodoItem(int position, TextView nameTextView, boolean isChecked) {
        TodoItem todo = todos.get(position);
        todo.hasDone = isChecked;
        todo.save();
        formatTextView(nameTextView,  isChecked);
    }

    public void addTodo(TodoItem todo) {
        todos.add(todo);
        notifyItemInserted(todos.size() - 1);
    }

    public void update(List<TodoItem> todoItems) {
        todos.clear();
        Collections.sort(todoItems, new TodoComparator());
        todos.addAll(todoItems);
        notifyDataSetChanged();
    }

    public void removeTodo(int position) {
        TodoItem removed = todos.remove(position);
        removed.delete();
        notifyItemRemoved(position);
        if (position < todos.size()) {
            notifyItemRangeChanged(position, todos.size() - position);
        }
    }

    private class TodoComparator implements Comparator<TodoItem> {
        @Override
        public int compare(TodoItem todo1, TodoItem todo2) {
            if (todo1.hasDone)
                return 1;
            else if (todo2.hasDone)
                return -1;
            return 0;
        }
    }
}
