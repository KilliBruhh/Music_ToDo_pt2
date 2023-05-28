package com.example.musicapp_project_appdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AddMusic extends AppCompatActivity {

    EditText songName, albumName, durationSong;
    Button addSongButton;
    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);


        songName = findViewById(R.id.songName);
        albumName = findViewById(R.id.AlbumName);
        durationSong = findViewById(R.id.durationSong);
        addSongButton = findViewById(R.id.addSongButton);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addMusicToDatabase(
                        songName.getText().toString().trim(),
                        albumName.getText().toString().trim(),
                        Integer.valueOf(durationSong.getText().toString().trim()));

                    Intent intent = new Intent(AddMusic.this, MainActivity.class);
                    startActivity(intent);

            }
            MusicDatabase db = new MusicDatabase(AddMusic.this);
        });

        // Navigation
        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.goAddSong:
                                Intent intentAddSong = new Intent(AddMusic.this, AddMusic.class);
                                startActivity(intentAddSong);
                                return true;
                            case R.id.goHome:
                                Intent intentHome = new Intent(AddMusic.this, MainActivity.class);
                                startActivity(intentHome);
                                break;
                            case R.id.goSettings:
                                Intent intentSettings = new Intent(AddMusic.this, Settings.class);
                                startActivity(intentSettings);
                                break;
                        }
                        navBar.getMenu().findItem(R.id.goHome).setChecked(false);
                        return false;
                    }
                });
        /*
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goHome:
                        Intent intentHome = new Intent(AddMusic.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.goSettings:
                        Intent intentSettings = new Intent(AddMusic.this, Settings.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.goAddSong:
                        Intent intentAddSong = new Intent(AddMusic.this, AddMusic.class);
                        startActivity(intentAddSong);
                        break;
                }
                return false;
            }
        });
        */
    }
}