package com.simplertutorials.android.milestonev2.data.database;

import android.content.Context;

import com.simplertutorials.android.milestonev2.ui.activities.ContextModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module (includes = {ContextModule.class})
public class DatabaseModule {

    @Singleton
    @Provides
    public Realm realmInitialize(Context context){

        Realm.init(context);
        return Realm.getDefaultInstance();
    }
}
