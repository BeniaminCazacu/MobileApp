package com.example.mylocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class FusedLocationProvider extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    protected static final String KEY = "1";
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fused_location_provider);

        Intent intent = getIntent();
        createLocationRequest();
        getLastLocation();

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                if(locationResult == null){
                    return;
                }
                //foreach(Location location: locationResult.getLocations(){...}
                else{
                    Location location = locationResult.getLastLocation();
                    latitude = Double.toString(location.getLatitude());
                    longitude = Double.toString(location.getLongitude());
                    printCoordinates();
                }
            }
        };
    }

    //Get last location
    private void getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mCurrentLocation = location;
                        latitude = Double.toString(location.getLatitude());
                        longitude = Double.toString(location.getLongitude());
                    } else {
                        mCurrentLocation = location;
                    }
                }
            });
        }
    }

    //Print location
    protected void printCoordinates() {
        TextView textView = findViewById(R.id.textViewGoogleLocationApiFusedCoordinates);
        textView.setText("Fused Location Provider" + "\nLatitude: " + latitude + "\nLongitude: " + longitude);
    }

    /* Get a location request
     * set intervals and priority
     */
    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); //5s
        locationRequest.setFastestInterval(5000); //5s
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //On Resume
    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    //Start location updates
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    //On Pause
    @Override
    protected void onPause(){
        super.onPause();
        stopLocationUpdates();
    }

    //Stop location updates
    protected void stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    //Send location to Google Map
    protected void sendLocationToMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        String[] coordinates = new String[2];
        coordinates[0] = latitude;
        coordinates[1] = longitude;
        intent.putExtra(KEY, coordinates);
        startActivity(intent);
    }
}
