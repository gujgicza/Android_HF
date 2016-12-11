package com.example.annagujgiczer.leckefuzet.ui.categories;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.example.annagujgiczer.leckefuzet.R;
import com.example.annagujgiczer.leckefuzet.ui.todos.TodoActivity;

import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/07.
 */

public class CategoryActivity extends AppCompatActivity
        implements NewCategoryItemDialogFragment.INewCategoryItemDialogListener {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFab();
        initRecyclerView();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewCategoryItemDialogFragment().show(getSupportFragmentManager(),
                        NewCategoryItemDialogFragment.TAG);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setAdapter() {
        adapter = new CategoryAdapter(recyclerView, new OnCategorySelectedListener() {
            @Override
            public void onCategorySelected(CategoryItem category) {
                createIntent(category);
            }
        });
    }

    private void createIntent(CategoryItem category) {
        Intent showTodosIntent = new Intent();
        showTodosIntent.setClass(CategoryActivity.this,
                TodoActivity.class);
        showTodosIntent.putExtra(
                TodoActivity.EXTRA_CATEGORY_NAME, category);
        showTodosIntent.putExtra(TodoActivity.CATEGORY_ID, category.getId());
        startActivity(showTodosIntent);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<CategoryItem>>() {
            @Override
            protected List<CategoryItem> doInBackground(Void... voids) {
                return CategoryItem.listAll(CategoryItem.class);
            }

            @Override
            protected void onPostExecute(List<CategoryItem> CategoryItems) {
                super.onPostExecute(CategoryItems);
                adapter.update(CategoryItems);
            }
        }.execute();
    }

    @Override
    public void onCategoryItemCreated(CategoryItem newItem) {
        adapter.addCategory(newItem);
    }
}
