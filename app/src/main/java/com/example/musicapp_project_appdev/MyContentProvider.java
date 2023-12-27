package com.example.musicapp_project_appdev;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.musicapp_project_appdev.MusicDatabase;


public class MyContentProvider extends ContentProvider {

    private MusicDatabase musicDatabase;

    // Update Content Provider Constants
    // Update Content Provider Constants
    private static final int DATA = 1;
    private static final int DATA_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(MyContract.CONTENT_AUTHORITY, MyContract.PATH_DATA, DATA);
        uriMatcher.addURI(MyContract.CONTENT_AUTHORITY, MyContract.PATH_DATA + "/#", DATA_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        musicDatabase = new MusicDatabase(context);
        return  true;
    }

    // Use URIs in Query Method:
    // Use URIs in Query Method:
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = musicDatabase.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case DATA:
                // Handle query for all data
                cursor = db.query(MyContract.DataEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DATA_ID:
                // Handle query for a specific row
                selection = MyContract.DataEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MyContract.DataEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    // Content provider methods
    // Content provider methods
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
