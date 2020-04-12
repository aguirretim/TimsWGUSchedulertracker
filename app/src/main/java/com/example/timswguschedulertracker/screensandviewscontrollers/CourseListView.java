package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.CourseAdapter;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseListView extends AppCompatActivity implements CourseAdapter.RecyclerClickListener {

    CourseAdapter CourseAdapter;
    private ArrayList<Course> courseList = new ArrayList<Course>();
    RecyclerView RecycleListView;
    FloatingActionButton addCourseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        addCourseButton = findViewById(R.id.addCourseButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
        CourseAdapter.setRecyclerClickListener(this);

        RecycleListView.setAdapter(CourseAdapter);
        RecycleListView.setLayoutManager(new LinearLayoutManager(CourseListView.this));

        Course testCourse = new Course(23,"Tesla Application Development C199","2020-06-01","2020-12-31","In Progress","Elon Musk","425-848-9278","emusk@gmail.com");
        Course testCourse2 = new Course(25,"Apple Application Development C196","2020-09-01","2020-03-31","Plan to Take","Tim Cook","206-212-9853","tcook@gmail.com");
        Course testCourse3 = new Course(29,"Amazon Application Development C100","2020-03-01","2020-09-31","completed","Jeff Bezos","425-848-9278","emusk@gmail.com");

        courseList.add(testCourse);
        courseList.add(testCourse2);
        courseList.add(testCourse3);


    }

    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

    @Override
    public void onClickPerformed(int postion) {
        Log.e("Position clicked"," "+ postion);
        showCourseDetailView();
    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showCourseDetailView() {

        Intent intent = new Intent(this, TermDetailView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }

    //Method for changing view
    private void showTermAddView() {

        Intent intent = new Intent(this, TermCreateView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }





}
