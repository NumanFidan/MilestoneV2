package com.simplertutorials.android.milestonev2.data.database;

import android.content.Context;
import android.util.Log;

import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;

import javax.inject.Inject;

import io.realm.Realm;

public class RealmService {
    private Realm realm;

    @Inject
    public RealmService(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void writeGenreToRealm(Genre genre) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(genre);
            }
        });
    }

    public Genre getGenreFromRealm(String id){
        Genre genre = realm.where(Genre.class).equalTo("id", id).findFirst();

        return genre;
    }

    public Movie getMovieFromRealm(String id){
        return realm.where(Movie.class).equalTo("id", id).findFirst();
    }

    public Genre getFirstGenre(){
        return realm.where(Genre.class).findFirst();
    }
}
