package com.example.ibeauty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ibeauty.home.HomeActivity;
import com.example.ibeauty.login.SignInActivity;
import com.example.ibeauty.value.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextInputLayout musername, mphoneNo, memail, maddress;
    Button signOut, update, back;
    TextView lbusername, lbemail;
    ImageView avt;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference reference;
    FirebaseDatabase rootnode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
        setContentView(R.layout.activity_profile);

        musername = findViewById(R.id.textInputProfileUsername);
        mphoneNo = findViewById(R.id.textInputProfileMobile);
        memail = findViewById(R.id.textInputProfileEmail);
        maddress = findViewById(R.id.textInputProfileAddress);
        signOut = findViewById(R.id.cirSignOutButton);
        lbemail = findViewById(R.id.lb_emailuser);
        lbusername = findViewById(R.id.lb_username);
        avt = findViewById(R.id.avatar_user);
        update = findViewById(R.id.cirUpdateButton);
        back = findViewById(R.id.btt_back_home);

        //Back home
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                finish();
            }
        });

        showAllDataUser();

        //update UI FB
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            musername.getEditText().setText(currentUser.getDisplayName());
            memail.getEditText().setText(currentUser.getEmail());
            mphoneNo.getEditText().setText(currentUser.getPhoneNumber());
            lbusername.setText(currentUser.getDisplayName());
            lbemail.setText(currentUser.getEmail());

            String url = currentUser.getPhotoUrl().toString();
            Picasso.with(this).load(url).into(avt);

        }

        //update UI GG
        mGoogleSignInClient = GoogleSignIn.getClient(ProfileActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            musername.getEditText().setText(account.getDisplayName());
            memail.getEditText().setText(account.getEmail());
            lbusername.setText(account.getDisplayName());
            lbemail.setText(account.getEmail());

            String url = account.getPhotoUrl().toString();
            Picasso.with(this).load(url).into(avt);

        }

        //Sign out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                LoginManager.getInstance().logOut();
                mAuth.signOut();
                Toast.makeText(ProfileActivity.this, "Sign out successfully!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(ProfileActivity.this, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        //Update profile
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("Users");

                String email = memail.getEditText().getText().toString();
                String username = musername.getEditText().getText().toString();
                String phoneNo = mphoneNo.getEditText().getText().toString();
                String address = maddress.getEditText().getText().toString();

                Intent intent = getIntent();
                String pass = intent.getStringExtra("password");;

                Query checkUser = reference.orderByChild("username").equalTo(username);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user = new User(username, phoneNo, email, pass, address);
                        reference.child(username).setValue(user);
                        Toast.makeText(ProfileActivity.this, "Update successfully!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

    }

    private void showAllDataUser(){
        Intent intent = getIntent();

        String profile_username = intent.getStringExtra("username");
        String profile_phoneNo = intent.getStringExtra("phoneNo");
        String profile_email = intent.getStringExtra("email");
        String profile_address = intent.getStringExtra("address");

        musername.getEditText().setText(profile_username);
        mphoneNo.getEditText().setText(profile_phoneNo);
        memail.getEditText().setText(profile_email);
        maddress.getEditText().setText(profile_address);
        lbusername.setText(profile_username);
        lbemail.setText(profile_email);
    }

    //transparent statusbar
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
