package com.simplertutorials.android.milestonev2;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

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
        if (movieLoadingDialog != null)
            movieLoadingDialog.dismiss();
    }

}
