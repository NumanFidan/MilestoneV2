package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.content.Context;

public interface MainActivityMVP {

    interface View  {
        Context getContext();

        String getLanguageString();
    }

    interface Presenter {
//        void initializeRealm();

        void fetchGenreList();
    }
}
