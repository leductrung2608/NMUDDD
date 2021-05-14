package com.example.ibeauty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ibeauty.R;

import java.util.ArrayList;

public class ProductDetail_Reviews_RecyclerViewAdapter extends RecyclerView.Adapter<ProductDetail_Reviews_RecyclerViewAdapter.ViewHolder>{

    //private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<>();
    private ArrayList<String> mVote = new ArrayList<>();
    private ArrayList<String> mReview = new ArrayList<>();
    private Context mContext;

    public ProductDetail_Reviews_RecyclerViewAdapter(Context context, ArrayList<String> name, ArrayList<Integer> image, ArrayList<String> vote, ArrayList<String> review) {
        mName = name;
        mImage = image;
        mVote = vote;
        mReview = review;
        mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productdetail_reviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");


        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);

        holder.name.setText(mName.get(position));
        holder.vote.setText(mVote.get(position));
        holder.review.setText(mReview.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView vote;
        TextView review;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.user_name);
            vote = itemView.findViewById(R.id.user_vote);
            review = itemView.findViewById(R.id.user_review);
        }
    }
}
