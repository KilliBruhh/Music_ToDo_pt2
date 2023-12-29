package com.example.musicapp_project_appdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewSongName;
    private TextView textViewAlbum;
    private TextView textViewDuration;
    MusicDatabase db;
    BottomNavigationView navBar;
    String songName, songAlbum, songDuration;
    FloatingActionButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new MusicDatabase(DetailActivity.this);

        // Retrieve the selected item ID from the intent
        int itemId = getIntent().getIntExtra("itemId", -1);
        Log.d("DetailActivity", "Received item ID: " + itemId);

        // Initialize your UI components (TextViews, etc.)
        // ...

        // Fetch data from the database using the correct column name for the ID
        Cursor cursor = db.readDataFromDataBaseById(String.valueOf(itemId));

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            // Get details from the cursor
            songName = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_NAME));
            songAlbum = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_ALBUM));
            songDuration = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_DURATION));

            // Log the details
            Log.d("DetailActivity", "Details: " + songName + ", " + songAlbum + ", " + songDuration);

            // Set the text of TextViews with the retrieved details
            // Example (replace with your actual TextViews):
            TextView textViewSongName = findViewById(R.id.textViewSongName);
            TextView textViewAlbum = findViewById(R.id.textViewAlbum);
            TextView textViewDuration = findViewById(R.id.textViewDuration);

            textViewSongName.setText("Song Name: " + songName);
            textViewAlbum.setText("Album: " + songAlbum);
            textViewDuration.setText("Duration: " + songDuration);

            // Close the cursor
            cursor.close();
        }

        Button goEditButton = findViewById(R.id.GoEditButton);

        goEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateSong.class);

                intent.putExtra("id", itemId);
                intent.putExtra("name", songName);
                intent.putExtra("album", songAlbum);
                intent.putExtra("duration", songDuration);

                startActivity(intent);
            }
        });
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.goAddSong:
                                // Handle "Add Song" action
                                Intent intentAddSong = new Intent(DetailActivity.this, AddMusic.class);
                                startActivity(intentAddSong);
                                return true;
                            case R.id.goHome:
                                Intent intentGohome = new Intent(DetailActivity.this, MainActivity.class);
                                startActivity(intentGohome);
                                return true;
                            case R.id.goSettings:
                                // Handle "Settings" action
                                Intent intentSettings = new Intent(DetailActivity.this, Settings.class);
                                startActivity(intentSettings);
                                return true;
                        }
                        return false;
                    }
                });

    }
}