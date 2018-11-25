package com.simplertutorials.android.milestonev2.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;
import com.simplertutorials.android.milestonev2.MainActivity;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.domain.Company;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieDetailsFragment extends Fragment {

    private Movie currentMovie;
    private ImageView posterView, backdropImageView, adultContent, imdbIcon, imdbPage;
    private TextView ratingTextView, title, tagLine, overview, seeOtherDetails,
            budget, popularity, originalTitle, originalLanguage,
            releaseDate, revenue, runTime, currentStatus, votes, companies;
    private View otherDetailsLayout;
    private LinearLayout genreLayout, companiesLayout;
    private boolean showingAllDetails = false;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(getActivity());
        currentMovie = DataHolder.getInstance().getDetailedMovie();

        setUpActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        init(view);
        updateUI();

        return view;
    }

    private void init(View view) {
        posterView = view.findViewById(R.id.poster_moviedetail_imageView);
        backdropImageView = view.findViewById(R.id.bacdropImage_detailsFragment_imageView);
        ratingTextView = view.findViewById(R.id.rating_moviDetails_textView);
        genreLayout = view.findViewById(R.id.genres_moviedetail_layout);
        title = view.findViewById(R.id.title_moviedetail_itemView);
        tagLine = view.findViewById(R.id.tagline_moviedetail_textView);
        adultContent = view.findViewById(R.id.adultContent_detailsFragment_imageView);
        overview = view.findViewById(R.id.overview_moviedetail_textView);
        seeOtherDetails = view.findViewById(R.id.otherDeails_moviedetail_textView);
        otherDetailsLayout = view.findViewById(R.id.otherDetails_movieDetail_layout);
        imdbIcon = view.findViewById(R.id.imdb_moviedetail_imageView);

        //Details Part
        companies = view.findViewById(R.id.companies_movieDetail_textview);
        companiesLayout = view.findViewById(R.id.companies_movieDetail_layout);
        budget = view.findViewById(R.id.budget_movieDetail_textView);
        popularity = view.findViewById(R.id.popularity_movieDetail_textView);
        imdbPage = view.findViewById(R.id.imdbPage_movieDetail_imageView);
        originalTitle = view.findViewById(R.id.originalTitle_movieDetail_textView);
        originalLanguage = view.findViewById(R.id.originalLanguage_movieDetail_textView);
        releaseDate = view.findViewById(R.id.releaseDate_movieDetail_textView);
        revenue = view.findViewById(R.id.revenue_movieDetail_textView);
        runTime = view.findViewById(R.id.runTime_movieDetail_textView);
        currentStatus = view.findViewById(R.id.currentStatus_movieDetail_textView);
        votes = view.findViewById(R.id.votes_movieDetail_textView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateUI() {
        loadWithPicasso(posterView, currentMovie.getPosterUrl());
        loadWithPicasso(backdropImageView, currentMovie.getBackdropUrl());

        ratingTextView.setText(String.valueOf(currentMovie.getVoteAverage()));
        title.setText(currentMovie.getTitle());
        tagLine.setText(currentMovie.getTagLine());
        overview.setText(currentMovie.getOverview());

        setImdbIconClick(imdbIcon);
        setGenres();
        updateAdultContentWarning();
        handleOtherDetails();
    }

    private void setGenres() {
        ArrayList<Integer> genreList = currentMovie.getGenreIds();
        genreLayout.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,24,0);
        params.gravity = Gravity.CENTER;

        for (int i = 0; i < genreList.size(); i++) {
            TextView genre = new TextView(getContext());
            genre.setText(DataHolder.getInstance().getGenreMap().get(genreList.get(i)));
            genre.setBackgroundResource(R.drawable.dark_rounded_background);
            genre.setTextColor(Color.WHITE);
            genre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            genre.setMaxLines(1);
            genre.setPadding(24, 0, 24, 8);
            genre.setLayoutParams(params);
            genreLayout.addView(genre);
        }
    }

    private void setImdbIconClick(View imdb) {
        imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imdbUrl = "https://www.imdb.com/title/"+currentMovie.getImdbLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imdbUrl));
                startActivity(intent);
            }
        });
    }

    private void handleOtherDetails() {
        seeOtherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if we don't show all the details
                if (!showingAllDetails) {
                    seeOtherDetails.setText(getString(R.string.hide_details));
                    showingAllDetails = true;

                    fillOtherInformation();
                    otherDetailsLayout.setVisibility(View.VISIBLE);
                } else {
                    seeOtherDetails.setText(getString(R.string.see_other_details));
                    showingAllDetails = false;
                    otherDetailsLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    private void fillOtherInformation() {
        //We want to show user understandable numbers
        //because of that we are using "," to sepera number.
        setCompanies();
        budget.setText(String.format(Locale.getDefault(), "%,d", currentMovie.getBudget()) +
                getString(R.string.currency));
        popularity.setText(String.format(Locale.getDefault(), "%,d", currentMovie.getPopularity()));
        imdbPage.setImageResource(R.mipmap.ic_imdb);
        setImdbIconClick(imdbPage);
        originalTitle.setText(currentMovie.getOriginalTitle());
        originalLanguage.setText(currentMovie.getOriginalLanguage());
        releaseDate.setText(currentMovie.getReleaseDate());
        revenue.setText(String.format(Locale.getDefault(), "%,d",currentMovie.getRevenue()) +
                getString(R.string.currency));
        runTime.setText(String.valueOf(currentMovie.getRunTime())+getString(R.string.minute));
        currentStatus.setText(currentMovie.getStatus());
        votes.setText(String.format(Locale.getDefault(), "%,d",currentMovie.getVoteCount()));
    }

    private void setCompanies() {
        ArrayList<Company> companyList = currentMovie.getCompanies();
        companiesLayout.removeAllViews();

        if (companyList.size()>1)
            companies.setText(getString(R.string.production_companies));
        else
            companies.setText(getString(R.string.production_company));

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < companyList.size(); i++) {
            View view = inflater.inflate(R.layout.item_company, companiesLayout, false);
            TextView companyName = view.findViewById(R.id.companyName_companyItem);
            CircleImageView logo = view.findViewById(R.id.company_logo);
            companyName.setText(currentMovie.getCompanies().get(i).getName());
            Picasso.get().load(currentMovie.getCompanies().get(i).getLogoUrl())
                    .into(logo);
            companiesLayout.addView(view);
        }
    }

    private void updateAdultContentWarning() {
        if (currentMovie.isAdult()) {
            adultContent.setImageResource(R.mipmap.adult);
            adultContent.setBackgroundResource(R.drawable.red_rounded_background);
        } else {
            adultContent.setImageResource(R.mipmap.family);
            adultContent.setBackgroundResource(R.drawable.primary_rounded_background);
        }
        adultContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                String message = currentMovie.isAdult() ? getString(R.string.this_film_contains_adult_contents) :
                        getString(R.string.this_film_doesnt_contain_adult_contents);
                alertDialog.setMessage(message);
                alertDialog.setTitle(null);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });
    }

    private void loadWithPicasso(final ImageView into, final String url) {
        //We will try to fetch offline data if it is possible.
        //If no we will fetch it from URL
        Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(into, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.w("Picasso", url+": Image loaded from cache");
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(url)
                                .into(into, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.w("Picasso", url+": couldn't download the image.");
                                    }
                                });
                    }
                });
    }

    private void setUpActionBar() {
        MainActivity mainActivity = ((MainActivity)getActivity());
        View actionBarRoot = mainActivity.getSupportActionBar().getCustomView();
        TextView actionBarTitle = actionBarRoot.findViewById(R.id.action_bar_title);
        actionBarTitle.setText(currentMovie.getTitle());
        actionBarTitle.setEms(16);
        actionBarTitle.setMaxLines(1);
        actionBarTitle.setEllipsize(TextUtils.TruncateAt.END);
    }
}
