package com.amex;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();

    @BindView(R.id.venues)
    RelativeLayout mVenuesButton;

    @BindView(R.id.online)
    RelativeLayout mOnlineButton;

    private static final String HUNDRED_METRE_GEOFENCE_KEY = "100m";
    private ArrayList<Venue> mVenues;

    private FusedLocationProviderClient mFusedLocationClient;

    private static final int PERMISSION_REQUEST_KEY = 123;
    private PendingIntent mGeofencePendingIntent;

    private GeofencingClient mGeofencingClient;
    private ArrayList<Geofence> mGeofenceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (!checkPermission()){
            requestPermission();
        }


        mVenuesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, VenuesActivity.class);
                        intent.putParcelableArrayListExtra("VENUES", mVenues);
                        startActivity(intent);
                    }
                }
        );

        mOnlineButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void proceedWithGeofence(){
        AmexApp appState = (AmexApp) getApplicationContext();
        mVenues = appState.mVenues;

        if (mVenues.isEmpty()){

            appState.mVenues.add(new Venue("Shopper Stop- D227 Anand Vihar , New Delhi-110033",
                    "10% cashback on shopping above Rs-4999",
                    "Anjali Jain", 28.6523086, 77.3152082,0));
            appState.mVenues.add(new Venue("McDonalds - 342, Main Market, Dwarka sector-12 , New Delhi -110021",
                    "", "NSIT", 28.608052, 77.036187,1));
            appState.mVenues.add(new Venue("Dominos- Plot No 54, East Punjabi Bagh , New Delhi 110026",
                    "20% cashback upto Rs-100",
                    "Ma2yank",
                    28.667589, 77.141083,2));
            appState.mVenues.add(new Venue("Stanmax, 1st Floor, Unity Mall , Shalimar Bagh , New Delhi-110023",
                    "",
                    "Simran Bedi", 28.7075
                    , 77.1677,3));
            appState.mVenues.add(new Venue("Taco Bell  DDA main market, Hauz Khas New Delhi-110016",
                    "5% cashback upto Rs-200",
                    " Aditya Gupta", 28.538144, 77.222046,4));

            mVenues = appState.mVenues;

        }

        mGeofenceList = new ArrayList<>();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mGeofencingClient = LocationServices.getGeofencingClient(this);

        setGeofences();

    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_KEY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_KEY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    proceedWithGeofence();

                } else {

                    Toast.makeText(this, "Location is needed for the app to function", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void setGeofences() throws SecurityException {

        mGeofencingClient.removeGeofences(getGeofencePendingIntent());

        for (Venue venue : mVenues) {

            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(String.valueOf(venue.getmId()))
                    .setCircularRegion(
                            venue.getmLat(),
                            venue.getmLong(),
                            100)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());

            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Geofences added
                            // ...
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to add geofences
                            // ...
                        }
                    });

        }

    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().

        //TODO Check request code
        mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

}
