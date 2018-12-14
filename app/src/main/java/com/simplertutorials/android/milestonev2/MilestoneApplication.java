package com.simplertutorials.android.milestonev2;

import android.support.multidex.MultiDexApplication;

import com.simplertutorials.android.milestonev2.components.ApplicationComponent;
import com.simplertutorials.android.milestonev2.components.ContextModule;
import com.simplertutorials.android.milestonev2.components.DaggerApplicationComponent;

public class MilestoneApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ApplicationComponent getCompenent(){
        return applicationComponent;
    }
}
