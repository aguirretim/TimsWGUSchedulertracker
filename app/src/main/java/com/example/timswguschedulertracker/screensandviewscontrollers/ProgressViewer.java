package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProgressViewer extends AppCompatActivity {

    private TextView prgViewCourseLable;
    private TextView txtPlannedAndInProgress;
    private TextView txtCompleted;
    private TextView txtDropped;
    private TextView txtCourseProgress;
    private TextView prgViewUpcomingCourses;
    private TextView prgViewUpcomingAssessmentAlrts;

    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
    DBOpenHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_viewer);

        prgViewCourseLable = (TextView) findViewById(R.id.prgViewCourseLable);
        txtPlannedAndInProgress = (TextView) findViewById(R.id.txtPlannedInProgress);
        txtCompleted = (TextView) findViewById(R.id.txtCompletedCourses);
        txtDropped = (TextView) findViewById(R.id.txtDroppedCourses);
        txtCourseProgress = (TextView) findViewById(R.id.prgViewCourseProgress);
        prgViewUpcomingCourses = (TextView) findViewById(R.id.prgViewUpcomingCourses);
        prgViewUpcomingAssessmentAlrts = (TextView) findViewById(R.id.prgViewUpcomingAssessmentAlrts);

        myDB = new DBOpenHelper(this);

        ArrayList<Course> allCourses = myDB.getAllCoursesAsArrayList();
        float plannedInProgress = 0;
        float completed = 0;
        float dropped = 0;
        int roundplannedInProgress = 0;
        int roundcompleted = 0;
        int rounddropped = 0;
        //also calculate percentage done
        /* Possible strings in the course status drop down {Plan To Take,In Progress,Dropped,Completed}
         */
        String[] statusArray = getResources().getStringArray(R.array.Status_Names);
        //prep to append all courses that happen after todays date
        StringBuilder upcomingCourses = new StringBuilder();
        upcomingCourses.append("Upcoming Courses\n");
        //get todays date
        String currentDateSTR = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());


        for (Course c : allCourses) {

            if (c.getStatus().equals(statusArray[0]) || c.getStatus().equals(statusArray[1])) {
                //plan to take or in progress
                roundplannedInProgress++;
            } else if (c.getStatus().equals(statusArray[2])) {
                rounddropped++;
            } else if (c.getStatus().equals(statusArray[3])) {
                roundcompleted++;
            }

            //check my dates to see if the courses are upcoming
            try {
                Date curDate = parser.parse(currentDateSTR);
                Date courseDate = parser.parse(c.getStartDate());
                if (courseDate.after(curDate)) {
                    upcomingCourses.append(c.getCourseTitle() + "\n");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }


        txtPlannedAndInProgress.setText(String.valueOf(roundplannedInProgress));
        txtCompleted.setText(String.valueOf(roundcompleted));
        txtDropped.setText(String.valueOf(rounddropped));
        //3/10 = .3
        float percent = (completed / allCourses.size()) * (float) 100; //300/1000
        int roundPercent = (int) percent;
        //String showMSG = String.format("You are %.0f",percent);
        txtCourseProgress.setText("You are " + roundPercent + "% done your courses"); //showMSG + "\\% done with your courses");

        prgViewUpcomingCourses.setText(upcomingCourses.toString());


        //TODO grab all assessments from DB


    }

    private int getTermIdBasedOnTodaysDate() {
        //Show the current term
        //get the current date
        //get all terms as ArrayList of term objects
        Date startDateValueDate = null;
        Date endDateValueDate = null;
        Date currentDateDate = null;

        ArrayList<Term> termList = myDB.getAllDataAsTermArrayList();
        //iterate through all terms
        for (Term term : termList) {
            String startDateValue = term.getStartDate();
            String endDateValue = term.getEndDate();
            String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
            Toast.makeText(this, "Emulator Date is " + currentDate, Toast.LENGTH_SHORT).show();
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
}
