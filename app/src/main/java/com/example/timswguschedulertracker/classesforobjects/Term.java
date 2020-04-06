package com.example.timswguschedulertracker.classesforobjects;

public class Term {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private int TermId;
    private String TermTitle;
    private String StartDate;
    private String EndDate;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public Term(int termId, String termTitle, String startDate, String endDate) {
        TermId = termId;
        TermTitle = termTitle;
        StartDate = startDate;
        EndDate = endDate;
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

}
