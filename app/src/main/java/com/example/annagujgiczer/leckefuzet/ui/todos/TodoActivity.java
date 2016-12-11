package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.annagujgiczer.leckefuzet.AlarmReceiver;
import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;

import java.util.Calendar;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/09.
 */

public class TodoActivity extends AppCompatActivity implements NewTodoItemDialogFragment.INewTodoItemDialogListener {

    public static final String EXTRA_CATEGORY_NAME = "extra.category_name";
    public static final String CATEGORY_ID = "extra.category_id";
    private CategoryItem category;

    private PendingIntent pendingIntent;

    private RecyclerView recyclerView;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo);
        Intent i = getIntent();
        category = (CategoryItem) i.getSerializableExtra(EXTRA_CATEGORY_NAME);
        category.setId(i.getLongExtra(CATEGORY_ID, -1));

        setTitle(category.name);

        initFab();
        initRecyclerView();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTodoItemDialogFragment newTodoItemDialogFragment = new NewTodoItemDialogFragment();
                        newTodoItemDialogFragment.setCategory(category);
                        newTodoItemDialogFragment.show(getSupportFragmentManager(), NewTodoItemDialogFragment.TAG);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.TodoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TodoAdapter(recyclerView);

        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<TodoItem>>() {
            @Override
            protected List<TodoItem> doInBackground(Void... voids) {
                return category.getTodos();
            }

            @Override
            protected void onPostExecute(List<TodoItem> todoItems) {
                super.onPostExecute(todoItems);
                adapter.update(todoItems);
            }
        }.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoItemCreated(TodoItem newItem, Calendar calendar) {
        adapter.addTodo(newItem);

        Intent myIntent = new Intent(TodoActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(TodoActivity.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        // Alarm one day before
        calendar.add(Calendar.DATE, -1);
        long dayBefore = calendar.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC, dayBefore, pendingIntent);
    }
}
