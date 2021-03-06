package com.simplertutorials.android.milestonev2.ui.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.simplertutorials.android.milestonev2.BuildConfig;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.domain.PopularMovie;
import com.simplertutorials.android.milestonev2.domain.PopularMoviesResponse;
import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivityMVP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainActivityPresenter implements MainActivityMVP.Presenter, HomeFragment.HomeFragmentCallback,
        MovieDetailsFragment.MovieDetailsFragmentCallback {

    private final MainActivityMVP.View view;
    private RealmService realmService;
    private ApiService apiService;
    private int currentRequestedPage = 0;
    private HomeFragment homeFragment;
    private MovieDetailsFragment movieDetailsFragment;

    public MainActivityPresenter(MainActivityMVP.View view, RealmService realmService, ApiService apiService) {
        this.view = view;
        this.realmService = realmService;
        this.apiService = apiService;
    }

    public void fetchGenreList() {
        Genre genre = realmService.getFirstGenre();

        if (genre == null)
            fetchGenreListFromApi();

    }

    @SuppressLint("CheckResult")
    private void fetchGenreListFromApi() {
        //Observable
        apiService.fetchGenres(BuildConfig.apiKey,
                view.getLanguageString())
                //Scheduler
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .flatMapIterable(genresResponse -> genresResponse.getGenreList())
                .doOnNext(this::writeGenreToRealm)
                .subscribe();

        /*//Observable
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
                });*/
    }

    private void writeGenreToRealm(Genre genre) {
        realmService.writeGenreToRealm(genre);
    }

    public void attachFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment)
            homeFragment = (HomeFragment) fragment;
        else if (fragment instanceof MovieDetailsFragment)
            movieDetailsFragment = (MovieDetailsFragment) fragment;
    }

    @Override
    public void loadNextPopularPage(ArrayList<PopularMovie> movieArrayList) {
        currentRequestedPage++;
        getPopularMoviesFromApiService(movieArrayList);
    }

    private void getPopularMoviesFromApiService(ArrayList<PopularMovie> movieArrayList) {
        view.showProgressDialogToUser("Loading Movies...");
        //Observable
        apiService.getPopularMovies(BuildConfig.apiKey,
                view.getLanguageString(),
                currentRequestedPage)
                //Scheduler
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Observer
                .subscribe(new SingleObserver<PopularMoviesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PopularMoviesResponse popularMoviesResponse) {
                        if (popularMoviesResponse.getTotalPages() < currentRequestedPage)
                            return;
                        movieArrayList.addAll(popularMoviesResponse.getPopularMovies());
                        if (homeFragment.isAdded())
                            homeFragment.dataChangedRecyclerView();
                        view.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                        currentRequestedPage--;
                        view.dismissLoadingDialog();
                        if (homeFragment.isAdded()) {
                            homeFragment.showConnectionErrorDialog();
                        }
                    }
                });
    }

    @Override
    public void getDetailsOfMovie(final String id, final View clickedItemView) {
        // We will going to try to fetch data from Realm
        // If we could not find, we will try API request.

        Movie currentMovie = realmService.getMovieFromRealm(id);

        if (currentMovie != null) {
            movieFetchSuccessfully(currentMovie, clickedItemView);
            view.showSnackBar("This movie fetched from local database.");
        } else {
            getDetailsFromAPI(id, clickedItemView);
        }

    }

    public void movieFetchSuccessfully(Movie currentMovie, View itemView) {

        //Load backdrop image with Picasso to cache
        Picasso.get().load(currentMovie.getBackdropPath()).fetch(new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                view.dismissLoadingDialog();
            }

            @Override
            public void onError(Exception e) {
                view.dismissLoadingDialog();
            }
        });
        //In case if user returns, load everything from beginning.
        currentRequestedPage = 0;

        //ReplaceFragment and send detailed movie as Parcelable
        Fragment fragment = MovieDetailsFragment.newInstance(currentMovie);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (homeFragment.isAdded())
                homeFragment.replaceFragmentWithExplodeAnimation(itemView, fragment);
        } else {
            if (homeFragment.isAdded())
                homeFragment.replaceFragment(fragment);
        }
    }

    private void writeMovieToRealm(Movie currentMovie, View itemView) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(currentMovie);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.w("Realm----------------", "Uploaded to Realm Succesfully" + currentMovie);
                movieFetchSuccessfully(currentMovie, itemView);
            }

        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.w("Realm----------------", error.getMessage());
                view.showSnackBar("Couldn't save data to local database.");
                movieFetchSuccessfully(currentMovie, itemView);
            }
        });


    }

    public void getDetailsFromAPI(final String id, final View itemView) {

        //Observable
        apiService.getMovieDetails(id, BuildConfig.apiKey,
                view.getLanguageString())
                //Schedulers
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Observer
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movie movie) {

                        writeMovieToRealm(movie, itemView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("FailedToGetMovieDetails", "Movie Details couldn't fetched from API"
                                + e.getMessage());
                        view.dismissLoadingDialog();
                        view.showProgressDialogToUser("Unable to fetch data from API!");
                    }
                });
    }

    @Override
    public Genre getGenreFromRealm(String id) {
        return realmService.getGenreFromRealm(id);
    }
}
