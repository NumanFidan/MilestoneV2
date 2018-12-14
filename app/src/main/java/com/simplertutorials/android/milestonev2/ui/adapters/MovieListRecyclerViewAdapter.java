package com.simplertutorials.android.milestonev2.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.PopularMovie;
import com.simplertutorials.android.milestonev2.ui.interfaces.MovieClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListRecyclerViewAdapter extends RecyclerView.Adapter<MovieListRecyclerViewAdapter.MovieListRecyclerViewHolder> {

    private ArrayList<PopularMovie> movieArrayList;
    private Context context;
    private MovieClickListener movieClickListener;
    private RealmService realmService;

    public MovieListRecyclerViewAdapter(Context context, ArrayList<PopularMovie> movieArrayList,
                                        MovieClickListener movieClickListener, RealmService realmService) {
        this.context = context;
        this.movieArrayList = movieArrayList;
        this.movieClickListener = movieClickListener;
        this.realmService = realmService;
    }

    @NonNull
    @Override
    public MovieListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_movie_recycler, parent, false);
        return new MovieListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieListRecyclerViewHolder holder, final int i) {

        holder.itemTitle.setText(movieArrayList.get(i).getTitle());
        addGenres(holder.genreLayout, i);
        holder.itemDescription.setText(movieArrayList.get(i).getOverview());
        holder.itemRating.setText(String.valueOf(movieArrayList.get(i).getVoteAverage()));
        setMoviePosterWithPicasso(holder.poster, i);

        holder.detailsBtn.setClickable(false);
        holder.parentLayout.setOnClickListener(view ->
                movieClickListener.onMovieItemClick(movieArrayList.get(i), holder.itemView));

    }

    private void addGenres(LinearLayout genreLayout, int position) {
        ArrayList<String> genreList = (ArrayList<String>) movieArrayList.get(position).getGenreIds();
        genreLayout.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        params.gravity = Gravity.CENTER;

        Genre genreObj;
        for (int i = 0; i < genreList.size(); i++) {
            genreObj = realmService.getGenreFromRealm(genreList.get(i));

            TextView genre = new TextView(context);
            genre.setText(genreObj.getName());
            genre.setBackgroundResource(R.drawable.dark_rounded_background);
            genre.setTextColor(Color.WHITE);
            genre.setMaxLines(1);
            genre.setPadding(16, 0, 16, 4);
            genre.setLayoutParams(params);
            genreLayout.addView(genre);
        }

        Log.w("--------------", movieArrayList.get(position).toString());
    }

    private void setMoviePosterWithPicasso(ImageView poster, int position) {
        Picasso.get().load(movieArrayList.get(position).getPosterUrl())
                .into(poster);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    class MovieListRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_title_textview)
        TextView itemTitle;
        @BindView(R.id.item_movie_description_textview)
        TextView itemDescription;
        @BindView(R.id.rating_itemMovie_textView)
        TextView itemRating;

        @BindView(R.id.movie_item_parent_layout)
        FrameLayout parentLayout;

        @BindView(R.id.genres_itemMovie_layout)
        LinearLayout genreLayout;

        @BindView(R.id.item_movie_poster_imageview)
        ImageView poster;

        @BindView(R.id.details_movieItem_button)
        Button detailsBtn;

        MovieListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
