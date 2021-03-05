package com.uncc.hw04;
/**
 * Homework #04
 * File Name:AppListRecyclerViewAdapter.java
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

public class AppListRecyclerViewAdapter extends RecyclerView.Adapter<AppListRecyclerViewAdapter.AppListViewHolder> {
    private final Context context;
    ArrayList<DataServices.App> lstAppsList;

    public AppListRecyclerViewAdapter(ArrayList<DataServices.App> data, Context context) {
        this.context = context;
        this.lstAppsList = data;
    }

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_apps_item, parent, false);
        return new AppListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppListViewHolder holder, int position) {
        DataServices.App app = lstAppsList.get(position);
        holder.txtAppName.setText(app.name);
        holder.txtArtistName.setText(app.artistName);
        holder.txtReleaseDate.setText(app.releaseDate);
    }

    @Override
    public int getItemCount() {
        if (lstAppsList != null) {
            return this.lstAppsList.size();
        } else {
            return 0;
        }
    }


    /**
     * Sets the view holder for each item with proper button
     */
    public class AppListViewHolder extends RecyclerView.ViewHolder {
        TextView txtAppName;
        TextView txtArtistName;
        TextView txtReleaseDate;
        View rootView;

        public AppListViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            txtAppName = itemView.findViewById(R.id.txtAppName);
            txtArtistName = itemView.findViewById(R.id.txtArtistName);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            itemView.setOnClickListener(v -> {

                iMainActivity mainActivity = (MainActivity) context;
                //Looks for the right reference in the data
                for (int i = 0; i < lstAppsList.size(); i++) {
                    if (lstAppsList.get(i).name.contentEquals(txtAppName.getText()) && lstAppsList.get(i).artistName.contentEquals(txtArtistName.getText())
                            && lstAppsList.get(i).releaseDate.contentEquals(txtReleaseDate.getText())) {
                        mainActivity.openAppWindow(lstAppsList.get(i));
                    }
                }


            });
        }
    }

}
