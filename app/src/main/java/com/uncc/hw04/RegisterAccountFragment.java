package com.uncc.hw04;
/**
 * Homework #04
 * File Name:RegisterAccountFragment.java
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
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * RegisterAccountFragment fragment
 * This fragment should allow a user to create a new account.
 */
public class RegisterAccountFragment extends Fragment {

    Button btnSubmit, btnCancel;
    EditText edtName, edtEmail, edtPassword;
    String token;


    public RegisterAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountFragment.
     */
    public static RegisterAccountFragment newInstance() {

        return new RegisterAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Register Account");

        // Inflate the layout for this fragment
        View registerAccountView = inflater.inflate(R.layout.fragment_register_account, container, false);

        btnSubmit = registerAccountView.findViewById(R.id.btnSubmit);
        btnCancel = registerAccountView.findViewById(R.id.btnCancel);

        edtName = registerAccountView.findViewById(R.id.edtName);
        edtEmail = registerAccountView.findViewById(R.id.edtEmailAddress);
        edtPassword = registerAccountView.findViewById(R.id.edtPassword);

        btnSubmit.setOnClickListener(v -> {
            //Toast.makeText(getActivity(), "Fragment Login-click on login button", Toast.LENGTH_SHORT).show();
            if (validate()) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String passWord = edtPassword.getText().toString();

                new RegisterAsyncTask().execute(name, email, passWord);

            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Clicking “Cancel” should replace this fragment with the Login Fragment.
             */
            @Override
            public void onClick(View v) {
                iMainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.openLoginWindow();
            }
        });

        return registerAccountView;
    }

    /**
     * Locks or Unlocks button
     *
     * @param lock lock or unlock
     */
    public void buttonLocker(Boolean lock) {
        btnCancel.setEnabled(lock);
        btnSubmit.setEnabled(lock);
    }

    /**
     * This method is used to check if an input is valid, if it is not it will send a Toast
     * about what is causing the error
     *
     * @return Returns a Boolean of whether the values for account are valid or not
     */
    public Boolean validate() {
        //Declares the strings
        String email = edtEmail.getText().toString();
        String passWord = edtPassword.getText().toString();
        String name = edtName.getText().toString();

        //Checks name
        if (name.replaceAll("\\s", "").isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgNameErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks email
        if (email.replaceAll("\\s", "").isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgEmailErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks password
        if (passWord.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgPasswordErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * This class should start the execution of a
     * background AsyncTask
     */
    private class RegisterAsyncTask extends AsyncTask<Object, String, AsyncTaskResult<DataServices.Account>> {

        @Override
        protected void onPreExecute() {

            //lock UI components on fragment
            buttonLocker(false);

        }

        @Override
        protected AsyncTaskResult<DataServices.Account> doInBackground(Object... params) {
            String name, email, password;

            AsyncTaskResult<DataServices.Account> asyncTaskResult = null;
            name = (String) params[0];
            email = (String) params[1];
            password = (String) params[2];
            DataServices.RequestException exception = null;
            DataServices.Account account;
            try {
                token = DataServices.register(name, email, password);
                if (token != null) {
                    account = DataServices.getAccount(token);
                    asyncTaskResult = new AsyncTaskResult<>(account);
                }
            } catch (DataServices.RequestException anyError) {
                return new AsyncTaskResult<>(anyError);
            }

            return asyncTaskResult;
        }

        protected void onPostExecute(AsyncTaskResult<DataServices.Account> result) {
            // return a string token if login successful
            if (result.getError() != null) {
                // error handling here
                Toast.makeText(getActivity(), result.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            } else if (isCancelled()) {
                // cancel handling here
            } else {
                DataServices.Account account = result.getResult();
                iMainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setAccountGoToAppCategoriesFragment(token, account);

            }

            //unlock UI components on fragment
            buttonLocker(true);

        }
    }
}