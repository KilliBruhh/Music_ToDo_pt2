package com.example.musicapp_project_appdev;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MasterFragment extends Fragment {
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected(int itemId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Ensure the hosting activity implements the OnItemSelectedListener interface
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have a ListView with the id "listView" in your fragment_master.xml layout
        ListView listView = view.findViewById(R.id.listView);

        // Fetch data from the database
        MusicDatabase db = new MusicDatabase(requireContext());
        Cursor cursor = db.readDataFromDataBase();

        // Assuming you have columns named COLUMN_NAME in your database
        ArrayList<String> songNames = new ArrayList<>();

        while (cursor != null && cursor.moveToNext()) {
            String songName = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_NAME));
            songNames.add(songName);
        }

        // Close the cursor after use
        if (cursor != null) {
            cursor.close();
        }

        // Use ArrayAdapter to populate the ListView with data from the database
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, songNames);
        listView.setAdapter(adapter);

        // Set up item click listener to notify the hosting activity when an item is selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Notify the hosting activity with the selected item ID
                if (listener != null) {
                    listener.onItemSelected(position + 1); // Assuming item IDs start from 1
                }
            }
        });
    }
}