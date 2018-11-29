package com.simplertutorials.android.milestonev2.domain;

import com.google.gson.annotations.SerializedName;
import com.simplertutorials.android.milestonev2.data.DataHolder;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    public String id;

    @SerializedName("adult")
    public Boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("budget")
    public Integer  budget;

    @SerializedName("genres")
    private RealmList<Genre> genres = null;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    public String popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private RealmList<Company> productionCompanies = null;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    public Integer revenue;

    @SerializedName("runtime")
    public Integer runtime;

    @SerializedName("status")
    public String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("vote_count")
    private Integer voteCount;

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        if (backdropPath.contains("http"))
            return backdropPath;
        else
            return DataHolder.getInstance().getApiBaseImageUrl() + backdropPath;
    }

    public Integer getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        if (posterPath.contains("http"))
            return posterPath;
        else
            return DataHolder.getInstance().getApiBaseImageUrl() + posterPath;

    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public void setGenres(RealmList<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setProductionCompanies(RealmList<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}

//
//
//
//
//
//
//
//
//public class Movie {
//    private ArrayList<Integer> genreIds;
//    private String id, title, overview, releaseDate, homePage;
//    private String imdbLink, status, tagLine;
//    private ArrayList<Company> companies;
//    private ArrayList<String> countries, laguages;
//    private int voteCount, popularity, budget, revenue, runTime;
//    private double voteAverage;
//    private String posterUrl, backdropUrl, originalLanguage, originalTitle;
//    private boolean isAdult, isDetailed;
//
//    public Movie(){
//        //This empty constructor is requested for FireStore
//    }
//
//    public Movie(String id, String title, String overview) {
//        initializeArrayLists();
//        setId(id);
//        setTitle(title);
//        setOverview(overview);
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getOverview() {
//        return overview;
//    }
//
//    public void setOverview(String overview) {
//        this.overview = overview;
//    }
//
//    public String getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(String releaseDate) {
//        this.releaseDate = releaseDate;
//    }
//
//    public int getVoteCount() {
//        return voteCount;
//    }
//
//    public void setVoteCount(int voteCount) {
//        this.voteCount = voteCount;
//    }
//
//    public int getPopularity() {
//        return popularity;
//    }
//
//    public void setPopularity(int popularity) {
//        this.popularity = popularity;
//    }
//
//    public double getVoteAverage() {
//        return voteAverage;
//    }
//
//    public void setVoteAverage(double voteAverage) {
//        this.voteAverage = voteAverage;
//    }
//
//    public String getPosterUrl() {
//        return posterUrl;
//    }
//
//    public void setPosterUrl(String posterUrl) {
//        //Here, we are checking if url is full url or are we need to add base url
//        if (posterUrl.contains("http"))
//            this.posterUrl = posterUrl;
//        else
//            this.posterUrl = DataHolder.getInstance().getApiBaseImageUrl() + posterUrl;
//    }
//
//    public String getBackdropUrl() {
//        return backdropUrl;
//    }
//
//    public void setBackdropUrl(String backdropUrl) {
//        //Here, we are checking if url is full url or are we need to add base url
//        if (backdropUrl.contains("http"))
//            this.backdropUrl = backdropUrl;
//        else
//            this.backdropUrl = DataHolder.getInstance().getApiBaseImageUrl() + backdropUrl;
//    }
//
//    public String getOriginalLanguage() {
//        return originalLanguage;
//    }
//
//    public void setOriginalLanguage(String originalLanguage) {
//        this.originalLanguage = originalLanguage;
//    }
//
//    public String getOriginalTitle() {
//        return originalTitle;
//    }
//
//    public void setOriginalTitle(String originalTitle) {
//        this.originalTitle = originalTitle;
//    }
//
//    public boolean isAdult() {
//        return isAdult;
//    }
//
//    public void setAdult(boolean adult) {
//        isAdult = adult;
//    }
//
//    public ArrayList<Integer> getGenreIds() {
//        return genreIds;
//    }
//
//    @Override
//    public String toString() {
//        return "Title: " + getTitle();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return this.getId() == ((Movie) obj).getId();
//    }
//
//    public void setGenreIds(ArrayList<Integer> genreIds) {
//        this.genreIds = genreIds;
//    }
//
//    public ArrayList<Company> getCompanies() {
//        return companies;
//    }
//
//    public void setCompanies(ArrayList<Company> companies) {
//        this.companies = companies;
//    }
//
//    public ArrayList<String> getCountries() {
//        return countries;
//    }
//
//    public void setCountries(ArrayList<String> countries) {
//        this.countries = countries;
//    }
//
//    public ArrayList<String> getLaguages() {
//        return laguages;
//    }
//
//    public void setLaguages(ArrayList<String> laguages) {
//        this.laguages = laguages;
//    }
//
//    public String getHomePage() {
//        return homePage;
//    }
//
//    public void setHomePage(String homePage) {
//        this.homePage = homePage;
//    }
//
//    public String getImdbLink() {
//        return imdbLink;
//    }
//
//    public void setImdbLink(String imdbLink) {
//        this.imdbLink = imdbLink;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getTagLine() {
//        return tagLine;
//    }
//
//    public void setTagLine(String tagLine) {
//        this.tagLine = tagLine;
//    }
//
//    public int getBudget() {
//        return budget;
//    }
//
//    public void setBudget(int budget) {
//        this.budget = budget;
//    }
//
//    public int getRevenue() {
//        return revenue;
//    }
//
//    public void setRevenue(int revenue) {
//        this.revenue = revenue;
//    }
//
//    public int getRunTime() {
//        return runTime;
//    }
//
//    public void setRunTime(int runTime) {
//        this.runTime = runTime;
//    }
//
//    public boolean isDetailed() {
//        return isDetailed;
//    }
//
//    public void setDetailed(boolean detailed) {
//        isDetailed = detailed;
//    }
//}


