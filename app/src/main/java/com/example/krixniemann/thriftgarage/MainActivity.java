package com.example.krixniemann.thriftgarage;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button createBtn, browseBtn, logoutBtn;
    TextView tvmenu, tvoption;

    private GoogleApiClient client;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() { //initialized mAuthListener

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //track the user when they sign in or out using the firebaseAuth
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Log.d("CSS3334", "onAuthStateChanged - User NOT is signed in");
                    Intent signInIntent = new Intent(getBaseContext(), AuthActivity.class);
                    startActivity(signInIntent);
                }
            }
        };


        createBtn = (Button) findViewById(R.id.createButton);
        browseBtn = (Button) findViewById(R.id.browseButton);
        logoutBtn  = (Button) findViewById(R.id.LogoutButton);
        setupButtonClickEvents();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupButtonClickEvents() {
        /**
         *   Set up button click event listener for the web info button for the first performance
         */


        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);

            }
        });
        browseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent1);

            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onStop();
                Intent intent1 = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent1);

            }
        });


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            AppIndex.AppIndexApi.end(client, getIndexApiAction());
            client.disconnect();
        }
    }
}
