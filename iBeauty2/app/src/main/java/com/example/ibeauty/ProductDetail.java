package com.example.ibeauty;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibeauty.adapter.ProductDetail_Reviews_RecyclerViewAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class    ProductDetail extends AppCompatActivity {

    //Reviews
    private static final String TAG = "ProductDetail";
    private ArrayList<Integer> mImage = new ArrayList<Integer>();
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mVote = new ArrayList<>();
    private ArrayList<String> mReview = new ArrayList<>();

    private void getReviews(){

       mImage.add(R.drawable.p1);
        mName.add("Misha cushion");
        mVote.add("5");
        mReview.add("Very good");


        mImage.add(R.drawable.p2);
        mName.add("CLIO kill cover cushion");
        mVote.add("4");
        mReview.add("Good");

        mImage.add(R.drawable.s1);
        mName.add(" Son MAC");
        mVote.add("4.5");
        mReview.add("Good");

        initRecyclerView();
    }

    private void initRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycle_review);
        recyclerView.setLayoutManager(layoutManager);
        ProductDetail_Reviews_RecyclerViewAdapter adapter = new ProductDetail_Reviews_RecyclerViewAdapter(this, mName, mImage, mVote, mReview);
        recyclerView.setAdapter(adapter);
    }

    //sliderimageVAr
    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    public void sliderView()
    {
        imageSliderModelList = new ArrayList<>();
        sliderView=findViewById(R.id.imageSlider);
        imageSliderModelList.add(new ImageSliderModel(R.drawable.p1));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.p102));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.p101));
        sliderView.setSliderAdapter(new ProductDetail_ImageSliderAdapter(this,imageSliderModelList));
    }

    //Similar_Product
    private ArrayList<Integer> mSimilarImage = new ArrayList<Integer>();
    private ArrayList<String> mSimilarName = new ArrayList<>();
    private ArrayList<String> mSimilarPice = new ArrayList<>();

    private void getSimilars(){

        mSimilarImage.add(R.drawable.p3);
        mSimilarName.add("AprilSkin Cushion");
        mSimilarPice.add("23$");


        mSimilarImage.add(R.drawable.p6);
        mSimilarName.add("Laneige Cushion");
        mSimilarPice.add("30$");

        mSimilarImage.add(R.drawable.p5);
        mSimilarName.add("Hera Cushion");
        mSimilarPice.add("35$");

        initSimilarRecyclerView();
    }

    private void initSimilarRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.similar_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ProductDetail_SimilarAdapter adapter = new ProductDetail_SimilarAdapter(this, mSimilarName, mSimilarImage, mSimilarPice);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        sliderView();
        getSimilars();
        getReviews();

        TextView textView = findViewById(R.id.item_old_price);
        textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        Button next = (Button) findViewById(R.id.Bag);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ShoppingBag.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }
}
