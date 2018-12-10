package com.simplertutorials.android.milestonev2.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.MainActivity;
import com.simplertutorials.android.milestonev2.MilestoneApplication;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.domain.PopularMovie;
import com.simplertutorials.android.milestonev2.ui.adapters.MovieListRecyclerViewAdapter;
import com.simplertutorials.android.milestonev2.ui.interfaces.HomeFragmentMVP;
import com.simplertutorials.android.milestonev2.ui.interfaces.MovieClickListener;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements MovieClickListener, HomeFragmentMVP.View {

    private ArrayList<PopularMovie> movieArrayList;
    private MovieListRecyclerViewAdapter adapter;

    @BindView(R.id.movieListRecyclerView)
    RecyclerView movieRecyclerView;

    private ProgressDialog movieLoadingDialog;
    private HomeFragmentMVP.Presenter presenter;

    @Inject
    public ApiService apiService;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MilestoneApplication) getActivity().getApplicationContext()).getCompenent().inject(this);

        presenter = new HomeFragmentPresenter(this, apiService);

        setUpActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);



        movieArrayList = new ArrayList<>();
        adapter = new MovieListRecyclerViewAdapter(getContext(), movieArrayList, this);

        setUpRecyclerView();
        presenter.loadNextPage(movieArrayList);

        return view;
    }

    private void setUpRecyclerView() {
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setAdapter(adapter);

        //This is for endless RecyclerView effect.

        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dy is positive if user scrolling down.
                if (dy > 0) {
                    //we need to check if user reach at the end of the RecyclerView
                    //if user is not able to scroll down this means its end of the RecyclerView
                    if (!recyclerView.canScrollVertically(View.FOCUS_DOWN)) {
                        //if we still have results to load from API, load next page
                        presenter.loadNextPage(movieArrayList);
                    }
                }
            }
        });

    }


    @Override
    public void dataChangedRecyclerView() {
        adapter.notifyDataSetChanged();
        dismissLoadingDialog();
    }

    @Override
    public void showConnectionErrorDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        String message = "Can not fetch movies from server.";
        alertDialog.setMessage(message);
        alertDialog.setTitle(null);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.loadNextPage(movieArrayList);
                        alertDialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMovieItemClick(PopularMovie movie, View itemView) {
        showProgressDialogToUser("Getting Movie From Server");

        presenter.getDetailsOfMovie(movie.getId(), itemView);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void replaceFragmentWithExplodeAnimation(View clickedView, final Fragment fragment) {
        // save rect of view in screen coordinated
        final Rect viewRect = new Rect();
        clickedView.getGlobalVisibleRect(viewRect);

        TransitionSet set = new TransitionSet()
                .addTransition(new Explode().setEpicenterCallback(new Transition.EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                }).excludeTarget(clickedView, true))
                .addTransition(new Fade().addTarget(clickedView))
                .addListener(new Transition.TransitionListenerAdapter() {
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        transition.removeListener(this);
                        replaceFragment(fragment);
                    }
                });
        TransitionManager.beginDelayedTransition(movieRecyclerView, set);

        // remove all views from Recycler View
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                movieRecyclerView.setAdapter(null);
                // Stuff that updates the UI

            }
        });
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        MainActivity mainActivity = (MainActivity) getContext();

        android.support.transition.Fade enteringFade = new android.support.transition.Fade();
        enteringFade.setStartDelay(500);
        enteringFade.setDuration(500);
        fragment.setEnterTransition(enteringFade);
        mainActivity.changeFragment(R.id.content_main, fragment);
    }

    private void setUpActionBar() {
        MainActivity mainActivity = ((MainActivity) getActivity());
        View actionBarRoot = mainActivity.getSupportActionBar().getCustomView();
        TextView actionBarTitle = actionBarRoot.findViewById(R.id.action_bar_title);
        actionBarTitle.setText(getString(R.string.popular_movies));
        actionBarTitle.setEms(16);
        actionBarTitle.setMaxLines(1);
        actionBarTitle.setEllipsize(TextUtils.TruncateAt.END);
    }

}
