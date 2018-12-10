package com.simplertutorials.android.milestonev2.ui.interfaces;

import com.simplertutorials.android.milestonev2.domain.Genre;

public interface MovieDetailsMVP {
    interface View{

    }
    interface Presenter{
        Genre getGenreFromRealm(String id);
    }
}
