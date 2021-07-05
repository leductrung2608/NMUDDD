package com.example.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.RegisterUserClass;
import com.example.app.fragment.CartFragment;
import com.example.app.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> ArrayCart;
    int IdGoods;

    private static final String INSERTDATA_URL = "https://ibeautycosmetic.000webhostapp.com/deleteProduct.php";

    public CartAdapter(Context context, ArrayList<Cart> arrayCart) {
        this.context = context;
        ArrayCart = arrayCart;
    }

    @Override
    public int getCount() {
        return ArrayCart.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        public TextView tvName, tvWeight, tvPrice;
        public ImageView image;
        public Button btnAdd, btnSubtract, btnDel;
        public EditText edtQuantity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_listitem,null);
            viewHolder.tvName = convertView.findViewById(R.id.item_name);
            viewHolder.tvPrice = convertView.findViewById(R.id.item_price);
            viewHolder.tvWeight = convertView.findViewById(R.id.item_weight);
            viewHolder.btnAdd = convertView.findViewById(R.id.btnAdd);
            viewHolder.btnSubtract = convertView.findViewById(R.id.btnSub);
            viewHolder.btnDel = convertView.findViewById(R.id.del);
            viewHolder.image = convertView.findViewById(R.id.item_image);
            viewHolder.edtQuantity = convertView.findViewById(R.id.spinner);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.tvName.setText(cart.getName());
        viewHolder.tvWeight.setText(cart.getWeight());
        DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
        viewHolder.tvPrice.setText(decimalFormat.format(cart.getCurrentPrice())+" VNĐ");
        Picasso.get().load(cart.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.image);
        viewHolder.edtQuantity.setText(cart.getQuantity() + "");
        IdGoods = cart.getIdGoods();

        int quantity = Integer.parseInt(viewHolder.edtQuantity.getText().toString());

        int MaxQuantity = MainActivity.CartList.get(position).getMaxQuantity();

        if(quantity <= 1) {
            viewHolder.btnSubtract.setVisibility(View.INVISIBLE);
        }else  if(quantity >= MaxQuantity) {
            viewHolder.btnAdd.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.btnSubtract.setVisibility(View.VISIBLE);
            viewHolder.btnAdd.setVisibility(View.VISIBLE);
        }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.edtQuantity.getText().toString()) +1;
                int slht = MainActivity.CartList.get(position).getQuantity();
                long giaht = MainActivity.CartList.get(position).getCurrentPrice();
                MainActivity.CartList.get(position).setQuantity(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.CartList.get(position).setCurrentPrice((int) giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
                finalViewHolder.tvPrice.setText(decimalFormat.format(giamoinhat)+" VNĐ");
                if(slmoinhat >= MaxQuantity){
                    finalViewHolder.btnSubtract.setVisibility(View.VISIBLE);
                    finalViewHolder.btnAdd.setVisibility(View.INVISIBLE);
                    finalViewHolder.edtQuantity.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnSubtract.setVisibility(View.VISIBLE);
                    finalViewHolder.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder.edtQuantity.setText(String.valueOf(slmoinhat));
                }
                CartFragment.EvenUltil();
            }
        });
        viewHolder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.edtQuantity.getText().toString()) - 1;
                int slht = MainActivity.CartList.get(position).getQuantity();
                long giaht = MainActivity.CartList.get(position).getCurrentPrice();
                MainActivity.CartList.get(position).setQuantity(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.CartList.get(position).setCurrentPrice((int) giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
                finalViewHolder.tvPrice.setText(decimalFormat.format(giamoinhat)+" VNĐ");
                if(slmoinhat < 2){
                    finalViewHolder.btnSubtract.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder.edtQuantity.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnSubtract.setVisibility(View.VISIBLE);
                    finalViewHolder.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder.edtQuantity.setText(String.valueOf(slmoinhat));
                }
                CartFragment.EvenUltil();
            }
        });

        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItem(MainActivity.CartList.get(position).getIdGoods(), MainActivity.userId);
                MainActivity.CartList.remove(position);
                notifyDataSetChanged();
                CartFragment.EvenUltil();
                Log.i("position", position + "");

            }
        });

        return convertView;
    }

    private void DeleteItem(int id, String iduser) {
        String Id1 = String.valueOf(id);
        String IdUser = iduser;


        register(Id1, IdUser);

    }

    private void register(String id1, String idUser) {

        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("IdGoods",params[0]);
                data.put("IdUser",params[1]);


                String result = ruc.sendPostRequest(INSERTDATA_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(id1,idUser);

        Toast.makeText(context,"Successfully delete from your Cart",Toast.LENGTH_SHORT).show();


    }
}
