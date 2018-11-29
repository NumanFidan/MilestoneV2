package com.simplertutorials.android.milestonev2.ui.fragments;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.simplertutorials.android.milestonev2.data.api.ApiClient;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.data.DataHolder;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.domain.PopularMovie;
import com.simplertutorials.android.milestonev2.domain.PopularMoviesResponse;
import com.simplertutorials.android.milestonev2.ui.interfaces.HomeFragmentMVP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter implements HomeFragmentMVP.Presenter {

    private final HomeFragmentMVP.View view;
    private int currentRequestedPage = 0;
    private ApiService apiService;

    HomeFragmentPresenter(HomeFragmentMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadNextPage(ArrayList<PopularMovie> movieArrayList) {
        currentRequestedPage++;

        if (apiService == null)
            initializeApiService();

        getPopularMoviesFromApiService(movieArrayList);
/*
        //If genres not fetched correctly on MainActivity then we will collect it here
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(Genre.class).findFirst() == null) {
            Log.w("RealmHomeFragment", "Local Genre database was empty, fetching" +
                    "data from Apı Server");
            getGenresFromApiService();

        }*/
    }

    private void getPopularMoviesFromApiService(ArrayList<PopularMovie> movieArrayList) {
        Call<PopularMoviesResponse> call = apiService.getPopularMovies(DataHolder.getInstance().getApiKey(),
                view.getLanguageString(),
                currentRequestedPage);

        view.showProgressDialogToUser("Loading Movies...");

        call.enqueue(new retrofit2.Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                if (response.body().getTotalPages() < currentRequestedPage)
                    return;
                movieArrayList.addAll(response.body().getPopularMovies());
                view.dataChangedRecyclerViewHandler();

            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                currentRequestedPage--;
                view.dismissLoadingDialog();
                view.connectionErrorHandler();
            }

        });
    }
/*
    private void getGenresFromApiService() {
        Call<GenresResponse> genresResponseCall = apiService.fetchGenres(DataHolder.getInstance().getApiKey(),
                view.getLanguageString());
        genresResponseCall.enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {

                //Saving all genre data to ArrayList in DataHolder for further usages

                if (response.body() != null) {
                    for (int j = 0; j < response.body().getGenreList().size(); j++)
                        RealmService.getInstance().writeGenreToRealm(response.body().getGenreList().get(j));
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {

            }
        });
    }*/

    private void initializeApiService() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void getDetailsOfMovie(final String id,final View clickedItemView) {
        // We will going to try to fetch data from Realm
        // If we could not find, we will try API request.


        Movie currentMovie = RealmService.getInstance().getMovieFromRealm(id);

        if (currentMovie != null) {
            Log.w("Realm Baby------------", "-------------------" + currentMovie.toString());
            movieFetchSuccessfully(currentMovie, clickedItemView);
            view.showSnackBar("This movie fetched from local database.");
        } else {
            getDetailsFromAPI(id, clickedItemView);
        }

    }

    @Override
    public void movieFetchSuccessfully(Movie currentMovie, View itemView) {
        //Update Detailed movie at the Data Holder so we can access it from DetailsFragment
        DataHolder.getInstance().setDetailedMovie(currentMovie);

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

        //ReplaceFragment
        Fragment fragment = new MovieDetailsFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.replaceFragmentWithExplodeAnimation(itemView, fragment);
        } else {
            view.replaceFragment(fragment);
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

    @Override
    public void getDetailsFromAPI(final String id, final View itemView) {

        Call<Movie> movieCall = apiService.getMovieDetails(id,
                DataHolder.getInstance().getApiKey(),
                view.getLanguageString());

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                writeMovieToRealm(response.body(), itemView);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.w("FailedToGetMovieDetails", "Movie Details couldn't fetched from API"
                        + t.getMessage());
                view.dismissLoadingDialog();
                view.showSnackBar("Unable to fetch data from API!");
            }
        });
    }
}
