package com.example.ibeauty;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibeauty.adapter.ShoppingBag_ListItem_ViewRecyclerAdapter;

import java.util.ArrayList;

public class ShoppingBag extends AppCompatActivity {

    private static final String TAG = "ShoppingBag";
    private ArrayList<Integer> mImages = new ArrayList<Integer>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mOldPrice = new ArrayList<>();
    private ArrayList<String> mKind = new ArrayList<>();
    private ArrayList<String> mQuantity = new ArrayList<>();

    private void getBag(){

        mImages.add(R.drawable.p1);
        mNames.add("Missha Cushion");
        mPrice.add("$30.0");
        mOldPrice.add("35.0");
        mKind.add(null);
        mQuantity.add("1");


        mImages.add(R.drawable.s1);
        mNames.add("MAC lipstick");
        mPrice.add("$20.0");
        mOldPrice.add("$23.0");
        mKind.add("#414");
        mQuantity.add("2");

        mImages.add(R.drawable.m1);
        mNames.add("Mascara");
        mPrice.add("$10.0");
        mOldPrice.add("$12.0");
        mKind.add("Long");
        mQuantity.add("3");

        mImages.add(R.drawable.p6);
        mNames.add("Cushion");
        mPrice.add("$23.0");
        mOldPrice.add("56$");
        mKind.add("Natural");
        mQuantity.add("4");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(layoutManager);
        ShoppingBag_ListItem_ViewRecyclerAdapter adapter = new ShoppingBag_ListItem_ViewRecyclerAdapter(this, mImages, mNames, mPrice, mOldPrice, mQuantity, mKind);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingbag);
        getBag();

        Button next = (Button) findViewById(R.id.back);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
