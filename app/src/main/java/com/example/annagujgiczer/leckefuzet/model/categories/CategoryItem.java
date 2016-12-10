package com.example.annagujgiczer.leckefuzet.model.categories;

import com.example.annagujgiczer.leckefuzet.model.todos.TodoItem;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/07.
 */

public class CategoryItem extends SugarRecord implements Serializable {

    public String name;
    public String description;


    public CategoryItem() {}

    public List<TodoItem> getTodos() {
        return TodoItem.find(TodoItem.class, "category = ?", String.valueOf(this.getId()));
    }
}
