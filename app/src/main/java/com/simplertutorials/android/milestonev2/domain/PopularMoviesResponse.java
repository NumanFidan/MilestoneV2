package com.simplertutorials.android.milestonev2.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesResponse {

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<PopularMovie> popularMovies;

    public PopularMoviesResponse(Integer totalPages, List<PopularMovie> popularMovies) {
        this.totalPages = totalPages;
        this.popularMovies = popularMovies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<PopularMovie> getPopularMovies() {
        return popularMovies;
    }
}