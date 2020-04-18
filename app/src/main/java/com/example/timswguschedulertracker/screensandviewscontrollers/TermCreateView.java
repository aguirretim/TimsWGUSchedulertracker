package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

import java.time.LocalDate;

public class TermCreateView extends AppCompatActivity {

    EditText termTitleTextEdit;
    EditText startDatePicker;
    EditText endDatePicker;
    Button saveButton;
    DBOpenHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create_view);
        saveButton = findViewById(R.id.saveButton);
        termTitleTextEdit = findViewById(R.id.termTitleTextEdit);




    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

    View.OnClickListener listener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {

            createTerm();


        }

    };

        saveButton.setOnClickListener(listener);

}


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createTerm() {
        String localDate = LocalDate.now().toString();
        String termCreatedTitle = termTitleTextEdit.getResources().toString();
                //termTitleTextEdit.getText().toString();
        String startDateValue = startDatePicker.getText().toString();
        String endDateValue = endDatePicker.getText().toString();
        /*boolean isInserted = myDb.insertData(
                termCreatedTitle,
                startDateValue,
                endDateValue,
                "", localDate);*/

        Toast.makeText(TermCreateView.this, termCreatedTitle,
                Toast.LENGTH_LONG).show();


       /* if (isInserted = true)
            Toast.makeText(TermCreateView.this, "Data Inserted",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(TermCreateView.this, "Data not Inserted",
                    Toast.LENGTH_LONG).show();*/

    }
}
