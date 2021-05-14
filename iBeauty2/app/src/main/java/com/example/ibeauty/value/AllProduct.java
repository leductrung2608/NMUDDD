package com.example.ibeauty.value;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ibeauty.R;

import java.util.ArrayList;

public class AllProduct extends AppCompatActivity {


    private void listProduct(){

        ListView listView;
        ArrayList<ListProduct> arrayList;
        ListProductAdapter adapter;

        listView = findViewById(R.id.listViewProduct);

        arrayList = new ArrayList<>();
        arrayList.add(new ListProduct("CLIO Cushion"," (15g + Refill 15g)","$30.00",R.drawable.p2));
        arrayList.add(new ListProduct("Misha Cushion","Maintain makeup for a long time without worrying about tone down","$35.00",R.drawable.p1));
        arrayList.add(new ListProduct("NARS","Eye shadow palette","$25.00",R.drawable.e1));
        arrayList.add(new ListProduct("Mascara","Phan nuoc cao cap tu Han Quoc","$10.00",R.drawable.m1));
        arrayList.add(new ListProduct("Laneige Cushion","Phan nuoc cao cap tu Han Quoc","$30.00",R.drawable.p6));

        adapter = new ListProductAdapter(AllProduct.this, R.layout.row_item_in_all_product, arrayList);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        listProduct();
    }
}