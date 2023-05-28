package com.example.musicapp_project_appdev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.webkit.WebView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;



public class Settings extends AppCompatActivity {

    BottomNavigationView navBar;
    Button goCp, goContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadLocaleLang();

        Button backButton;
        Switch lanSwitch, modeSwitch;

        // Switch for Language
        Button engbutton = findViewById(R.id.englishButton);
        Button nlbutton = findViewById(R.id.dutchButton);
        engbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", true);
                editor.apply();
                Intent intentSettings = new Intent(Settings.this, Settings.class);
                startActivity(intentSettings);
                setLocale("en");
            }
        });
        nlbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", false);
                editor.apply();
                Intent intentSettings = new Intent(Settings.this, Settings.class);
                startActivity(intentSettings);
                setLocale("nl");
            }
        });


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
                    /*
                    SharedPreferences.Editor editorMode = getSharedPreferences("saveMode", MODE_PRIVATE).edit();
                    editorMode.putBoolean("valueMode", true);
                    editorMode.apply();
                    */

                    modeSwitch.setChecked(true);
                    //Dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }
                else {
                    /*
                    SharedPreferences.Editor editorMode = getSharedPreferences("saveMode", MODE_PRIVATE).edit();
                    editorMode.putBoolean("valueMode", false);
                    editorMode.apply();
                    */

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

    public void loadLocaleLang() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if(lang.equalsIgnoreCase("")) {
            return;
        }
        Locale myLocale = new Locale(lang);
        saveLocaleLang(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocaleLang(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
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