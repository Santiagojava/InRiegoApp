package com.example.santi.inriegoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by santi on 22/9/2017.
 */

public class BaseSQLiteHelper extends SQLiteOpenHelper {
    String sql = "CREATE TABLE DatosJson (Id INTEGER PRIMARY KEY, json TEXT)";
    public BaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DatosJson");
        db.execSQL(sql);
    }
}
