package com.example.ibeauty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ibeauty.ProductDetail;
import com.example.ibeauty.R;

import java.util.ArrayList;


public class RecyclerViewAdapter_hot extends RecyclerView.Adapter<RecyclerViewAdapter_hot.ViewHolder>  {

    //private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> nHotName = new ArrayList<>();
    private ArrayList<Integer> nHotImage = new ArrayList<>();
    private ArrayList<String> nHotprice = new ArrayList<>();
    private Context nHotContext;
   // private ArrayList<Integer> nProBar = new ArrayList<>();


    public RecyclerViewAdapter_hot(Context nHotContext,ArrayList<String> nHotName, ArrayList<Integer> nHotImage, ArrayList<String> nHotprice ) {
        this.nHotName = nHotName;
        this.nHotImage = nHotImage;
        this.nHotprice = nHotprice;
        this.nHotContext = nHotContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");


        Glide.with(nHotContext)
                .asBitmap()
                .load(nHotImage.get(position))
                .into(holder.hotImage);

        holder.hotName.setText(nHotName.get(position));
        holder.hotPrice.setText(nHotprice.get(position));
        holder.hotImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                //Toast.makeText(nHotContext, nHotName.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(nHotContext, ProductDetail.class);
                nHotContext.startActivity(intent);
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nHotContext, ProductDetail.class);
                //intent.putExtra("title",newsItemList.get(position).getTitle());
                nHotContext.startActivity(intent);
                //  mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nHotImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView hotImage;
        TextView hotName;
        TextView hotPrice;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            hotImage = itemView.findViewById(R.id.hot_image_view);
            hotName= itemView.findViewById(R.id.hot_name);
            hotPrice = itemView.findViewById(R.id.hot_price);
            relativeLayout = itemView.findViewById(R.id.relative_hot);

        }
    }
}
