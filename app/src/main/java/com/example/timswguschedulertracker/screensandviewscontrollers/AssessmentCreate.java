package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentCreate extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker dueDatePickerCourse;
    private EditText txtAssessmentTitle;
    private Button btnSaveAssessment;
    private Spinner StatusSelector;
    private EditText edtDetails;
    private boolean isEditAssessment = false;
    DBOpenHelper myDB;
    Assessment selectedAssessment;
    int AssessmentID;
    int CourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_create);

        timePicker = findViewById(R.id.timePicker);
        dueDatePickerCourse = (DatePicker) findViewById(R.id.dueDatePicker_assessment);
        txtAssessmentTitle = (EditText) findViewById(R.id.txtAssessmentTitle);
        btnSaveAssessment = (Button) findViewById(R.id.btnSaveAssessment);
        StatusSelector = (Spinner) findViewById(R.id.StatusSelector);
        edtDetails = findViewById(R.id.edtAssessDetail);

        myDB = new DBOpenHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            AssessmentID = extras.getInt("AssessmentID");
            CourseID = extras.getInt("CourseID");
            String isEdit = extras.getString("isEdit");
            isEditAssessment = Boolean.parseBoolean(isEdit);

            //implement if editing
            if (isEditAssessment) {
                //fill in all the data for the course

                selectedAssessment = myDB.getAssessmentObjectFromID(AssessmentID);
                txtAssessmentTitle.setText(selectedAssessment.getAssessmentTitle());


                StatusSelector.setSelection(getStatusIDXFromString(selectedAssessment.getAssessmentType()));
                edtDetails.setText(selectedAssessment.getDetail());


                SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat parser2 = new SimpleDateFormat("HH");
                SimpleDateFormat parser3 = new SimpleDateFormat("mm");
                try {
                    Date yourDate = parser.parse(selectedAssessment.getEndDate());
                    Date yourtime = parser2.parse(selectedAssessment.getEndDate());
                    Date yourtime2 = parser2.parse(selectedAssessment.getEndDate());
                    //getYear returns the year minus 1900
                    //Todo get the time from the date and parse it to set the time picker


                    dueDatePickerCourse.init(yourDate.getYear() + 1900, yourDate.getMonth(), yourDate.getDate(), null);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/
        btnSaveAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save to db
                /* Finds and set the Variables in the from the layout controls */
                String assessmentCreatedTitle = txtAssessmentTitle.getText().toString();

                String endDateValue = String.format("%02d", dueDatePickerCourse.getMonth() + 1) + "/" +
                        String.format("%02d", dueDatePickerCourse.getDayOfMonth()) + "/" + dueDatePickerCourse.getYear();
                //"MM/dd/yyyy hh:mm"
                String status = StatusSelector.getSelectedItem().toString();
                String details = edtDetails.getText().toString();
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                endDateValue = endDateValue + " " + String.format("%02d", hour) + ":" + String.format("%02d", min);


                //myDb.insertCourseData(courseCreatedTitle,startDateValue,endDateValue,status,mentorName,mentorPhone,mentorEmail);


                if (isEditAssessment) {
                    //update the database
                    if (myDB.updateAssessmentData(AssessmentID + "", CourseID + "",
                            assessmentCreatedTitle, endDateValue, status, details)) {
                          Toast.makeText(AssessmentCreate.this, "Updated Assessment with ID: " + AssessmentID, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AssessmentCreate.this, "Could not update edited Assesment", Toast.LENGTH_SHORT).show();
                    }


                    //this uses the OnResume of the previous activity to update database items

                } else {

                    // Inserts the data from the Course obj to the database
                    if (myDB.insertAssessmentData(null, CourseID + "", assessmentCreatedTitle, endDateValue, status, details)) {
                        finish();
                    } else {
                        Toast.makeText(AssessmentCreate.this, "Could not insert new assessment into database", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    private int getStatusIDXFromString(String status) {
        String statusItems[] = getResources().getStringArray(R.array.AssessmentType);

        for (int i = 0; i < statusItems.length; i++) {
            if (statusItems[i].equals(status)) {
                return i;
            }
        }
        Toast.makeText(this, "Error: Type saved in Assement does not match status options. First options returned by default", Toast.LENGTH_LONG).show();
        return 0;

    }
}
