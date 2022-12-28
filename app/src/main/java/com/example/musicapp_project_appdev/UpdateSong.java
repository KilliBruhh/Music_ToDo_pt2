package com.example.musicapp_project_appdev;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateSong extends AppCompatActivity {

    EditText songName, songAlbum, songDuration;
    Button editButton, deleteButton;

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

        ActionBar ab = getSupportActionBar();
        ab.setTitle(name);

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
    }

    void getChosenSongsData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("album") && getIntent().hasExtra("duration")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            album = getIntent().getStringExtra("album");
            duration = getIntent().getStringExtra("duration");

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