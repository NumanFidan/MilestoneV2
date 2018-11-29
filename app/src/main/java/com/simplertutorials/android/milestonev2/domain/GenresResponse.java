package com.simplertutorials.android.milestonev2.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {

    @SerializedName("genres")
    private List<Genre> genres;

    public GenresResponse(List<Genre> genres) {
        this.genres= genres;
    }

    public List<Genre> getGenreList() {
        return genres;
    }
}