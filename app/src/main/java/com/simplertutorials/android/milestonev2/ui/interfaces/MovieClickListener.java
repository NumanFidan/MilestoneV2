package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.view.View;
import android.widget.ImageView;

import com.simplertutorials.android.milestonev2.domain.Movie;

public interface MovieClickListener {
    void onMovieItemClick(Movie movie, View ClickedView);
}
