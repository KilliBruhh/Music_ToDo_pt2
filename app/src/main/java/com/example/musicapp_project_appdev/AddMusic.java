package com.example.musicapp_project_appdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMusic extends AppCompatActivity {

    EditText songName, albumName, durationSong;
    Button addSongButton;


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
                MusicDatabase db = new MusicDatabase(AddMusic.this);
                db.addMusicToDatabase(
                        songName.getText().toString().trim(),
                        albumName.getText().toString().trim(),
                        Integer.valueOf(durationSong.getText().toString().trim()));
            }
        });
    }
}