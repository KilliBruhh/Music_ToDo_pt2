package com.example.musicapp_project_appdev;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewSongName;
    private TextView textViewAlbum;
    private TextView textViewDuration;
    MusicDatabase db;

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
            String songName = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_NAME));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_ALBUM));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_DURATION));

            // Log the details
            Log.d("DetailActivity", "Details: " + songName + ", " + album + ", " + duration);

            // Set the text of TextViews with the retrieved details
            // Example (replace with your actual TextViews):
            TextView textViewSongName = findViewById(R.id.textViewSongName);
            TextView textViewAlbum = findViewById(R.id.textViewAlbum);
            TextView textViewDuration = findViewById(R.id.textViewDuration);

            textViewSongName.setText("Song Name: " + songName);
            textViewAlbum.setText("Album: " + album);
            textViewDuration.setText("Duration: " + itemId);

            // Close the cursor
            cursor.close();
        }
    }
}