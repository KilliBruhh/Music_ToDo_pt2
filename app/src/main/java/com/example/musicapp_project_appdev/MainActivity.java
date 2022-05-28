package com.example.musicapp_project_appdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addMusicButton;
    MusicDatabase db;
    ArrayList<String> songId, songName, songAlbum, songDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addMusicButton = findViewById(R.id.addMusicButton);
        addMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMusic.class);
                startActivity(intent);
            }
        });

        db = new MusicDatabase(MainActivity.this);
        songId = new ArrayList<>();
        songName = new ArrayList<>();
        songAlbum = new ArrayList<>();
        songDuration = new ArrayList<>();

        savaData();
    }

    void savaData() {
        Cursor cursor = db.readDataFromDataBase();
        // 0 means no data
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data in Database", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                songId.add(cursor.getString(0));
                songName.add(cursor.getString(1));
                songAlbum.add(cursor.getString(2));
                songDuration.add(cursor.getString(3));
            }
        }
    }
}