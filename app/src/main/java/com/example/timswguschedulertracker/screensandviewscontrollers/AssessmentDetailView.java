package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentDetailView extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/

    private TextView assessmentTitleLabel;
    private TextView endDate;
    private TextView assessmentType;
    private TextView assessmentDetail;
    private Button editAssessmentButton;
    private Button delAssessmentButton;
    Assessment curAssessment;
    DBOpenHelper myDB;
    int AssessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail_view);

        // Assigns the Views from the layout file to the corresponding variables.
        assessmentTitleLabel = (TextView) findViewById(R.id.assessmentTitleLabel);
        endDate = (TextView) findViewById(R.id.endDate);
        assessmentType = (TextView) findViewById(R.id.assessmentType);
        assessmentDetail = (TextView) findViewById(R.id.assessmentDetail);
        editAssessmentButton = (Button) findViewById(R.id.editAssessmentButton);
        delAssessmentButton = (Button) findViewById(R.id.delAssessmentButton);

        myDB = new DBOpenHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             AssessID = extras.getInt("AssessmentID");
            curAssessment = myDB.getAssessmentObjectFromID(AssessID);
            assessmentTitleLabel.setText(curAssessment.getAssessmentTitle());
            //convert the saved 24hr format to 12 hr
            //Date/time pattern of input date
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            //Date/time pattern of desired output date
            DateFormat outputformat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try {
                //Conversion of input String to date
                date = df.parse(curAssessment.getEndDate());
                //old date format to new date format
                output = outputformat.format(date);
                endDate.setText(output);
            } catch (ParseException pe) {
                Toast.makeText(this, "could not parse 24 hour date in Assessment Detail onCreate", Toast.LENGTH_SHORT).show();
            }



            assessmentType.setText(curAssessment.getAssessmentType());
            assessmentDetail.setText(curAssessment.getDetail());
        }

        delAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the Screen you want to showthe mentality and mind set is correct fffffff
                showConfirmDeleteDialog();

            }
        });

        editAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetailView.this, AssessmentCreate.class);
                intent.putExtra("isEdit", "true");
                intent.putExtra("AssessmentID", curAssessment.getAssessmentId());
                intent.putExtra("CourseID", curAssessment.getCourseId());
                startActivity(intent);
            }
        });
    }

    private void showConfirmDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        int result = myDB.deleteAssementDataByID(String.valueOf(curAssessment.getAssessmentId()));
                        if (result != -1) {
                            Toast.makeText(AssessmentDetailView.this, "Deleted Course with ID: " + curAssessment.getAssessmentId(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AssessmentDetailView.this, "Error, couldn't delete term", Toast.LENGTH_SHORT).show();
                        }

                        Intent dataToSendBack = new Intent();
                        dataToSendBack.putExtra("updateCourseList", "true");
                        setResult(RESULT_OK, dataToSendBack);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog object and return
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    protected void onResume() {
        if (curAssessment != null) {
            curAssessment = myDB.getAssessmentObjectFromID(AssessID);
            assessmentTitleLabel.setText(curAssessment.getAssessmentTitle());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            //Date/time pattern of desired output date
            DateFormat outputformat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try {
                //Conversion of input String to date
                date = df.parse(curAssessment.getEndDate());
                //old date format to new date format
                output = outputformat.format(date);
                endDate.setText(output);
            } catch (ParseException pe) {
                Toast.makeText(this, "could not parse 24 hour date in Assessment Detail onCreate", Toast.LENGTH_SHORT).show();
            }

            assessmentType.setText(curAssessment.getAssessmentType());
            assessmentDetail.setText(curAssessment.getDetail());

        }
        super.onResume();
    }
}


