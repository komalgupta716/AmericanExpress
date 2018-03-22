package com.amex;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;


public class GeofenceTransitionsIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GeofenceTransitionsIntentService(String name) {
        super(name);
    }

    public GeofenceTransitionsIntentService() {
        super("empty constructor");
    }

    private static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();
    private ArrayList<Venue> mVenues;

    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    private static final String HUNDRED_METRE_GEOFENCE_KEY = "100m";
    public static final String ACTION_PAY = "pay";


    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        AmexApp appState = (AmexApp) getApplicationContext();
        mVenues = appState.mVenues;

        if (geofencingEvent.hasError()) {
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        Log.d("GeofencingService", "Received geofencing event");

        //TODO Request 3
        //TODO Remove exit code
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            for (Geofence geofence : triggeringGeofences) {
                sendNotification(geofence.getRequestId());

                String requestId = geofence.getRequestId();
                Log.d("dfs", requestId);
            }

        } else {
            // Log the error.
            Log.e(TAG, "Geofence transition invalid type " +
                    geofenceTransition);
        }
    }

    private void sendNotification(String id) {
        Intent intent = new Intent
                (this, DetailActivity.class)
                .setAction(ACTION_PAY);

        intent.putExtra("VENUE",mVenues.get(Integer.parseInt(id)).getmId());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);




        String message = mVenues.get(Integer.parseInt(id)).getmAddress() +  "\n" +
                mVenues.get(Integer.parseInt(id)).getmOffer();
        String action = "Open";
        sendNotification(contentIntent, message, action, 1);
    }


    private void sendNotification(PendingIntent contentIntent, String message, String action, int notifId) {

        Log.d("DDS", "Sending notification " + message);
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Payment location nearby")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setContentText(message)
                        .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher_round, action, contentIntent))
                        .setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notifId, mBuilder.build());
    }
}
