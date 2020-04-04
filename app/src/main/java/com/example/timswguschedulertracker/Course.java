package com.example.timswguschedulertracker;

public class Course {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private int CourseId;
    private String CourseTitle;
    private String StartDate;
    private String EndDate;
    private String Status;
    private String CourseMentorNames;
    private String CourseMentorPhoneNumber;
    private String CourseMentorEmailAddresses;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public Course(int courseId, String courseTitle, String startDate, String endDate, String status, String courseMentorNames, String courseMentorPhoneNumber, String courseMentorEmailAddresses) {
        CourseId = courseId;
        CourseTitle = courseTitle;
        StartDate = startDate;
        EndDate = endDate;
        Status = status;
        CourseMentorNames = courseMentorNames;
        CourseMentorPhoneNumber = courseMentorPhoneNumber;
        CourseMentorEmailAddresses = courseMentorEmailAddresses;
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

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCourseMentorNames() {
        return CourseMentorNames;
    }

    public void setCourseMentorNames(String courseMentorNames) {
        CourseMentorNames = courseMentorNames;
    }

    public String getCourseMentorPhoneNumber() {
        return CourseMentorPhoneNumber;
    }

    public void setCourseMentorPhoneNumber(String courseMentorPhoneNumber) {
        CourseMentorPhoneNumber = courseMentorPhoneNumber;
    }

    public String getCourseMentorEmailAddresses() {
        return CourseMentorEmailAddresses;
    }

    public void setCourseMentorEmailAddresses(String courseMentorEmailAddresses) {
        CourseMentorEmailAddresses = courseMentorEmailAddresses;
    }

}
