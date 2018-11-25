package com.simplertutorials.android.milestonev2.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.R;

class MovieListRecyclerViewHolder extends ViewHolder {

    TextView itemTitle, itemDescription, itemRating;
    FrameLayout parentLayout;
    LinearLayout genreLayout;
    ImageView poster;
    Button detailsBtn;

    MovieListRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        itemTitle = itemView.findViewById(R.id.item_movie_title_textview);
        itemDescription = itemView.findViewById(R.id.item_movie_description_textview);
        parentLayout = itemView.findViewById(R.id.movie_item_parent_layout);
        poster = itemView.findViewById(R.id.item_movie_poster_imageview);
        itemRating = itemView.findViewById(R.id.rating_itemMovie_textView);
        genreLayout = itemView.findViewById(R.id.genres_itemMovie_layout);
        detailsBtn = itemView.findViewById(R.id.details_movieItem_button);

    }
}
