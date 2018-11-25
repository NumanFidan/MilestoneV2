package com.simplertutorials.android.milestonev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;
import com.simplertutorials.android.milestonev2.ui.interfaces.MainActivtyMVP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements  MainActivtyMVP.View{

    private MainActivityPresenter presenter;

    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
        presenter.setUpFireStore();
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

    @Override
    public String getLanguageString() {
        return getString(R.string.languageCodeForApı);
    }
}
