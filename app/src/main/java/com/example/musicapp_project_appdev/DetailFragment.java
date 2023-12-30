package com.example.musicapp_project_appdev;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;


public class DetailFragment extends Fragment {

    private TextView V_textViewSongName;
    private TextView V_textViewAlbum;
    private TextView V_textViewDuration;
    private TextView rand;
    private Button editButton;


    private static final String KEY_SONG = "select_song";


    public String song, album, duration, id;
    public String L_song, L_album, L_duration;
    MusicDatabase db;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("DetailFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize Button
        editButton = (Button) view.findViewById(R.id.OnEditButton);

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
                    id = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_ID));
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

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempId = Integer.parseInt(id);
                Intent intent = new Intent(getActivity(), UpdateSong.class);
                intent.putExtra("id", tempId);
                intent.putExtra("name", song);
                intent.putExtra("album", album);
                intent.putExtra("duration", duration);
                if (id != null && song != null && album != null && duration != null) {
                    startActivity(intent);
                } else {
                    String snackbarNoDataString = getString(R.string.snackbarNoData);
                    Snackbar.make(view, snackbarNoDataString, Snackbar.LENGTH_LONG).show();
                }
            }
        });

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

        L_song = getString(R.string.name_selected);
        L_album = getString(R.string.album_selected);
        L_duration = getString(R.string.duration_selected);

        setText(song);

        if (V_textViewSongName != null && V_textViewAlbum != null && V_textViewDuration != null) {
            V_textViewSongName.setText(L_song+ " " + song);
            V_textViewAlbum.setText(L_album+ " " + album);
            V_textViewDuration.setText(L_duration+ " " + duration);
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