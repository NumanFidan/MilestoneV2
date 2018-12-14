package com.simplertutorials.android.milestonev2.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.simplertutorials.android.milestonev2.R;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog movieLoadingDialog;

    public Context getContext() {
        return this;
    }

    public String getLanguageString() {
        return getContext().getString(R.string.languageCodeForApı);
    }

    public void showProgressDialogToUser(String message) {
        if (movieLoadingDialog == null)
            movieLoadingDialog = new ProgressDialog(getContext());

        movieLoadingDialog.setTitle(null);
        movieLoadingDialog.setMessage(message);
        movieLoadingDialog.setCancelable(false);
        movieLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        movieLoadingDialog.dismiss();
    }

    public void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT).show();
    }
}
