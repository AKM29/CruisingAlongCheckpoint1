package com.example.alex.cruisingalong;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient client;
    private LocationRequest request;

    private String Location;
    private Location cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener(){
            //onclick event
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlacesActivity.class);
                intent.putExtra("Latitude", cur.getLatitude());
                intent.putExtra("Longitude", cur.getLongitude());
                startActivity(intent);
            }
        });


        //view location click and listener
        //opens places activity and sets intention to view location
        final ImageButton imageButtonViewLocation = (ImageButton) findViewById(R.id.imageButtonViewLocation);
        assert imageButtonViewLocation != null;
        imageButtonViewLocation.setOnClickListener(new View.OnClickListener() {
            //click event
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("Type", 2);
                Toast.makeText(getApplicationContext(), "View Location", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        //places button click and listener
        //opens places activity and sets intention to places
        final ImageButton imageButtonPlaces = (ImageButton) findViewById(R.id.imageButtonPlaces);
        assert imageButtonPlaces != null;
        imageButtonPlaces.setOnClickListener(new View.OnClickListener() {
            //click event
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("Intention", 3);
                startActivity(intent);
            }
        });

        //tour button click and listener
        //
        final ImageButton imageButtonTour = (ImageButton) findViewById(R.id.imageButtonTour);
        assert imageButtonTour != null;
        imageButtonTour.setOnClickListener(new View.OnClickListener(){
            //click event
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ToursActivity.class);
                startActivity(intent);
            }
        });

        //feedback button and listener
        //
        final ImageButton imageButtonFeedback = (ImageButton) findViewById(R.id.imageButtonFeedBack);
        assert imageButtonFeedback != null;
        imageButtonFeedback.setOnClickListener(new View.OnClickListener(){
            //click event
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        //chat button and listener
        final ImageButton imageButtonChat = (ImageButton) findViewById(R.id.imageButtonChat);
        assert imageButtonChat != null;
        imageButtonChat.setOnClickListener(new View.OnClickListener(){
            //click event
            public void onClick(View v ) {
                Intent intent = new Intent(getApplication(), ChatActivity.class);
                startActivity(intent);
            }
        });

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)          //2 second
                .setFastestInterval(2000);  //2 second
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

        //check if app has permission to get location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        client.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(client.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
            client.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Latitude" + location.getLatitude() + "Longitude" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        cur = location;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String locale = address.get(0).getSubLocality() + ", " + address.get(0).getLocality();

            //TextView textView = (TextView) findViewById(R.id.textViewDisplay);
            //textView.setText(locale);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


