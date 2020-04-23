package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.timswguschedulertracker.R;

public class TermDetailView extends AppCompatActivity {

    private Button btnCourses;
    private TextView txtCourseTitle, txtStartDate, txtEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);

        btnCourses = findViewById(R.id.termDetailBtnCourses);
        txtCourseTitle = findViewById(R.id.termDetailTxtTermTitle);
        txtStartDate = findViewById(R.id.termDetailTxtStartDate);
        txtEndDate = findViewById(R.id.termDetailTxtEndDate);

        //Get the data that was passed in from the All Terms Page
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            txtCourseTitle.setText( extras.getString("title") );
            txtStartDate.setText(extras.getString("startDate"));
            txtEndDate.setText(extras.getString("endDate"));
        }


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        btnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the current term
                Intent intent = new Intent(TermDetailView.this, CourseListView.class);
                startActivity(intent);
            }
        });

    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showTermView() {

        Intent intent = new Intent(this, CourseListView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);

    }

}


