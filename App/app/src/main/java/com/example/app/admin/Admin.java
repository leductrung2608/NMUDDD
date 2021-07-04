package com.example.app.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ChatActivity;
import com.example.app.R;
import com.example.app.adapter.Home_AllItem_RecyclerAdapter;
import com.example.app.login.Login;
import com.example.app.model.AllProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class Admin extends AppCompatActivity {
    CircleButton logout;
    FirebaseAuth fAuth;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton btt_chat, btt_logout;

    RecyclerView recyclerView;
    ArrayList<AllProductModel> mangsanpham;
    Home_AllItem_RecyclerAdapter adminRcvAdapter;

    Home_AllItem_RecyclerAdapter.RecyclerViewClickListener listener;

    ArrayList<AllProductModel> filteredlist;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_admin);

        recyclerView = findViewById ( R.id.allProAdmin );
        btt_chat = findViewById(R.id.btt_chatadmin);
        btt_logout = findViewById(R.id.btt_logoutadmin);

        fAuth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new GridLayoutManager (getApplicationContext (), 2 ) );

        mangsanpham = new ArrayList<AllProductModel> (  );

        //adminRcvAdapter = new Home_AllItem_RecyclerAdapter(mangsanpham, getApplicationContext(), listener);
        //recyclerView.setAdapter(adminRcvAdapter);

        JsonFetchAllProduct jsonFetchAllProduct = new JsonFetchAllProduct ();
        jsonFetchAllProduct.execute (  );

        NavigationView ();

        editText = findViewById ( R.id.searchAdmin);

        editText.addTextChangedListener(new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String key_search = editText.getText().toString();

                if (key_search.trim().equals("")) {

                    recyclerView.setVisibility ( View.VISIBLE );
                    // JsonFetchAllProduct jsonFetchAllProduct = new JsonFetchAllProduct ();
                    //jsonFetchAllProduct.execute (  );
                    adminRcvAdapter = new Home_AllItem_RecyclerAdapter( mangsanpham, getApplicationContext ( ),listener );

                    //Admin a = new Admin ();

                    recyclerView.setAdapter ( adminRcvAdapter );


                } else {

                    filter(s.toString());

                }
            }
        });

        SetOnClick();

        //open chat activity
        btt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });

        //logout
        btt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();

                startActivity(new Intent(Admin.this, Login.class));
                finish();
            }
        });
    }

    private void SetOnClick() {
        listener = (v, position) -> {
            if(filteredlist == null || editText.getText ().equals ( "" )) {
                //position = filteredlist.get ( position ).getIdGoods ();
                Intent intent = new Intent (getApplicationContext ( ), Detail.class );
                intent.putExtra ( "information", mangsanpham.get ( position ) );
                startActivity ( intent );
            }else {
                Intent intent = new Intent (getApplicationContext ( ), Detail.class );
                intent.putExtra ( "information", filteredlist.get ( position ) );
                startActivity ( intent );
            }

        };
    }

    private void filter(String s) {
        // creating a new array list to filter our data.
        filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (AllProductModel item : mangsanpham) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName ().toLowerCase().contains(s.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
                //item.notifyAll ();
            }
        }
        if (filteredlist.isEmpty()) {

            recyclerView.setVisibility ( View.INVISIBLE );
            btt_chat.setVisibility ( View.INVISIBLE );
            btt_chat.setVisibility ( View.INVISIBLE );

        } else {

            adminRcvAdapter.filterList(filteredlist);
            btt_chat.setVisibility ( View.VISIBLE );
            btt_logout.setVisibility ( View.VISIBLE );

        }


    }

    private void NavigationView()
    {
        //
        drawerLayout = findViewById( R.id.drawer_layout_admin);
        navigationView = findViewById( R.id.nav_view);
        toolbar = findViewById( R.id.toolbar);
        // setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNaviClick);


    }

    public boolean onNaviClick(@NonNull MenuItem menuItem) {
        navigationView = findViewById( R.id.nav_view);
        switch (menuItem.getItemId())
        {
            case R.id.nav_add:
                Intent intent0 = new Intent(Admin.this, AddProduct.class);
                startActivity(intent0);
                break;

            case R.id.nav_money:
                Intent intent = new Intent(Admin.this, Proceeds.class);
                startActivity(intent);
                break;

            case R.id.nav_unconfirmed:
                Intent intent2 = new Intent(Admin.this, UnconfirmedOrder.class);
                startActivity(intent2);
                break;

            case R.id.nav_confirmed:
                Intent intent3 = new Intent(Admin.this, ConfirmedOrder.class);
                startActivity(intent3);
                break;

            case R.id.nav_sigout:
                fAuth.signOut();
                startActivity(new Intent(Admin.this, Login.class));
                finish();


        }
        return true;
    }

    public class JsonFetchAllProduct extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getGoods.php" );
                httpURLConnection = (HttpURLConnection) url.openConnection ( );
                httpURLConnection.connect ( );

                InputStream inputStream = httpURLConnection.getInputStream ( );
                BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader ( inputStream ) );

                StringBuffer stringBuffer = new StringBuffer ( );
                String line = "";
                while ((line = bufferedReader.readLine ( )) != null) {

                    stringBuffer.append ( line );

                }

                mainfile = stringBuffer.toString ( );

                JSONArray parent = new JSONArray ( mainfile );
                int i = 0;
                while (i <= parent.length ( )) {


                    JSONObject child = parent.getJSONObject ( i );
                    int id = child.getInt ( "id" );
                    String name = child.getString ( "name" );
                    int price = child.getInt ( "currentPrice" );
                    int status = child.getInt ( "status" );
                    String weight = child.getString ( "weight" );
                    String description = child.getString ( "description" );
                    String image = child.getString ( "image" );
                    int kind = child.getInt ( "idKind" );

                    mangsanpham.add ( new AllProductModel ( id,name,price,status,weight,description,image,kind ) );

                    i++;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            } catch (JSONException e) {
                e.printStackTrace ( );
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );

            adminRcvAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham,getApplicationContext ( ),listener );

            recyclerView.setAdapter ( adminRcvAdapter );

        }
    }




}