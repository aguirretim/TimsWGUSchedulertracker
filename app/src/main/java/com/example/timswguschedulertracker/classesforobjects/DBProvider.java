package com.example.timswguschedulertracker.classesforobjects;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBProvider extends ContentProvider {

    //Authority and path strings
    private static final String AUTHORITY = "com.example.timswguschedulertracker";
    private static final String AssessmentPath=".classesforobjects.Assessment";
    private static final String TermsPath=".classesforobjects.Term";
    private static final String CoursePath=".classesforobjects.Course";
    private static final String NotesPath=".classesforobjects.Notes";

    //URIs
    public static final Uri TermURI = Uri.parse("content://"+AUTHORITY+"/"+TermsPath);
    public static final Uri AssessmentURI = Uri.parse("content://"+AUTHORITY+"/"+AssessmentPath);
    public static final Uri CourseURI = Uri.parse("content://"+AUTHORITY+"/"+CoursePath);
    public static final Uri NotesURI = Uri.parse("content://"+AUTHORITY+"/"+NotesPath);


    //Constant to identity requested operation
    private static final int Term = 1;
    private static final int TermId = 2;
    private static final int COURSES = 3;
    private static final int COURSESID = 4;
    private static final int COURSE_ALERTS = 5;
    private static final int COORSE_ALERTS_IO = 6;
    private static final int COUSE_NOTES = 7;
    private static final int COURSE_NOTES_ID = 8;
    private static final int ASSESSMENT = 9;
    private static final int ASSESSMENTSID = 10;
    private static final int ASSESSMENT_Alerts = 11;
    private static final int AssementAlertsId = 12;
    private static final int ASSESSMENT_NOTES = 13;
    private static final int ASSESSNENT_NOTES_ID = 14;

    //URI Matcher
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,TermsPath,Term);

    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        //Create database for app
       /* DBOpenHelper helper = new DBOpenHelper(getContext());
        db = helper.getWritableDatabase;
        return true;*/
       return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id;
        //id = db.insert(DBOpenHelper.TableTerms, moCaumni-eck:null, values); return 044004(TERHS_PATH • "/' r. id);

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }



}
