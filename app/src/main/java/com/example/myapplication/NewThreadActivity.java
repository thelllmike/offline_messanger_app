package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewThreadActivity extends AppCompatActivity {

    private EditText threadNameInput;
    private EditText descriptionInput;
    private Button createThreadButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread);

        threadNameInput = findViewById(R.id.threadNameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        createThreadButton = findViewById(R.id.createThreadButton);

        dbHelper = new DatabaseHelper(this);

        createThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveThread();
            }
        });
    }

    private void saveThread() {
        String threadName = threadNameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (threadName.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertThread(threadName, description);

        if (isInserted) {
            Toast.makeText(this, "Thread Created", Toast.LENGTH_SHORT).show();
            // Navigate to HomeActivity
            startActivity(new Intent(NewThreadActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Failed to Create Thread", Toast.LENGTH_SHORT).show();
        }
    }
}