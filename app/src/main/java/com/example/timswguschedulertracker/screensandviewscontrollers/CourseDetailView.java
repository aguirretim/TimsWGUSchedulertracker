package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button editCourseButton;
    Course curCourse;
    DBOpenHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_view);

        courseTitleLabel = (TextView) findViewById(R.id.courseTitleLabel);
        startDate = (TextView) findViewById(R.id.endDate);
        endDate = (TextView) findViewById(R.id.endDate);
        courseStatus = (TextView) findViewById(R.id.courseStatus);
        courseMentor = (TextView) findViewById(R.id.courseMentor);
        courseMentorPhone = (TextView) findViewById(R.id.courseMentorPhone);
        courseMentorEmail = (TextView) findViewById(R.id.courseMentorEmail);
        delCourseButton = (Button) findViewById(R.id.delCourseButton);
        notesListButton = (Button) findViewById(R.id.notesListButton);
        assessmentListButton = (Button) findViewById(R.id.assessmentListButton);
        editCourseButton = (Button) findViewById(R.id.editCourseButton);


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

        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailView.this, CourseCreateView.class);
                intent.putExtra("isEdit", "true");
                intent.putExtra("CourseID", curCourse.getCourseId());
                startActivity(intent);
            }
        });

        delCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the Screen you want to show
                showConfirmDeleteDialog();

            }
        });




        assessmentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CourseDetailView.this, AssessmentListView.class);
                intent.putExtra("CourseID", curCourse.getCourseId());
                intent.putExtra("isEdit", "false");
                startActivity(intent);
            }
        });

        notesListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the current term
                Intent intent = new Intent(CourseDetailView.this, NoteListView.class);
                 intent.putExtra("CourseID", curCourse.getCourseId());
                startActivity(intent);
            }
        });

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

    private void showConfirmDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //TODO make validation so you cant delete a term that has courses assigned to it

                        int result = myDB.deleteCourseDataByID(String.valueOf(curCourse.getCourseId()));
                        if (result != -1) {
                            Toast.makeText(CourseDetailView.this, "Deleted Course with ID: " + curCourse.getCourseId(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CourseDetailView.this, "Error, couldn't delete term", Toast.LENGTH_SHORT).show();
                        }
                        Intent dataToSendBack = new Intent();
                        dataToSendBack.putExtra("updateCourseList", "true");
                        setResult(RESULT_OK, dataToSendBack);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onResume() {
        if (curCourse != null) {
            curCourse = myDB.getCourseObjectFromID(curCourse.getCourseId());
            courseTitleLabel.setText(curCourse.getCourseTitle());
            startDate.setText(curCourse.getStartDate());
            endDate.setText(curCourse.getEndDate());
            courseStatus.setText(curCourse.getStatus());
            courseMentor.setText(curCourse.getCourseMentorNames());
            courseMentorPhone.setText(curCourse.getCourseMentorPhoneNumber());
            courseMentorEmail.setText(curCourse.getCourseMentorEmailAddresses());

        }
        super.onResume();
    }
}

