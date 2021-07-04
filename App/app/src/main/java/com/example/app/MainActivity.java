package com.example.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.app.adapter.CartAdapter;
import com.example.app.adapter.ViewPagerAdapter;
import com.example.app.login.Login;
import com.example.app.map.PlacePicker;
import com.example.app.model.AllProductModel;
import com.example.app.model.Cart;
import com.example.app.model.Chat;
import com.example.app.util.MyAppContext;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Fragment fragment = null;
    public static ArrayList<Cart> CartList;
    FloatingActionButton btt_logout, btt_chat;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btt_chat = findViewById(R.id.btt_chatUser);
        btt_logout = findViewById(R.id.btt_logoutUser);

        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        //InsertData ( userId );
        AnhXa();
        BottomNav();
        //CountItemInCart(MainActivity.CartList.size ());

       btt_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               GoogleSignInOptions gso = new GoogleSignInOptions.
                       Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                       build();
               GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(),gso);
               googleSignInClient.signOut();

               LoginManager.getInstance().logOut();

               fAuth.signOut();
               startActivity(new Intent(getApplicationContext(), Login.class));
               finish();
           }
       });

       btt_chat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), ChatActivity.class));
           }
       });

        CartList = new ArrayList<Cart>();

    }

    private void CountItemInCart(int count) {
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor( ContextCompat.getColor( MainActivity.this, R.color.red))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.whiteTextColor))
                .build();
        ahBottomNavigation.setNotification(notification, 2);
    }

    private void AnhXa() {
        ahBottomNavigation = findViewById(R.id.BotNav);
        ahBottomNavigationViewPager = findViewById(R.id.BotNavVPager);
    }

    public void BottomNav(){

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ahBottomNavigationViewPager.setAdapter(viewPagerAdapter);
        ahBottomNavigationViewPager.setPagingEnabled(true);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_shopping, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_profile, R.color.colorPrimary);

        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                ahBottomNavigationViewPager.setCurrentItem(position);
                return true;
            }
        });

        ahBottomNavigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ahBottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}