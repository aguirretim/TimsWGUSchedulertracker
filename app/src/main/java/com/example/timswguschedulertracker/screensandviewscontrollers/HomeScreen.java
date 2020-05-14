package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.timswguschedulertracker.R;

public class HomeScreen extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/

    private TextView welcomeText;
    private ImageView titleImageView;
    private Button btnCurrentTerm;
    private Button btnAllTerms;
    private Button homeBtnProgress;
    private ConstraintLayout constraintlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigns the Views from the layout file to the corresponding variables.
        welcomeText = findViewById(R.id.welcomeText);
        //currentTermButton = findViewById(R.id.currentTermButton);
        btnCurrentTerm = findViewById(R.id.homeBtnCurrentTerm);
        btnAllTerms = findViewById(R.id.homeBtnViewAllTerms);
        homeBtnProgress = findViewById(R.id.homeBtnProgress);
        constraintlayout = findViewById(R.id.constraintlayout);


        /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/
        //TODO Term Summary button with term view.


        btnCurrentTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the current term
                Intent intent = new Intent(HomeScreen.this, TermDetailView.class);
                startActivity(intent);

            }
        });

        btnAllTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show all terms
                showAllTermsView();
            }
        });

        homeBtnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show all terms
                Intent intent = new Intent(HomeScreen.this, ProgressViewer.class);
                startActivity(intent);
            }
        });


    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showAllTermsView() {
        Intent intent = new Intent(this, AllTerms.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }


}
