package com.simplertutorials.android.milestonev2.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.simplertutorials.android.milestonev2.MilestoneApplication;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Company;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.ui.activities.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MovieDetailsFragment extends Fragment {

    private Movie currentMovie;

    @BindView(R.id.poster_moviedetail_imageView)
    ImageView posterView;
    @BindView(R.id.bacdropImage_detailsFragment_imageView)
    ImageView backdropImageView;
    @BindView(R.id.adultContent_detailsFragment_imageView)
    ImageView adultContent;
    @BindView(R.id.imdb_moviedetail_imageView)
    ImageView imdbIcon;
    @BindView(R.id.imdbPage_movieDetail_imageView)
    ImageView imdbPage;

    @BindView(R.id.rating_moviDetails_textView)
    TextView ratingTextView;
    @BindView(R.id.title_moviedetail_itemView)
    TextView title;
    @BindView(R.id.tagline_moviedetail_textView)
    TextView tagLine;
    @BindView(R.id.overview_moviedetail_textView)
    TextView overview;
    @BindView(R.id.otherDeails_moviedetail_textView)
    TextView seeOtherDetails;
    @BindView(R.id.budget_movieDetail_textView)
    TextView budget;
    @BindView(R.id.popularity_movieDetail_textView)
    TextView popularity;
    @BindView(R.id.originalTitle_movieDetail_textView)
    TextView originalTitle;
    @BindView(R.id.originalLanguage_movieDetail_textView)
    TextView originalLanguage;
    @BindView(R.id.releaseDate_movieDetail_textView)
    TextView releaseDate;
    @BindView(R.id.revenue_movieDetail_textView)
    TextView revenue;
    @BindView(R.id.runTime_movieDetail_textView)
    TextView runTime;
    @BindView(R.id.currentStatus_movieDetail_textView)
    TextView currentStatus;
    @BindView(R.id.votes_movieDetail_textView)
    TextView votes;
    @BindView(R.id.companies_movieDetail_textview)
    TextView companies;

    @BindView(R.id.otherDetails_movieDetail_layout)
    View otherDetailsLayout;

    @BindView(R.id.genres_moviedetail_layout)
    LinearLayout genreLayout;
    @BindView(R.id.companies_movieDetail_layout)
    LinearLayout companiesLayout;

    @Inject
    RealmService realmService;

    private boolean showingAllDetails = false;
    private Activity activity;

    private static final String ARG_MOVIE_PARAM = "detailedMovie";

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(Parcelable movieParam) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_PARAM, movieParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(getActivity());

        if (getArguments() != null) {
            currentMovie = getArguments().getParcelable(ARG_MOVIE_PARAM);
            getArguments().remove(ARG_MOVIE_PARAM);
        }

        ((MilestoneApplication) getActivity().getApplicationContext()).getCompenent().inject(this);


        setUpActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ButterKnife.bind(this, view);
        updateUI();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if (activity instanceof MainActivity)
            ((MainActivity)activity).getPresenter().attachFragment(this);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateUI() {
        loadWithPicasso(posterView, currentMovie.getPosterPath());
        loadWithPicasso(backdropImageView, currentMovie.getBackdropPath());

        ratingTextView.setText(String.valueOf(currentMovie.getVoteAverage()));
        title.setText(currentMovie.getTitle());
        tagLine.setText(currentMovie.getTagline());
        overview.setText(currentMovie.getOverview());

        setGenres();
        updateAdultContentWarning();
        handleOtherDetails();
    }

    private void setGenres() {
        List<Genre> genreList = currentMovie.getGenres();
        genreLayout.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 24, 0);
        params.gravity = Gravity.CENTER;
        Genre genreObj;
        for (int i = 0; i < genreList.size(); i++) {

            genreObj = ((MainActivity)activity).getPresenter().getGenreFromRealm(genreList.get(i).getId());
            TextView genre = new TextView(getContext());
            genre.setText(genreObj.getName());
            genre.setBackgroundResource(R.drawable.dark_rounded_background);
            genre.setTextColor(Color.WHITE);
            genre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            genre.setMaxLines(1);
            genre.setPadding(24, 0, 24, 8);
            genre.setLayoutParams(params);
            genreLayout.addView(genre);
        }
    }

    @OnClick({R.id.imdb_moviedetail_imageView, R.id.imdbPage_movieDetail_imageView})
    public void imdbIconClicked(View imdb) {
        String imdbUrl = "https://www.imdb.com/title/" + currentMovie.getImdbId();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(imdbUrl));
        startActivity(intent);
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
        popularity.setText(currentMovie.getPopularity());
        imdbPage.setImageResource(R.mipmap.ic_imdb);
        originalTitle.setText(currentMovie.getOriginalTitle());
        originalLanguage.setText(currentMovie.getOriginalLanguage());
        releaseDate.setText(currentMovie.getReleaseDate());
        revenue.setText(String.format(Locale.getDefault(), "%,d", currentMovie.getRevenue()) +
                getString(R.string.currency));
        runTime.setText(String.valueOf(currentMovie.getRuntime()) + getString(R.string.minute));
        currentStatus.setText(currentMovie.getStatus());
        votes.setText(String.format(Locale.getDefault(), "%,d", currentMovie.getVoteCount()));
    }

    private void setCompanies() {
        List<Company> companyList = currentMovie.getProductionCompanies();
        companiesLayout.removeAllViews();

        if (companyList.size() > 1)
            companies.setText(getString(R.string.production_companies));
        else
            companies.setText(getString(R.string.production_company));

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < companyList.size(); i++) {
            View view = inflater.inflate(R.layout.item_company, companiesLayout, false);
            TextView companyName = view.findViewById(R.id.companyName_companyItem);
            CircleImageView logo = view.findViewById(R.id.company_logo);
            companyName.setText(currentMovie.getProductionCompanies().get(i).getName());
            loadWithPicasso(logo, currentMovie.getProductionCompanies().get(i).getLogoPath());
            companiesLayout.addView(view);
        }
    }

    private void updateAdultContentWarning() {
        if (currentMovie.getAdult()) {
            adultContent.setImageResource(R.mipmap.adult);
            adultContent.setBackgroundResource(R.drawable.red_rounded_background);
        } else {
            adultContent.setImageResource(R.mipmap.family);
            adultContent.setBackgroundResource(R.drawable.primary_rounded_background);
        }
    }

    @OnClick(R.id.adultContent_detailsFragment_imageView)
    public void adultButtonClicked() {

        String message = currentMovie.getAdult() ? getString(R.string.this_film_contains_adult_contents) :
                getString(R.string.this_film_doesnt_contain_adult_contents);
        showAlertDialog(message);
    }

    private void showAlertDialog(String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
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

    private void loadWithPicasso(final ImageView into, final String url) {
        //if url is empty that means we don't have the url simply return.
        if (url.equalsIgnoreCase(""))
            return;
        //We will try to fetch offline data if it is possible.
        //If no we will fetch it from URL
        Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(into, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.w("Picasso", url + ": Image loaded from cache");
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
                                        Log.w("Picasso", url + ": couldn't download the image.");
                                    }
                                });
                    }
                });
    }

    private void setUpActionBar() {
        MainActivity mainActivity = ((MainActivity) getActivity());
        View actionBarRoot = mainActivity.getSupportActionBar().getCustomView();
        TextView actionBarTitle = actionBarRoot.findViewById(R.id.action_bar_title);
        actionBarTitle.setText(currentMovie.getTitle());
        actionBarTitle.setEms(16);
        actionBarTitle.setMaxLines(1);
        actionBarTitle.setEllipsize(TextUtils.TruncateAt.END);
    }

    public interface MovieDetailsFragmentCallback{
        Genre getGenreFromRealm(String id);
    }

}
