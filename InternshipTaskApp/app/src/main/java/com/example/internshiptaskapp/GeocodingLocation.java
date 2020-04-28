package com.example.internshiptaskapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GeocodingLocation {

    private static final String TAG = "GeocodingLocation";
    public  Address address1;
    public   static double latUser, lonUser;
   // public TextView distance1;
    //public  static float[] resultsF;
    //public Address address;


    public  void getAddressFromLocation(final String locationAddress,
                                        final Context context, final Handler handler) {
        final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Thread thread = new Thread() {
            @Override
            public void run() {

                String result = null;
                //String result;
                try {
                    List
                            addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        address1 = (Address) addressList.get(0);
                        latUser = address1.getLatitude();
                        lonUser = address1.getLongitude();
                        System.out.println(latUser );
                        System.out.println(lonUser );


                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }
            }
        };
        thread.start();
    }
}