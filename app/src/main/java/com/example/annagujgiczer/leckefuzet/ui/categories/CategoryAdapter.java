package com.example.annagujgiczer.leckefuzet.ui.categories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.annagujgiczer.leckefuzet.model.categories.CategoryItem;
import com.example.annagujgiczer.leckefuzet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by annagujgiczer on 2016/12/07.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<CategoryItem> categories;
    private OnCategorySelectedListener selectedListener;

    public CategoryAdapter(OnCategorySelectedListener selectedListener) {
        categories = new ArrayList<>();
        this.selectedListener = selectedListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        CategoryItem category = categories.get(position);
        holder.position = position;
        holder.nameTextView.setText(category.name);
        holder.descriptionTextView.setText(category.description);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        int position;
        Button removeButton;

        TextView nameTextView;
        TextView descriptionTextView;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.CategoryItemNameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.CategoryItemDescriptionTextView);

            removeButton = (Button) itemView.findViewById( R.id.CategoryItemRemoveButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedListener != null) {
                        selectedListener.onCategorySelected(categories.get(position));
                    }
                }
            });
        }
    }

    public void addCategory(CategoryItem category) {
        categories.add(category);
        notifyItemInserted(categories.size() - 1);
    }

    public void update(List<CategoryItem> categoryItems) {
        categories.clear();
        categories.addAll(categoryItems);
        notifyDataSetChanged();
    }

    public void removeCategory(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
        if (position < categories.size()) {
            notifyItemRangeChanged(position, categories.size() - position);
        }
    }
}
