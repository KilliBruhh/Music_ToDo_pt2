package com.example.musicapp_project_appdev;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import androidx.fragment.app.Fragment;


public class DetailFragment extends Fragment {

    private TextView textViewSongName;
    private TextView textViewAlbum;
    private TextView textViewDuration;

    public String song, album, duration;
    MusicDatabase db;

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

        // Check for arguments and display details
        Bundle args = getArguments();
        if (args != null) {
            int itemId = args.getInt("itemId", -1);
            if (itemId != -1) {
                // Fetch details from the database or wherever you get them
                db = new MusicDatabase(getActivity());
                Cursor cursor = db.readDataFromDataBaseById(String.valueOf(itemId));
                if (cursor != null && cursor.moveToFirst()) {
                    song = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_NAME));
                    album = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_ALBUM));
                    duration = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_DURATION));

                    // Call displayDetails with the fetched details
                    displayDetails(song, album, duration);

                    // Log the details
                    Log.d("DetailFragment", "Details: " + song + ", " + album + ", " + duration);
                    // Close the cursor
                    cursor.close();
                }
            }
        }

        return view;
    }

    public void displayDetails(String songName, String songAlbum, String songDuration) {
        song = songName;
        album = songAlbum;
        duration = songDuration;

        textViewSongName.setText("Song Name: " + song);
        textViewAlbum.setText("Album: " + album);
        textViewDuration.setText("Duration: " + duration);
    }

    public static DetailFragment newInstance(int itemId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("itemId", itemId);
        fragment.setArguments(args);
        return fragment;
    }

}