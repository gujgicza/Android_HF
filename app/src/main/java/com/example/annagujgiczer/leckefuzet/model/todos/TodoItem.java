package com.example.annagujgiczer.leckefuzet.model.todos;

import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.orm.SugarRecord;

/**
 * Created by annagujgiczer on 2016/12/09.
 */

public class TodoItem extends SugarRecord {

    public String name;
    public Boolean hasDone;
    public String deadline;

    public CategoryItem category;

    public TodoItem() {}
}
