package com.simplertutorials.android.milestonev2.data;

import com.simplertutorials.android.milestonev2.domain.Movie;

public class DataHolder {

    private static DataHolder instance = new DataHolder();
    private final String apiKey= "6947b6102ef7af19f5aee4b5a566ecbc";
    private final String apiBaseImageUrl = "http://image.tmdb.org/t/p/w342";
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

    public Movie getDetailedMovie() {
        return detailedMovie;
    }

    public void setDetailedMovie(Movie detailedMovie) {
        this.detailedMovie = detailedMovie;
    }
}
