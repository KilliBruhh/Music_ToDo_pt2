package com.example.musicapp_project_appdev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MusicDatabase extends SQLiteOpenHelper {

    private  Context context;
    public static final String DATABASE_NAME = "MusicLib.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Music_Lib";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "song_name";
    private static final String COLUMN_ALBUM = "album_name";
    private static final String COLUMN_DURATION = "song_duration";

    public MusicDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + "  TEXT, " +
                COLUMN_ALBUM + " TEXT, " +
                COLUMN_DURATION + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addMusicToDatabase(String nameS, String albumS, int durationS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, nameS);
        contentValues.put(COLUMN_ALBUM, albumS);
        contentValues.put(COLUMN_DURATION, durationS);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readDataFromDataBase() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }
}
