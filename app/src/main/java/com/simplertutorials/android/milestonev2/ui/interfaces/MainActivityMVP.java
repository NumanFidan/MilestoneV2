package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.content.Context;

public interface MainActivityMVP {

    interface View  {
        Context getContext();

        String getLanguageString();
        void showProgressDialogToUser(String message);
        void dismissLoadingDialog();
        void showSnackBar(String message);
    }

    interface Presenter {
//        void initializeRealm();

        void fetchGenreList();
    }
}
