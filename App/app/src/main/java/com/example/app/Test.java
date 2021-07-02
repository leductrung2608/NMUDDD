package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.adapter.CartAdapter;
import com.example.app.model.Cart;

import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Test extends AppCompatActivity {

    ListView lvCart;
    TextView tvNotify, tvTotal;
    Button btnPay;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnPay = findViewById(R.id.btnPayTest);
        lvCart = findViewById(R.id.listview_cart1);
        //tvNotify = view.findViewById(R.id.tvNotyfi);
        cartAdapter = new CartAdapter(this  , MainActivity.CartList);
        lvCart.setAdapter(cartAdapter);
        Pay();
    }

    private void Pay() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });
    }

    private void CreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to oder?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Test.this, "Your oder is on the way", Toast.LENGTH_LONG).show();



            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}