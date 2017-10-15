package com.example.santi.inriegoapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KevinQ on 15/10/2017.
 */

public class BD extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "inriego.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto ;


  String  Sqlcreate="CREATE TABLE INGRESOS(_ID INTEGER PRIMARY KEY AUTOINCREMENT,JSON TEXT NOT NULL,REG TIMESTAMP)";
    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NOMBRE_BASE_DATOS, factory, version);
        this.contexto=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
if(db.isReadOnly()){
    db=getWritableDatabase();
}
db.execSQL(Sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
