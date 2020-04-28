package com.example.internshiptaskapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;

public class GeofenceHelper extends ContextWrapper {

    private static final String TAG = "GeofenceHelper";
    PendingIntent pendingIntent;
   public  static int score =-10;
    public GeofenceHelper(Context base) {

        super(base);
    }

    public GeofencingRequest getGeofencingRequest(Geofence geofence){
        return new GeofencingRequest.Builder()
                   .addGeofence(geofence)
                   .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                   .build();
    }

    public Geofence getGeofence(String ID, double lat, double lon, float radius, int transitionTypes){
         lat = GeocodingLocation.latUser;
         lon = GeocodingLocation.lonUser;
        return new Geofence.Builder()
                    .setCircularRegion(lat, lon,radius)
                    .setRequestId(ID)
                    .setTransitionTypes(transitionTypes)
                    .setLoiteringDelay(5000)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
    }

    public PendingIntent getPendingIntent(){
        if(pendingIntent!=null){
            score = score -10;
            System.out.println(score);

            return pendingIntent;
        }
/*        AlarmManager alarmManager;
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);*/
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,6969, intent, PendingIntent.FLAG_UPDATE_CURRENT);
/*
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),10000, pendingIntent);// Calls the broadcaster every 10 minutes
*/

        return pendingIntent;
    }

    public  String getErrorString (Exception e){
        if(e instanceof ApiException){
            ApiException apiException = (ApiException)e;
            switch (apiException.getStatusCode()){
                case GeofenceStatusCodes
                    .GEOFENCE_NOT_AVAILABLE:
                    return "GEOFENCE_NOT_AVAILABLE";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_GEOFENCES:
                    return "GEOFENCE_TOO_MANY_GEOFENCES";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "GEOFENCE_TOO_MANY_PENDING_INTENTS";

            }
        }
        return  e.getLocalizedMessage();
    }
}


