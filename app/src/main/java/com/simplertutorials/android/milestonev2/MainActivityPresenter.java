package com.simplertutorials.android.milestonev2;

import com.simplertutorials.android.milestonev2.data.api.ApiClient;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.data.DataHolder;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.domain.Genre;
import com.simplertutorials.android.milestonev2.domain.GenresResponse;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivityMVP;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private final MainActivityMVP.View view;
    private boolean realmInitialized = false;

    public MainActivityPresenter(MainActivityMVP.View view) {
        this.view = view;
    }

    public void initializeRealm(){
        Realm.init(view.getContextFromMainActivity());
        realmInitialized = true;
    }

    public void fetchGenreList() {
        if (!realmInitialized)
            initializeRealm();

        Realm realm = Realm.getDefaultInstance();

        Genre genre = realm.where(Genre.class).findFirst();

        if (genre == null)
            fetchGenreListFromApi();

    }

    private void fetchGenreListFromApi() {

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<GenresResponse> genresResponseCall = apiService.fetchGenres(
                DataHolder.getInstance().getApiKey(),
                view.getLanguageString());

        genresResponseCall.enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {

                //Saving all genre data to ArrayList in DataHolder for further usages

                if (response.body() != null)
                    for (int i=0; i<response.body().getGenreList().size();i++)
                        writeGenreToRealm(response.body().getGenreList().get(i));

            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {

            }
        });

    }

    private void writeGenreToRealm(Genre genre) {
        RealmService.getInstance().writeGenreToRealm(genre);
    }
}
