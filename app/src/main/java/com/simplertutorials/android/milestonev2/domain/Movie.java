package com.simplertutorials.android.milestonev2.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.simplertutorials.android.milestonev2.BuildConfig;

import java.util.List;

import javax.annotation.Nonnull;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject implements Parcelable {

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

    public Movie(){

    }

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        if (backdropPath.contains("http"))
            return backdropPath;
        else
            return BuildConfig.imageBaseUrl + backdropPath;
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
            return BuildConfig.imageBaseUrl + posterPath;

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

    @Nonnull
    @Override
    public String toString() {
        return "["+getId()+", "+getTitle()+"]";
    }

    protected Movie(Parcel in) {
        id = in.readString();
        byte adultVal = in.readByte();
        adult = adultVal == 0x02 ? null : adultVal != 0x00;
        backdropPath = in.readString();
        budget = in.readByte() == 0x00 ? null : in.readInt();
        genres = (RealmList) in.readValue(RealmList.class.getClassLoader());
        homepage = in.readString();
        imdbId = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
        productionCompanies = (RealmList) in.readValue(RealmList.class.getClassLoader());
        releaseDate = in.readString();
        revenue = in.readByte() == 0x00 ? null : in.readInt();
        runtime = in.readByte() == 0x00 ? null : in.readInt();
        status = in.readString();
        tagline = in.readString();
        title = in.readString();
        voteAverage = in.readByte() == 0x00 ? null : in.readFloat();
        voteCount = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (adult == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adult ? 0x01 : 0x00));
        }
        dest.writeString(backdropPath);
        if (budget == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(budget);
        }
        dest.writeValue(genres);
        dest.writeString(homepage);
        dest.writeString(imdbId);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(popularity);
        dest.writeString(posterPath);
        dest.writeValue(productionCompanies);
        dest.writeString(releaseDate);
        if (revenue == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(revenue);
        }
        if (runtime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(runtime);
        }
        dest.writeString(status);
        dest.writeString(tagline);
        dest.writeString(title);
        if (voteAverage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(voteAverage);
        }
        if (voteCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(voteCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}