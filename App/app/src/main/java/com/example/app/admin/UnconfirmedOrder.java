package com.example.app.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.model.OrderModel;
import com.example.app.RegisterUserClass;

import org.jetbrains.annotations.NotNull;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UnconfirmedOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView button;
    Button showBtt2;
    ArrayList<OrderModel> arrayList;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_unconfirmed_order );

        //showBtt2 = findViewById ( R.id.showBillBtt2 );
        //showBtt2.setVisibility ( View.GONE );

        recyclerView = findViewById ( R.id.unconfirmRcv );
        button = findViewById ( R.id.backBtt );

        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext (), LinearLayoutManager.VERTICAL, false ) );

        arrayList = new ArrayList<OrderModel> (  );

        JsonBill jsonBill = new JsonBill ();
        jsonBill.execute (  );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnconfirmedOrder.super.onBackPressed();
            }
        });

    }









    public class JsonBill extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getBill.php" );
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
                    String idBill = child.getString ( "idBill" );
                    String name = child.getString ( "name" );
                    String mobile = child.getString ( "mobile" );
                    String address = child.getString ( "address" );
                    String total = child.getString ( "total" );
                    String shippingCharges = child.getString ( "shippingCharges" );
                    String grandTotal = child.getString ( "grandTotal" );
                    String idUser = child.getString ( "idUser" );
                    String situation = child.getString ( "situation" );

                    arrayList.add ( new OrderModel ( idBill, name, mobile, address, total, shippingCharges, grandTotal, idUser, situation ) );

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

            adapter = new OrderAdapter ( arrayList,getApplicationContext ( ));
            recyclerView.setAdapter ( adapter );




        }
    }



    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
        //public  int i;
        ArrayList<OrderModel> list;
        Context context;

        public OrderAdapter(ArrayList<OrderModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public OrderHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.bill_item, parent, false );
            return new OrderHolder ( view );

        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull OrderHolder holder, int position) {

            //i = position;
            OrderModel currentData = list.get ( position );
            holder.name.setText ( currentData.getNameUser ( ) );
            holder.phone.setText ( currentData.getMobile ( ) );
            DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
            holder.total.setText (currentData.getGrandTotal ( ) );
            holder.address.setText ( currentData.getAddress ( ) );
            holder.situasiton.setText ( currentData.getSituation () );
            holder.show.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent ( getApplicationContext(), OrderDetail.class );
                    i.putExtra ( "idBill", currentData );
                    startActivity ( i );


                }
            } );


            holder.confirm.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    //adapter.notifyDataSetChanged ();
                    notifyDataSetChanged();

                    String a = list.get ( position ).getIdBill ();
                    String b = list.get ( position ).getAddress ();
//                    holder.id.setText ( currentData.getIdBill () );
//                    holder.address.setText ( currentData.getAddress ( ) );
//                    holder.situasiton.setText ( currentData.getSituation () );

                    String proceed = list.get ( position ).getGrandTotal ();

                    Date currentTime = Calendar.getInstance().getTime();
                    String month = currentTime.toString ();
                    SimpleDateFormat sdf = new SimpleDateFormat ( "M");
                    month = sdf.format(new Date());

                    register (a,b,"1");
                    registerProceed (proceed,month);
                    list.remove ( position );
                    //Toast.makeText ( getApplicationContext (), a+b+c ,Toast.LENGTH_LONG ).show ();
                }
            } );
            holder.show.setVisibility ( View.VISIBLE);
            holder.show2.setVisibility ( View.GONE);
            holder.confirm.setVisibility ( View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return list.size ( );
        }

        public class OrderHolder extends RecyclerView.ViewHolder {

            TextView name, phone, total, id, situasiton;
            EditText address;
            Button show, confirm, show2;

            public OrderHolder(@NonNull @NotNull View itemView) {

                super ( itemView );
                itemView.setTag(this);
                situasiton = itemView.findViewById ( R.id.situationBillTv );
                name = itemView.findViewById ( R.id.namebillTv );
                phone = itemView.findViewById ( R.id.quantityBillTv );
                total = itemView.findViewById ( R.id.priceBillTv );
                address = itemView.findViewById ( R.id.addressBillEdt );
                show = itemView.findViewById ( R.id.showBillBtt );
                confirm = itemView.findViewById ( R.id.confirmBtt );

                show2 =itemView.findViewById ( R.id.showBillBtt2 );


            }

        }
    }


    private void register(String IdR,String AddressR,String SituationR) {

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
                Toast.makeText( getApplicationContext(), "Confirm order successfully!", Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put ("IdBill",IdR);
                data.put("Address",AddressR);
                data.put("Situation",SituationR);

                String result = ruc.sendPostRequest("https://ibeautycosmetic.000webhostapp.com/updateBill.php", data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute (IdR,AddressR ,SituationR);
    }


    private void registerProceed(String Proceed, String Month) {

        Toast.makeText ( UnconfirmedOrder.this,Month+" , "+Proceed,Toast.LENGTH_LONG ).show ();

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
                //Toast.makeText( getApplicationContext(), "Cộng sản phẩm mới thành công!", Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("Proceed",Proceed);
                data.put ("Month",Month);


                String result = ruc.sendPostRequest("https://ibeautycosmetic.000webhostapp.com/updateProceeds.php", data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute (Proceed,Month);
    }
}
