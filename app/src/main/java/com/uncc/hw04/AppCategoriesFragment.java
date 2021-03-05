package com.uncc.hw04;
/**
 * Homework #04
 * File Name:AppCategoriesFragment.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppCategoriesFragment extends Fragment {

    Button btnLogOut;
    TextView tvWelcomeUser;
    LinearLayoutManager layoutManager;

    RecyclerView recyclerViewCat;
    ArrayList<String> appCategoriesList;
    AppCategoriesRecyclerViewAdapter adapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";
    private static final String ARG_PARAM_ACCOUNT_TOKEN = "TOKEN";


    private DataServices.Account account;
    private String token;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account This object hold the information of an account.
     * @return A new instance of fragment AppCategoriesFragment.
     */
    public static AppCategoriesFragment newInstance(String token, DataServices.Account account) {
        AppCategoriesFragment accountFragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        args.putString(ARG_PARAM_ACCOUNT_TOKEN, token);
        accountFragment.setArguments(args);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
            token = getArguments().getString(ARG_PARAM_ACCOUNT_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("App Categories");
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        //mapping components on view and show data from account variable.
        btnLogOut = view.findViewById(R.id.btnLogOut);
        tvWelcomeUser = view.findViewById(R.id.txtWelcomeUser);
        recyclerViewCat = view.findViewById(R.id.recyclerViewCat);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerViewCat.setLayoutManager(layoutManager);
        /**
         * Gets the account for DataServices and updates the greeting with it
         */
        new GetAccountAsyncTask().execute(token);
        new AppCategoriesAsyncTask().execute(token);

        btnLogOut.setOnClickListener(v -> {
            //delete the account stored in the Main Activity
            iMainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.deleteAccount(null);

            //replace this fragment with the Login Fragment
            mainActivity.openLoginWindow();
        });

        return view;
    }

    /**
     * This class should start the execution in the background
     */
    private class GetAccountAsyncTask extends AsyncTask<Object, String, AsyncTaskResult<DataServices.Account>> {

        @Override
        protected void onPreExecute() {

            //lock UI components on fragment
            buttonLocker(false);

        }

        @Override
        protected AsyncTaskResult<DataServices.Account> doInBackground(Object... params) {
            String token;

            AsyncTaskResult<DataServices.Account> accountAsyncTaskResult = null;
            token = (String) params[0];
            DataServices.RequestException exception = null;
            try {
                if (token != null) {
                    account = DataServices.getAccount(token);
                    accountAsyncTaskResult = new AsyncTaskResult<>(account);
                }
            } catch (DataServices.RequestException anyError) {
                return new AsyncTaskResult<>(anyError);
            }

            //publishProgress(token);
            return accountAsyncTaskResult;
        }

        protected void onPostExecute(AsyncTaskResult<DataServices.Account> result) {
            // return a string token if login successful
            if (result.getError() != null) {
                // error handling here
                Toast.makeText(getActivity(), result.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            } else if (isCancelled()) {
                // cancel handling here
            } else {
                account = result.getResult();
                tvWelcomeUser.setText("Welcome " + account.getName());
            }

            //unlock UI components on fragment
            buttonLocker(true);
        }
    }


    /**
     * This class should start the execution in the background
     */
    private class AppCategoriesAsyncTask extends AsyncTask<Object, String, AsyncTaskResult<ArrayList>> {

        @Override
        protected AsyncTaskResult<ArrayList> doInBackground(Object... params) {
            AsyncTaskResult<ArrayList> asyncTaskResult = null;
            String token = (String) params[0];
            DataServices.RequestException exception = null;
            try {
                if (token != null) {
                    ArrayList<String> data = DataServices.getAppCategories(token);
                    asyncTaskResult = new AsyncTaskResult<ArrayList>(data);
                }
            } catch (DataServices.RequestException anyError) {
                return new AsyncTaskResult<>(anyError);
            }

            //publishProgress(token);
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
                appCategoriesList = result.getResult();
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewCat.getContext(),
                        layoutManager.getOrientation());
                recyclerViewCat.addItemDecoration(dividerItemDecoration);
                adapter = new AppCategoriesRecyclerViewAdapter(appCategoriesList, getContext());
                recyclerViewCat.setAdapter(adapter);

            }

            //unlock the UI components
            //lock UI components on MainActivity

        }
    }

    /**
     * Locks or Unlocks button
     *
     * @param lock lock or unlock
     */
    public void buttonLocker(Boolean lock) {
        btnLogOut.setEnabled(lock);
    }


}