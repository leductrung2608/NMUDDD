package com.example.ibeauty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductDetail_SimilarAdapter extends RecyclerView.Adapter<ProductDetail_SimilarAdapter.ViewHolder> {
    @NonNull

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private Context mContext;

    public ProductDetail_SimilarAdapter(Context context, ArrayList<String> name, ArrayList<Integer> image, ArrayList<String> price) {
        mName = name;
        mImage = image;
        mPrice = price;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);

        holder.name.setText(mName.get(position));
        holder.price.setText(mPrice.get(position));

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.similar_image);
            name = itemView.findViewById(R.id.similar_name);
            price = itemView.findViewById(R.id.similar_price);
        }
    }
}
