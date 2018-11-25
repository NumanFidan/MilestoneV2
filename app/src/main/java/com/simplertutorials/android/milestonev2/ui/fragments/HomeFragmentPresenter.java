package com.simplertutorials.android.milestonev2.ui.fragments;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.simplertutorials.android.milestonev2.DataHolder.ApiRequest;
import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.ui.interfaces.HomeFragmentMVP;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragmentPresenter implements HomeFragmentMVP.Presenter {

    private final HomeFragmentMVP.View view;
    private int currentRequestedPage =0;

    public HomeFragmentPresenter(HomeFragmentMVP.View view) {
        this.view = view;
    }


    @Override
    public void loadNextPage( final ArrayList<Movie> movieArrayList) {
        currentRequestedPage++;
        if (!(ApiRequest.getInstance().getNumberOfAvailablePage() >= currentRequestedPage))
            return;
        view.showProgressDialogToUser("Loading Movies...");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    movieArrayList.addAll(ApiRequest.getInstance()
                            .requestPopularMovies(currentRequestedPage,
                                    view.getLanguageString()));
                    //If genres not fetched correctly on MainActivity then we will collect it here
                    if (DataHolder.getInstance().getGenreMap().size() < 1) {
                        Log.w("DataHolderHomeFragment", "DataHolder was empty, fetching" +
                                "data from Apı Server");
                        DataHolder.getInstance().setGenreMap(ApiRequest.getInstance()
                                .fetchGenreList(view.getLanguageString()));
                    }

                    view.dataChangedRecyclerViewHandler();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    currentRequestedPage--;
                    view.dismissLoadingDialog();
                    view.connectionErrorHandler();
                }
            }
        });
        thread.start();
    }

    @Override
    public void getDetailsOfMovie(final String id, Source source, final boolean tryFirestoreServer, final boolean tryAPI, final View clickedItemView) {
        // We will going to try to fetch data from Firestore cache
        // If we could not find we will try Firestore Server
        // If we could nor find again, we will try API request.

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Movies")
                .document(id)
                .get(source)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Movie currentMovie = documentSnapshot.toObject(Movie.class);
                            movieFetchSuccessfully(currentMovie, clickedItemView);

                            Log.w("Firestore", currentMovie.getTitle() + ": Movie fetched from firestore isCache?"
                                            + tryFirestoreServer);
                        } else {
                            //This means we could not find data on Cache
                            //We could not found it on Server
                            getDetailsFromAPI(id, clickedItemView);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (tryFirestoreServer)
                            getDetailsOfMovie(id, Source.SERVER, false,
                                    true, clickedItemView);
                        else if (tryAPI)
                            getDetailsFromAPI(id, clickedItemView);
                    }
                });
    }

    @Override
    public void uploadMovieToFirestore(Movie currentMovie) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Movies")
                .document(currentMovie.getId())
                .set(currentMovie);
    }

    @Override
    public void movieFetchSuccessfully(Movie currentMovie, View itemView) {
        //Update Detailed movie at the Data Holder so we can access it from DetailsFragment
        DataHolder.getInstance().setDetailedMovie(currentMovie);

        //Load backdrop image with Picasso to cache
        Picasso.get().load(currentMovie.getBackdropUrl()).fetch(new Callback() {
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

    @Override
    public void getDetailsFromAPI(final String id, final View itemView) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Movie currentMovie = ApiRequest.getInstance().getMovieById(Integer.parseInt(id),
                            view.getLanguageString());
                    //Upload movie to Firestore so we can fetch this movie firestore later.
                    uploadMovieToFirestore(currentMovie);
                    movieFetchSuccessfully(currentMovie, itemView);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    view.dismissLoadingDialog();
                    view.showSnackBar("Unable to fetch data from API!");
                }
            }
        });
        thread.start();
    }
}
