package com.example.app_comestic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class SignUpTab extends Fragment {

    EditText email2, pass2, cfpass;
    Button signup;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab, container,false);

        email2 = root.findViewById(R.id.email2);
        pass2 = root.findViewById(R.id.pass2);
        cfpass = root.findViewById(R.id.cfpass);
        signup = root.findViewById(R.id.signup);

        email2.setTranslationX(800);
        pass2.setTranslationX(800);
        cfpass.setTranslationX(800);
        signup.setTranslationX(800);

        email2.setAlpha(v);
        pass2.setAlpha(v);
        cfpass.setAlpha(v);
        signup.setAlpha(v);

        email2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        cfpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
