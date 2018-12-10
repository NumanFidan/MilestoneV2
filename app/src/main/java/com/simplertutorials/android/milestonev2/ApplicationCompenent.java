package com.simplertutorials.android.milestonev2;

import com.simplertutorials.android.milestonev2.ui.fragments.HomeFragment;
import com.simplertutorials.android.milestonev2.ui.fragments.MovieDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DatabaseModule.class, ApiModule.class, ContextModule.class})
public interface ApplicationCompenent {

    void inject(HomeFragment homeFragment);
    void inject(MovieDetailsFragment movieDetailsFragment);
    void inject(MainActivity mainActivity);

}
