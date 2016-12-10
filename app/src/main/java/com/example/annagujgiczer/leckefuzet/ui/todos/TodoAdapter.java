package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/09.
 */

public class TodoAdapter extends RecyclerView.Adapter <TodoAdapter.TodoViewHolder> {

    private final List<TodoItem> todos;

    public TodoAdapter() {
        todos = new ArrayList<>();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        TodoViewHolder viewHolder = new TodoViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {

        TodoItem todo = todos.get(position);
        //holder.position = position;
        holder.nameTextView.setText(todo.name);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        //int position; // TODO: szerkesztéshez jó lehet még
        TextView nameTextView;

        public TodoViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.TodoItemNameTextView);
        }
    }

    public void addTodo(TodoItem todo) {
        todos.add(todo);
        notifyItemInserted(todos.size() - 1);
    }

    public void update(List<TodoItem> todoItems) {
        todos.clear();
        todos.addAll(todoItems);
        notifyDataSetChanged();
    }

    public void removeTodo(int position) {
        todos.remove(position);
        notifyItemRemoved(position);
        if (position < todos.size()) {
            notifyItemRangeChanged(position, todos.size() - position);
        }
    }
}
