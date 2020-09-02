package com.example.mylocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected LocationManager gpsProviderLocationManager;
    protected LocationManager networkProviderLocationManager;
    protected LocationManager passiveProviderLocationManager;
    protected LocationListener gpsLocationListener;
    protected LocationListener networkLocationListener;
    protected LocationListener passiveLocationListener;
    protected TextView gpsProviderCoordinates;
    protected TextView networkProviderCoordinates;
    protected TextView passiveProviderCoordinates;
    protected boolean ACCESS_FINE_LOCATION_PERMISSION_STATE = false;
    protected final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    protected static final String KEY = "1";
    private String latitude;
    private String longitude;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toast.makeText(this, "Activity has been created", Toast.LENGTH_SHORT).show();

        getGpsCoordinates();
        getNetworkCoordinates();
        getPassiveCoordinates();
    }

    //GPS Provider
    protected void getGpsCoordinates() {
        gpsProviderLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String latitude = Double.toString(location.getLatitude());
                String longitude = Double.toString(location.getLongitude());
                printGpsProviderCoordinates(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        //Verificam daca aplicatia are acces la <locatie>
        //Bineinteles ca nu are din moment ce permisiunea este de tipul <dangerous>
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Permisiunea este respinsa
            //Daca utilizatorul a respins anterior cererea de <request>, atunci <shouldShowRequestPermissionRationale> returneaza <true>
            //Daca utilizatorul a respins anterior cererea de <request> si a bifat <Don't ask again>, atunci <shouldShowRequestPermissionRationale> returneaza <false>
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(this, "Accessing your location allows us to determine your time bus arrival. Please allow this functionality.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        else
        {
            //Permisiunea este acceptata
            //urmeaza sa verific daca providerul este disponibil(enabled)
            if(!gpsProviderLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                Toast.makeText(this, "Please turn on your location.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                gpsProviderLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
            }
        }
    }

    //Network Provider
    protected void getNetworkCoordinates()
    {
        networkProviderLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
                printNetworkProviderCoordinates(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        //Verificam daca aplicatia are acces la <locatie>
        //Bineinteles ca nu are din moment ce permisiunea este de tipul <dangerous>
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Permisiunea este respinsa
            //Daca utilizatorul a respins anterior cererea de <request>, atunci <shouldShowRequestPermissionRationale> returneaza <true>
            //Daca utilizatorul a respins anterior cererea de <request> si a bifat <Don't ask again>, atunci <shouldShowRequestPermissionRationale> returneaza <false>
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(this, "Accessing your location allows us to determine your time bus arrival. Please allow this functionality.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        else
        {
            //Permisiunea este acceptata
            //urmeaza sa verific daca providerul este disponibil(enabled)
            if(!networkProviderLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                Toast.makeText(this, "Please turn on your network.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                networkProviderLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
            }
        }
    }

    //Passive Provider
    protected void getPassiveCoordinates()
    {
        passiveProviderLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        passiveLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String latitude = Double.toString(location.getLatitude());
                String longitude = Double.toString(location.getLongitude());
                printPassiveProviderCoordinates(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };


        //Verificam daca aplicatia are acces la <locatie>
        //Bineinteles ca nu are din moment ce permisiunea este de tipul <dangerous>
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Permisiunea este respinsa
            //Daca utilizatorul a respins anterior cererea de <request>, atunci <shouldShowRequestPermissionRationale> returneaza <true>
            //Daca utilizatorul a respins anterior cererea de <request> si a bifat <Don't ask again>, atunci <shouldShowRequestPermissionRationale> returneaza <false>
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(this, "Accessing your location allows us to determine your time bus arrival. Please allow this functionality.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        else
        {
            //Permisiunea este acceptata
            //urmeaza sa verific daca providerul este disponibil(enabled)
            if(!passiveProviderLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
            {
                Toast.makeText(this, "Passive Provider disabled.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                passiveProviderLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, passiveLocationListener);
            }
        }
    }

    //Se afiseaza coordonatele obtinute prin satelit(GPS Provider)
    protected void printGpsProviderCoordinates(String latitude, String longitude)
    {
        gpsProviderCoordinates = findViewById(R.id.textViewAndroidLocationApiGpsCoordinates);
        gpsProviderCoordinates.setText("GPS provider" + "\nLatitude: " + latitude + "\nLongitude: " + longitude);
    }

    //Se afiseaza coordonatele obtinute prin WiFi(Network Provider)
    protected void printNetworkProviderCoordinates(String latitude, String longitude)
    {
        networkProviderCoordinates = findViewById(R.id.textViewAndroidLocationApiNetworkCoordinates);
        networkProviderCoordinates.setText("Network Provider" + "\nLatitude: " + latitude + "\nLongitude: " + longitude);
    }

    //Se afiseaza coordonatele obtinute prin alte surse(Passive Provider)
    protected void printPassiveProviderCoordinates(String latitude, String longitude)
    {
        passiveProviderCoordinates = findViewById(R.id.textViewAndroidLocationApiPassiveCoordinates);
        passiveProviderCoordinates.setText("Passive Provider" + "\nLatitude: " + latitude + "\nLongitude: " + longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        //in cazul nostru, requestCode = MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permisiune acceptata
                    ACCESS_FINE_LOCATION_PERMISSION_STATE = true;
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Permisiune respinsa
                    ACCESS_FINE_LOCATION_PERMISSION_STATE = false;
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected void getGoogleLocation(View view)
    {
        Intent intent = new Intent(this, FusedLocationProvider.class);
        startActivity(intent);
    }
}