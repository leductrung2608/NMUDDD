package com.example.app_comestic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTaps;

    public LoginAdapter(FragmentManager fm, Context context, int TotalTabs){
        super(fm);
        this.context = context;
        this.totalTaps = TotalTabs;
    }

    @Override
    public int getCount() {
        return totalTaps;
    }

    public Fragment getItem(int position) {
        switch(position){
            case 0:
                SignUpTab signUpTab = new SignUpTab();
                return signUpTab;
            case 1:
                SignInTab signInTab = new SignInTab();
                return signInTab;
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "SIGN UP";
            case 1:
                return "SIGN IN";
        }
        return null;
    }
}



