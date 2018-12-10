package com.simplertutorials.android.milestonev2.ui.activities;

import com.simplertutorials.android.milestonev2.BuildConfig;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.GenresResponse;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivityMVP;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private final MainActivityMVP.View view;
    private Realm realm;
    private ApiService apiService;
//    private boolean realmInitialized = false;

    public MainActivityPresenter(MainActivityMVP.View view, Realm realm, ApiService apiService) {
        this.view = view;
        this.realm = realm;
        this.apiService = apiService;
    }
//
//    public void initializeRealm() {
//        Realm.init(view.getContext());
//        realmInitialized = true;
//    }

    public void fetchGenreList() {
//        if (!realmInitialized)
//            initializeRealm();

//        Realm realm = Realm.getDefaultInstance();

        Genre genre = realm.where(Genre.class).findFirst();

        if (genre == null)
            fetchGenreListFromApi();

    }

    private void fetchGenreListFromApi() {
        //Observable
        apiService.fetchGenres(BuildConfig.apiKey,
                view.getLanguageString())
                //Scheduler
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Observer
                .subscribe(new SingleObserver<GenresResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GenresResponse genresResponse) {
                        for (int i = 0; i < genresResponse.getGenreList().size(); i++)
                            writeGenreToRealm(genresResponse.getGenreList().get(i));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
//
//                Call < GenresResponse > genresResponseCall = apiService.fetchGenres(
//                DataHolder.getInstance().getApiKey(),
//                view.getLanguageString());
//
//        genresResponseCall.enqueue(new Callback<GenresResponse>() {
//            @Override
//            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
//
//                //Saving all genre data to ArrayList in DataHolder for further usages
//
//                if (response.body() != null)
//                    for (int i = 0; i < response.body().getGenreList().size(); i++)
//                        writeGenreToRealm(response.body().getGenreList().get(i));
//
//            }
//
//            @Override
//            public void onFailure(Call<GenresResponse> call, Throwable t) {
//
//            }
//        });

    }

    private void writeGenreToRealm(Genre genre) {
        RealmService.getInstance().writeGenreToRealm(genre);
    }
}
