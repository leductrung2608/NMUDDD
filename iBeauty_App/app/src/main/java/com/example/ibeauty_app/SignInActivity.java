package com.example.ibeauty_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    private int RC_SIGN_IN = 1;
    EditText memail, mpass;
    ImageView gg, fb;
    Button signin;
    TextView change;

    FirebaseAuth mfirebaseAuth;

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    private  String TAG = "MainActivity";
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_signin);

        memail = findViewById(R.id.email);
        mpass = findViewById(R.id.pass);
        signin = findViewById(R.id.signin);
        change = findViewById(R.id.tv);
        gg = findViewById(R.id.gg);

        memail.setTranslationX(800);
        mpass.setTranslationX(800);
        signin.setTranslationX(800);

        memail.setAlpha(v);
        mpass.setAlpha(v);
        signin.setAlpha(v);

        memail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        mfirebaseAuth = FirebaseAuth.getInstance();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        //Sign In with Google

    }

    private void loginUser(){
        String email2 = memail.getText().toString();
        String pass2 = mpass.getText().toString();

        if (!email2.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            if (!pass2.isEmpty()) {
                mfirebaseAuth.signInWithEmailAndPassword(email2, pass2)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this, "Sign In Successfully!!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this,"Email or Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });
            }else if(pass2.isEmpty()) {
                mpass.setError("Required");
            }
        }else if(email2.isEmpty()){
            memail.setError("Required");
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