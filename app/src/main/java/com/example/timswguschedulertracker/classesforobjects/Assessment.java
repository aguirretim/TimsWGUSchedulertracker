package com.example.timswguschedulertracker.classesforobjects;

public class Assessment {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private int CourseId;
    private int AssessmentId;
    private String AssessmentTitle;
    private String AssessmentType;
    private String EndDate;
    private String Detail;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public Assessment(int courseId, int assessmentId, String assessmentTitle, String assessmentType, String endDate, String inDetail) {
        CourseId = courseId;
        AssessmentId = assessmentId;
        AssessmentTitle = assessmentTitle;
        AssessmentType = assessmentType;
        EndDate = endDate;
        Detail = inDetail;
    }

    /************************
     * Getters and setters  *
     ************************/

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public int getAssessmentId() {
        return AssessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        AssessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return AssessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        AssessmentTitle = assessmentTitle;
    }

    public String getAssessmentType() {
        return AssessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        AssessmentType = assessmentType;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
