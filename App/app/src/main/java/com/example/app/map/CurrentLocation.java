package com.example.app.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.app.R;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

public class CurrentLocation extends AppCompatActivity implements LocationListener {

    Button button_location;
    TextView textView_location;
    LocationManager locationManager;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentlocation);

        textView_location = findViewById(R.id.tv);

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(CurrentLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CurrentLocation.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        getLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, CurrentLocation.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(CurrentLocation.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address = addresses.get(0).getAddressLine(0);

            Toast.makeText(CurrentLocation.this, address + "hihi", Toast.LENGTH_SHORT).show();
            textView_location.setText(address);

            EventBus.getDefault().postSticky(new com.example.app.map.Address(address));

        }catch (Exception e){
            e.printStackTrace();
        }

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

}
