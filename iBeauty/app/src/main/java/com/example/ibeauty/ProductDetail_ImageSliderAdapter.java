package com.example.ibeauty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductDetail_ImageSliderAdapter extends SliderViewAdapter<ProductDetail_SlideViewHolder> {

    Context context;
    List<ImageSliderModel> imageSliderModelList;

    public ProductDetail_ImageSliderAdapter(Context context, List<ImageSliderModel> imageSliderModelList) {
        this.context = context;
        this.imageSliderModelList = imageSliderModelList;
    }

    @Override
    public ProductDetail_SlideViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,parent,false);
        return new ProductDetail_SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductDetail_SlideViewHolder viewHolder, int position) {
        viewHolder.sliderImageView.setImageResource(imageSliderModelList.get(position).getImage());
    }

    @Override
    public int getCount() {
        return imageSliderModelList.size();
    }
}

class ProductDetail_SlideViewHolder extends SliderViewAdapter.ViewHolder
{
    ImageView sliderImageView;
    public ProductDetail_SlideViewHolder(View itemView) {
        super(itemView);
        sliderImageView=itemView.findViewById(R.id.imageView);
    }
}