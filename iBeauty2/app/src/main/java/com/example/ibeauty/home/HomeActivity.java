package com.example.ibeauty.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibeauty.ImageSliderModel;
import com.example.ibeauty.ProfileActivity;
import com.example.ibeauty.R;
import com.example.ibeauty.adapter.RecyclerViewAdapter;
import com.example.ibeauty.ShoppingBag;
import com.example.ibeauty.adapter.RecyclerViewAdapter_discount;
import com.example.ibeauty.adapter.RecyclerViewAdapter_hot;
import com.example.ibeauty.login.SignInActivity;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
{
    //private static final String TAG = "MainActivity";

    //var listitem
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<Integer>();

    //var sliderimage
    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    //var discountitem
    private ArrayList<String> nSaleName = new ArrayList<>();
    private ArrayList<Integer> nSaleImage = new ArrayList<>();
    private ArrayList<String> nOldPrice = new ArrayList<>();
    private ArrayList<String> nSalePrice = new ArrayList<>();

   //var navigation
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //var hot
    private ArrayList<String> nHotName = new ArrayList<>();
    private ArrayList<Integer> nHotImage = new ArrayList<>();
    private ArrayList<String> nHotprice = new ArrayList<>();


////////////////////////////////////////items genre///////////////////////////////////////////////////////////
    private void getImages(){
        //Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImage.add(R.drawable.brush);
        mNames.add("Brushes");

        mImage.add(R.drawable.eyeliner);
        mNames.add("Eye");

        mImage.add(R.drawable.mascara);
        mNames.add("Mascara");

        mImage.add(R.drawable.nail);
        mNames.add("Nail");

        mImage.add(R.drawable.lipstick);
        mNames.add("Lipstick");

        mImage.add(R.drawable.foundation);
        mNames.add("Foudation");

        initRecyclerView();

    }

    private void initRecyclerView(){
       // Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(layoutManager);
        // ArrayList<Integer> mImageUrls;
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImage);
        recyclerView.setAdapter(adapter);
    }

/////////////////////////////////////discount items////////////////////////////////////////////////////////////////

    private void getImagesDiscount(){
        //Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        nSaleName.add("Son MAC #314 Mull It Over");
        nSaleImage.add(R.drawable.s1);
        nOldPrice.add("$80.00");
        nSalePrice.add("$60.00");


        nSaleName.add("NARS Eyeshadow Palette");
        nSaleImage.add(R.drawable.e1);
        nOldPrice.add("$33.00");
        nSalePrice.add("$20.00");

        nSaleName.add("Warrior Princess Mascara");
        nSaleImage.add(R.drawable.m1);
        nOldPrice.add("$10.00");
        nSalePrice.add("$6.00");

        initRecyclerViewDiscount();
    }

    private void initRecyclerViewDiscount()
    {
      //  Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewDiscount = findViewById(R.id.recycleViewDiscount);
        recyclerViewDiscount.setLayoutManager(layoutManager);
        // ArrayList<Integer> mImageUrls;
        RecyclerViewAdapter_discount adapter = new RecyclerViewAdapter_discount(this, nSaleName, nSaleImage,nOldPrice,nSalePrice);
        recyclerViewDiscount.setAdapter(adapter);
    }

    /////////////////////////////////hot items//////////////////////////////////////////////////////////////////////////
    private void getImagesHot(){
        //Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        nHotImage.add(R.drawable.p1);
        nHotName.add("Misha cushion");
        nHotprice.add("$20.00");

        nHotImage.add(R.drawable.p2);
        nHotName.add("CLIO kill cover cushion");
        nHotprice.add("$35.00");

        nHotImage.add(R.drawable.p3);
        nHotName.add("Aprilskin cushion");
        nHotprice.add("$30.00");

        initRecyclerViewHot();

    }

    private void initRecyclerViewHot()
    {
        //  Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewHot = findViewById(R.id.recycleViewHot);
        recyclerViewHot.setLayoutManager(layoutManager);
        // ArrayList<Integer> mImageUrls;
        RecyclerViewAdapter_hot adapter = new RecyclerViewAdapter_hot(this, nHotName, nHotImage,nHotprice);
        recyclerViewHot.setAdapter(adapter);
    }


////////////////////////////////////IMAGE_SLIDER///////////////////////////////////////////////////////////
    public void sliderView()
    {
        imageSliderModelList = new ArrayList<>();
        //goi imageslider ben activity main layout
        sliderView=findViewById(R.id.imageSlider);
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n0));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n4));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n2));
        sliderView.setSliderAdapter(new com.example.ibeauty.ImageSliderAdapter(this,imageSliderModelList));
        sliderView.startAutoCycle();
    }

///////////////////////////////////////NAVIGATION_MENU///////////////////////////////////////////////////
    private void NavigationView()
    {
        //
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNaviClick);


    }

    public boolean onNaviClick(@NonNull MenuItem menuItem) {
        navigationView = findViewById(R.id.nav_view);
        switch (menuItem.getItemId()){

            case R.id.nav_home:
                    break;

            case R.id.person:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_cart:
                Intent intent2 = new Intent(HomeActivity.this, ShoppingBag.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                Intent intent3 = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent3);
                finish();
        }
        return true;
    }


private void shoppingBagClick(){
    ImageView imageView;
    imageView= findViewById(R.id.shoppingBag);
    imageView.setOnClickListener(new View.OnClickListener() {
        //@Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.this, ShoppingBag.class);
            startActivity(intent);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //transparentStatusAndNavigation();
        setContentView(R.layout.activity_home);

        getImages();
        sliderView();
        getImagesDiscount();
        NavigationView();
        getImagesHot();
        shoppingBagClick();

    }

    //transparent statusbar
    private void transparentStatusAndNavigation ()
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

    private void setWindowFlag ( final int bits, boolean on){
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