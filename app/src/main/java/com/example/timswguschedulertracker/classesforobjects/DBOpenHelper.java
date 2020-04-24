package com.example.timswguschedulertracker.classesforobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    //Constant for db name and version
    private static final String DatabaseName ="termAssist.db";
    private static final int DatabaseVersion =1;

    //Constants for Terms table
    public static final String TableName = "Terms";
    public static final String TermID ="TermID";
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
        SQLiteDatabase db = this.getWritableDatabase();
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

       /* db.execSQL("create table " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT)");*/
        String s = String.format("create table ");
        String s3 = String.format(" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TermTitle+", "+
                TermStart+", "+
                TermEnd+", "+
                TermCurrent+", "+
                TermCreateDate+")"
                );
        db.execSQL(s+TableName+s3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String s = String.format("DROP TABLE IF EXISTS "+ TableName);
        //String s1 = String.format("");
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
        long result = db.insert(TableName,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TableName,null);
        return res;
    }

    public ArrayList<Term> getAllDataAsTermArrayList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor query = db.rawQuery("select * from "+TableName,null);

        if(query.getCount() != 0) { //this represents the number of rows in the database
            ArrayList<Term> allTerms = new ArrayList<>();
            while (query.moveToNext()) {
                int id = Integer.parseInt(query.getString(0));
                String title = query.getString(1);
                String startDate = query.getString(2);
                String endDate = query.getString(3);
                String currentTerm = query.getString(4);
                Term temp = new Term(id,title,startDate,endDate,false);
                allTerms.add(temp);
            }

            return allTerms;
        } else {
            return null;
        }




    }

    //TODO get alldata as array list

    public boolean updateData(String id, String pTermTitle,String pTermStart,String pTermEnd){//,String pTermCurrent,String pTermCreateDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(TermID, id);
        contentValues.put(TermTitle, pTermTitle);
        contentValues.put(TermStart, pTermStart);
        contentValues.put(TermEnd, pTermEnd);
       // contentValues.put(TermCurrent, pTermCurrent);
        //contentValues.put(TermCreateDate, pTermCreateDate);
        int result = db.update(TableName, contentValues,"ID = ?", new String[] {id} );
        if (result != -1) {
            return true;
        }else {
            return false;
        }
    }

    public Integer deleteDataByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,"ID = ?", new String[] {id});

    }

    public Integer deleteDataByTitle(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,"TermTitle = ?", new String[] {title});

    }

    public Integer deleteDataBy(String parameter, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,parameter + " = ?", new String[] {value});
    }

    public Term getTermObjectFromId(int idIn){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TableName, null, "ID = ?", new String[]{String.valueOf(idIn)}, null, null, null);

        if (cur.moveToNext()) {
            int id = Integer.parseInt(cur.getString(0));
            String title =cur.getString(1);
            String startDate= cur.getString(2);
            String endDate=cur.getString(3);
            String currentTerm = cur.getString(4);
            Term temp = new Term(id,title,startDate,endDate,false);
            cur.close();
            return temp;
        } else {
            cur.close();
            return null;
        }


    }



}
