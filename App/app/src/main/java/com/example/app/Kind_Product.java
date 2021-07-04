package com.example.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.Home_AllItem_RecyclerAdapter;
import com.example.app.fragment.HomeFragment;
import com.example.app.model.AllProductModel;

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

public class Kind_Product extends AppCompatActivity {
    ArrayList<AllProductModel> mangsanpham;
    RecyclerView allItemRecyclerView;
    ImageView imageView;
    TextView textView;
    private Home_AllItem_RecyclerAdapter.RecyclerViewClickListener listener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_kind_product );

        imageView = findViewById ( R.id.backBttDetail);
        textView = findViewById ( R.id.kindTextAdmin );

        imageView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getApplicationContext (),MainActivity.class );
                startActivity ( intent );
            }
        } );



        allItemRecyclerView = findViewById ( R.id.rcvKind );
        allItemRecyclerView.setHasFixedSize ( true );
        allItemRecyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (), 2 ) );

        mangsanpham = new ArrayList<AllProductModel> (  );

        setOnClickListener();

        ///do du lieu from data kind
        if(HomeFragment.i == 0) {

            textView.setText ( "Lipstick" );
            JsonFetchSon jsonFetchSon = new JsonFetchSon ();
            jsonFetchSon.execute (  );

        }else if (HomeFragment.i == 1) {
            textView.setText ( "Eyes" );
            JsonFetchMat jsonFetchMat = new JsonFetchMat ();
            jsonFetchMat.execute (  );

        }else if (HomeFragment.i==2) {
            textView.setText ( "Blush" );
            JsonFetchPhan jsonFetchPhan = new JsonFetchPhan ();
            jsonFetchPhan.execute (  );
        }else if (HomeFragment.i==3) {
            textView.setText ( "Foundation" );
            JsonFetchKemNen jsonFetchKemNen = new JsonFetchKemNen ();
            jsonFetchKemNen.execute (  );

        }else if (HomeFragment.i==4) {
            textView.setText ( "Pink blush" );
            JsonFetchMaHong jsonFetchMaHong = new JsonFetchMaHong ();
            jsonFetchMaHong.execute (  );



        }else {
            textView.setText ( "Mascara" );
            JsonFetchMascara jsonFetchMascara = new JsonFetchMascara ();
            jsonFetchMascara.execute (  );

        }

        ;


    }

    private void setOnClickListener() {
        listener1 = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
            intent.putExtra("information", mangsanpham.get(position) );
            startActivity(intent);
        };
    }


    public class JsonFetchSon extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getSon.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ),listener1 );

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }




    public class JsonFetchMat extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getMat.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ),listener1 );

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }




    public class JsonFetchPhan extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getPhan.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ),listener1 );

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }



    public class JsonFetchKemNen extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getKemNen.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ),listener1 );

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }



    public class JsonFetchMaHong extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getMaHong.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ) ,listener1);

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }


    public class JsonFetchMascara extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getMas.php" );
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

                    mangsanpham.add ( new AllProductModel ( id, name, price, status, weight, description, image, kind ) );

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

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter ( mangsanpham, getApplicationContext ( ),listener1 );

            allItemRecyclerView.setAdapter (homeAllItemRecyclerAdapter);


        }
    }


}