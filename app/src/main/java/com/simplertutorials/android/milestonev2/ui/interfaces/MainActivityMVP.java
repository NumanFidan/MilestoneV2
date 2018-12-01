package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.content.Context;

public interface MainActivityMVP {
    interface View{
        String getLanguageString();
        Context getContextFromMainActivity();
    }

    interface Presenter{
        void initializeRealm();
        void fetchGenreList();
    }
}
