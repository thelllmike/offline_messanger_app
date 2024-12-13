package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Splash screen delay before navigating to the next screen
        new Handler().postDelayed(() -> {
            // Replace NextActivity.class with your actual next activity
            Intent intent = new Intent(MainActivity.this, HomeEmptyActivity.class);
           startActivity(intent);
            finish(); // Close the splash activity
        }, SPLASH_DURATION);
    }
}