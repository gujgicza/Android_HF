package com.example.annagujgiczer.leckefuzet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by annagujgiczer on 2016/12/07.
 */

public class NewCategoryItemDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = "NewCategoryItemDialogFragment";

    private EditText nameEditText;
    private EditText descriptionEditText;

    public interface INewCategoryItemDialogListener {
        void onCategoryItemCreated(CategoryItem newItem);
    }

    private INewCategoryItemDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof INewCategoryItemDialogListener) {
            listener = (INewCategoryItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the INewCategoryItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.new_category_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isValid()) {
                                    listener.onCategoryItemCreated(getCategoryItem());
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private CategoryItem getCategoryItem() {
        CategoryItem CategoryItem = new CategoryItem();
        CategoryItem.name = nameEditText.getText().toString();
        CategoryItem.description = descriptionEditText.getText().toString();
        return CategoryItem;
    }

    private View getContentView() {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_category_item, null);
        nameEditText = (EditText) contentView.findViewById(R.id.CategoryItemNameEditText);
        descriptionEditText = (EditText) contentView.findViewById(R.id.CategoryItemDescriptionEditText);

        return contentView;
    }

}
