package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timswguschedulertracker.R;

public class HomeScreen extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/

    private TextView welcomeText;
    private ImageView titleImageView;
    private Button currentTermButton;
    private Button viewTermButton;
    private ConstraintLayout constraintlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigns the Views from the layout file to the corresponding variables.
        welcomeText = findViewById(R.id.welcomeText);
        currentTermButton = findViewById(R.id.currentTermButton);
        viewTermButton = findViewById(R.id.viewTermButton);
        constraintlayout = findViewById(R.id.constraintlayout);

    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showTermView();

            }

        };

        viewTermButton.setOnClickListener(listener);

    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showTermView() {

        Intent intent = new Intent(this, AllTerms.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }


}