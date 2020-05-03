package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

public class CourseCreateView extends AppCompatActivity {

    /***************************
     * initialized Variables.  *
     ***************************/

    private DatePicker endDatePickerCourse;
    private TextView endDateLable;
    private DatePicker startDatePickerCourse;
    private TextView courseStartLable;
    private TextView courseTitleLable;
    private EditText courseTitleTextEdit;
    private Button saveButton;
    private TextView courseStatusLabel;
    private Spinner StatusSelector;
    private TextView mentorNameLabel;
    private EditText mentorNameTextEdit;
    private TextView mentorPhoneLabel;
    private EditText mentorPhoneTextEdit;
    private TextView mentorEmailLabel;
    private EditText mentorEmailTextEdit;
    DBOpenHelper myDb;
    boolean isEditCourse = false;
    Course selectedCourse;
    public static String EXTRA_COURSE_TITLE = "title";
    public static String EXTRA_COURSE_STARTDATE = "courseStartDate";
    public static String EXTRA_COURSE_ENDDATE = "courseEndDate";
    public static String EXTRA_COURSE_STATUS = "courseStatus";
    public static String EXTRA_COURSE_MENTORNAME = "courseMentor";
    public static String EXTRA_COURSE_PHONE = "courseMentorPhone";
    public static String EXTRA_COURSE_EMAIL = "courseMentorEmail";

    /**************************************
     * Main initialized Method.  *
     **************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_createview);

        /* Find the Views in the layout */
        courseTitleTextEdit = (EditText) findViewById(R.id.courseTitleTextEdit);
        startDatePickerCourse = (DatePicker) findViewById(R.id.startDatePicker_course);
        endDatePickerCourse = (DatePicker) findViewById(R.id.endDatePicker_course);
        StatusSelector = (Spinner) findViewById(R.id.StatusSelector);
        mentorNameTextEdit = (EditText) findViewById(R.id.mentorNameTextEdit);
        mentorPhoneTextEdit = (EditText) findViewById(R.id.mentorPhoneTextEdit);
        mentorEmailTextEdit = (EditText) findViewById(R.id.mentorEmailTextEdit);
        saveButton = (Button) findViewById(R.id.saveButton);

        myDb = new DBOpenHelper(this);

    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCourse();
            }
        });

    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    public void createCourse() {

        /* Finds and set the Variables in the from the layout controls */
        String courseCreatedTitle = courseTitleTextEdit.getText().toString();
        String startDateValue = String.format("%02d", startDatePickerCourse.getMonth() + 1) + "/" +
                String.format("%02d", startDatePickerCourse.getDayOfMonth()) + "/" + startDatePickerCourse.getYear();
        String endDateValue = String.format("%02d", endDatePickerCourse.getMonth() + 1) + "/" +
                String.format("%02d", endDatePickerCourse.getDayOfMonth()) + "/" + endDatePickerCourse.getYear();

        String status = StatusSelector.getSelectedItem().toString();
        String mentorName = mentorNameTextEdit.getText().toString();
        String mentorPhone = mentorPhoneTextEdit.getText().toString();
        String mentorEmail = mentorEmailTextEdit.getText().toString();


        //myDb.insertCourseData(courseCreatedTitle,startDateValue,endDateValue,status,mentorName,mentorPhone,mentorEmail);


        if (isEditCourse) {
            //update the database
            if (myDb.updateData(String.valueOf(selectedCourse.getTermId()), courseTitleTextEdit.getText().toString(), startDateValue, endDateValue)) {
                Toast.makeText(this, "Updated Course with ID: " + selectedCourse.getTermId(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Could not update edited term", Toast.LENGTH_SHORT).show();
            }
            finish();

            //this uses the OnResume of the previous activity to update database items

        } else {

            //this sends the data back to the activity that start this activity, From there we save to the database and reload the screen
            Intent dataToSendBack = new Intent();
            dataToSendBack.putExtra("title", courseCreatedTitle);
            dataToSendBack.putExtra(EXTRA_COURSE_STARTDATE, startDateValue);
            dataToSendBack.putExtra(EXTRA_COURSE_ENDDATE, endDateValue);
            dataToSendBack.putExtra(EXTRA_COURSE_STATUS, status);
            dataToSendBack.putExtra(EXTRA_COURSE_MENTORNAME, mentorName);
            dataToSendBack.putExtra(EXTRA_COURSE_PHONE, mentorPhone);
            dataToSendBack.putExtra(EXTRA_COURSE_EMAIL, mentorEmail);

            //todo check that dates are valid
            boolean validDates = true;
            if (validDates) {
                setResult(RESULT_OK, dataToSendBack);
                finish();
            } else {
                Toast.makeText(this, "End Date must be after Start Date", Toast.LENGTH_LONG).show();
            }


            Toast.makeText(CourseCreateView.this, courseCreatedTitle,
                    Toast.LENGTH_LONG).show();
        }


    }
}



