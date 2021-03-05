package com.uncc.hw04;
/**
 * Homework #04
 * File Name:AppDetailsFragment.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CATEGORY = "CATEGORY";
    private static final String ARG_PARAM_TOKEN = "TOKEN";
    DataServices.App appInfo;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerViewAppGenres;
    ArrayList<String> appGenresList;
    AppGenresRecyclerViewAdapter adapter;
    private String token, category;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param appInfo This object hold the information of an app.
     * @return A new instance of fragment AppDetailsFragment.
     */
    public static AppDetailsFragment newInstance(DataServices.App appInfo) {
        AppDetailsFragment accountFragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("AppDetailInfo", appInfo);

        accountFragment.setArguments(args);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
            /*token = getArguments().getString(ARG_PARAM_TOKEN);
            category = getArguments().getString(ARG_PARAM_CATEGORY);*/
            appInfo = (DataServices.App) getArguments().getSerializable("AppDetailInfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("App Details");
        View view = inflater.inflate(R.layout.fragment_view_apps_details, container, false);

        recyclerViewAppGenres = view.findViewById(R.id.recyclerViewGenres);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAppGenres.setLayoutManager(layoutManager);

        TextView txtAppName = (TextView) view.findViewById(R.id.txtAppName);
        TextView txtArtistName = (TextView) view.findViewById(R.id.txtArtistName);
        TextView txtReleaseDate = (TextView) view.findViewById(R.id.txtReleaseDate);

        txtAppName.setText(appInfo.name);
        txtArtistName.setText(appInfo.artistName);
        txtReleaseDate.setText(appInfo.releaseDate);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAppGenres.getContext(),
                layoutManager.getOrientation());
        recyclerViewAppGenres.addItemDecoration(dividerItemDecoration);
        appGenresList = appInfo.genres;
        adapter = new AppGenresRecyclerViewAdapter(getContext(), appGenresList);
        recyclerViewAppGenres.setAdapter(adapter);

        return view;
    }


}