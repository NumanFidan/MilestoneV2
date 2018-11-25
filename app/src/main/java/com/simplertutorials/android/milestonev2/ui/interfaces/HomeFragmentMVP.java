package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.support.v4.app.Fragment;
import android.view.View;

import com.google.firebase.firestore.Source;
import com.simplertutorials.android.milestonev2.domain.Movie;

import java.util.ArrayList;

public interface HomeFragmentMVP {
    interface View{
        void dataChangedRecyclerViewHandler();
        void connectionErrorHandler();
        void showProgressDialogToUser(String message);
        void dismissLoadingDialog();
        void replaceFragmentWithExplodeAnimation(android.view.View clickedView, final Fragment fragment);
        void replaceFragment(Fragment fragment);
        void showSnackBar(String message);
        String getLanguageString();

    }
    interface Presenter{
        void loadNextPage(ArrayList<Movie> movieArrayList);
        void getDetailsOfMovie(final String id, Source source,
                               final boolean tryFirestoreServer, final boolean tryAPI,
                               final android.view.View clickedItemView);
        void uploadMovieToFirestore(Movie currentMovie);
        void movieFetchSuccessfully(Movie currentMovie, final android.view.View itemView);
        void getDetailsFromAPI(final String id, final android.view.View itemView);
    }
}
