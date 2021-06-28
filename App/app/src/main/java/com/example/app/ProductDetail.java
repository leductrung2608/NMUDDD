package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.model.AllProductModel;
import com.example.app.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {

    private List<AllProductModel> mListProduct;
    ImageView image;
    TextView name, weight, currentprice, description;
    Button btnAddToCart, btnBack;
    EditText edtQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        AnhXa();
        ClickBack();
        //EventAddToCart();
        //GetInformation();


    }

    private void GetInformation() {
        int id = 0;
        String Name = "";
        String Weight = "0";
        int CurrentPrice = 0;
        String Image = "";
        String Description = "";
        int Kind = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("id");
            Name = extras.getString("name");
            Weight = extras.getString("weight");
            CurrentPrice = extras.getInt("currentPrice");
            Image = extras.getString("image");
            Description = extras.getString("description");
            Kind = extras.getInt("idKind");

        }
        name.setText(Name);
        weight.setText(Weight);
        currentprice.setText(CurrentPrice);
        description.setText(Description);
        edtQuantity.setText("1");

    }

    private void ClickBack(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        image = findViewById(R.id.imageSource);
        name = findViewById(R.id.item_name);
        weight = findViewById(R.id.special);
        currentprice = findViewById(R.id.item_price);
        description = findViewById(R.id.description);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        edtQuantity = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btnBack);
    }

    private void EventAddToCart() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.ListCart.size() > 0){
                    //int sl = Integer.parseInt(EditText.

                }else{
                    //int Quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    //double Price = Quantity;
                    //MainActivity.ListCart.add(new Cart())
                }
            }
        });
    }
}
