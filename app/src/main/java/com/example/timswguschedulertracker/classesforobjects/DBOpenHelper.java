package com.example.timswguschedulertracker.classesforobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    //Constant for db name and version
    private static final String DatabaseName = "allData.db";
    private static final int DatabaseVersion = 1;

    //Constants for Terms table
    public static final String TermTableName = "Terms";
    public static final String TermID = "TermID";
    public static final String TermTitle = "TermTitle";
    public static final String TermStart = "TermStart";
    public static final String TermEnd = "TermEnd";
    public static final String TermCurrent = "TermCurrent";
    public static final String TermCreateDate = "TermCreateDate";
    public static final String TermCourses = "TermCourses";
    public static final String[] TermColumns = {TermID, TermTitle, TermStart, TermEnd, TermCurrent, TermCreateDate, TermCourses};

    //Constants for Course table
    public static final String CourseTableName = "Course";
    public static final String CourseID = "CourseID";
    public static final String CourseTitle = "CourseTitle";
    public static final String CourseStart = "CourseStart";
    public static final String CourseEnd = "CourseEnd";
    public static final String CourseStatus = "CourseStatus";
    public static final String CourseMentor = "CourseMentor";
    public static final String CourseMentorPhone = "CourseMentorPhone";
    public static final String CourseMentorEmail = "CourseMentorEmail";
    public static final String[] CourseColumns = {CourseID, CourseTitle, CourseStart, CourseEnd, CourseMentor, CourseMentorPhone, CourseMentorEmail};


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
        String s3 = String.format(" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TermTitle + ", " +
                TermStart + ", " +
                TermEnd + ", " +
                TermCurrent + ", " +
                TermCreateDate + "," + TermCourses + ")"
        );


        db.execSQL(s + TermTableName + s3);

        String courseTableString = String.format("CREATE TABLE " + CourseTableName);
        String courseTableString2 = String.format(" (CourseID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TermID + ", " +
                CourseTitle + ", " +
                CourseStart + ", " +
                CourseEnd + ", " +
                CourseStatus + ", " +
                CourseMentor + ", " +
                CourseMentorPhone + ", " +
                CourseMentorEmail +
                ")"
        );
        db.execSQL(courseTableString + courseTableString2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String s = String.format("DROP TABLE IF EXISTS " + TermTableName);
        //String s1 = String.format("");
        db.execSQL(s);
        db.execSQL("DROP TABLE IF EXISTS " + CourseTableName);


        onCreate(db);
    }

    public boolean insertData(String pTermTitle, String pTermStart, String pTermEnd, String pTermCurrent, String pTermCreateDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TermTitle, pTermTitle);
        contentValues.put(TermStart, pTermStart);
        contentValues.put(TermEnd, pTermEnd);
        contentValues.put(TermCurrent, pTermCurrent);
        contentValues.put(TermCreateDate, pTermCreateDate);

        long result = db.insert(TermTableName, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertCourseData(String pTermID,
                                    String pCourseID,
                                    String pCourseTitle,
                                    String pCourseStart,
                                    String pCourseEnd,
                                    String pCourseStatus,
                                    String pCourseMentor,
                                    String pCourseMentorPhone,
                                    String pCourseMentorEmail) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TermID, pTermID);
        contentValues.put(CourseID, pCourseID);
        contentValues.put(CourseTitle, pCourseTitle);
        contentValues.put(CourseStart, pCourseStart);
        contentValues.put(CourseEnd, pCourseEnd);
        contentValues.put(CourseStatus, pCourseStatus);
        contentValues.put(CourseMentor, pCourseMentor);
        contentValues.put(CourseMentorPhone, pCourseMentorPhone);
        contentValues.put(CourseMentorEmail, pCourseMentorEmail);

        long result = db.insert(CourseTableName, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean updateCourseData(String id,
                                    String pTermID,
                                    String pCourseID,
                                    String pCourseTitle,
                                    String pCourseStart,
                                    String pCourseEnd,
                                    String pCourseStatus,
                                    String pCourseMentor,
                                    String pCourseMentorPhone,
                                    String pCourseMentorEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TermID, pTermID);
        contentValues.put(CourseID, pCourseID);
        contentValues.put(CourseTitle, pCourseTitle);
        contentValues.put(CourseStart, pCourseStart);
        contentValues.put(CourseEnd, pCourseEnd);
        contentValues.put(CourseStatus, pCourseStatus);
        contentValues.put(CourseMentor, pCourseMentor);
        contentValues.put(CourseMentorPhone, pCourseMentorPhone);
        contentValues.put(CourseMentorEmail, pCourseMentorEmail);

        int result = db.update(CourseTableName, contentValues, "ID = ?", new String[]{id});
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TermTableName, null);
        return res;
    }

    public ArrayList<Term> getAllDataAsTermArrayList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TermTableName, null);

        if (res.getCount() != 0) { //this represents the number of rows in the database
            ArrayList<Term> allTerms = new ArrayList<>();
            while (res.moveToNext()) {
                int id = Integer.parseInt(res.getString(0));
                String title = res.getString(1);
                String startDate = res.getString(2);
                String endDate = res.getString(3);
                String currentTerm = res.getString(4);
                Term temp = new Term(id, title, startDate, endDate, false);
                allTerms.add(temp);
            }

            return allTerms;
        } else {
            res.close();
            return null;

        }


    }

    public ArrayList<Course> getAllDataByTermIDAsCourseArrayList(int id) {
        SQLiteDatabase db = this.getWritableDatabase();   //
        Cursor query = db.rawQuery("SELECT * FROM " + CourseTableName + " WHERE " + TermID + " = ?", new String[]{String.valueOf(id)});

        if (query.getCount() != 0) { //this represents the number of rows in the database
            ArrayList<Course> allCourses = new ArrayList<>();
            while (query.moveToNext()) {

                int Courseid = Integer.parseInt(query.getString(0));
                int Termid = Integer.parseInt(query.getString(1));
                String title = query.getString(2);
                String startDate = query.getString(3);
                String endDate = query.getString(4);
                String courseStatus = query.getString(5);
                String Mentor = query.getString(6);
                String MentorPhone = query.getString(7);
                String MentorEmail = query.getString(8);
                Course temp = new Course(Termid, Courseid, title, startDate, endDate, courseStatus, Mentor, MentorPhone, MentorEmail);
                allCourses.add(temp);
            }

            return allCourses;
        } else {
            return null;
        }
    }

    //TODO get alldata as array list

    public boolean updateData(String id, String pTermTitle, String pTermStart, String pTermEnd) {//,String pTermCurrent,String pTermCreateDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(TermID, id);
        contentValues.put(TermTitle, pTermTitle);
        contentValues.put(TermStart, pTermStart);
        contentValues.put(TermEnd, pTermEnd);
        // contentValues.put(TermCurrent, pTermCurrent);
        //contentValues.put(TermCreateDate, pTermCreateDate);

        int result = db.update(TermTableName, contentValues, "ID = ?", new String[]{id});

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Integer deleteDataByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TermTableName, "ID = ?", new String[]{id});

    }
    public Integer deleteCourseDataByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CourseTableName, "CourseID = ?", new String[]{id});

    }

    public Integer deleteDataByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TermTableName, "TermTitle = ?", new String[]{title});

    }

    public Integer deleteDataBy(String parameter, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TermTableName, parameter + " = ?", new String[]{value});
    }


    public Term getTermObjectFromId(int idIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TermTableName, null, "ID = ?", new String[]{String.valueOf(idIn)}, null, null, null);

        if (cur.moveToNext()) {
            int id = Integer.parseInt(cur.getString(0));
            String title = cur.getString(1);
            String startDate = cur.getString(2);
            String endDate = cur.getString(3);
            String currentTerm = cur.getString(4);
            Term temp = new Term(id, title, startDate, endDate, false);
            cur.close();
            return temp;
        } else {
            cur.close();
            return null;
        }


    }

    public Course getCourseObjectFromID(int idIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor query = db.query(CourseTableName, null, CourseID + " = ?", new String[]{String.valueOf(idIn)}, null, null, null);

        if (query.moveToNext()) {
            int Courseid = Integer.parseInt(query.getString(0));
            int Termid = Integer.parseInt(query.getString(1));
            String title = query.getString(2);
            String startDate = query.getString(3);
            String endDate = query.getString(4);
            String courseStatus = query.getString(5);
            String Mentor = query.getString(6);
            String MentorPhone = query.getString(7);
            String MentorEmail = query.getString(8);
            Course temp = new Course(Termid, Courseid, title, startDate, endDate, courseStatus, Mentor, MentorPhone, MentorEmail);
            query.close();
            return temp;
        } else {
            query.close();
            return null;
        }


    }


}
