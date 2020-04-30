package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.AssessmentAdapter;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AssessmentListView extends AppCompatActivity implements AssessmentAdapter.RecyclerClickListener {


    AssessmentAdapter AssessmentAdapter;
    private ArrayList<Assessment> AssessmentList = new ArrayList<Assessment>();
    RecyclerView RecycleListView;
    FloatingActionButton addAssessmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_list_view);
        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        AssessmentAdapter = new AssessmentAdapter(AssessmentList, AssessmentListView.this);
        AssessmentAdapter.setRecyclerClickListener(this);

        RecycleListView.setAdapter(AssessmentAdapter);
        RecycleListView.setLayoutManager(new LinearLayoutManager(AssessmentListView.this));

        //Test object to display in list.
        Assessment testAssessment = new Assessment(34, 32, "Final Program Exam", "objective assessment", "2020-06-01");

        AssessmentList.add(testAssessment);

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showAssessmentAddView();

            }

        };


        addAssessmentButton.setOnClickListener(listener);


    }


    @Override
    public void onClickPerformed(int postion) {
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

}