package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empty);

        // Find the button by its ID
        Button createThreadButton = findViewById(R.id.createThreadButton);

        // Set a click listener for the button
        createThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a simple toast message (you can replace this with an actual action)
                Toast.makeText(HomeEmptyActivity.this, "Create New Thread Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}