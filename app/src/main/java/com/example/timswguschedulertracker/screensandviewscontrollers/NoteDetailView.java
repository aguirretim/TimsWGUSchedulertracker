package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Note;

public class NoteDetailView extends AppCompatActivity {

    private TextView noteTitleLabel;
    private TextView noteDetail;
    private Button editNoteButton;
    private Button delNoteButton;
    Note curNote;
    DBOpenHelper myDB;


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

    }
}




