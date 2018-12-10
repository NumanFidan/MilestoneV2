package com.simplertutorials.android.milestonev2;

import android.support.multidex.MultiDexApplication;

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
