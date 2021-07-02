package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Category_RecyclerAdapter extends RecyclerView.Adapter<Category_RecyclerAdapter.CategoryHolder> {

    private RecyclerViewClickListener listener;


    ImageView imageView;
    ArrayList<Category> list;
    Context context;

    public Category_RecyclerAdapter() {
    }

    public Category_RecyclerAdapter(ArrayList<Category> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CategoryHolder(View itemView) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.categoryImage );
            itemView.setOnClickListener ( this );

        }
        @Override
        public void onClick(View v) {
            listener.onClick ( v, getAdapterPosition () );
        }
    }
    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.category_items, parent, false );
        return new CategoryHolder ( view );
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {

        Category currentData = list.get(position);
        imageView.setTag(position);
        Picasso.get().load(currentData.getImageurl()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return list.size ( );
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}

