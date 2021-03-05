package com.uncc.hw04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Homework #04
 * File Name:MainActivity.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

public class MainActivity extends AppCompatActivity implements iMainActivity {

    DataServices.Account account;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();


    }


    /**
     * This method opens the register Fragment
     */
    public void openRegisterWindow() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, RegisterAccountFragment.newInstance())
                .commit();
    }

    /**
     * This method opens the Login Fragment
     */
    public void openLoginWindow() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, LoginFragment.newInstance())
                .commit();
    }

    /**
     * This method opens App Details
     */
    public void openAppWindow(DataServices.App appInfo) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppDetailsFragment.newInstance(appInfo))
                .addToBackStack(null)  //push the current fragment on the back stack
                .commit();
    }


    /**
     * This method set account information from Login fragment to App Categories Fragment
     *
     * @param account This object hold the information of an account
     */
    @Override
    public void setAccountGoToAppCategoriesFragment(String token, DataServices.Account account) {
        //Toast.makeText(this, account.getName() + "-" + account.getEmail(), Toast.LENGTH_SHORT).show();
        this.account = account;
        this.token = token;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppCategoriesFragment.newInstance(token, account))
                .commit();

    }

    /**
     * This method open the Apps List Fragment
     *
     * @param category
     */
    @Override
    public void openAppsListWindow(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppsListFragment.newInstance(this.token, category))
                .addToBackStack(null)  //push the current fragment on the back stack
                .commit();
    }

    /**
     * deletes the account within the main activity
     */
    @Override
    public void deleteAccount(DataServices.Account account) {
        this.account = account;
        this.token = null;

    }


}