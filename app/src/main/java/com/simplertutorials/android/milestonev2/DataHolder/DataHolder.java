package com.simplertutorials.android.milestonev2.DataHolder;

import com.simplertutorials.android.milestonev2.domain.Movie;

import java.util.HashMap;

public class DataHolder {

    private static DataHolder instance = new DataHolder();
    private final String apiKey= "6947b6102ef7af19f5aee4b5a566ecbc";
    private final String apiBaseImageUrl = "http://image.tmdb.org/t/p/w342";
    private final String apiBaseMoviesUrl = "https://api.themoviedb.org/3/movie";
    public final String APP_LANGUAGE = "en-US";
    private HashMap<Integer, String> genreMap;
    private Movie detailedMovie = null;

    public DataHolder() {

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

    public HashMap<Integer, String> getGenreMap() {
        return genreMap;
    }

    public void setGenreMap(HashMap<Integer, String> genreMap) {
        this.genreMap = genreMap;
    }
}
