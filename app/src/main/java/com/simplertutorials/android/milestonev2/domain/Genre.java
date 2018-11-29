package com.simplertutorials.android.milestonev2.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import io.realm.RealmObject;

public class Genre extends RealmObject {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    public Genre() {
        //empty constructor is requiered by Firebase FireStore
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        //if id of two genre is equal than those genres are equal.
        if (obj instanceof Genre)
            return ((Genre) obj).getId().equalsIgnoreCase(this.getId());
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return "["+getId()+" , "+getName()+"]";
    }
}
