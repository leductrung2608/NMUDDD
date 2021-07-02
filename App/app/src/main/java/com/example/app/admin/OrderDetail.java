package com.example.app.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.OrderDetailModel;
import com.example.app.model.OrderModel;

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

public class OrderDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView button;
    ArrayList<OrderDetailModel> arrayList;
    OrderDetailAdapter adapter;
    String idBill;

    public void getId() {
        OrderModel model = (OrderModel) getIntent ( ).getSerializableExtra ( "idBill" );
        idBill = model.getIdBill ( );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_order_detail );

        recyclerView = findViewById ( R.id.orderDetailRcv );
        button = findViewById(R.id.backBttDetail);

        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext (), LinearLayoutManager.VERTICAL, false ) );

        arrayList = new ArrayList<OrderDetailModel> (  );

        getId ();
        //Toast.makeText ( this, ""+ idBill,Toast.LENGTH_SHORT ).show ();

        JsonOrderDetail jsonOrderDetail = new JsonOrderDetail ();
        jsonOrderDetail.execute (  );

        //back
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetail.super.onBackPressed();
            }
        });

    }



    public class JsonOrderDetail extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getOrderDetail.php" );
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
                    int idBillDetail = child.getInt ( "idBillDetail" );
                    String idBillInDetail = child.getString ( "idBill" );
                    int price = child.getInt ( "price" );
                    int quantity = child.getInt ( "quantity" );
                    String name = child.getString ( "name" );


                    //Toast.makeText ( getApplicationContext (), ""+idBillInDetail,Toast.L ).show ();
                    if(idBillInDetail.equals ( idBill ) ) {
                        arrayList.add ( new OrderDetailModel ( idBillDetail, idBillInDetail, name, price, quantity ) );

                    }

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

            adapter = new OrderDetailAdapter ( arrayList, getApplicationContext ( ));
            recyclerView.setAdapter ( adapter );


        }
    }
}
