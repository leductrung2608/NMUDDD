package com.example.ibeauty_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class IntroduceActivity extends AppCompatActivity {

    ImageView logo, appname, splashimg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusAndNavigation();
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
                    Intent intent = new Intent(com.example.ibeauty_app.IntroduceActivity.this, SignUpActivity.class);
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

    private void transparentStatusAndNavigation()
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
    private void setWindowFlag(final int bits, boolean on) {
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
