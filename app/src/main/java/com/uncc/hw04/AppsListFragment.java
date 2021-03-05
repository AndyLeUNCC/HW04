package com.uncc.hw04;
/**
 * Homework #04
 * File Name:AppsListFragment.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppsListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CATEGORY = "CATEGORY";
    private static final String ARG_PARAM_TOKEN = "TOKEN";

    LinearLayoutManager layoutManager;
    RecyclerView recyclerViewAppList;
    ArrayList<DataServices.App> appsList;
    AppListRecyclerViewAdapter myAdapter;
    private String token, category;

    public AppsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token    This object hold the information of an account.
     * @param category This object hold the information of an account.
     * @return A new instance of fragment AppCategoriesFragment.
     */
    public static AppsListFragment newInstance(String token, String category) {
        AppsListFragment accountFragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TOKEN, token);
        args.putString(ARG_PARAM_CATEGORY, category);
        accountFragment.setArguments(args);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
            token = getArguments().getString(ARG_PARAM_TOKEN);
            category = getArguments().getString(ARG_PARAM_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(category);
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);

        //mapping components on view

        recyclerViewAppList = view.findViewById(R.id.recyclerViewAppList);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAppList.setLayoutManager(layoutManager);


        /**
         * The app categories should be retrieved from DataServices by calling
         * getAppCategories method. Note that you should provide the token retrieved during
         * login/registration in order to retrieve the list of categories.
         */
        new AppListAsyncTask().execute(token, category);

        return view;
    }

    /**
     * This class should start the execution in the background
     */
    private class AppListAsyncTask extends AsyncTask<Object, String, AsyncTaskResult<ArrayList>> {

        @Override
        protected void onPreExecute() {

            //lock UI components on fragment

        }

        @Override
        protected AsyncTaskResult<ArrayList> doInBackground(Object... params) {
            AsyncTaskResult<ArrayList> asyncTaskResult = null;
            String token = (String) params[0];
            String category = (String) params[1];

            DataServices.RequestException exception = null;
            try {
                if (token != null) {
                    ArrayList<DataServices.App> data = DataServices.getAppsByCategory(token, category);
                    asyncTaskResult = new AsyncTaskResult<ArrayList>(data);
                }
            } catch (DataServices.RequestException anyError) {
                return new AsyncTaskResult<>(anyError);
            }

            return asyncTaskResult;
        }

        protected void onPostExecute(AsyncTaskResult<ArrayList> result) {
            // return a string token if login successful
            if (result.getError() != null) {
                // error handling here
                Toast.makeText(getActivity(), result.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            } else if (isCancelled()) {
                // cancel handling here
            } else {
                appsList = result.getResult();
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAppList.getContext(),
                        layoutManager.getOrientation());
                recyclerViewAppList.addItemDecoration(dividerItemDecoration);
                myAdapter = new AppListRecyclerViewAdapter(appsList, getContext());
                recyclerViewAppList.setAdapter(myAdapter);

            }

            //unlock UI components on fragment

        }
    }


}