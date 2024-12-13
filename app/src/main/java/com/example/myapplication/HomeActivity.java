package com.example.postpilot;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menu Icon Click Listener
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Post Item 1 Click Listener
        LinearLayout postItem1 = findViewById(R.id.postItem1);
        postItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Post plan for December clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Post Item 2 Click Listener
        LinearLayout postItem2 = findViewById(R.id.postItem2);
        postItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Post plan for November clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}