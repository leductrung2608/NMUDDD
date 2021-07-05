package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.app.R;
import com.example.app.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageSliderAdapter extends SliderViewAdapter<SlideViewHolder> {
    Context context;
    List<ImageSliderModel> imageSliderModelList;

    public ImageSliderAdapter(Context context, List<ImageSliderModel> imageSliderModelList) {
        this.context = context;
        this.imageSliderModelList = imageSliderModelList;
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        //ham slideviewholder o duoi
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlideViewHolder viewHolder, int position) {
        Picasso.get ().load ( imageSliderModelList.get( position).getImage() )
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.sliderImageView );
        //viewHolder.sliderImageView.setImageResource(imageSliderModelList.get(position).getImage());
    }

    @Override
    public int getCount() {
        return imageSliderModelList.size();
    }
}

class SlideViewHolder extends SliderViewAdapter.ViewHolder
{
    ImageView sliderImageView;
    public SlideViewHolder(View itemView) {
        super(itemView);
        //goi imageview ben slider_item_layout
        sliderImageView=itemView.findViewById(R.id.imageProfile);
    }
}