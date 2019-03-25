package com.simplertutorials.android.milestonev2.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.MilestoneApplication;
import com.simplertutorials.android.milestonev2.R;
import com.simplertutorials.android.milestonev2.data.api.ApiService;
import com.simplertutorials.android.milestonev2.data.database.RealmService;
import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivityMVP;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements  MainActivityMVP.View{

    private MainActivityPresenter presenter;

    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;

    @Inject
    RealmService realmService;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MilestoneApplication)getApplicationContext()).getCompenent().inject(this);

        presenter = new MainActivityPresenter(this, realmService, apiService);
        presenter.fetchGenreList();

        setUpActionBar();
        changeFragment(R.id.content_main, new HomeFragment());


    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();

        ButterKnife.bind(this, view);
        actionBarTitle.setText(getString(R.string.popular_movies));
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

    public MainActivityPresenter getPresenter() {
        return presenter;
    }
}
