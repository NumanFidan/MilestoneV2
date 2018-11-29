package com.simplertutorials.android.milestonev2.ui.interfaces;

import android.view.View;

import com.simplertutorials.android.milestonev2.domain.PopularMovie;

public interface MovieClickListener {
    void onMovieItemClick(PopularMovie movie, View ClickedView);
}
