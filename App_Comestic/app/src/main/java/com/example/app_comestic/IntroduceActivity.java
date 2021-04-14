package com.example.app_comestic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

public class IntroduceActivity extends AppCompatActivity {

    ImageView logo, appname, splashimg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        Thread time =new Thread(){
            public void run()
            {
                try {
                    sleep(6000);
                } catch (Exception e) {

                }
                finally
                {
                    Intent intent = new Intent(IntroduceActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        time.start();

        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.app_name);
        splashimg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        splashimg.animate().translationY(-3200).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


    }
}
