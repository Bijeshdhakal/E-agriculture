package com.bjshDkl.agriculture.splash;

import android.content.Intent;
import android.os.Bundle;

import com.bjshDkl.agriculture.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bjshDkl.agriculture.R;

public class SplashActivity extends AppCompatActivity {

    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appname = (TextView) findViewById(R.id.textView);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_zoom_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SplashActivity.this.finish();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        appname.setAnimation(animation);
    }

}
