package com.example.bookplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    Animation bottomAnim, topAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide(); //hide action bar

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.loading_anim_bottom);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.loading_anim_top);

        findViewById(R.id.textView).setAnimation(bottomAnim);
        findViewById(R.id.textView2).setAnimation(bottomAnim);
        findViewById(R.id.imageView).setAnimation(topAnim);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }, 5000);
    }
}