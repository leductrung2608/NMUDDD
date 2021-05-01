package com.example.ibeauty_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.ibeauty_app.R.id.resetPass;

public class SignInActivity extends AppCompatActivity {

    EditText memail, mpass;
    Button signin;
    SignInButton gg;
    LoginButton fb;
    TextView forgotpass;
    TextView change;

    ImageView avatar;
    TextView userName;

    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 123;
    String TAG ="FB";

    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    AccessTokenTracker accessToken;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_signin);

        memail = findViewById(R.id.email);
        forgotpass = findViewById(resetPass);
        mpass = findViewById(R.id.resetpass);
        signin = findViewById(R.id.reset);
        change = findViewById(R.id.tv);
        gg = findViewById(R.id.gg);
        fb = findViewById(R.id.fb);

        memail.setTranslationX(800);
        forgotpass.setTranslationX(800);
        mpass.setTranslationX(800);
        signin.setTranslationX(800);
        change.setTranslationX(800);
        gg.setTranslationX(800);
        fb.setTranslationX(800);

        memail.setAlpha(v);
        mpass.setAlpha(v);
        signin.setAlpha(v);
        forgotpass.setAlpha(v);
        gg.setAlpha(v);
        fb.setAlpha(v);
        change.setAlpha(v);

        memail.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(300).start();
        mpass.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        signin.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        change.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(300).start();
        forgotpass.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        gg.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        fb.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();

        mpass.setTransformationMethod(new PasswordTransformationMethod());

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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signInwithGG();
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });

        //Forgot password
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });

        //Sign in with Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);
            }
        });

        accessToken = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null){
                    mfirebaseAuth.signOut();
                }
            }
        };

    }

    //Sign in with FB
    private  void handleFacebookToken(AccessToken token){
        Log.d(TAG, "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential((token.getToken()));
        mfirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    FirebaseUser user = mfirebaseAuth.getCurrentUser();

                }else{
                    Toast.makeText(SignInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Sign in with GG
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                String s = "Google sign in successfully";
                displayToast(s);
                try {
                    GoogleSignInAccount signInAccount = signInAccountTask.getResult(ApiException.class);

                    if (signInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                        mfirebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(SignInActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            displayToast("Successful");
                                        } else {
                                            displayToast("Failed" + task.getException().getMessage());
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    //Sign in
    private void loginUser(){
        String email2 = memail.getText().toString();
        String pass2 = mpass.getText().toString();

        if (!email2.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            if (!pass2.isEmpty()) {
                mfirebaseAuth.signInWithEmailAndPassword(email2, pass2)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this, "Sign in Successfully!!", Toast.LENGTH_LONG).show();
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

    //Transparent
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