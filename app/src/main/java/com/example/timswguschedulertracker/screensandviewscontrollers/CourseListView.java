package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.CourseAdapter;
import com.example.timswguschedulertracker.adapters.TermAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);

        myDb = new DBOpenHelper(this);

        addCourseButton = findViewById(R.id.addCourseButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        //get data that was passed into this acticity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("TermID");
            //access database and populate courseList
            courseList = myDb.getAllDataByTermIDAsCourseArrayList(id);
        } else {
            Toast.makeText(this, "No TermID was passed to this activity", Toast.LENGTH_SHORT).show();
        }


        if (courseList != null) {
            CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
            CourseAdapter.setRecyclerClickListener(this);
            RecycleListView.setAdapter(CourseAdapter);
        } else {
            Toast.makeText(this, "No courses for this term in the databse", Toast.LENGTH_SHORT).show();
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
                startActivity(intent);

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

    private void  updateTermList(){
        //courseList = myDb.getAllDataAsCourseArrayList();
        //termAdapter.notifyDataSetChanged();
        CourseAdapter = new CourseAdapter(courseList, CourseListView.this);
        CourseAdapter.setRecyclerClickListener(this);
        RecycleListView.setAdapter(CourseAdapter);
    }


}
