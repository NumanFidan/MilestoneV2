package com.simplertutorials.android.milestonev2.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.simplertutorials.android.milestonev2.Data.DataHolder;

import java.util.List;

public class PopularMovie {

    @SerializedName("id")
    public String id;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("title")
    public String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("genre_ids")
    private List<String> genreIds ;

    public PopularMovie(String id, Float voteAverage, String title,
                        String posterPath, String overview, List<String> genreIds) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.genreIds = genreIds;
    }

    public String getId() {
        return id;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return DataHolder.getInstance().getApiBaseImageUrl() + posterPath;
    }

    public List<String> getGenreIds() {
        return genreIds;
    }

    public String getOverview() {
        return overview;
    }

    @NonNull
    @Override
    public String toString() {
        return "["+ getId() + "," + getTitle() + "]";
    }

    class PopularMoviesResponseList{

    }
}
