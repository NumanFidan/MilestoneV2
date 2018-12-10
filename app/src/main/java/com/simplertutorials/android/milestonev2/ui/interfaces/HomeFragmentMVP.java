package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.support.v4.app.Fragment;

import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.domain.PopularMovie;

import java.util.ArrayList;

public interface HomeFragmentMVP {
    interface View{
        void dataChangedRecyclerView();
        void showConnectionErrorDialog();
        void showProgressDialogToUser(String message);
        void dismissLoadingDialog();
        void replaceFragmentWithExplodeAnimation(android.view.View clickedView, final Fragment fragment);
        void replaceFragment(Fragment fragment);
        void showSnackBar(String message);
        String getLanguageString();

    }
    interface Presenter{
        void loadNextPage(ArrayList<PopularMovie> movieArrayList);
        void getDetailsOfMovie(final String id, final android.view.View clickedItemView);
        void movieFetchSuccessfully(Movie currentMovie, final android.view.View itemView);
        void getDetailsFromAPI(final String id, final android.view.View itemView);
    }
}
