package com.example.app.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.app.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

public class PlacePicker extends AppCompatActivity {

    TextView tv_address;
    WifiManager wifiManager;
    Button btt_picklocation;
    ImageView back;

    private final static int PLACE_PICKER_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_placepicker);

        back = findViewById(R.id.btt_backtoregister);
        tv_address = findViewById(R.id.address);
        btt_picklocation = findViewById(R.id.btt_picklocation);
        wifiManager= (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiManager.setWifiEnabled(false);
        openPlacePicker();

        btt_picklocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacePicker();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.super.onBackPressed();
            }
        });

    }

    private void openPlacePicker() {
        com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder builder = new com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            wifiManager.setWifiEnabled(true);

        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();

            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = com.google.android.gms.location.places.ui.PlacePicker.getPlace(PlacePicker.this, data);

                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;

                    String address = getCompleteAddressString(latitude, longitude);
                    tv_address.setText(address);

                    EventBus.getDefault().postSticky(new com.example.app.map.Address(address));

            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("TAG", strReturnedAddress.toString());
            } else {
                Log.w("TAG", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("TAG", "Cant get Address!");
        }
        return strAdd;
    }

    public void transparentStatusAndNavigation ()
    {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    public void setWindowFlag ( final int bits, boolean on){
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
