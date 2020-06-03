package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    DBOpenHelper myDb;
    private ArrayList<Term> termList = new ArrayList<Term>();
    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");

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

        myDb = new DBOpenHelper(this);

        termList = myDb.getAllDataAsTermArrayList();

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/


        btnCurrentTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (termList == null) {
                    termList = new ArrayList<>();
                }

                if (termList.size() > 0) {

                    //if todays date is not in the range of any terms in our database this function will return -1
                    int curTermId = getTermIdBasedOnTodaysDate();
                    if (curTermId != -1) {
                        //startActivity for current term
                        Term curTerm = myDb.getTermObjectFromId(curTermId);
                        welcomeText.setText("Hello, I hope you are enjoying your current term " + curTerm.getTermTitle() + " Please feel free to contact student services if you have any issues.");
                        Intent intent = new Intent(HomeScreen.this, TermDetailView.class);
                        intent.putExtra("ID", curTerm.getTermId());
                        //pass the Term id to the detail
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(HomeScreen.this, "There are no terms in the database", Toast.LENGTH_SHORT).show();
                }
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

    private int getTermIdBasedOnTodaysDate() {
        //Show the current term
        //get the current date
        //get all terms as ArrayList of term objects
        Date startDateValueDate = null;
        Date endDateValueDate = null;
        Date currentDateDate = null;

        //iterate through all terms
        for (Term term : termList) {
            String startDateValue = term.getStartDate();
            String endDateValue = term.getEndDate();
            String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
            //Toast.makeText(this, "Emulator Date is " + currentDate, Toast.LENGTH_SHORT).show();
            //The TRY CATCH Parses String dates to date values for calculations
            try {

                startDateValueDate = parser.parse(startDateValue);
                endDateValueDate = parser.parse(endDateValue);
                currentDateDate = parser.parse(currentDate);

            } catch (ParseException e) {

                e.printStackTrace();
            }

            if ((currentDateDate.after(startDateValueDate) &&
                    currentDateDate.before(endDateValueDate)) ||
                    currentDateDate.equals(startDateValueDate) ||
                    currentDateDate.equals(endDateValueDate)) {

//                welcomeText.setText("Hello, I hope you are enjoying your current term " + term.getTermTitle() + " Please feel free to contact student services if you have any issues.");
//                Intent intent = new Intent(HomeScreen.this, TermDetailView.class);
//                intent.putExtra("ID", term.getTermId());
//                //pass the Term id to the detail
//                startActivity(intent);
                return term.getTermId();
            }
        }

        return -1;


    }

    //Method for changing view
    private void showAllTermsView() {
        Intent intent = new Intent(this, AllTerms.class);
        // to pass a key intent.putExtra("name",name);
        startActivity(intent);
    }

}
