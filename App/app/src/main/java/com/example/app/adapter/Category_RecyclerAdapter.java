package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Category_RecyclerAdapter extends RecyclerView.Adapter<CategoryHolder> {

    ArrayList<Category> list;
    Context context;

    public Category_RecyclerAdapter(){}
    public Category_RecyclerAdapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public CategoryHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from ( context ).inflate ( R.layout.category_items, parent, false );




        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {

        Category currentData = list.get ( position );


        Picasso.get ().load ( currentData.getImageurl () ).into ( holder.imageView );




    }

    @Override
    public int getItemCount() {
        return list.size ();
    }
}

// lets import all the category images