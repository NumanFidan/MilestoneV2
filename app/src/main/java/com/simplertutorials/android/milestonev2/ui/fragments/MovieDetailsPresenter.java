package com.simplertutorials.android.milestonev2.ui.fragments;

import com.simplertutorials.android.milestonev2.data.DataHolder;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.ui.interfaces.MovieDetailsMVP;

public class MovieDetailsPresenter implements MovieDetailsMVP.Presenter {
    private MovieDetailsMVP.View view;
    private Movie currentMovie;

    MovieDetailsPresenter(MovieDetailsMVP.View view){
        this.view = view;
    }

    @Override
    public Movie getCurrentMovie() {
        currentMovie = DataHolder.getInstance().getDetailedMovie();
        return currentMovie;
    }

    @Override
    public Genre getGenreFromRealm(String id) {
        return RealmService.getInstance().getGenreFromRealm(id);
    }
}
