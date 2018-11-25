package com.simplertutorials.android.milestonev2.ui.interfaces;

public interface MainActivtyMVP {
    interface View{
        String getLanguageString();
    }

    interface Presenter{
        void setUpFireStore();
        void fetchGenreList();
    }
}
