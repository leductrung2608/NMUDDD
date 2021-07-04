package com.example.app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.RegisterUserClass;
import com.example.app.adapter.CartAdapter;
import com.example.app.map.Address;
//import com.example.app.map.CurrentLocation;
//import com.example.app.map.Map;
import com.example.app.map.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;


public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private static final String INSERTDATA_URL_BILL = "https://ibeautycosmetic.000webhostapp.com/pushBill.php";

    private static final String INSERTDATA_URL_DELETE_CART = "https://ibeautycosmetic.000webhostapp.com/deleteCart.php";

    private static final String INSERTDATA_URL_BILLDETAIL    = "https://ibeautycosmetic.000webhostapp.com/pushBillDetails.php";

    ArrayList<Integer> mangidbill;//arrayList

    TextView tvAddress, tvPhone, tvUsername;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    ListView lvCart;
    TextView tvNotify;
    static TextView tvTotal, tvGrandTotal, tvShipCharge;
    Button btnPay;
    CartAdapter cartAdapter;
    ImageView edit_phoneNo, edit_address;

    int sizeidbill;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_cart, container, false);

        ImageView ggMap = view.findViewById( R.id.ggMap);
        tvAddress = view.findViewById( R.id.tv_addressCart);
        tvPhone = view.findViewById( R.id.tv_phoneCart);
        tvUsername = view.findViewById( R.id.tv_nameCart);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();

        lvCart = view.findViewById( R.id.listview_cart);
        tvNotify = view.findViewById( R.id.tvNotify);
        tvTotal = view.findViewById( R.id.total);
        tvGrandTotal = view.findViewById( R.id.grand_total);
        tvShipCharge = view.findViewById( R.id.ship_charge);
        btnPay = view.findViewById( R.id.btnPay);
        edit_address = view.findViewById(R.id.edit_address);
        edit_phoneNo = view.findViewById(R.id.edit_phoneNo);

        //change address and phoneNo
        edit_phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPhoneNo();
            }
        });

        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddress();
            }
        });

        //gg Map
        ggMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), PlacePicker.class));
          }
        });

        //Update info user
        DocumentReference df = fStore.collection("Users").document(userId);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    tvUsername.setText(documentSnapshot.getString("Username") );
                    tvPhone.setText(documentSnapshot.getString("PhoneNumber") );
                    tvAddress.setText(documentSnapshot.getString("Address") );
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });

        mangidbill = new ArrayList<Integer> (  );
        JsonFetchIdBill jsonFetchIdBill = new JsonFetchIdBill();
        jsonFetchIdBill.execute (  );

        CheckData();
        EvenUltil();
        Pay();



        cartAdapter = new CartAdapter( getContext(), MainActivity.CartList);
        lvCart.setAdapter(cartAdapter);

        return view;
    }

    public class JsonFetchIdBill extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {
            

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getIdBill.php" );
                httpURLConnection = (HttpURLConnection) url.openConnection ( );
                httpURLConnection.connect ( );

                InputStream inputStream = httpURLConnection.getInputStream ( );
                BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader( inputStream ) );

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
                    int id = child.getInt ( "idBill" );

                    mangidbill.add ( new Integer( id ) );

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
        }
    }

    private void Pay() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeidbill = mangidbill.size();
                if (MainActivity.CartList.size() == 0) {
                    Toast.makeText(getContext(), "Please add product to your cart", Toast.LENGTH_SHORT).show();
                } else {
                    CreateDialog();
                }
            }
        });
    }

    private void CreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure to oder?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Your oder is on the way", Toast.LENGTH_LONG).show();

                String Name1 = tvUsername.getText().toString();
                String Mobile1 = tvPhone.getText().toString();
                String Address1 = tvAddress.getText().toString();
                String Total1 = tvTotal.getText().toString();
                String Shippingcharges1= tvShipCharge.getText().toString();
                String GrandTotal1 = tvGrandTotal.getText().toString();
                String IdUser1 = MainActivity.userId;

                PushBill(Name1, Mobile1, Address1, Total1, Shippingcharges1, GrandTotal1, IdUser1);

                for(int i = 0; i < MainActivity.CartList.size(); i++) {
                    String IdGood2 = String.valueOf(MainActivity.CartList.get(i).getIdGoods());
                    String IdBill2 = String.valueOf(mangidbill.size() + 1);
                    String Name2 = MainActivity.CartList.get(i).getName();
                    String Price2 = String.valueOf(MainActivity.CartList.get(i).getCurrentPrice());
                    String Weight2 = MainActivity.CartList.get(i).getWeight();
                    String Quantity2 = String.valueOf(MainActivity.CartList.get(i).getQuantity());
                    String Image2 = MainActivity.CartList.get(i).getImage();

                    PushBillDetails(IdGood2, IdBill2, Name2, Price2, Weight2, Quantity2, Image2);
                }

                DeleteCart(IdUser1);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }


    private void DeleteCart(String userid) {
        registerDeleteCart(userid);
    }

    private void registerDeleteCart(String userid) {

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

                data.put("IdUser", params[0]);


                String result = ruc.sendPostRequest(INSERTDATA_URL_DELETE_CART, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(userid);

        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);
    }

    private void PushBillDetails(String idGoods, String idBill, String name, String price, String weight, String quantity, String image) {

        registerBillDetails(idGoods, idBill, name, price, weight, quantity, image);

    }

    private void registerBillDetails(String idGoods, String idBill, String name, String price, String weight, String quantity, String image) {

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
                data.put("IdBill",params[1]);
                data.put("Name",params[2]);
                data.put("Price",params[3]);
                data.put("Weight",params[4]);
                data.put("Quantity",params[5]);
                data.put("Image",params[6]);


                String result = ruc.sendPostRequest(INSERTDATA_URL_BILLDETAIL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(idGoods, idBill, name, price, weight, quantity, image);

    }

    private void PushBill(String name, String mobile, String address, String total, String shippingcharges, String grandtotal, String iduser) {

        registerBill(name, mobile, address, total, shippingcharges, grandtotal, iduser);

    }

    private void registerBill(String name, String mobile, String address, String total, String shippingcharges, String grandtotal, String iduser) {

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


                data.put("Name",params[0]);
                data.put("Mobile",params[1]);
                data.put("Address",params[2]);
                data.put("Total",params[3]);
                data.put("ShippingCharges",params[4]);
                data.put("GrandTotal",params[5]);
                data.put("IdUser",params[6]);


                String result = ruc.sendPostRequest(INSERTDATA_URL_BILL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name, mobile, address, total, shippingcharges, grandtotal, iduser);

    }


    public static void EvenUltil() {
        int Total = 0;
        if(MainActivity.CartList.size() != 0){
            for(int i = 0 ; i < MainActivity.CartList.size() ; i++) {
                Total += MainActivity.CartList.get(i).getCurrentPrice();
            }
            DecimalFormat decimalFormat = new DecimalFormat ( "###,###,###" );
            tvTotal.setText(decimalFormat.format(Total));
            int ship_charge = 0;
            if(Total >= 1000000) {
            }else if(Total == 0) {
            }else {
                ship_charge = 30000;
            }
            tvShipCharge.setText(decimalFormat.format(ship_charge));
            int Grand_Total = Total + ship_charge;
            tvGrandTotal.setText( ""+Grand_Total );
        }

    }

    private void CheckData() {
        if(MainActivity.CartList.size() <= 0){

            tvNotify.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        }else {
            tvNotify.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }
    }

    //Get address
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);

    }

    public void onEventMainThread(Address event) {
        tvAddress.setText(event.getAddress());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void openDialogPhoneNo(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phoneno);
        Button btt_done = dialog.findViewById(R.id.btt_done);
        EditText edPhoneNo = dialog.findViewById(R.id.ed_address);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams wLayoutParams = window.getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(wLayoutParams);

        btt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPhoneNo.getText().toString().matches(""))
                {

                }else{
                    tvPhone.setText(edPhoneNo.getText().toString());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openDialogAddress(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_address);
        Button btt_done = dialog.findViewById(R.id.btt_done);
        EditText edAddress= dialog.findViewById(R.id.ed_address);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams wLayoutParams = window.getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(wLayoutParams);

        btt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edAddress.getText().toString().matches(""))
                {

                }else{
                    tvAddress.setText(edAddress.getText().toString());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

}