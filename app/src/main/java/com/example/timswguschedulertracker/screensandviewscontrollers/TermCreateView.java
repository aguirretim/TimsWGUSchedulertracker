package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TermCreateView extends AppCompatActivity {

    public static String EXTRA_TERM_TITLE = "title";
    public static String EXTRA_TERM_STARTDATE = "termStartDate";
    public static String EXTRA_TERM_ENDDATE = "termEndDate";
    EditText termTitleTextEdit;
    DatePicker startDatePicker, endDatePicker;
    Button saveButton;
    DBOpenHelper myDb;
    boolean isEditTerm = false;
    Term selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create_view);
        saveButton = findViewById(R.id.saveButton);
        termTitleTextEdit = findViewById(R.id.termTitleTextEdit);
        startDatePicker = findViewById(R.id.startDatePicker_Term);
        endDatePicker = findViewById(R.id.endDatePicker_Term);
        myDb = new DBOpenHelper(this);

        //btnSave will set result OK
        //btnCancel will set result CANCEL
        Bundle extras = getIntent().getExtras();
        //this will only happen when we start this activity from the Edit Term button
        if (extras != null) {
            isEditTerm = true;
            int id = extras.getInt("ID");
            selectedTerm = myDb.getTermObjectFromId(id);
            termTitleTextEdit.setText(selectedTerm.getTermTitle());

            SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
            try {

                Date yourDate = parser.parse(selectedTerm.getStartDate());
                //getYear returns the year minus 1900
                startDatePicker.init(yourDate.getYear() + 1900, yourDate.getMonth(), yourDate.getDate(), null);

                yourDate = parser.parse(selectedTerm.getEndDate());

                endDatePicker.init(yourDate.getYear() + 1900, yourDate.getMonth(), yourDate.getDate(), null);

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

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

        String startDateValue = String.format("%02d", startDatePicker.getMonth() + 1) + "/" +
                String.format("%02d", startDatePicker.getDayOfMonth()) + "/" + startDatePicker.getYear();
        String endDateValue = String.format("%02d", endDatePicker.getMonth() + 1) + "/" +
                String.format("%02d", endDatePicker.getDayOfMonth()) + "/" + endDatePicker.getYear();


        //Term newTerm = new Term(000, termCreatedTitle, startDateValue,endDateValue, false);

        //Checks te if term is an edited term create term


        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        boolean validDates = false;



        try {
            Date startTerm = parser.parse(startDateValue);
            Date endTerm = parser.parse(endDateValue);

            //compare dates
            if (startTerm.after(endTerm)) {
                Toast.makeText(this, "Error: Term start date is before term End  date", Toast.LENGTH_SHORT).show();
                validDates = false;
            } else {
                validDates = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Dates not valid because parser couldnt parse date string", Toast.LENGTH_SHORT).show();
        }


       /* if (isEditTerm) {
            //update the database
            if (myDb.updateData(String.valueOf(selectedTerm.getTermId()),
                    termTitleTextEdit.getText().toString(),
                    startDateValue, endDateValue)) {
                Toast.makeText(this,
                        "Updated Term with ID: " +
                                selectedTerm.getTermId(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Could not update edited term", Toast.LENGTH_SHORT).show();
            }
            finish();

            //this uses the OnResume of the previous activity to update database items

        } else {

            //this sends the data back to the activity that start this activity, From there we save to the database and reload the screen

            Intent dataToSendBack = new Intent();

            dataToSendBack.putExtra("title", termCreatedTitle);
            dataToSendBack.putExtra(EXTRA_TERM_STARTDATE, startDateValue);
            dataToSendBack.putExtra(EXTRA_TERM_ENDDATE, endDateValue);

            //TODO check that dates are valid
            boolean validDates = true;
            String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
*/
            if (validDates) {
                if (isEditTerm) {

                    if (myDb.updateData(String.valueOf(selectedTerm.getTermId()),
                            termCreatedTitle,
                            startDateValue,
                            endDateValue
                    )) {
                        Toast.makeText(this, "Updated Term with ID: " + selectedTerm.getTermId(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Could not update edited term", Toast.LENGTH_SHORT).show();
                    }


                    setResult(RESULT_OK/*dataToSendBack*/);
                finish();
            } else {
                    String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

                    if (myDb.insertData(termCreatedTitle, startDateValue, endDateValue, "false", currentDate)) {
                        Toast.makeText(TermCreateView.this, "Created Term " + termCreatedTitle,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Could not insert new term into database", Toast.LENGTH_SHORT).show();
                    }

                }

            }





        }


    }

