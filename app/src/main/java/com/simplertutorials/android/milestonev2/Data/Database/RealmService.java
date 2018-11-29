package com.simplertutorials.android.milestonev2.Data.Database;

import android.util.Log;

import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class RealmService {
    private static RealmService instance = new RealmService();
    private Realm realm;


    private RealmService() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmService getInstance() {
        return instance;
    }

    public RealmAsyncTask writeGenreToRealmAsync(Genre genre) {
        return realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(genre);
            }
        });
    }
    public void writeGenreToRealm(Genre genre) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(genre);
            }
        });
        Log.w("---Genre Added to Realm", ""+genre);
    }

    public Genre getGenreFromRealm(String id){

        Genre genre = realm.where(Genre.class).equalTo("id", id).findFirst();

        Log.w("genre From Realm", genre+"---"+id);

        return genre;
    }

    public Movie getMovieFromRealm(String id){
        return realm.where(Movie.class).equalTo("id", id).findFirst();
    }

    public RealmAsyncTask writeMovieToRealm(Movie movie){
        return realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(movie);
            }
        });
    }

}
