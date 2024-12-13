package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 1;

    private DatabaseHelper dbHelper;
    private ListView messageListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> messageList;
    private EditText messageInput;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        dbHelper = new DatabaseHelper(this);
        messageListView = findViewById(R.id.messageListView);
        messageInput = findViewById(R.id.messageInput);
        messageList = new ArrayList<>();

        ImageView sendButton = findViewById(R.id.sendButton);
        ImageView attachmentIcon = findViewById(R.id.attachmentIcon);

        loadMessages();

        sendButton.setOnClickListener(v -> sendMessage());

        attachmentIcon.setOnClickListener(v -> openGallery());
    }

    private void loadMessages() {
        Cursor cursor = dbHelper.getAllMessages();
        messageList.clear();

        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_TEXT));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_URI));

                if (imageUri != null) {
                    messageList.add("Photo: " + imageUri);
                } else {
                    messageList.add(text);
                }
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        messageListView.setAdapter(adapter);
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();

        if (!text.isEmpty()) {
            dbHelper.insertMessage(text, null);
            messageInput.setText("");
            loadMessages();
        } else if (imageUri != null) {
            dbHelper.insertMessage(null, imageUri.toString());
            imageUri = null;
            loadMessages();
        } else {
            Toast.makeText(this, "Please enter a message or select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY && data != null) {
                imageUri = data.getData();
                Toast.makeText(this, "Image selected: " + imageUri, Toast.LENGTH_SHORT).show();
            }
        }
    }
}