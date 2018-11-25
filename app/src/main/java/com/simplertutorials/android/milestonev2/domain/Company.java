package com.simplertutorials.android.milestonev2.domain;

import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;

public class Company {
    private int id;
    private String logoUrl, name, country;

    public Company() {
        //This empty constructor is requested for FireStore
    }

    public Company(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        //Here, we are checking if url is full url or are we need to add base url
        if (logoUrl.contains("http"))
            this.logoUrl = logoUrl;
        else
            this.logoUrl = DataHolder.getInstance().getApiBaseImageUrl()+logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
