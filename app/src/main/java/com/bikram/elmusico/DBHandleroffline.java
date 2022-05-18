package com.bikram.elmusico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandleroffline extends SQLiteOpenHelper {
    public DBHandleroffline(@Nullable Context context) {
        super(context, "OfflineDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table MusicTableoffline(name TEXT primary key, artist_name TEXT not null, uri TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists MusicTableoffline");
    }
    public boolean insertData(String name, String artist_name, String uri){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("artist_name", artist_name);
        contentValues.put("uri", uri);
        long result = DB.insert("MusicTableoffline", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from MusicTableoffline Where name=?", new String[]{name} );
        if (cursor.getCount() > 0) {
            long result = DB.delete("MusicTableoffline", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from MusicTableoffline", null);
        return cursor;
    }
}
