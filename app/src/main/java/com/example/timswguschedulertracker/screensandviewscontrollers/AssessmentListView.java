package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.AssessmentAdapter;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AssessmentListView extends AppCompatActivity implements AssessmentAdapter.RecyclerClickListener {


    AssessmentAdapter AssessmentAdapter;
    private ArrayList<Assessment> AssessmentList = new ArrayList<Assessment>();
    RecyclerView RecycleListView;
    FloatingActionButton addAssessmentButton;
    DBOpenHelper myDB;
    int CourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_list_view);


        myDB = new DBOpenHelper(this);

        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        RecycleListView = (RecyclerView) findViewById(R.id.recyclerAssessmentList);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CourseID = extras.getInt("CourseID");
            //access database and populate courseList
            AssessmentList = myDB.getAllDataByCourseIDAsAssessmentArrayList(CourseID);

            if (AssessmentList != null) {
                AssessmentAdapter = new AssessmentAdapter(AssessmentList, AssessmentListView.this);
                AssessmentAdapter.setRecyclerClickListener(this);
                RecycleListView.setAdapter(AssessmentAdapter);
            } else {
                Toast.makeText(this, "No assessments for this course in the database", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "No CourseID was passed to this activity", Toast.LENGTH_SHORT).show();
        }


        //Test object to display in list.
        //Assessment testAssessment = new Assessment(34, 32, "Final Program Exam", "objective assessment", "2020-06-01");

        //AssessmentList.add(testAssessment);

        RecycleListView.setLayoutManager(new LinearLayoutManager(AssessmentListView.this));


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showAssessmentAddView();

            }

        };


        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentListView.this, AssessmentCreate.class);
                // to pass a key intent.putExtra("name",name);
                intent.putExtra("CourseID", CourseID);
                intent.putExtra("isEdit", "false");
                startActivity(intent);
            }
        });

    }
        @Override
        public void onClickPerformed ( int postion){
            Log.e("Position clicked", " " + postion);
            showAssessmentDetailView();
        }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showAssessmentDetailView() {
        Intent intent = new Intent(this, TermCreateView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);

    }

    //Method for changing view
    private void showAssessmentAddView() {
        Intent intent = new Intent(this, TermCreateView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);
    }

    private void updateAssessmentList() {
        AssessmentList = myDB.getAllDataByCourseIDAsAssessmentArrayList(CourseID);
        //termAdapter.notifyDataSetChanged();
        if (AssessmentList == null) {
            AssessmentList = new ArrayList<>();
        }
        AssessmentAdapter = new AssessmentAdapter(AssessmentList, AssessmentListView.this);
        AssessmentAdapter.setRecyclerClickListener(this);
        RecycleListView.setAdapter(AssessmentAdapter);
    }

    @Override
    protected void onResume() {
        //TODO this should really be onActivityResult
        //refresh data in recycler view
        AssessmentList = null;
        AssessmentList = new ArrayList<>();
        updateAssessmentList();
        super.onResume();
    }
}