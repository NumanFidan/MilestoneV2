package com.simplertutorials.android.milestonev2.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.simplertutorials.android.milestonev2.DataHolder.ApiRequest;
import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;
import com.simplertutorials.android.milestonev2.MainActivity;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.ui.adapters.MovieListRecyclerViewAdapter;
import com.simplertutorials.android.milestonev2.ui.interfaces.MovieClickListener;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements MovieClickListener {

    private static final int NOTIFY_ADAPTER_DATA_CHANGE = 100;
    private static final int NOTIFY_CONNECTION_ERROR = 101;
    private ArrayList<Movie> movieArrayList;
    private MovieListRecyclerViewAdapter adapter;
    private RecyclerView movieRecyclerView;
    private int currentRequestedPage = 0;
    private Handler updateHandler;
    private ProgressDialog movieLoadingDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        movieArrayList = new ArrayList<>();
        adapter = new MovieListRecyclerViewAdapter(getContext(), movieArrayList, this);

        setUpRecyclerView(view);
        loadNextPage();

        return view;
    }

    private void setUpRecyclerView(View view) {
        movieRecyclerView = view.findViewById(R.id.movieListRecyclerView);
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
                        if (ApiRequest.getInstance().getNumberOfAvailablePage() > currentRequestedPage)
                            loadNextPage();
                    }
                }
            }
        });

    }

    private void loadNextPage() {
        currentRequestedPage++;
        showProgressDialogToUser("Loading Movies...");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    movieArrayList.addAll(ApiRequest.getInstance()
                            .requestPopularMovies(currentRequestedPage,
                                    DataHolder.getInstance().APP_LANGUAGE));
                    //If genres not fetched correctly on MainActivity then we will collect it here
                    if (DataHolder.getInstance().getGenreMap().size() < 1) {
                        Log.w("DataHolderHomeFragment", "DataHolder was empty, fetching" +
                                "data from Apı Server");
                        DataHolder.getInstance().setGenreMap(ApiRequest.getInstance()
                                .fetchGenreList(getString(R.string.languageCodeForApı)));
                    }

                    Message handlerMessage = new Message();
                    handlerMessage.what = NOTIFY_ADAPTER_DATA_CHANGE;
                    updateHandler.sendMessage(handlerMessage);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    dismissLoadingDialog();

                    Message handlerMessage = new Message();
                    handlerMessage.what = NOTIFY_CONNECTION_ERROR;
                    updateHandler.sendMessage(handlerMessage);
                }
            }
        });
        thread.start();
    }

    private void showConnectionErrorDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        String message = "Can not fetch movies from server.";
        alertDialog.setMessage(message);
        alertDialog.setTitle(null);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentRequestedPage--;
                        loadNextPage();
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

    private void showProgressDialogToUser(String message) {
        movieLoadingDialog = new ProgressDialog(getContext());
        movieLoadingDialog.setTitle(null);
        movieLoadingDialog.setMessage(message);
        movieLoadingDialog.setCancelable(false);
        movieLoadingDialog.show();
    }

    private void dismissLoadingDialog() {
        movieLoadingDialog.dismiss();
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onResume() {
        super.onResume();

        updateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == NOTIFY_ADAPTER_DATA_CHANGE) {
                    adapter.notifyDataSetChanged();
                    dismissLoadingDialog();
                } else if (msg.what == NOTIFY_CONNECTION_ERROR) {
                    showConnectionErrorDialog();
                }
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMovieItemClick(Movie movie, View itemView) {
        showProgressDialogToUser("Getting Movie From Server");

        getDetailsFromFirestore(movie.getId(), Source.CACHE, true, true, itemView);
    }


    private void getDetailsFromFirestore(final String id, Source source,
                                         final boolean tryFirestoreServer, final boolean tryAPI,
                                         final View clickedItemView) {
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

                            Log.w("Firestore",
                                    currentMovie.getTitle() + ": Movie fetched from firestore isCache?"
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
                            getDetailsFromFirestore(id, Source.SERVER, false,
                                    true, clickedItemView);
                        else if (tryAPI)
                            getDetailsFromAPI(id, clickedItemView);
                    }
                });
    }

    private void uploadMovieToFirestore(Movie currentMovie) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Movies")
                .document(currentMovie.getId())
                .set(currentMovie);
    }

    private void getDetailsFromAPI(final String id, final View itemView) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Movie currentMovie = ApiRequest.getInstance().getMovieById(Integer.parseInt(id),
                            getContext().getString(R.string.languageCodeForApı));
                    //Upload movie to Firestore so we can fetch this movie firestore later.
                    uploadMovieToFirestore(currentMovie);
                    movieFetchSuccessfully(currentMovie, itemView);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    dismissLoadingDialog();
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "Unable to fetch data from API!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        thread.start();
    }

    private void movieFetchSuccessfully(Movie currentMovie, final View itemView) {
        //Update Detailed movie at the Data Holder so we can access it from DetailsFragment
        DataHolder.getInstance().setDetailedMovie(currentMovie);

        //Load backdrop image with Picasso to cache
        Picasso.get().load(currentMovie.getBackdropUrl()).fetch(new Callback() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
            }
        });
        //In case if user returns, load everything from beginning.
        currentRequestedPage = 0;
        //ReplaceFragment
        Fragment fragment = new MovieDetailsFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            replaceFragmentWithExplodeAnimation(itemView, fragment);
        } else {
            replaceFragment(fragment);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void replaceFragmentWithExplodeAnimation(View clickedView, final Fragment fragment) {
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

    private void replaceFragment(Fragment fragment) {
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
