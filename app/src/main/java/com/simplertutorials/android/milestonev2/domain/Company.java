package com.simplertutorials.android.milestonev2.domain;

import com.google.gson.annotations.SerializedName;
import com.simplertutorials.android.milestonev2.Data.DataHolder;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Company extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    public Integer id;

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("name")
    public String name;

    @SerializedName("origin_country")
    private String originCountry;

    public Company() {
        //This empty constructor is requested for FireStore
    }

    public Company(Integer id, String logoPath, String name, String originCountry) {
        this.id = id;
        this.logoPath = logoPath;
        this.name = name;
        this.originCountry = originCountry;
    }

    public Integer getId() {
        return id;
    }

    public String getLogoPath() {
        if (logoPath == null)
            return "";

        //Here, we are checking if url is full url or are we need to add base url
        if (logoPath.contains("http"))
            return logoPath;
        else
            return DataHolder.getInstance().getApiBaseImageUrl()+logoPath;
    }

    public String getName() {
        return name;
    }

    public String getOriginCountry() {
        return originCountry;
    }
}
