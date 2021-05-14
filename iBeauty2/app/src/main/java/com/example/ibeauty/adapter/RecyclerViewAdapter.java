package com.example.ibeauty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ibeauty.R;
import com.example.ibeauty.value.AllProduct;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    //private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<>();
    private Context mContext;

    //constructor
    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<Integer> images) {
        mNames = names;
        mImage = images;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_of_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
       // Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
               // Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AllProduct.class);
                //intent.putExtra("title",newsItemList.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AllProduct.class);
                //intent.putExtra("title",newsItemList.get(position).getTitle());
                mContext.startActivity(intent);
              //  mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name;
        RelativeLayout relativeLayout;
         //CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            relativeLayout= itemView.findViewById(R.id.relative_genre);
         //   cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
