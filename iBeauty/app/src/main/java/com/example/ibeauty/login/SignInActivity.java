package com.example.ibeauty.login;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ibeauty.home.HomeActivity;
import com.example.ibeauty.R;
import com.example.ibeauty.intro.PrefManager;
import com.example.ibeauty.intro.WelcomeActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignInActivity extends AppCompatActivity {
    TextView change2;
    Button signIn;
    SignInButton gg;
    LoginButton fb;
    EditText edusername2, edpass2;
    TextInputLayout musername2, mpass2;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    AccessTokenTracker accessToken;
    CallbackManager callbackManager;
    DatabaseReference reference;
    FirebaseDatabase rootnode;
    final static int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_sign_in);

        change2 = findViewById(R.id.changeRegister);
        signIn = findViewById(R.id.cirSignInpButton);
        gg = findViewById(R.id.gg_btt);
        fb = findViewById(R.id.fb_btt);

        edusername2 = findViewById(R.id.editTextEmail2);
        edpass2 = findViewById(R.id.editTextPass2);

        musername2 = findViewById(R.id.textInputEmail2);
        mpass2 = findViewById(R.id.textInputPassword2);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.tvapp_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(SignInActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isUser();
            }
        });

        //Google Sign In
        createRequest();
        gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGG();


            }
        });


        //Facebook Sign In
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fb.setReadPermissions("email", "public_profile");
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "onError" + error);
            }
        });

//        accessToken = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null){
//                    mAuth.signOut();
//                }
//            }
//        };



    }

    private void isUser(){
        String enterUsername = musername2.getEditText().getText().toString().trim();
        String enterPass = mpass2.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("username").equalTo(enterUsername);

        if (enterUsername.isEmpty()){
            edusername2.setError("Required");
        }else if(enterPass.isEmpty()){
            edpass2.setError("Required");
        }else{

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        String passDB = snapshot.child(enterUsername).child("password").getValue(String.class);

                        if (passDB.equals(enterPass)){
                            String usernameDB = snapshot.child(enterUsername).child("username").getValue(String.class);
                            String phoneNoDB = snapshot.child(enterUsername).child("phoneNo").getValue(String.class);
                            String emailDB = snapshot.child(enterUsername).child("email").getValue(String.class);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                            intent.putExtra("username", usernameDB);
                            intent.putExtra("phoneNo", phoneNoDB);
                            intent.putExtra("email", emailDB);
                            intent.putExtra("password", passDB);

                            startActivity(intent);
                        }
                        else{
                            edpass2.setError("Wrong password");
                        }
                    }else{
                        edusername2.setError("No such user exist");
                        edusername2.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    //Sign In GG
    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signInGG() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "Sign in Google successfully!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                            finish();

                        } else {
                            Toast.makeText(SignInActivity.this, "Sign in Google failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //FB Sign In
    private  void handleFacebookToken(AccessToken token){
        Log.d("TAG", "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential((token.getToken()));
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();

                }else{
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                }
            }
        });
    }


    //Trans statusbar
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