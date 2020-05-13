package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Note;

public class NoteCreateView extends AppCompatActivity {

    /***************************
     * initialized Variables.  *
     ***************************/

    private TextView noteTitleLable;
    private EditText noteTitleTextEdit;
    private EditText edtNoteDetail;
    private Button noteCreateButton;
    DBOpenHelper myDb;
    boolean isEditNote = false;
    Note selectedNote;
    public static String EXTRA_NOTE_TITLE = "title";
    public static String EXTRA_NOTE_DETAIL = "noteDetail";
    int CourseID, NoteID;

    /**************************************
     * Main initialized Method.  *
     **************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_create_view);

        noteTitleLable = (TextView) findViewById(R.id.noteTitleLable);
        noteTitleTextEdit = (EditText) findViewById(R.id.noteTitleTextEdit);
        edtNoteDetail = (EditText) findViewById(R.id.edtNoteDetail);
        noteCreateButton = (Button) findViewById(R.id.noteCreateButton);

        myDb = new DBOpenHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NoteID = extras.getInt("NoteID");
            CourseID = extras.getInt("CourseID");
            String isEdit = extras.getString("isEdit");
            isEditNote = Boolean.parseBoolean(isEdit);
            int test = 1;
            if (isEditNote) {
                //fill in all the data for the course

                selectedNote = myDb.getNoteObjectFromID(NoteID);
                CourseID = selectedNote.getCourseId();
                noteTitleTextEdit.setText(selectedNote.getNotesTitle());
                edtNoteDetail.setText(selectedNote.getNotesText());
            }

        }

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        noteCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNote();
            }
        });

    }

    public void createNote() {

        /* Finds and set the Variables in the from the layout controls */
        String noteCreatedTitle = noteTitleTextEdit.getText().toString();
        String noteDetail = edtNoteDetail.getText().toString();


        //myDb.insertCourseData(courseCreatedTitle,startDateValue,endDateValue,status,mentorName,mentorPhone,mentorEmail);


        if (isEditNote) {
            //update the database
            if (myDb.updateNoteData(NoteID + "",
                    CourseID + "",
                    noteCreatedTitle,
                    noteDetail
            )) {
                Toast.makeText(this, "Updated Course with ID: " + selectedNote.getNotesId(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Could not update edited term", Toast.LENGTH_SHORT).show();
            }
            finish();

            //this uses the OnResume of the previous activity to update database items

        } else {

            //this sends the data back to the activity that start this activity, From there we save to the database and reload the screen
            Intent dataToSendBack = new Intent();
            dataToSendBack.putExtra(EXTRA_NOTE_TITLE, noteCreatedTitle);
            dataToSendBack.putExtra(EXTRA_NOTE_DETAIL, noteDetail);


            Toast.makeText(NoteCreateView.this, noteCreatedTitle,
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_OK, dataToSendBack);
            finish();
        }


    }


}



