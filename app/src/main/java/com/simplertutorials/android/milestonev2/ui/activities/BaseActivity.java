package com.simplertutorials.android.milestonev2.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.simplertutorials.android.milestonev2.R;

public class BaseActivity extends AppCompatActivity {

    public Context getContext() {
        return this;
    }

    public String getLanguageString() {
        return getContext().getString(R.string.languageCodeForApı);
    }
 
}
