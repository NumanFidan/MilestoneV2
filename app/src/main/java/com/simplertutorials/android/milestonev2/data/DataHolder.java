package com.simplertutorials.android.milestonev2.data;

import com.simplertutorials.android.milestonev2.domain.Movie;

public class DataHolder {

    private static DataHolder instance = new DataHolder();
    private final String apiKey= "6947b6102ef7af19f5aee4b5a566ecbc";
    private final String apiBaseImageUrl = "http://image.tmdb.org/t/p/w342";
    private final String apiBaseMoviesUrl = "https://api.themoviedb.org/3/movie";
    private Movie detailedMovie = null;

    private DataHolder() {

    }

    public static DataHolder getInstance() {
        return instance;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiBaseImageUrl() {
        return apiBaseImageUrl;
    }

    public String getApiBaseMoviesUrl() {
        return apiBaseMoviesUrl;
    }

    public Movie getDetailedMovie() {
        return detailedMovie;
    }

    public void setDetailedMovie(Movie detailedMovie) {
        this.detailedMovie = detailedMovie;
    }
}
