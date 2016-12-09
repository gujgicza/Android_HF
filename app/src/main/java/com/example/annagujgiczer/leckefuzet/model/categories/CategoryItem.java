package com.example.annagujgiczer.leckefuzet.model.categories;

import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/07.
 */

public class CategoryItem extends SugarRecord {

    public String name;
    public String description;

    public List<TodoItem> todos;

    public CategoryItem() {
        todos = new ArrayList<>();
    }

}
