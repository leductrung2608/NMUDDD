package com.example.app_comestic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SignInTab extends Fragment {
    EditText email, pass;
    Button signin;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signin_tab, container,false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        signin = root.findViewById(R.id.signin);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        signin.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        signin.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;
    }
}
