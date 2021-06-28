package com.example.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;

import java.util.ArrayList;

public class Cart_ListItem_RecyclerAdapter extends RecyclerView.Adapter<Cart_ListItem_RecyclerAdapter.ViewHolder> {

    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mOldPrice = new ArrayList<>();
    private ArrayList<String> mKind = new ArrayList<>();
    private ArrayList<String> mQuantity = new ArrayList<>();
    private final Context mBagContext;

    public Cart_ListItem_RecyclerAdapter(Context mBagContext, ArrayList<Integer> images, ArrayList<String> names, ArrayList<String> prices, ArrayList<String> oldprices, ArrayList<String> quantity, ArrayList<String> kinds ) {
        this.mImages = images;
        this.mPrice = prices;
        this.mOldPrice = oldprices;
        this.mNames = names;
        this.mKind = kinds;
        this.mQuantity = quantity;
        this.mBagContext = mBagContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_listitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Glide.with(mBagContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.itemImage);

        holder.itemName.setText(mNames.get(position));
        holder.itemKind.setText(mKind.get(position));
        holder.itemPrice.setText(mPrice.get(position));
        holder.itemOldPrice.setText(mOldPrice.get(position));
        holder.itemQuantity.setText(mQuantity.get(position));

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mBagContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemKind;
        TextView itemPrice;
        TextView itemOldPrice;
        EditText itemQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemKind = itemView.findViewById(R.id.item_kind);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemOldPrice = itemView.findViewById(R.id.item_old_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
        }
    }
}
