package com.uncc.hw04;
/**
 * Homework #04
 * File Name:AppGenresRecyclerViewAdapter.java
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

public class AppGenresRecyclerViewAdapter extends RecyclerView.Adapter<AppGenresRecyclerViewAdapter.AppGenresViewHolder> {
    private final Context context;
    ArrayList<String> lstGenres;

    public AppGenresRecyclerViewAdapter(Context context, ArrayList<String> lstGenres) {
        this.context = context;
        this.lstGenres = lstGenres;
    }

    @NonNull
    @Override
    public AppGenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_app_genre_item, parent, false);
        return new AppGenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppGenresViewHolder holder, int position) {
        String appCategory = lstGenres.get(position);
        holder.txtGenre.setText(appCategory);
    }

    @Override
    public int getItemCount() {
        if (lstGenres != null) {
            return this.lstGenres.size();
        } else {
            return 0;
        }
    }

    /**
     * Sets the view holder for each item
     */
    public static class AppGenresViewHolder extends RecyclerView.ViewHolder {
        TextView txtGenre;
        View rootView;

        public AppGenresViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            txtGenre = itemView.findViewById(R.id.txtGenre);
        }
    }
}
