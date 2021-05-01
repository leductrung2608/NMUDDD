package com.example.ibeauty_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    Button signout;
    GoogleSignInClient client;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authStateListener;
    TextView userName;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signout = findViewById(R.id.signout);
        userName = findViewById(R.id.userName);
        avatar = findViewById(R.id.avatar);

        mFirebaseAuth = FirebaseAuth.getInstance();

        client = GoogleSignIn.getClient(MainActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        //update UI
        user = mFirebaseAuth.getCurrentUser();
        userName.setText(user.getDisplayName());
        if (user.getPhotoUrl() != null){
            String photoUrl = user.getPhotoUrl().toString();
            Picasso.get().load(photoUrl).into(avatar);
        }else
        {
            avatar.setImageResource(R.drawable.woman);
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mFirebaseAuth.signOut();

                            LoginManager.getInstance().logOut();

                            Toast.makeText(MainActivity.this, "Sign out successfully",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(MainActivity.this, SignInActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}