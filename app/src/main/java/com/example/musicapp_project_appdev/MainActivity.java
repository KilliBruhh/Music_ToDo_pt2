package com.example.musicapp_project_appdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MasterFragment.OnItemSelectedListener, AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Your implementation when an item is selected in the AdapterView
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle the case where nothing is selected (optional)
    }

    @Override
    public void onItemSelected(int itemId) {
        // Your implementation when an item is selected in the MasterFragment
    }

    RecyclerView recyclerView;

    MusicDatabase db;
    ArrayList<String> songId, songName, songAlbum, songDuration;
    CustomAdapter customAdapter;
    BottomNavigationView navBar;
    Button b;

    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Check theme Condition

        // Set up the MasterFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_master, new MasterFragment())
                    .commit();
        }

        // Bottom Navigation setup
        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.goAddSong:
                                // Handle "Add Song" action
                                Intent intentAddSong = new Intent(MainActivity.this, AddMusic.class);
                                startActivity(intentAddSong);
                                return true;
                            case R.id.goHome:
                                // Handle "Home" action
                                // No need to start a new MainActivity, as we're already in it
                                return true;
                            case R.id.goSettings:
                                // Handle "Settings" action
                                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                                startActivity(intentSettings);
                                return true;
                        }
                        return false;
                    }
                });

        db = new MusicDatabase(MainActivity.this);
        songId = new ArrayList<>();
        songName = new ArrayList<>();
        songAlbum = new ArrayList<>();
        songDuration = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        savaData();

        customAdapter = new CustomAdapter(MainActivity.this, songId, songName, songAlbum, songDuration);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    void savaData() {
       Uri uri = MyContract.DataEntry.CONTENT_URI;
       String[] projection = {MyContract.DataEntry.COLUMN_ID, MyContract.DataEntry.COLUMN_NAME, MyContract.DataEntry.COLUMN_ALBUM, MyContract.DataEntry.COLUMN_DURATION};

       Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

       if(cursor != null) {
           while (cursor.moveToNext()) {
               songId.add(cursor.getString(cursor.getColumnIndexOrThrow(MyContract.DataEntry.COLUMN_ID)));
               songName.add(cursor.getString(cursor.getColumnIndexOrThrow(MyContract.DataEntry.COLUMN_NAME)));
               songAlbum.add(cursor.getString(cursor.getColumnIndexOrThrow(MyContract.DataEntry.COLUMN_ALBUM)));
               songDuration.add(cursor.getString(cursor.getColumnIndexOrThrow(MyContract.DataEntry.COLUMN_DURATION)));
           }
           cursor.close();
       }
    }
}