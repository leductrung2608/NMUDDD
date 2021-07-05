package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.AllProductModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Home_AllItem_RecyclerAdapter extends RecyclerView.Adapter<Home_AllItem_RecyclerAdapter.Home_AllItem_Holder> {


    ArrayList<AllProductModel> list;
    RecyclerViewClickListener listener;
    Context context;

    public Home_AllItem_RecyclerAdapter(ArrayList<AllProductModel> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public class Home_AllItem_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // truyen layout
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;

        public Home_AllItem_Holder(View itemView) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.sale_image_view );
            textViewName = itemView.findViewById ( R.id.pro_name );
            textViewPrice = itemView.findViewById ( R.id.pro_price );
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NotNull
    @Override
    public Home_AllItem_Holder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext() ).inflate ( R.layout.allitem_layout,parent,false );

        return new Home_AllItem_Holder (view);
    }

    @Override
    public void onBindViewHolder( Home_AllItem_Holder holder, int position) {

        AllProductModel currentData = list.get ( position );
        if(currentData.getImage().isEmpty()){
            holder.imageView.setImageResource(R.drawable.noimage);
        }else {
            Picasso.get ().load ( currentData.getImage () )
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(holder.imageView );
        }
        holder.textViewName.setText ( currentData.getName () );
        DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
        holder.textViewPrice.setText (decimalFormat.format ( currentData.getCurrentPrice () ) +" vnd" );
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public void filterList(ArrayList<AllProductModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

}
