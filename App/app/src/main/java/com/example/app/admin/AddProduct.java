package com.example.app.admin;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.app.R;
import com.example.app.RegisterUserClass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class AddProduct extends AppCompatActivity {

    String arr[]={
            "LipStick",
            "Eyes",
            "Powder",
            "Foundation",
            "PinkPowder",
            "Mascara"
    };
    EditText name,price,number,weight,description;
    Spinner spin;
    Button insertdata;
    ImageView back;
    ImageView imageView;
    ProgressDialog mProgressDialog;
    Bitmap bitmap;
    String encodedimage;
    private static final String INSERTDATA_URL = "https://ibeautycosmetic.000webhostapp.com/pushInfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_product);

        AnhXa ();
        SelectImage();


        insertdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim().toLowerCase();
                String Price = price.getText().toString().trim().toLowerCase();
                String Number = number.getText().toString().trim().toLowerCase();
                String Weight = weight.getText().toString().trim().toLowerCase();
                String Description = description.getText().toString().trim().toLowerCase();

               // String  temp = spin.getSelectedItem().toString().trim().toLowerCase();


                if (Name.equals("")||Price.equals("")||Number.equals("")||Weight.equals("")||Description.equals("")){
                    Toast.makeText(AddProduct.this, "Please enter full infomation!", Toast.LENGTH_SHORT).show();
                }
                InsertData();

            }
        });
        back = findViewById ( R.id.backBttAdd );
        ClickBack ();
        Spin();

    }

    private void SelectImage() {

        imageView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.READ_EXTERNAL_STORAGE )
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions (
                            AddProduct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
                    );
                }else{

                    Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
                    startActivityForResult ( intent,1 );
                }
            }
        } );
    }

    private void Spin() {

        ArrayAdapter<String> adapter=new ArrayAdapter<String> ( this, android.R.layout.simple_spinner_item, arr );
        adapter.setDropDownViewResource (android.R.layout.simple_list_item_single_choice);
        spin.setAdapter(adapter);
        //spin.setOnItemSelectedListener(new MyProcessEvent());
    }

    private void ClickBack(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( AddProduct.this, Admin.class );
                startActivity ( i );
                    }
                });

    }


    public void AnhXa(){

        name = findViewById ( R.id.name );
        price = findViewById ( R.id.price );
        number = findViewById ( R.id.number );
        weight = findViewById ( R.id.weight );
        description = findViewById ( R.id.description );
        //image = findViewById ( R.id.image );
        imageView = findViewById ( R.id.image );
        spin= findViewById( R.id.kind);
        insertdata = findViewById ( R.id.addBtt );
        back = findViewById ( R.id.backBttDetail);
        //chooseImage = findViewById ( R.id.chooseImage );



    }
    private void getData(){

    }
    private void InsertData() {

        String Name = name.getText().toString().trim().toLowerCase();
        String Price = price.getText().toString().trim().toLowerCase();
        String Number = number.getText().toString().trim().toLowerCase();
        String Weight = weight.getText().toString().trim().toLowerCase();
        String Description = description.getText().toString().trim().toLowerCase();

        //String Kind = kind.getText().toString().trim().toLowerCase();
        //String temp = spin.getSelectedItem().toString();
        String temp = spin.getSelectedItem().toString();
        String Kind = null;
        if(temp.equals ( "LipStick" )){
            Kind = "1";
        }else if(temp.equals ( "Eyes" )){
            Kind = "2";
        }else if(temp.equals ( "Powder" )){
            Kind = "3";
        }else if(temp.equals ( "Foundation" )){
            Kind = "4";
        }else if(temp.equals ( "PinkPowder" )){
            Kind = "5";
        }else if(temp.equals ( "Mascara" )){
            Kind = "6";
        }

        if (Name.equals("")||Price.equals("")||Number.equals("")||Weight.equals("")||Description.equals("")){
            Toast.makeText(AddProduct.this, "Please enter full information!", Toast.LENGTH_SHORT).show();
        }
        else {
            register(Name, Price,Number,Weight,Description,encodedimage,Kind);
        }
    }

    private void register(String Name, String Price, String Number, String Weight,String Description,String Image,String Kind) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(AddProduct.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setMessage("Please wait a moment...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgress(0);
                mProgressDialog.setProgressNumberFormat(null);
                mProgressDialog.setProgressPercentFormat(null);
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Add product successfully!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddProduct.this,AddProduct.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("Name",params[0]);
                data.put("CurrentPrice",params[1]);
                data.put("Status",params[2]);
                data.put("Weight",params[3]);
                data.put("Description",params[4]);
                data.put("Image",params[5]);
                data.put("Id",params[6]);


                String result = ruc.sendPostRequest(INSERTDATA_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Name,Price,Number,Weight,Description,Image,Kind);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( requestCode == 1 & resultCode == RESULT_OK && data != null){

            Uri filePath = data.getData ();
            try {
                InputStream inputStream = getContentResolver ().openInputStream ( filePath );
                bitmap = BitmapFactory.decodeStream ( inputStream );
                imageView.setImageBitmap ( bitmap );

                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace ( );
            }
        }

        super.onActivityResult ( requestCode, resultCode, data );

    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream (  );
        bitmap.compress ( Bitmap.CompressFormat.JPEG,100,stream );

        byte[] imageBytes = stream.toByteArray ();

        encodedimage = Base64.encodeToString ( imageBytes, Base64.DEFAULT);

    }
}