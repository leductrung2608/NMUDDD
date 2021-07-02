package com.example.app.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.OrderDetailModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailHolder> {
    ArrayList<OrderDetailModel> list;
    Context context;

    public OrderDetailAdapter(ArrayList<OrderDetailModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.order_detail_item, parent, false );
        return new OrderDetailHolder ( view );
    }

    @Override
    public void onBindViewHolder(OrderDetailHolder holder, int position) {

        OrderDetailModel currentData = list.get ( position );
        holder.name.setText ( currentData.getName ( ) );
        holder.quantity.setText ( ""+ currentData.getQuantity ( ) );
        holder.price.setText ( ""+ currentData.getPrice ( ) );
    }

    @Override
    public int getItemCount() {
       return list.size ();
    }

    public class OrderDetailHolder extends RecyclerView.ViewHolder {

        TextView name, quantity, price;


        public OrderDetailHolder(@NonNull @NotNull View itemView) {
            super ( itemView );
            name = itemView.findViewById ( R.id.namebillTv );
            quantity = itemView.findViewById ( R.id.quantityBillTv );
            price = itemView.findViewById ( R.id.priceBillTv );
        }
    }
}
