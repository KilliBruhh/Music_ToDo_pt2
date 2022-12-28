package com.example.musicapp_project_appdev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;



public class Settings extends AppCompatActivity {

    BottomNavigationView navBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // CHeck Condition
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            // Is Dark
            setTheme(R.style.Theme_Dark);
        }else {
            // is Light
            setTheme((R.style.Theme_Light));
        }

        Button backButton;
        Switch lanSwitch, modeSwitch;

        // Switch for Language
        lanSwitch = findViewById(R.id.taalSwitch);

        setTheme(R.style.Theme_Light);



        SharedPreferences sp = getSharedPreferences("save", MODE_PRIVATE);

        lanSwitch.setChecked(sp.getBoolean("value", false));

        lanSwitch.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lanSwitch.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    lanSwitch.setChecked(true);
                    setLocale("en");
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    lanSwitch.setChecked(false);
                    setLocale("nl");
                }
            }
        }));

        // Navigation
        navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goHome:
                        Intent intentHome = new Intent(Settings.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.goSettings:
                        Intent intentSettings = new Intent(Settings.this, Settings.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.goAddSong:
                        Intent intentAddSong = new Intent(Settings.this, AddMusic.class);
                        startActivity(intentAddSong);
                        break;
                }
                return false;
            }
        });


        // Switch for theme mode
        modeSwitch = findViewById(R.id.kleurSwitch);

        SharedPreferences spMode = getSharedPreferences("saveMode", MODE_PRIVATE);

        modeSwitch.setChecked(spMode.getBoolean("valueMode", false));

        modeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modeSwitch.isChecked()) {
                    SharedPreferences.Editor editorMode = getSharedPreferences("saveMode", MODE_PRIVATE).edit();
                    editorMode.putBoolean("valueMode", true);
                    editorMode.apply();
                    modeSwitch.setChecked(true);
                    //Dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    SharedPreferences.Editor editorMode = getSharedPreferences("saveMode", MODE_PRIVATE).edit();
                    editorMode.putBoolean("valueMode", false);
                    editorMode.apply();
                    modeSwitch.setChecked(false);

                    // Light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });






        // Back button
        backButton = findViewById(R.id.settingsBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        // startActivity(refresh);
    }

}