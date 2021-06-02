package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.login.Login;
import com.google.firebase.auth.FirebaseAuth;

import at.markushi.ui.CircleButton;

public class Admin extends AppCompatActivity {
    CircleButton logout;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logout = findViewById(R.id.btt_logoutAdmin);

        fAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();

                startActivity(new Intent(Admin.this, Login.class));
                finish();
            }
        });
    }
}