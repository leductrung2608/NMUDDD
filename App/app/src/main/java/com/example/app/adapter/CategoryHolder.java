package com.example.app.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
//import com.example.app.R;

//JsonDataViewHolder
public class CategoryHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    int id;
    public CategoryHolder(View itemView) {
        super ( itemView );
        imageView = itemView.findViewById( R.id.categoryImage);

    }
}
