package com.simplertutorials.android.milestonev2;

import android.os.Bundle;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.simplertutorials.android.milestonev2.DataHolder.ApiRequest;
import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;
import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;

import org.json.JSONException;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActionBar();
        setUpFireStore();
        fetchGenreList();

        changeFragment(R.id.content_main, new HomeFragment());

    }

    private void fetchGenreList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataHolder.getInstance().setGenreMap(ApiRequest.getInstance()
                            .fetchGenreList(getString(R.string.languageCodeForApı)));
                } catch (MalformedURLException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    private void setUpFireStore() {
        // Enabling local data (Cache) for firestore to use data efficiently.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();

        TextView title = view.findViewById(R.id.action_bar_title);
        title.setText(getString(R.string.popular_movies));
        //Look here
        ImageView logIcon = view.findViewById(R.id.action_bar_log_icon);
        logIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void changeFragment(int containerId, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        //If we are at Details Fragment we will change fragment with Home Fragment.
        //If we are already at HomeFragment than we will app super.
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if (fragment instanceof MovieDetailsFragment)
            changeFragment(R.id.content_main, new HomeFragment());
        else
            super.onBackPressed();
    }
}
