package com.uncc.hw04;

/**
 * Homework #04
 * File Name:AppCategoriesRecyclerViewAdapter.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<AppCategoriesRecyclerViewAdapter.AppCategoriesViewHolder> {
    private final Context context;
    ArrayList<String> lstDataItem;


    public AppCategoriesRecyclerViewAdapter(ArrayList<String> data, Context context) {
        this.context = context;
        this.lstDataItem = data;
    }

    @NonNull
    @Override
    public AppCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_category_item, parent, false);
        return new AppCategoriesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AppCategoriesViewHolder holder, int position) {
        String appCategory = lstDataItem.get(position);
        holder.txtContent.setText(appCategory);
    }

    /**
     * Gets the count
     *
     * @return returns the count
     */
    @Override
    public int getItemCount() {
        if (lstDataItem != null) {
            return this.lstDataItem.size();
        } else {
            return 0;
        }
    }

    /**
     * Sets the view holder with the proper button
     */
    public class AppCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView txtContent;
        View rootView;

        public AppCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            txtContent = itemView.findViewById(R.id.txtContext);
            itemView.setOnClickListener(v -> {
                iMainActivity mainActivity = (MainActivity) context;
                mainActivity.openAppsListWindow(txtContent.getText().toString());
            });
        }
    }
}
