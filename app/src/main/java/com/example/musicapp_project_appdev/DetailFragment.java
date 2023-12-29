package com.example.musicapp_project_appdev;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private TextView textViewSongName;
    private TextView textViewAlbum;
    private TextView textViewDuration;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize TextViews
        textViewSongName = view.findViewById(R.id.textViewSongName);
        textViewAlbum = view.findViewById(R.id.textViewAlbum);
        textViewDuration = view.findViewById(R.id.textViewDuration);

        return view;
    }

    public void displayDetails(String songName, String songAlbum, String songDuration) {
        // Update TextViews with details
        textViewSongName.setText("Song Name: " + songName);
        textViewAlbum.setText("Album: " + songAlbum);
        textViewDuration.setText("Duration: " + songDuration);
    }

}