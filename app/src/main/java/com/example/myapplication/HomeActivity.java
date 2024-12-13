package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private ArrayList<String> threadList;
    private ArrayList<Integer> threadIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.threadListView);
        threadList = new ArrayList<>();
        threadIds = new ArrayList<>();

        loadThreads();

        // Menu Icon Click Listener
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(this::showPopupMenu);

        // Handle thread item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedThreadId = threadIds.get(position);
                String selectedThreadName = threadList.get(position);
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("thread_id", selectedThreadId);
                intent.putExtra("thread_name", selectedThreadName);
                startActivity(intent);
            }
        });
    }

    private void loadThreads() {
        Cursor cursor = dbHelper.getAllThreads();
        threadList.clear();
        threadIds.clear();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THREAD_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THREAD_NAME));
                threadIds.add(id);
                threadList.add(name);
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, threadList);
        listView.setAdapter(adapter);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenu().add(0, 1, 0, "Create new thread").setIcon(R.drawable.ic_create_thread);
        popupMenu.getMenu().add(0, 2, 1, "Delete Selected").setIcon(R.drawable.ic_delete);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                navigateToNewThread();
                return true;
            } else if (item.getItemId() == 2) {
                showDeleteConfirmation();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void navigateToNewThread() {
        startActivity(new Intent(HomeActivity.this, NewThreadActivity.class));
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete the selected item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Toast.makeText(HomeActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}