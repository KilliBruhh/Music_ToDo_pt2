package com.example.musicapp_project_appdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addMusicButton, goToSettings;
    MusicDatabase db;
    ArrayList<String> songId, songName, songAlbum, songDuration;
    CustomAdapter customAdapter;
    BottomNavigationView navBar;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // CHeck

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            // Is Dark
            setTheme(R.style.Theme_Dark);
        }else {
            // is Light
            setTheme((R.style.Theme_Light));
        }



        recyclerView = findViewById(R.id.recyclerView);

        // Navigation
        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goHome:
                        Intent intentHome = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.goSettings:
                        Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.goAddSong:
                        Intent intentAddSong = new Intent(MainActivity.this, AddMusic.class);
                        startActivity(intentAddSong);
                        break;
                }
                return false;
            }
        });

        /*
        addMusicButton = findViewById(R.id.addMusicButton);
        addMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMusic.class);
                startActivity(intent);
            }
        });



        goToSettings = findViewById(R.id.settingsButton);
        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);


            }
        });

        */


        db = new MusicDatabase(MainActivity.this);
        songId = new ArrayList<>();
        songName = new ArrayList<>();
        songAlbum = new ArrayList<>();
        songDuration = new ArrayList<>();

        savaData();

        customAdapter = new CustomAdapter(MainActivity.this, songId, songName, songAlbum, songDuration);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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

    // Content provider
    public void onGetContact(View view) {
        getContact();
    }

    // Getting the contact function
    //First check if it is allwed --> ask for permission
    private void getContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)  {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS}, 0);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        Log.i("CONTACT_PROVIDER_DEMO", "TOTAL # of Contacts: " + Integer.toString(cursor.getCount()));

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               int contactName = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
               int contactNumber = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);



                Log.i("CONTACT_PROVIDER_DEMO", "Contact Name: " + contactName + " ph# : "+ contactNumber);
            }
        }


    }



}