package com.example.ibeauty.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ibeauty.R;
import com.example.ibeauty.value.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView change;
    Button signUp;
    TextInputLayout musername, memail, mphoneNo, mpass;
    EditText edusername, edemail, edphoneNo, edpass;

    DatabaseReference reference;
    FirebaseDatabase rootnode;
    Boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_sign_up);

        change = findViewById(R.id.changeSignIn);
        signUp = findViewById(R.id.cirSignUpButton);

        memail = findViewById(R.id.textInputEmail);
        musername = findViewById(R.id.textInputName);
        mpass = findViewById(R.id.textInputPassword);
        mphoneNo = findViewById(R.id.textInputMobile);

        edusername = findViewById(R.id.editTextName);
        edemail = findViewById(R.id.editTextEmail);
        edphoneNo = findViewById(R.id.editTextMobile);
        edpass = findViewById(R.id.editTextPassword);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("Users");
                String email = memail.getEditText().getText().toString();
                String username = musername.getEditText().getText().toString();

                Query checkUser = reference.orderByChild("username").equalTo(username);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            edusername.setError("The username has already been taken");
                            check = false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

                if (check == true) {
                    Validate();

                }
                check = true;
            }
        });


    }

    private void Validate(){
        String username = musername.getEditText().getText().toString();
        String email = memail.getEditText().getText().toString();
        String phoneNo = mphoneNo.getEditText().getText().toString();
        String password = mpass.getEditText().getText().toString();
        String address = " 123 ";

        if (username.isEmpty()){

            edusername.setError("Required");

        }else if(phoneNo.isEmpty()){

            edphoneNo.setError("Required");

        }else if(email.isEmpty()){

            edemail.setError("Required");

        }else if(password.isEmpty()){

            edpass.setError("Required");

        }else if (mpass.getEditText().length() <6){

            edpass.setError("Password must be at least 6 characters");

        }
        else if(isValidEmailId(email) == false){
            edemail.setError("Email is incorrect");

        }else {
            User user = new User(username, phoneNo, email, password, address);
            reference.child(username).setValue(user);
            Toast.makeText(SignUpActivity.this, "Sign in successfully!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        }

    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void transparentStatusAndNavigation ()
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

    private void setWindowFlag ( final int bits, boolean on){
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
