package com.example.ibeauty.value;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibeauty.R;
import com.example.ibeauty.value.ListProduct;

import java.util.ArrayList;
import java.util.List;

public class ListProductAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private List<ListProduct> arrayList;

    public ListProductAdapter(Context context, int layout, ArrayList<ListProduct> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }




    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout,null);

        ListProduct product = arrayList.get(position);

        //anh xa
        TextView name = convertView.findViewById(R.id.Name);
        TextView price = convertView.findViewById(R.id.Price);
        TextView describe = convertView.findViewById(R.id.Describe);
        ImageView image= convertView.findViewById(R.id.Image);

        name.setText(product.getName());
        price.setText(product.getPrice());
        describe.setText(product.getDescribe());
        image.setImageResource(product.getImage());

        return convertView;
    }
}
