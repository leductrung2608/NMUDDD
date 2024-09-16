package com.example.ibeauty_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    Button SignUpbtt;
    EditText memail, mpass;
    TextView change;
    float v=0;
    FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_signup);

        SignUpbtt = findViewById(R.id.signin);
        memail = findViewById(R.id.email);
        mpass = findViewById(R.id.pass);
        change = findViewById(R.id.already);

        memail.setTranslationX(800);
        mpass.setTranslationX(800);
        SignUpbtt.setTranslationX(800);

        memail.setAlpha(v);
        mpass.setAlpha(v);
        SignUpbtt.setAlpha(v);

        memail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        SignUpbtt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        mfirebaseAuth = FirebaseAuth.getInstance();

        SignUpbtt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               createUser();
           }
       });

       change.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
               finish();
           }
       });
    }

    private void createUser(){
        String email = memail.getText().toString();
        String pass = mpass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty()) {
                mfirebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "Registered Successfully!!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,"Registered Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                memail.setError("Required");
            }
        }else if(email.isEmpty()){
            memail.setError("Required");
        }else if(pass.length() <= 6){
            mpass.setError("Password must be at least 6 characters");
        }else{
            memail.setError("Please enter correct email");
        }
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
