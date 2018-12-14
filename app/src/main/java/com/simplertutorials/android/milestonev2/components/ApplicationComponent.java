package com.simplertutorials.android.milestonev2.components;

import com.simplertutorials.android.milestonev2.ui.activities.MainActivity;
import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ContextModule.class})
public interface ApplicationComponent {

    void inject(HomeFragment homeFragment);
    void inject(MovieDetailsFragment movieDetailsFragment);
    void inject(MainActivity mainActivity);

}
