package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.time.LocalDate;

public class TermCreateView extends AppCompatActivity {

    EditText termTitleTextEdit;
    DatePicker startDatePicker, endDatePicker;
    Button saveButton;
    DBOpenHelper myDb;
    public static String EXTRA_TERM_TITLE = "title";
    public static String EXTRA_TERM_STARTDATE = "termStartDate";
    public static String EXTRA_TERM_ENDDATE = "termEndDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create_view);
        saveButton = findViewById(R.id.saveButton);
        termTitleTextEdit = findViewById(R.id.termTitleTextEdit);
        startDatePicker = findViewById(R.id.startDatePicker_Term);
        endDatePicker = findViewById(R.id.endDatePicker_Term);


        //btnSave will set result OK
        //btnCancel will set result CANCEL

    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTerm();
            }
        });

        /*
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCEL);
                finish();
            }
        });
         */

}



    public void createTerm() {
        //String localDate = LocalDate.now().toString();

        String termCreatedTitle = termTitleTextEdit.getText().toString();
                //termTitleTextEdit.getText().toString();
        String startDateValue = (startDatePicker.getMonth() + 1) + "/" +
                startDatePicker.getDayOfMonth() + "/" + startDatePicker.getYear();
        String endDateValue = (endDatePicker.getMonth() + 1) + "/" +
                endDatePicker.getDayOfMonth() + "/" + endDatePicker.getYear();



        //Term newTerm = new Term(000, termCreatedTitle, startDateValue,endDateValue, false);

        Intent dataToSendBack = new Intent();
        dataToSendBack.putExtra("title", termCreatedTitle);
        dataToSendBack.putExtra(EXTRA_TERM_STARTDATE, startDateValue);
        dataToSendBack.putExtra(EXTRA_TERM_ENDDATE, endDateValue);

        //todo check that dates are valid
        boolean validDates = true;
        if (validDates) {
            setResult(RESULT_OK, dataToSendBack);
            finish();
        } else {
            Toast.makeText(this, "End Date must be after Start Date", Toast.LENGTH_LONG).show();
        }

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
