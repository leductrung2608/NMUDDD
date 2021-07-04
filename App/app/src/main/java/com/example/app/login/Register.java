package com.example.app.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.RegisterUserClass;
import com.example.app.map.Address;
import com.example.app.map.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import de.greenrobot.event.EventBus;


public class Register extends AppCompatActivity {

    public static String idUser;

    EditText username, address, password, email, phoneNo;
    CircularProgressButton btt_register;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        username = findViewById(R.id.editTextName);
        address = findViewById(R.id.editTextAddress);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        phoneNo = findViewById(R.id.editTextMobile);
        btt_register = findViewById(R.id.cirRegisterButton);

        btt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEnteredRegister()) {
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(), "Created Successfully", Toast.LENGTH_LONG).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            String userId = user.getUid ();
                            register ( userId );
                            //idUser = userID;


                            ////////////////////////////////
                            //MainActivity.InsertData ( userID );
                            //Toast.makeText(Register.this, userID, Toast.LENGTH_SHORT).show();

                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Username", username.getText().toString());
                            userInfo.put("Address", address.getText().toString());
                            userInfo.put("PhoneNumber", phoneNo.getText().toString());
                            userInfo.put("Email", email.getText().toString());

                            //if the user is admin
                            userInfo.put("isUser", "1");

                            df.set(userInfo);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getUid());

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", fAuth.getUid());
                            hashMap.put("username", username.getText().toString());
                            hashMap.put("search", username.getText().toString().toLowerCase());
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("isAdmin", "0");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                    }
                                }
                            });
                            //

                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    });
                }
            }

        });

    }

    private boolean checkDataEnteredRegister(){
        String memail = email.getText().toString();
        String mpass = password.getText().toString();
        String maddress= address.getText().toString();
        String mphoneNo = phoneNo.getText().toString();
        String musername = username.getText().toString();

        if (memail.isEmpty()) {
            email.setError("Required");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(memail).matches()){
            email.setError("Invalid email");
            return false;
        } else if (mpass.isEmpty()){
            password.setError("Required");
            return false;
        }else if (mpass.length() < 6){
            password.setError("Password must be at least 6 characters");
            return false;
        }
        else if(musername.isEmpty()){
            username.setError("Required");
            return false;
        }else if (mphoneNo.isEmpty()){
            phoneNo.setError("Required");
            return false;
        }else if (maddress.isEmpty()){
            address.setError("Required");
            return false;
        }else{
            return true;
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

    //Auto get address
    public void onAddressClick(View view){
        startActivity(new Intent(this, PlacePicker.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);

    }

    public void onEventMainThread(Address event) {
        address.setText(event.getAddress());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void register(String id) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
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


                data.put ( "IdUser", id );


                String result = ruc.sendPostRequest ( "https://ibeautycosmetic.000webhostapp.com/getUser.php", data );

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers ( );
        ru.execute ( id );

    }
}
