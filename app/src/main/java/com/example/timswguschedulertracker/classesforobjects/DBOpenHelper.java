package com.example.timswguschedulertracker.classesforobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    //Constant for db name and version
    private static final String DatabaseName ="termAssist.db";
    private static final int DatabaseVersion =1;

    //Constants for Terms table
    public static final String TableTerms = "terms";
    public static final String TermID ="ID";
    public static final String TermTitle= "TermTitle";
    public static final String TermStart = "TermStart";
    public static final String TermEnd = "TermEnd";
    public static final String TermCurrent = "TermCurrent";
    public static final String TermCreateDate = "TermCreateDate";
    public static final String[] TermColumns = {TermID, TermTitle, TermStart, TermEnd, TermCurrent, TermCreateDate};

    //Database Constructor
    /* public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    //Database Constructor
    public DBOpenHelper(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /*public DBOpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }*/

    //Creates Table when on create method of class is called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Excutes query as an argument
        String s = String.format("create table ");
        String s3 = String.format("(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TermTitle+", "+
                TermStart+", "+
                TermEnd+", "+
                        TermCurrent+", "+
                        TermCreateDate+")"
                );
        db.execSQL(s+TableTerms+s3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String s = String.format("DROP TABLE IF EXISTS "+TableTerms);
        String s1 = String.format("");
        db.execSQL(s);
        onCreate(db);
    }

    public boolean insertData(String pTermTitle,String pTermStart,String pTermEnd,String pTermCurrent,String pTermCreateDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TermTitle,pTermTitle);
        contentValues.put(TermStart, pTermStart);
        contentValues.put(TermEnd,pTermEnd);
        contentValues.put(TermCurrent,pTermCurrent);
        contentValues.put(TermCreateDate,pTermCreateDate);
        long result = db.insert(TableTerms,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TableTerms,null);
        return res;
    }

}
