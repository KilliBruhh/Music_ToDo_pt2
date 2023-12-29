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

import org.w3c.dom.Text;


public class DetailFragment extends Fragment {

    private TextView V_textViewSongName;
    private TextView V_textViewAlbum;
    private TextView V_textViewDuration;
    private TextView rand;


    private static final String KEY_SONG = "select_song";


    public String song, album, duration;
    MusicDatabase db;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("DetailFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize TextViews
        V_textViewSongName = (TextView)  view.findViewById(R.id.textViewSongName);
        V_textViewAlbum = (TextView) view.findViewById(R.id.textViewAlbum);
        V_textViewDuration = (TextView) view.findViewById(R.id.textViewDuration);
        Log.d("DetailFragment", "Data From Main to Detail: " + song + "," + album+ "," +duration);


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

                    // Log the details
                    Log.d("DetailFragment", "Details: " + song + ", " + album + ", " + duration);
                    // Close the cursor

                    // Call displayDetails with the fetched details after TextViews are initialized
                    displayDetails(song, album, duration);
                    cursor.close();

                }
            }
        }

        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state to the bundle
        outState.putString(KEY_SONG, song);
        outState.putString(KEY_SONG, album);
        outState.putString(KEY_SONG, duration);
    }

    public void displayDetails(String songName, String songAlbum, String songDuration) {


        song = songName;
        album = songAlbum;
        duration = songDuration;
        setText(song);

        if (V_textViewSongName != null && V_textViewAlbum != null && V_textViewDuration != null) {
            V_textViewSongName.setText("Song Name: " + song);
            V_textViewAlbum.setText("Album: " + album);
            V_textViewDuration.setText("Duration: " + duration);
        } else {
            Log.e("DetailFragment", "TextViews are null");
        }
    }

    public void saveDetails(String songName, String songAlbum, String songDuration) {
        song = songName;
        album = songAlbum;
        duration = songDuration;
        Log.d("DetailFragment", "Data In SaveDetails :" + song + ", " + album + ", " + duration);
    }

    public static DetailFragment newInstance(int itemId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("itemId", itemId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setText(String text){

    }



}