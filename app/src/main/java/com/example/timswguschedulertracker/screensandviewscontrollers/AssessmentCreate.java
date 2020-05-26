package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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
import com.example.timswguschedulertracker.classesforobjects.AlarmReceiver;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    Course currentCourse;
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
            currentCourse = myDB.getCourseObjectFromID(CourseID);
            String isEdit = extras.getString("isEdit");
            isEditAssessment = Boolean.parseBoolean(isEdit);

            //TODO loading data from 12hour format with AM/PM
            SimpleDateFormat dateParseWithTime = new SimpleDateFormat("MM/dd/yyyy HH:mm"); //24hr
            SimpleDateFormat dateParse = new SimpleDateFormat("MM/dd/yyyy");
            //implement if editing
            if (isEditAssessment) {
                //fill in all the data for the course

                selectedAssessment = myDB.getAssessmentObjectFromID(AssessmentID);
                txtAssessmentTitle.setText(selectedAssessment.getAssessmentTitle());


                StatusSelector.setSelection(getStatusIDXFromString(selectedAssessment.getAssessmentType()));
                edtDetails.setText(selectedAssessment.getDetail());


                try {
                    //Date yourDate = parser.parse(selectedAssessment.getEndDate());
                    Date dateWithTime = dateParseWithTime.parse(selectedAssessment.getEndDate());

                    //getYear returns the year minus 1900
                    //Todo get the time from the date and parse it to set the time picker


                    dueDatePickerCourse.init(dateWithTime.getYear() + 1900, dateWithTime.getMonth(), dateWithTime.getDate(), null);
                    //set time picker
                    timePicker.setCurrentHour(dateWithTime.getHours());
                    timePicker.setCurrentMinute(dateWithTime.getMinutes());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {

                //set the due date picker to be the end date of the current course
                try {
                    Date dueDate = dateParse.parse(currentCourse.getEndDate());
                    dueDatePickerCourse.init(dueDate.getYear() + 1900, dueDate.getMonth(), dueDate.getDate(), null);
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
                String dueDateWithHour = endDateValue + " " + String.format("%02d", hour) + ":" + String.format("%02d", min);
                //MM/dd/yyyy HH:mm


                //myDb.insertCourseData(courseCreatedTitle,startDateValue,endDateValue,status,mentorName,mentorPhone,mentorEmail);
                //TODO check valid dates, shouldnt be before course start or after course end
                SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
                boolean validDates = false;
                try {
                    Date courseStartDate = parser.parse(currentCourse.getStartDate());
                    Date courseEndDate = parser.parse(currentCourse.getEndDate());

                    Date assessmentDueDate = parser.parse(endDateValue);
                    if (assessmentDueDate.after(courseEndDate) || assessmentDueDate.before(courseStartDate)) {
                        Toast.makeText(AssessmentCreate.this, "Error: assessment due date must be between: " + currentCourse.getStartDate() + " - " + currentCourse.getEndDate(), Toast.LENGTH_SHORT).show();
                    } else {
                        validDates = true;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                //We save the date in 24hr format but when we show it in any textview we reformat to 12 hour
                if (validDates) {
                    //save the item based on if it is an edit or create
                    if (isEditAssessment) {
                        //update the database
                        if (myDB.updateAssessmentData(AssessmentID + "", CourseID + "",
                                assessmentCreatedTitle, dueDateWithHour, status, details)) {
                            Toast.makeText(AssessmentCreate.this, "Updated Assessment with ID: " + AssessmentID, Toast.LENGTH_SHORT).show();
                            createAlarmForThisAssesment(assessmentCreatedTitle, dueDateWithHour);
                            finish();
                        } else {
                            Toast.makeText(AssessmentCreate.this, "Could not update edited Assesment", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //if saving a new assessment                    // Inserts the data from the Course obj to the database
                        if (myDB.insertAssessmentData(null, CourseID + "", assessmentCreatedTitle, dueDateWithHour, status, details)) {
                            createAlarmForThisAssesment(assessmentCreatedTitle, dueDateWithHour);
                            finish();
                        } else {
                            Toast.makeText(AssessmentCreate.this, "Could not insert new assessment into database", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });


    }

    private void createAlarmForThisAssesment(String assessmentCreatedTitle, String dueDateWithHour) {
        //If the device is on API 26 or newer we have to create a notification channel in order to show a notification
        //with our alarm
        //Build Code Oreo is API 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "ScheduleTracker";
            String description = "Channel for ScheduleTracker";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ScheduleTrackerChannelID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //MM/dd/yyyy HH:mm   this is the format of dueDateWithHour
        //String date = "5/25/2020 12:58";
        //dueDateWithHour = "5/25/2020 2:07";
        //We need to use a pendingIntent because technically the intent for the alarm is pending. We dont want the intent to
        //fire until the alarm actually goes off. We want to pass some data to the notificaiton for the alarm
        //so we have to put that into the intent for our AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("asg", assessmentCreatedTitle); //add in assignment title
        intent.putExtra("date", dueDateWithHour); //add in the due date with the hour
        //this pendingIntent will fire when the alarm time happens, and its going to trigger the AlarmReceiver class
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0); //280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        //Now actually setup the date/time you want the alarm to go off
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        try {
            //set the calendar time based on the string dueDateWithHour
            cal.setTime(sdf.parse(dueDateWithHour));

            //get the alarm manager
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //actually sets the alarm, all alarm times are set in milliseconds
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


            //SIDE NOTE: alarms are in milliseconds, if you want an alarm to go off in 5 minutes
            //get the current time convert to milliseconds  60000 * numMinutes

            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),1000*60, pendingIntent);

            Toast.makeText(this, "Alarm will vibrate at " + dueDateWithHour,
                    Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "couldnt parse date", Toast.LENGTH_SHORT).show();
        }


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
