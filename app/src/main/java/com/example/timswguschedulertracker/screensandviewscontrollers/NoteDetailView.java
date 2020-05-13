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
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Note;

public class NoteDetailView extends AppCompatActivity {

    /*************************************
     * Variables for Buttons and Field.  *
     *************************************/

    private TextView noteTitleLabel;
    private TextView noteDetail;
    private Button editNoteButton;
    private Button delNoteButton;
    Note curNote;
    DBOpenHelper myDB;


    /**********************************
     *initializer for Screen elements.*
     **********************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_view);

        noteTitleLabel = (TextView) findViewById(R.id.noteTitleLabel);
        noteDetail = (TextView) findViewById(R.id.noteDetail);
        editNoteButton = (Button) findViewById(R.id.editNoteButton);
        delNoteButton = (Button) findViewById(R.id.delNoteButton);

        myDB = new DBOpenHelper(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int NoteID = extras.getInt("NoteID");
            curNote = myDB.getNoteObjectFromID(NoteID);
            noteTitleLabel.setText(curNote.getNotesTitle());
            noteDetail.setText(curNote.getNotesText());

        }
        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        delNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the Screen you want to showthe mentality and mind set is correct fffffff
                showConfirmDeleteDialog();

            }
        });

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteDetailView.this, NoteCreateView.class);
                intent.putExtra("isEdit", "true");
                intent.putExtra("NoteID", curNote.getNotesId());
                startActivity(intent);
            }
        });

    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    private void showConfirmDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //TODO make validation so you cant delete a term that has courses assigned to it

                        int result = myDB.deleteNoteDataByID(String.valueOf(curNote.getNotesId()));
                        if (result != -1) {
                            Toast.makeText(NoteDetailView.this, "Deleted Course with ID: " + curNote.getNotesId(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NoteDetailView.this, "Error, couldn't delete term", Toast.LENGTH_SHORT).show();
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
        if (curNote != null) {
            curNote = myDB.getNoteObjectFromID(curNote.getNotesId());
            noteTitleLabel.setText(curNote.getNotesTitle());
            noteDetail.setText(curNote.getNotesText());
        }
        super.onResume();
    }
}




