package com.example.app.admin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.app.model.AllProductModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Detail extends AppCompatActivity {

    String arr[]={"LipStick", "Eyes", "Powder", "Foundation", "PinkPowder", "Mascara"};
    // Spinner spin;
    EditText id, name, price, quantity, weight, description;
    ImageView imageView;
    Button  upload, dalete;
    Spinner spinner;
    Bitmap bitmap;
    //String encodedimage;
    String check;
    ImageView back;
    //String updateSQL ="https://ibeautycosmetic.000webhostapp.com/updateInfo.php";
    ProgressDialog mProgressDialog;
    int Id = 0;
    String Name = "";
    String Weight = "0";
    int CurrentPrice = 0;
    String Image = "";
    String Description = "";
    int Quantity = 1;
    int Kind = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_detail );

        check = "0";
        AnhXa ();
        Spin ();
        GetInformation ();

        SelectImage ();
        upload.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                UpdateData ();
                Intent i = new Intent ( Detail.this, Admin.class );
                startActivity ( i );
            }
        } );
        dalete.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder( Detail.this);
                b.setTitle("Delete product");
                b.setMessage("You want to delete this product?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteData ();
                        Intent i = new Intent ( Detail.this, Admin.class );
                        startActivity ( i );
                        finish();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();

            }
        } );
        back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent i = new Intent ( Detail.this, Admin.class );
                startActivity ( i );
                finish();
            }
        } );

    }
    private void Spin() {

        ArrayAdapter<String> adapter=new ArrayAdapter<String> ( this, android.R.layout.simple_spinner_item, arr );
        adapter.setDropDownViewResource (android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        //spin.setOnItemSelectedListener(new MyProcessEvent());
    }

    private void GetInformation() {
        AllProductModel productModel = (AllProductModel) getIntent().getSerializableExtra( "information");

        Id = productModel.getIdGoods();
        Name = productModel.getName();
        Weight = productModel.getWeight();
        CurrentPrice = productModel.getCurrentPrice();
        Quantity = productModel.getStatus ();
        Image = productModel.getImage();
        Description = productModel.getDescription();
        Kind = productModel.getKind();

        id.setText ( Id +"");
        name.setText(Name);
        price.setText ( CurrentPrice + "" );
        quantity.setText ( Quantity + "" );
        weight.setText(Weight);

        description.setText ( Description );
        if(Kind==1){
            spinner.setSelection ( 0 ) ;
        }else if(Kind == 2){
            spinner.setSelection ( 1 );
        }else if(Kind == 3){
            spinner.setSelection ( 2 );
        }else if(Kind == 4){
            spinner.setSelection ( 3 );
        }else if(Kind == 5){
            spinner.setSelection ( 4 );
        }else if(Kind == 6){
            spinner.setSelection ( 5 );
        }

        Picasso.get().load(Image)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageView);

    }



    private void AnhXa(){

        id = findViewById ( R.id.idGoodAM );
        name = findViewById(R.id.nameGoodAM);
        price = findViewById ( R.id.priceGoodAM );
        quantity = findViewById ( R.id.numberGoodAM );
        weight = findViewById(R.id.weightGoodAM);
        description = findViewById(R.id.descriptionGoodAM);
        imageView = findViewById(R.id.imageGoodAM);
        spinner = findViewById ( R.id.kindGoodAM );
        back = findViewById ( R.id.backBttDetail );
        //chooseImage1 = findViewById ( R.id.chooseImage1 );
        upload= findViewById ( R.id.updateBtt );
        dalete = findViewById ( R.id.deleteBtt );
    }



    private void SelectImage() {

        imageView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                check = "1";
                if(ContextCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.READ_EXTERNAL_STORAGE )
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions (
                            Detail.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
                    );
                }else{

                    Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
                    startActivityForResult ( intent,1 );
                }
            }
        } );
    }


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
        Image = Base64.encodeToString ( imageBytes, Base64.DEFAULT);

    }


    private void UpdateData() {
        String IdG = id.getText().toString ();
        String NameG = name.getText().toString();
        String PriceG = price.getText().toString();
        String NumberG = quantity.getText().toString();
        String WeightG = weight.getText().toString();
        String DescriptionG = description.getText().toString();

        String temp = spinner.getSelectedItem().toString();
        String KindG = null;
        if(temp.equals ( "LipStick" )){
            KindG = "1";
        }else if(temp.equals ( "Eyes" )){
            KindG = "2";
        }else if(temp.equals ( "Powder" )){
            KindG = "3";
        }else if(temp.equals ( "Foundation" )){
            KindG = "4";
        }else if(temp.equals ( "PinkPowder" )){
            KindG = "5";
        }else if(temp.equals ( "Mascara" )){
            KindG = "6";
        }

        if (NameG.equals("")||PriceG.equals("")||NumberG.equals("")||WeightG.equals("")||DescriptionG.equals("")){
            Toast.makeText(Detail.this, "Please enter full infomation!", Toast.LENGTH_SHORT).show();
        }
        else {

            register(IdG, NameG, PriceG,NumberG,WeightG,DescriptionG, Image, check,  KindG);
        }
    }
    private void register(String IdR,String NameR, String PriceR, String NumberR, String WeightR,String DescriptionR,String Image,String Check,  String KindR) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Detail.this);
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
                Toast.makeText( getApplicationContext(), "Update product successfully!", Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put ("IdGoods",IdR);
                data.put("Name",NameR);
                data.put("CurrentPrice",PriceR);
                data.put("Status",NumberR);
                data.put("Weight",WeightR);
                data.put("Description",DescriptionR);
                data.put("Image",Image);
                //data.put("ImageNew",ImageRN);
                data.put("Check",Check);
                data.put("Id",KindR);

                String result = ruc.sendPostRequest("https://ibeautycosmetic.000webhostapp.com/updateInfo.php", data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute (IdR, NameR,PriceR,NumberR,WeightR,DescriptionR,Image,Check ,KindR);
    }











    private void DeleteData() {

        String IdGoods = id.getText().toString();

        register1(IdGoods);
    }


    private void register1(String Id) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Detail.this);
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
                Toast.makeText(getApplicationContext(),  "Delete product successfully!", Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("IdGoods",Id);


                String result = ruc.sendPostRequest("https://ibeautycosmetic.000webhostapp.com/deleteData.php", data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Id);
    }
}
