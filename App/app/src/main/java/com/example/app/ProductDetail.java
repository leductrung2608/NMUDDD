package com.example.app;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {

    //Reviews
    private static final String TAG = "ProductDetail";
    private ArrayList<Integer> mImage = new ArrayList<Integer>();
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mVote = new ArrayList<>();
    private ArrayList<String> mReview = new ArrayList<>();

    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;


    public void sliderView()
    {
        imageSliderModelList = new ArrayList<>();
        sliderView=findViewById(R.id.imageSlider);
        imageSliderModelList.add(new ImageSliderModel(R.drawable.p1));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n2));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n3));
        sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        TextView textView = findViewById(R.id.item_old_price);

        textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        sliderView();


    }
}
