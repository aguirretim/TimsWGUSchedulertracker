package com.example.timswguschedulertracker.classesforobjects;
import java.util.ArrayList;

public class Term {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private int TermId;
    private String TermTitle;
    private String StartDate;
    private String EndDate;
    private ArrayList <Course> courseList;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public Term(int termId, String termTitle, String startDate, String endDate) {
        TermId = termId;
        TermTitle = termTitle;
        StartDate = startDate;
        EndDate = endDate;
        courseList = new ArrayList<Course> ();
    }

    /************************
     * Getters and setters  *
     ************************/

    public int getTermId() {
        return TermId;
    }

    public void setTermId(int termId) {
        TermId = termId;
    }

    public String getTermTitle() {
        return TermTitle;
    }

    public void setTermTitle(String termTitle) {
        TermTitle = termTitle;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void  appendCourse(Course crs){

        courseList.add(crs);

    }

    public void  removeCourse(int index){

        courseList.remove(index);

    }

    //To do find method
    public void  findCourse(Course crs){

    }
}

