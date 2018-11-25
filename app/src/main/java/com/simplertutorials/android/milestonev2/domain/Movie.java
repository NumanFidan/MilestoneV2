package com.simplertutorials.android.milestonev2.domain;

import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;

import java.util.ArrayList;

public class Movie {
    private ArrayList<Integer> genreIds;
    private String id, title, overview, releaseDate, homePage;
    private String imdbLink, status, tagLine;
    private ArrayList<Company> companies;
    private ArrayList<String> countries, laguages;
    private int voteCount, popularity, budget, revenue, runTime;
    private double voteAverage;
    private String posterUrl, backdropUrl, originalLanguage, originalTitle;
    private boolean isAdult, isDetailed;

    public Movie(){
        //This empty constructor is requested for FireStore
    }

    public Movie(String id, String title, String overview) {
        initializeArrayLists();
        setId(id);
        setTitle(title);
        setOverview(overview);
    }

    private void initializeArrayLists() {
        genreIds = new ArrayList<>();
        companies = new ArrayList<>();
        countries = new ArrayList<>();
        laguages = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        //Here, we are checking if url is full url or are we need to add base url
        if (posterUrl.contains("http"))
            this.posterUrl = posterUrl;
        else
            this.posterUrl = DataHolder.getInstance().getApiBaseImageUrl() + posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        //Here, we are checking if url is full url or are we need to add base url
        if (backdropUrl.contains("http"))
            this.backdropUrl = backdropUrl;
        else
            this.backdropUrl = DataHolder.getInstance().getApiBaseImageUrl() + backdropUrl;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Movie) obj).getId();
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public ArrayList<String> getLaguages() {
        return laguages;
    }

    public void setLaguages(ArrayList<String> laguages) {
        this.laguages = laguages;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public void setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public boolean isDetailed() {
        return isDetailed;
    }

    public void setDetailed(boolean detailed) {
        isDetailed = detailed;
    }
}


