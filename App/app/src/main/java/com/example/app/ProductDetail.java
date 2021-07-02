package com.example.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.model.Cart;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.model.AllProductModel;
import com.example.app.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetail extends AppCompatActivity{

    private static final String INSERTDATA_URL = "https://ibeautycosmetic.000webhostapp.com/pushcart.php";

    private List<AllProductModel> mListProduct;
    ImageView image;
    TextView name, weight, currentprice, description;
    Button btnAddToCart, btnBack, btnCart;
    EditText edtQuantity;

    int id ;
    String Name;
    String Weight;
    int CurrentPrice;
    String Image ;
    String Description;
    int Quantity;
    int Kind ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_product_detail);

        AnhXa();
        ClickBack();
        EventAddToCart();
        GetInformation();
        GoToCart();

    }

    private void GoToCart() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetail.this, Test.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetInformation() {
        AllProductModel productModel = (AllProductModel) getIntent().getSerializableExtra("information");
        id = productModel.getIdGoods();
        Name = productModel.getName();
        Weight = productModel.getWeight();
        CurrentPrice = productModel.getCurrentPrice();
        Image = productModel.getImage();
        Description = productModel.getDescription();
        Kind = productModel.getKind();

        name.setText(Name);
        weight.setText(Weight);
        DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
        currentprice.setText(decimalFormat.format(CurrentPrice) + " VNĐ");
        description.setText(Description);
        Picasso.get().load(Image)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(image);
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
        description = findViewById(R.id.text_view_show_more);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        edtQuantity = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btnBack);
        btnCart = findViewById(R.id.cart);
    }

    private void EventAddToCart() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.CartList.size() > 0){
                    boolean exists = false;
                    for(int i = 0; i < MainActivity.CartList.size(); i++){
                        if(MainActivity.CartList.get(i).getIdGoods() == id) {
                            Toast.makeText(ProductDetail.this, "Already existed in your Cart", Toast.LENGTH_SHORT).show();
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int Quantity = Integer.parseInt(edtQuantity.getText().toString());
                        CurrentPrice = Quantity * CurrentPrice;
                        PushCart(id, Name, CurrentPrice, Weight, Quantity, Image, MainActivity.userId);
                        }
                }else{
                    int Quantity = Integer.parseInt(edtQuantity.getText().toString());
                    CurrentPrice = Quantity * CurrentPrice;
                    PushCart(id, Name, CurrentPrice, Weight, Quantity, Image, MainActivity.userId);
                }

            }
        });
    }

    private void PushCart(int id, String name, int price, String weight, int quantity, String image, String iduser) {
        String Id1 = String.valueOf(id);
        String Name1 = name;
        String Price1 = String.valueOf(price);
        String Weight1 = weight;
        String Quantity1= String.valueOf(quantity);
        String Image = image;
        String IdUser = iduser;

        register(Id1, Name1, Price1, Weight1, Quantity1, Image, IdUser);

    }

    private void register(String id1, String name1, String price1, String weight1, String quantity1, String image, String idUser) {

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
                data.put("Name",params[1]);
                data.put("Price",params[2]);
                data.put("Weight",params[3]);
                data.put("Quantity",params[4]);
                data.put("Image",params[5]);
                data.put("IdUser",params[6]);


                String result = ruc.sendPostRequest(INSERTDATA_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(id1,name1,price1,weight1,quantity1,image,idUser);

        Toast.makeText(getApplicationContext(),"Successfully add to your Cart",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProductDetail.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void transparentStatusAndNavigation ()
    {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    public void setWindowFlag ( final int bits, boolean on){
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
