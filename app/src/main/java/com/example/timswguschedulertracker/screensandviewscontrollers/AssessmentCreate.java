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
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

public class AssessmentCreate extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker dueDatePickerCourse;
    private EditText txtAssessmentTitle;
    private Button btnSaveAssessment;
    private Spinner StatusSelector;
    private EditText edtDetails;
    private boolean isEditAssessment = false;
    DBOpenHelper myDB;
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
            CourseID = extras.getInt("CourseID");
            String isEdit = extras.getString("isEdit");
            isEditAssessment = Boolean.parseBoolean(isEdit);

            //implement if editing

        }


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
                   /* if (myDb.updateCourseData(String.valueOf(curCourse.getCourseId()), courseTitleTextEdit.getText().toString(), startDateValue, endDateValue,status,mentorName,mentorPhone,mentorEmail)) {
                        Toast.makeText(this, "Updated Course with ID: " + curCourse.getTermID(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Could not update edited term", Toast.LENGTH_SHORT).show();
                    }
*/

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
}
