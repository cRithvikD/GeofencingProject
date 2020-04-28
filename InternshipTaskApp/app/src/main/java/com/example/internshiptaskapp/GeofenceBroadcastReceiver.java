package com.example.internshiptaskapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GeofenceBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Geofence triggered", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: Geofence triggered");
        System.out.println("Geofence triggered");
        GeofenceHelper.score = GeofenceHelper.score + 10;
        System.out.println(GeofenceHelper.score);
        System.out.println(GeocodingLocation.latUser);
        System.out.println(GeocodingLocation.lonUser);



        //NotificationHelper notificationHelper = new NotificationHelper(context);
       /* GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()){
            Log.d(TAG, "onReceive: Error receiving Geofence event");
            return;

        }

       List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence : geofenceList){
            Log.d(TAG, "onReceive: list  " +geofence.getRequestId());
        }
 //       Location location = geofencingEvent.getTriggeringLocation();
        int TransitionType = geofencingEvent.getGeofenceTransition();
        switch(TransitionType){
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
                break;
        }*/
    }
}