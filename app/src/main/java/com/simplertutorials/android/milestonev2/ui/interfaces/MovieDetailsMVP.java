package com.simplertutorials.android.milestonev2.ui.interfaces;

import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;

public interface MovieDetailsMVP {
    interface View{

    }
    interface Presenter{
        Movie getCurrentMovie();
        Genre getGenreFromRealm(String id);
    }
}
