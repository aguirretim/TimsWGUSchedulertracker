package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.util.ArrayList;

public class TermDetailView extends AppCompatActivity {

    private Button btnCourses, btnDelete, btnEdit;
    private TextView txtCourseTitle, txtStartDate, txtEndDate;
    DBOpenHelper myDb;
    Term selectedTerm = null;
    //int id;
    int SelectedID;
    boolean hasCourses = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);

        btnCourses = findViewById(R.id.termDetailBtnCourses);
        txtCourseTitle = findViewById(R.id.termDetailTxtTermTitle);
        txtStartDate = findViewById(R.id.termDetailTxtStartDate);
        txtEndDate = findViewById(R.id.termDetailTxtEndDate);
        btnDelete = findViewById(R.id.termDetailBtnDelete);
        btnEdit = findViewById(R.id.termDetailBtnEdit);

        myDb = new DBOpenHelper(this);

        //Get the data that was passed in from the All Terms Page
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Receives ID from AllTerms selected term Screen.
            int id = extras.getInt("ID");
            SelectedID = id;
            //Searches database for term by id.
            selectedTerm = myDb.getTermObjectFromId(id);
            //Sets the attributes to the selected found object.
            txtCourseTitle.setText(selectedTerm.getTermTitle());
            txtStartDate.setText(selectedTerm.getStartDate());
            txtEndDate.setText(selectedTerm.getEndDate());

        }


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        btnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the current term
                Intent intent = new Intent(TermDetailView.this, CourseListView.class);
                intent.putExtra("TermID", selectedTerm.getTermId());
                startActivity(intent);
            }
        });
        // TODO (Validation so that a term cannot be deleted if courses are assigned to it)
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDeleteDialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetailView.this, TermCreateView.class);
                intent.putExtra("ID", selectedTerm.getTermId());
                startActivity(intent);
            }
        });


    }


    private void showConfirmDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        SelectedID = SelectedID;
        ArrayList<Course> courseArray = new ArrayList<Course>();
        courseArray = myDb.getAllDataByTermIDAsCourseArrayList(SelectedID);
        //termAdapter.notifyDataSetChanged();
        if (courseArray == null) {
            courseArray = new ArrayList<>();
        }

        if (courseArray.size() == 0 || courseArray.isEmpty() || courseArray == null) {
            hasCourses = false;
        } else {
            hasCourses = true;
        }
        if (hasCourses == false) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            //TODO make validation so you cant delete a term that has courses assigned to it


                            int result = myDb.deleteDataByID(String.valueOf(selectedTerm.getTermId()));
                            if (result != -1) {
                                Toast.makeText(TermDetailView.this, "Deleted Term with ID: " + selectedTerm.getTermId(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TermDetailView.this, "Error, couldn't delete term", Toast.LENGTH_SHORT).show();
                            }

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
        } else {
            Toast.makeText(TermDetailView.this, "Term cannot be deleted with courses associated with it", Toast.LENGTH_SHORT).show();
        }
    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showTermView() {

        Intent intent = new Intent(this, CourseListView.class);

        // to pass a key intent.putExtra("name",name);
        intent.putExtra("TermID", SelectedID);

        startActivity(intent);

    }

    //Refreshes the data when the detail screen is loaded
    @Override
    protected void onResume() {

        if (selectedTerm != null) {
            selectedTerm = myDb.getTermObjectFromId(selectedTerm.getTermId());
            txtCourseTitle.setText(selectedTerm.getTermTitle());
            txtStartDate.setText(selectedTerm.getStartDate());
            txtEndDate.setText(selectedTerm.getEndDate());
        }

        super.onResume();
    }
}


