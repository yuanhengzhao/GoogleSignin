package com.henry.googlesignin;

import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by henry on 2016-02-10.
 */
public class GetUsernameTask extends AsyncTask<Void, Void, String> {
    Activity mActivity;
    String mScope;
    Account account;

    GetUsernameTask(Activity activity, Account account, String scope) {
    this.mActivity = activity;
    this.mScope = scope;
    this.account = account ;
    //this.delegate = delegate;
    }




//    public interface AsyncResponse {
//        void processFinish(String output);
//    }
//
//    public AsyncResponse delegate = null;


    /**
 * Executes the asynchronous job. This runs when you call execute()
 * on the AsyncTask instance.
 */
@Override
    protected String doInBackground(Void ... params) {
        try {
        String token = fetchToken();
                if (token != null) {
                return token;
                // **Insert the good stuff here.**
                // Use the token to access the user's Google data.
                }
        } catch (IOException e) {
        // The fetchToken() method handles Google-specific exceptions,
        // so this indicates something went wrong at a higher level.
        // TIP: Check for network connectivity before starting the AsyncTask.
        }
        return null;
    }

/**
 * Gets an authentication token from Google and handles any
 * GoogleAuthException that may occur.
 */
    protected String fetchToken() throws IOException {
        try {
        return GoogleAuthUtil.getToken(mActivity, account, mScope);
        } catch (UserRecoverableAuthException userRecoverableException) {
            Log.e("++++1++++", userRecoverableException.getMessage());
        // GooglePlayServices.apk is either old, disabled, or not present
        // so we need to show the user some UI in the activity to recover.
        //mActivity.handleException(userRecoverableException);
        } catch (GoogleAuthException fatalException) {
            Log.e("+++++2++++",fatalException.getMessage());
        // Some other type of unrecoverable exception has occurred.
        // Report and log the error as appropriate for your app.
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        //delegate.processFinish(result);
        Toast.makeText(mActivity, result,LENGTH_LONG).show();

    }


}
