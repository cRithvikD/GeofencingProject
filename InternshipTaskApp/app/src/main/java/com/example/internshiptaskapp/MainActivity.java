package com.example.internshiptaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    Button Search, Fence, timerBtn;
    EditText address;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;
    public static double latitudeCurrent,longitudeCurrent,la, lo;
    public static float radius = 100;///////////////////////////////////////////////////////////////////////
    private String Geofence_ID = "newlol";
    private static final String TAG = "MainActivity";
    //GeocodingLocation locationAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search = findViewById(R.id.btn1);
        address = findViewById(R.id.enterAddress);
        Fence = findViewById(R.id.btn2);
        timerBtn = findViewById(R.id.btn3);

        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
        la = GeocodingLocation.latUser;
        lo = GeocodingLocation.lonUser;

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String address1 = address.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address1,
                        getApplicationContext(), new GeocoderHandler());

            }
        });

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Timer.class));
            }
        });

        Fence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGeofence(GeocodingLocation.latUser,GeocodingLocation.lonUser,radius);
            }
        });

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );

        } else {
            getCurrentLocation();

        }



    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                  //  Log.d("latttt",locationAddress);
                    break;
                default:
                    locationAddress = null;
            }
          //  textView.setText(locationAddress);
        }
    }

    //For address
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Location getCurrentLocation() {
        final Location location  = new Location("providerNA");
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            latitudeCurrent = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitudeCurrent = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            System.out.println("Device lat and long are " + latitudeCurrent + ' ' + longitudeCurrent);

                         /* textLatLong.setText(
                                    String.format(
                                            "Latitude: %s\n Longitude: %s",
                                            latitude,
                                            longitude
                                    )
                            );*/


                          //  location.setLatitude(latitude);
                          //  location.setLongitude(longitude);



                            //Sending latitude and longitude to MapsActivity
                            /////
                           /* Intent lati = new Intent(getApplicationContext(),MapsActivity.class);
                            lati.putExtra("latit", latitude);
                            startActivity(lati);

                            Intent longi = new Intent(getApplicationContext(),MapsActivity.class);
                            longi.putExtra("longit", longitude);
                            startActivity(longi);*/
                            /////
                        }
                    }
                }, Looper.getMainLooper());
        return location;
    }

    private void addGeofence( double la,double lo, float radius){
        Geofence geofence = geofenceHelper.getGeofence(Geofence_ID, la,lo, radius,Geofence.GEOFENCE_TRANSITION_DWELL  | Geofence.GEOFENCE_TRANSITION_ENTER );
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        AlarmManager alarmManager;
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),600000, pendingIntent);
        geofencingClient.addGeofences(geofencingRequest,pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Geofence " + GeocodingLocation.latUser);
                        System.out.println("Geofence " + GeocodingLocation.lonUser);
                        Log.d(TAG, "onSuccess: Geofence Added...");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " +errorMessage);

                    }
                });
    }

}
