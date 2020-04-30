package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

public class CourseDetailView extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/

    private Button assessmentListButton;
    Course curCourse;
    DBOpenHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_view);

        myDB = new DBOpenHelper(this);

        // Assigns the Views from the layout file to the corresponding variables.
        assessmentListButton=findViewById(R.id.assessmentListButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int courseId = extras.getInt("CourseID");
            curCourse = myDB.getCourseObjectFromID(courseId);
            int stop = 0;
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

