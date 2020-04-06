package com.example.timswguschedulertracker.classesforobjects;

public class Notes {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private int CourseId;
    private int NotesId;
    private String NotesTitle;
    private String NotesText;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public Notes(int courseId, int notesId, String notesTitle, String notesText) {
        CourseId = courseId;
        NotesId = notesId;
        NotesTitle = notesTitle;
        NotesText = notesText;
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

    public int getNotesId() {
        return NotesId;
    }

    public void setNotesId(int notesId) {
        NotesId = notesId;
    }

    public String getNotesTitle() {
        return NotesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        NotesTitle = notesTitle;
    }

    public String getNotesText() {
        return NotesText;
    }

    public void setNotesText(String notesText) {
        NotesText = notesText;
    }

}
