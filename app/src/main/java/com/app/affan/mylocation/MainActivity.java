package com.app.affan.mylocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

    Location currentLocation;
    GoogleApiClient googleApiClient;
    TextView lat;
    TextView lng;
    private static final int REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = (TextView) findViewById(R.id.lat);
        lng = (TextView) findViewById(R.id.lng);
        buildGoogleApiClient();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getMyLocation(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(this,"Location Access Required",Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_COARSE_LOCATION);
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        getMyLastLocation();

    }

    protected void buildGoogleApiClient(){

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(this,"Suspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this,"Connection Failed",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode == REQUEST_COARSE_LOCATION){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show();
            }

        }else{

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    public void getMyLastLocation(){

        if(currentLocation != null){
            lat.setText(String.valueOf(currentLocation.getLatitude()));
            lng.setText(String.valueOf(currentLocation.getLongitude()));
        }else {
            Toast.makeText(this,"Location not available",Toast.LENGTH_LONG).show();
        }
    }
}
