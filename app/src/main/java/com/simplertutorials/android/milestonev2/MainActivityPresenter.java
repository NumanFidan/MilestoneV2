package com.simplertutorials.android.milestonev2;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.simplertutorials.android.milestonev2.DataHolder.ApiRequest;
import com.simplertutorials.android.milestonev2.DataHolder.DataHolder;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivtyMVP;

import org.json.JSONException;

import java.net.MalformedURLException;

public class MainActivityPresenter implements MainActivtyMVP.Presenter{

    private final MainActivtyMVP.View view;

    public MainActivityPresenter(MainActivtyMVP.View view) {
        this.view = view;
    }

    public void setUpFireStore() {
        // Enabling local data (Cache) for firestore to use data efficiently.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void fetchGenreList() {
        Thread thread = new Thread(() -> {
            try {
                DataHolder.getInstance().setGenreMap(ApiRequest.getInstance()
                        .fetchGenreList(view.getLanguageString()));
            } catch (MalformedURLException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }
}
