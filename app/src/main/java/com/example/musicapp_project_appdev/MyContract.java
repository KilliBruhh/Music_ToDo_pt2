package com.example.musicapp_project_appdev;

import android.net.Uri;

public class MyContract {

    public  static  final  String CONTENT_AUTHORITY = "com.example.musicapp_project_appdev.provider";
    public  static  final  Uri   BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public  static  final  String PATH_DATA = "data";

    public  static class DataEntry {
        public static final  Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATA).build();
        public static final String TABLE_NAME = "Music_lib";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "song_name";
        public static final String COLUMN_ALBUM = "album_name";
        public static final String COLUMN_DURATION = "song_duration";

    }


}
