package com.simplertutorials.android.milestonev2.ui.fragments;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.simplertutorials.android.milestonev2.R;

public class BaseFragment extends Fragment {

    private ProgressDialog movieLoadingDialog;

    public String getLanguageString() {
        return getString(R.string.languageCodeForApı);
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
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT).show();
    }
}
