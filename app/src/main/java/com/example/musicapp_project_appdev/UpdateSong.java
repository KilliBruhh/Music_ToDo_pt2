package com.example.musicapp_project_appdev;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UpdateSong extends AppCompatActivity {

    EditText songName, songAlbum, songDuration;
    Button editButton, deleteButton;
    BottomNavigationView navBar;

    String id, name, album, duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song);
        songName = findViewById(R.id.EditSongName);
        songAlbum = findViewById(R.id.EditAlbumSong);
        songDuration = findViewById(R.id.EditDurationSong);
        editButton = findViewById(R.id.EditSongButton);
        deleteButton = findViewById(R.id.DeleteSongButton);


        getChosenSongsData();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicDatabase db = new MusicDatabase(UpdateSong.this);
                db.updateData(id,
                        songName.getText().toString().trim(),
                        songAlbum.getText().toString().trim(),
                        songDuration.getText().toString().trim());

                Intent intent = new Intent(UpdateSong.this, MainActivity.class);
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });

        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goHome:
                        Intent intentHome = new Intent(UpdateSong.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.goSettings:
                        Intent intentSettings = new Intent(UpdateSong.this, Settings.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.goAddSong:
                        Intent intentAddSong = new Intent(UpdateSong.this, AddMusic.class);
                        startActivity(intentAddSong);
                        break;
                }
                return false;
            }
        });

    }

    void getChosenSongsData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("album") && getIntent().hasExtra("duration")) {
            id = String.valueOf(getIntent().getIntExtra("id", -1));
            name = getIntent().getStringExtra("name");
            album = getIntent().getStringExtra("album");
            duration = getIntent().getStringExtra("duration");
            Log.d("UpdateSongActivity", name + " " + id + " " + album+ " " + duration );

            // add data to textfields
            songName.setText(name);
            songAlbum.setText(album);
            songDuration.setText(duration);
        }
        else {
            Toast.makeText(this, "No data in Song", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do You want to delete this song");
        builder.setMessage("This cant be undone");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MusicDatabase db = new MusicDatabase(UpdateSong.this);
                db.deleteSong(id);

                Intent intent = new Intent(UpdateSong.this, MainActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}