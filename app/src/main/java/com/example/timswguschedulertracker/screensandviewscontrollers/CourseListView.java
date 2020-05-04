package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.CourseAdapter;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.DBProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseListView extends AppCompatActivity implements CourseAdapter.RecyclerClickListener {

    CourseAdapter CourseAdapter;
    private ArrayList<Course> courseList = new ArrayList<Course>();
    RecyclerView RecycleListView;
    FloatingActionButton addCourseButton;
    private DBProvider db;
    DBOpenHelper myDb;
    int TermID;
    private static int REQ_CODE_ADDCOURSE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);

        myDb = new DBOpenHelper(this);

        addCourseButton = findViewById(R.id.addCourseButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);


       // courseList = myDb.getAllDataByTermIDAsCourseArrayList(TermID);

        //get data that was passed into this acticity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TermID = extras.getInt("TermID");
            //access database and populate courseList
            courseList = myDb.getAllDataByTermIDAsCourseArrayList(TermID);
        } else {
            Toast.makeText(this, "No TermID was passed to this activity", Toast.LENGTH_SHORT).show();
        }


        if (courseList != null) {
            CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
            CourseAdapter.setRecyclerClickListener(this);
            RecycleListView.setAdapter(CourseAdapter);
        } else {
            Toast.makeText(this, "No courses for this term in the database", Toast.LENGTH_SHORT).show();
        }

        RecycleListView.setLayoutManager(new LinearLayoutManager(CourseListView.this));


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the Screen you want to show
                Intent intent = new Intent(CourseListView.this, CourseCreateView.class);
                startActivityForResult(intent, REQ_CODE_ADDCOURSE);
            }
        });
    }

    @Override
    public void onClickPerformed(int postion) {
        Log.e("Position clicked", " " + postion);
        Intent intent = new Intent(this, CourseDetailView.class);
        intent.putExtra("CourseID", courseList.get(postion).getCourseId());
        // to pass a key intent.putExtra("name",name);
        startActivity(intent);
    }


    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showCourseDetailView() {

        Intent intent = new Intent(this, CourseDetailView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);

    }

/*    private void updateCourseList() {
        //courseList = myDb.getAllDataAsCourseArrayList();
        //termAdapter.notifyDataSetChanged();
        CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
        CourseAdapter.setRecyclerClickListener(this);
        RecycleListView.setAdapter(CourseAdapter);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check to see which activity the data is coming back from
        if (requestCode == REQ_CODE_ADDCOURSE) {

            //check to see what the result type is
            if (resultCode == RESULT_OK) {

                //extract information from the data Intent (this is passed into this function as the third argument)
                Bundle extras = data.getExtras();
                String title = extras.getString("title");
                String startDate = extras.getString(CourseCreateView.EXTRA_COURSE_STARTDATE);
                String endDate = extras.getString(CourseCreateView.EXTRA_COURSE_ENDDATE);
                String status = extras.getString(CourseCreateView.EXTRA_COURSE_STATUS);
                String mentor = extras.getString(CourseCreateView.EXTRA_COURSE_MENTORNAME);
                String mentorPhone = extras.getString(CourseCreateView.EXTRA_COURSE_PHONE);
                String mentorEmail = extras.getString(CourseCreateView.EXTRA_COURSE_EMAIL);

                String update = extras.getString("updateCourseList");
                //update the courselist from DB
                //refresh data in recycler view
                courseList = null;
                courseList = new ArrayList<>();
                updateCourseList();

                //add new term
                //TODO figure out where the user will choose whether or not this is the current term
                Course newCourse = new Course(TermID, 000, title, startDate, endDate, status, mentor, mentorPhone, mentorEmail);
                //TODO save item to database
                // Inserts the data from the Course obj to the database
                if (myDb.insertCourseData(TermID + "", null, title, startDate, endDate, status, mentor, mentorPhone, mentorEmail)) {
                    updateCourseList();
                } else {
                    Toast.makeText(this, "Could not insert new term into database", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateCourseList() {
        courseList = myDb.getAllDataByTermIDAsCourseArrayList(TermID);
        //termAdapter.notifyDataSetChanged();
        if (courseList == null) {
            courseList = new ArrayList<>();
        }
        CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
        CourseAdapter.setRecyclerClickListener(this);
        RecycleListView.setAdapter(CourseAdapter);

    }
    @Override
    protected void onResume() {
        //TODO this should really be onActivityResult
        //refresh data in recycler view
        courseList = null;
        courseList = new ArrayList<>();
        updateCourseList();
        super.onResume();
    }


}
