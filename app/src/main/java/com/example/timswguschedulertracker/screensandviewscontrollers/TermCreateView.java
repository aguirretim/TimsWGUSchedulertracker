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
import java.util.ArrayList;
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
    private ArrayList<Term> termList = new ArrayList<Term>();
    boolean validLocalDates = false;
    boolean validGlobalDates = false;

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

    }


    public void createTerm() {
        String termCreatedTitle = termTitleTextEdit.getText().toString();
        String startDateValue = String.format("%02d", startDatePicker.getMonth() + 1) + "/" +
                String.format("%02d", startDatePicker.getDayOfMonth()) + "/" + startDatePicker.getYear();
        String endDateValue = String.format("%02d", endDatePicker.getMonth() + 1) + "/" +
                String.format("%02d", endDatePicker.getDayOfMonth()) + "/" + endDatePicker.getYear();

        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");

        Date startTerm = null;
        Date endTerm = null;
        Date currentDateDate = null;

        //The TRY CATCH Parses String dates to date values for calculations
        try {
            startTerm = parser.parse(startDateValue);
            endTerm = parser.parse(endDateValue);
            currentDateDate = parser.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        termList = myDb.getAllDataAsTermArrayList();

        //compare local dates make sure start is before end
        if (startTerm.after(endTerm)) {
            Toast.makeText(this, "Error: Term start date is before term End date", Toast.LENGTH_SHORT).show();
            validLocalDates = false;
        } else {
            validLocalDates = true;
        }

        for (Term term : termList) {

            String startDateLValue = term.getStartDate();
            String endDateLValue = term.getEndDate();
            Date startDateLValueDate = null;
            Date endDateLValueDate = null;

            //The TRY CATCH Parses String dates to date values for calculations
            try {
                startDateLValueDate = parser.parse(startDateLValue);
                endDateLValueDate = parser.parse(endDateLValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //startTerm is the term im trying to create
            //startDateLValueDate is the date of the term Im checking against in the datase
            //Here we are defining when the dates are NOT VALID
            //if current term start Date is in the range of another term, curTerm Strt = anyTermStart, curTermStart = anyTerm End
            //if current term end Date is in the range of another term, curTerm End = anyTermEnd, curTermEnd = anyTerm End
            if (isEditTerm && term.getTermId() == selectedTerm.getTermId()) {

                validGlobalDates = true;

            } else if ((startTerm.after(startDateLValueDate) && startTerm.before(endDateLValueDate))
                    || startTerm.equals(startDateLValueDate)
                    || (endTerm.after(startDateLValueDate) && endTerm.before(endDateLValueDate))
                    || endTerm.equals(endDateLValueDate)) {
                Toast.makeText(this, "Error: Term dates are in the range of existing term: " + term.getTermTitle() + " with Start Date " + startDateLValue + " and End Date " + endDateLValue, Toast.LENGTH_LONG).show();
                validGlobalDates = false;
                break;
            } else {
                validGlobalDates = true;
            }

        }

        if (validLocalDates && validGlobalDates) {

            //make sure the current term dates do not fall within another terms dates

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
                setResult(RESULT_OK);
                finish();

            } else {
                if (myDb.insertData(termCreatedTitle, startDateValue, endDateValue, "false", currentDate)) {
                    Toast.makeText(TermCreateView.this, "Created Term " + termCreatedTitle,
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "Could not insert new term into database", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}




