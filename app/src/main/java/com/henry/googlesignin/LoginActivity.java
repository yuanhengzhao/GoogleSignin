package com.henry.googlesignin;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

public class LoginActivity extends FragmentActivity
    implements ConnectionCallbacks, OnConnectionFailedListener{


    // Request code to use when launching the resolution activity         GetUsernameTask.AsyncResponse
    private static final int REQUEST_RESOLVE_ERROR = 9001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    protected GoogleApiClient mGoogleApiClient;

    private static int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS;

    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;

    static String token;

    //GetUsernameTask asyncTask =new GetUsernameTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("++++++++++", "onCreate");
        //asyncTask.delegate = this;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("++++++++++", "onStart");
        //if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        //}
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        setContentView(R.layout.activity_login);
        Log.d("++++++++++", "onConnected");
        //Log.d("++++++++++", bundle.toString());
        //GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        //\\googleAPI.isGooglePlayServicesAvailable(this);

        //Log.d("++++++++",GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
       // Log.d("++++++++", accounts[0].type);
//        final String scope = "Oauth2:" + Scopes.PROFILE;
        String token = null;
//        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
//        Log.d("+++++++++++", String.valueOf(accounts.length));
//        try {

        if (ContextCompat.checkSelfPermission(this,
                "android.permission.GET_ACCOUNTS")
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    "android.permission.GET_ACCOUNTS")) {
                Log.d("+++++++++", "Please I don't know what is this");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.GET_ACCOUNTS"},
                        1);
                Log.d("+++++++++这个是requestcode", String.valueOf(MY_PERMISSIONS_REQUEST_GET_ACCOUNTS));
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            // With the account name acquired, go get the auth token    "745833729089-79i7mvk7m4vffpdu16kf95qj1j5qllcc.apps.googleusercontent.com"
            Log.d("+++++++++", "Please");
            AccountManager manager = AccountManager.get(this);
            Account[] accounts = manager.getAccountsByTypeForPackage("com.google", "com.henry.googlesignin");
            Log.d("+++++++++++", String.valueOf(accounts.length));
            Log.d("+++++++++++", String.valueOf(accounts[0]));
            Log.d("+++++++++++", String.valueOf(accounts[1]));
//            Log.d("+++++++++++", String.valueOf(accounts[2]));


            String mScope="oauth2:"+Scopes.PROFILE;
                new GetUsernameTask(LoginActivity.this, accounts[1], mScope).execute();

        }



//        try {
//            token = GoogleAuthUtil.getToken(getApplicationContext(), accounts[1],
//                    "audience:server:client_id:745833729089-79i7mvk7m4vffpdu16kf95qj1j5qllcc.app");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GoogleAuthException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("++++++++",token);

//        Map<String, String> logins = new HashMap<String, String>();
//        Log.d("++++",token);
//        logins.put("accounts.google.com", token);
//        //credentialsProvider.setLogins(logins);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("++++++++++++", "onRequestPErmission");
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty. This 1 is the requestcode.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("++++++++++++", "success");
                    Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
                    Log.d("+++++++++++", String.valueOf(accounts.length));
                    Log.d("+++++++++++", accounts[0].name);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.d("++++++++++++", "Failed");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("++++++++++","suspended");

    }


//    private void pickUserAccount() {
//        String[] accountTypes = new String[]{"com.google"};
//        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
//                accountTypes, false, null, null, null, null);
//        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
//    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("++++++++++", "Failed");
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                Log.d("+++++++++","I want");
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }
    }

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

//    @Override
//    public void processFinish(String output) {
//        token
//    }


    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((LoginActivity) getActivity()).onDialogDismissed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK){
                if ( !mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                //Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
                // With the account name acquired, go get the auth token
                //new GetUsernameTask(this, accounts[0],"audience:server:client_id:745833729089-79i7mvk7m4vffpdu16kf95qj1j5qllcc.app").execute();
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, "yeah", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
