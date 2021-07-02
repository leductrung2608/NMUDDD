package com.example.app;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.app.adapter.CartAdapter;
import com.example.app.adapter.ViewPagerAdapter;
import com.example.app.map.PlacePicker;
import com.example.app.model.AllProductModel;
import com.example.app.model.Cart;
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

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_main);

        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        //InsertData ( userId );
        AnhXa();
        BottomNav();

        CartList = new ArrayList<Cart>();

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



    public static void InsertData(String id) {
        register ( id );

    }

    public static void register(String id) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass ( );


            @Override
            protected void onPreExecute() {
                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute ( s );
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String> ( );


                data.put ( "IdUser", params[0] );


                String result = ruc.sendPostRequest ( "https://ibeautycosmetic.000webhostapp.com/getUser.php", data );

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers ( );
        ru.execute ( id );

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