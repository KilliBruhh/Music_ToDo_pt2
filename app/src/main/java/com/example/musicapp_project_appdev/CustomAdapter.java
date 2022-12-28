package com.example.musicapp_project_appdev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private ArrayList songNameRV, songAlbumRV, songIdRV, songDurationRV;


    CustomAdapter(Context context, ArrayList songIdRV, ArrayList songNameRV, ArrayList songAlbumRV, ArrayList songDurationRV) {
        this.context = context;
        this.songIdRV = songIdRV;
        this.songNameRV = songNameRV;
        this.songAlbumRV = songAlbumRV;
        this.songDurationRV = songDurationRV;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.songIdRV.setText(String.valueOf(songIdRV.get(position)));
        holder.songNameRV.setText(String.valueOf(songNameRV.get(position)));
        holder.songAlbumRV.setText(String.valueOf(songAlbumRV.get(position)));
        holder.songDurationRV.setText(String.valueOf(songDurationRV.get(position)));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context, UpdateSong.class);
                intent.putExtra("id", String.valueOf(songIdRV.get(position)));
                intent.putExtra("name", String.valueOf(songNameRV.get(position)));
                intent.putExtra("album", String.valueOf(songAlbumRV.get(position)));
                intent.putExtra("duration", String.valueOf(songDurationRV.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return songIdRV.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView songIdRV, songNameRV, songAlbumRV, songDurationRV;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songIdRV = itemView.findViewById(R.id.songIdRV);
            songNameRV = itemView.findViewById(R.id.songNameRV);
            songAlbumRV = itemView.findViewById(R.id.songAlbumRV);
            songDurationRV = itemView.findViewById(R.id.songDurationRV);
            rowLayout = itemView.findViewById(R.id.rowLayoutToUpdate);
        }
    }

}
