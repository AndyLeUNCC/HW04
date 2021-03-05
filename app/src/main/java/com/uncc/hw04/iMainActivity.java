package com.uncc.hw04;
/**
 * Homework #04
 * File Name:iMainActivity.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */

/**
 * This interface using to send account to MainActivity.
 */
public interface iMainActivity {
    void setAccountGoToAppCategoriesFragment(String token, DataServices.Account account);

    void openAppsListWindow(String category);

    void deleteAccount(DataServices.Account account);

    void openAppWindow(DataServices.App appInfo);

    void openLoginWindow();

    void openRegisterWindow();

}
