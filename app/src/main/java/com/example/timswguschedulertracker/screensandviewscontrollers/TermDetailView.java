package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.timswguschedulertracker.R;

public class TermDetailView extends AppCompatActivity {

    private Button courseListButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);

        courseListButton = findViewById(R.id.courseListButton);


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showTermView();

            }

        };

        courseListButton.setOnClickListener(listener);

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


