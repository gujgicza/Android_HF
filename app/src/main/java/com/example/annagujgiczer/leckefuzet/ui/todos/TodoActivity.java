package com.example.annagujgiczer.leckefuzet.ui.todos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.annagujgiczer.leckefuzet.R;

/**
 * Created by annagujgiczer on 2016/12/09.
 */

public class TodoActivity extends AppCompatActivity {

    private static final String TAG = "TodoActivity";
    public static final String EXTRA_CATEGORY_NAME = "extra.category_name";
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        category = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        //getSupportActionBar().setTitle(category);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
