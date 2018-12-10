package com.simplertutorials.android.milestonev2;

import android.support.multidex.MultiDexApplication;

import com.simplertutorials.android.milestonev2.ui.activities.ContextModule;

public class MilestoneApplication extends MultiDexApplication {

    private ApplicationCompenent applicationCompenent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationCompenent = DaggerApplicationCompenent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ApplicationCompenent getCompenent(){
        return applicationCompenent;
    }
}
