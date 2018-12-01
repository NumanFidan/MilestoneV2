package com.simplertutorials.android.milestonev2.data.database;

import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;

import io.realm.Realm;

public class RealmService {
    private static RealmService instance = new RealmService();
    private Realm realm;


    private RealmService() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmService getInstance() {
        return instance;
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
}
