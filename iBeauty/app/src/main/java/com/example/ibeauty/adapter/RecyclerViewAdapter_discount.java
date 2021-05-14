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


public class RecyclerViewAdapter_discount extends RecyclerView.Adapter<RecyclerViewAdapter_discount.ViewHolder>  {

    //private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> nSaleName = new ArrayList<>();
    private ArrayList<Integer> nSaleImage = new ArrayList<>();
    private ArrayList<String> nOldPrice = new ArrayList<>();
    private ArrayList<String> nSalePrice = new ArrayList<>();
    private ArrayList<Integer> nProBar = new ArrayList<>();
    private Context nSaleContext;

    public RecyclerViewAdapter_discount(Context nSaleContext, ArrayList<String> nSaleName, ArrayList<Integer> nSaleImage, ArrayList<String> nOldPrice, ArrayList<String> nSalePrice)
    {
        this.nSaleName = nSaleName;
        this.nSaleImage = nSaleImage;
        this.nOldPrice = nOldPrice;
        this.nSalePrice = nSalePrice;
        //this.nProBar = nProBar;
        this.nSaleContext = nSaleContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(nSaleContext)
                .asBitmap()
                .load(nSaleImage.get(position))
                .into(holder.saleImage);

        holder.saleName.setText(nSaleName.get(position));

        holder.oldPrice.setText(nOldPrice.get(position));

        holder.salePrice.setText(nSalePrice.get(position));

        holder.saleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
               // Toast.makeText(nSaleContext, nSaleName.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(nSaleContext, ProductDetail.class);
                //intent.putExtra("title",newsItemList.get(position).getTitle());
                nSaleContext.startActivity(intent);
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nSaleContext, ProductDetail.class);
                //intent.putExtra("title",newsItemList.get(position).getTitle());
                nSaleContext.startActivity(intent);
                //  mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nSaleImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView saleImage;
        TextView saleName;
        TextView oldPrice;
        TextView salePrice;
        RelativeLayout relativeLayout;
      //  ProgressBar proBar;

        public ViewHolder(View itemView) {
            super(itemView);
            saleImage = itemView.findViewById(R.id.sale_image_view);
            saleName = itemView.findViewById(R.id.sale_name);
            oldPrice = itemView.findViewById(R.id.old_price);
            salePrice = itemView.findViewById(R.id.sale_price);
            relativeLayout = itemView.findViewById(R.id.relative_discount);
            //proBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
