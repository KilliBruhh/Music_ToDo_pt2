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
import android.widget.SimpleCursorAdapter;

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

        ListView listView = view.findViewById(R.id.listView);

        // Fetch data from the database
        MusicDatabase db = new MusicDatabase(requireContext());
        Cursor cursor = db.readDataFromDataBase();

        // Use a SimpleCursorAdapter to bind data to the ListView
        String[] fromColumns = {MusicDatabase.COLUMN_NAME};
        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Assuming you have a cursor with a column named "_id"
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                // Get the _id value from the cursor
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(MusicDatabase.COLUMN_ID));

                // Notify the hosting activity with the selected item ID
                if (listener != null) {
                    listener.onItemSelected(itemId);
                }
            }
        });
    }
}