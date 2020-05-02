package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

public class CourseDetailView extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/
    private TextView courseTitleLabel;
    private TextView startDate;
    private TextView endDate;
    private TextView courseStatus;
    private TextView courseMentor;
    private TextView courseMentorPhone;
    private TextView courseMentorEmail;
    private Button delCourseButton;
    private Button notesListButton;
    private Button assessmentListButton;
    Course curCourse;
    DBOpenHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_view);

        courseTitleLabel = (TextView) findViewById(R.id.courseTitleLabel);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        courseStatus = (TextView) findViewById(R.id.courseStatus);
        courseMentor = (TextView) findViewById(R.id.courseMentor);
        courseMentorPhone = (TextView) findViewById(R.id.courseMentorPhone);
        courseMentorEmail = (TextView) findViewById(R.id.courseMentorEmail);
        delCourseButton = (Button) findViewById(R.id.delCourseButton);
        notesListButton = (Button) findViewById(R.id.notesListButton);
        assessmentListButton = (Button) findViewById(R.id.assessmentListButton);


        myDB = new DBOpenHelper(this);

        // Assigns the Views from the layout file to the corresponding variables.
        assessmentListButton = findViewById(R.id.assessmentListButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int courseId = extras.getInt("CourseID");
            curCourse = myDB.getCourseObjectFromID(courseId);
            courseTitleLabel.setText(curCourse.getCourseTitle());
            startDate.setText(curCourse.getStartDate());
            endDate.setText(curCourse.getEndDate());
            courseStatus.setText(curCourse.getStatus());
            courseMentor.setText(curCourse.getCourseMentorNames());
            courseMentorPhone.setText(curCourse.getCourseMentorPhoneNumber());
            courseMentorEmail.setText(curCourse.getCourseMentorEmailAddresses());

        }

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showCourseListView();

            }

        };

        assessmentListButton.setOnClickListener(listener);

    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/
    //Method for changing view
    private void showCourseListView() {

        Intent intent = new Intent(this, AssessmentListView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }

}

