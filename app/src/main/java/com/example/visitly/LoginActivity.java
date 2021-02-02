package com.example.visitly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    //    AUTH RELATED VARIABLES
    static FirebaseUser user;
    static final int RC_SIGN_IN = 123;
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // AUTHENTICATION RELATED
        FirebaseApp.initializeApp(this);
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Choose authentication providers
        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

          /* Initializing  Auth Listener
           RUNS EVERY TIME WHEN ACTIVITY OR APP OPENS */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                /* USER IS LOGGED IN */
                if (user != null) {

                    Toast.makeText(LoginActivity.this, "SIGNED IN", Toast.LENGTH_SHORT).show();

                    /* START MAIN APP */
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    /* IF NO USER IS SIGNED IN THEN Create and launch sign-in intent */
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(), RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();

                /* START MAIN APP */
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Attach Listener on activity running */
    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.addAuthStateListener(mAuthStateListener);
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

    /* Detach Listener on activity not running */
    @Override
    protected void onPause() {
        super.onPause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.removeAuthStateListener(mAuthStateListener);
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


}