package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.NotesAdapter;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteListView extends AppCompatActivity implements NotesAdapter.RecyclerClickListener {

    /***************************
     * initialized Variables.  *
     ***************************/

    NotesAdapter NoteAdapter;
    private ArrayList<Note> noteList = new java.util.ArrayList<Note>();
    RecyclerView RecycleListView;
    FloatingActionButton addNoteButton;
    DBOpenHelper myDb;
    int CourseID;
    private static int REQ_CODE_ADDNOTE = 100;

    /**********************************
     *initializer for Screen elements.*
     **********************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_view);

        myDb = new DBOpenHelper(this);

        addNoteButton = (FloatingActionButton) findViewById(R.id.addNoteButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            CourseID = extras.getInt("CourseID");
            //access database and populate courseList
            noteList = myDb.getAllNotesByCourseIDAsNoteArrayList(CourseID);
        } else {
            Toast.makeText(this,
                    "No NoteID was passed to this activity",
                    Toast.LENGTH_SHORT).show();
        }

        if (noteList != null) {
            NoteAdapter = new NotesAdapter(noteList,
                    NoteListView.this);
            NoteAdapter.setRecyclerClickListener(this);
            RecycleListView.setAdapter(NoteAdapter);
        } else {
            Toast.makeText(this,
                    "No Notes for this Course in the database",
                    Toast.LENGTH_SHORT).show();
        }


        RecycleListView.setLayoutManager(new LinearLayoutManager(NoteListView.this));


        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the Screen you want to show
                Intent intent = new Intent(NoteListView.this, NoteCreateView.class);
                startActivityForResult(intent, REQ_CODE_ADDNOTE);
            }
        });

    }

    @Override
    public void onClickPerformed(int postion) {
        Log.e("Position clicked", " " + postion);
        Note noteInList = noteList.get(postion);
        Intent intent = new Intent(this, NoteDetailView.class);
        intent.putExtra("NoteID", noteInList.getNotesId());
        // to pass a key intent.putExtra("name",name);
        startActivity(intent);
    }

    /****************************************
     * Methods and Actions that do things  *
     ****************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check to see which activity the data is coming back from
        if (requestCode == REQ_CODE_ADDNOTE) {

            //check to see what the result type is
            if (resultCode == RESULT_OK) {

                //extract information from the data Intent (this is passed into this function as the third argument)
                Bundle extras = data.getExtras();
                String title = extras.getString(NoteCreateView.EXTRA_NOTE_TITLE);
                String noteDetails = extras.getString(NoteCreateView.EXTRA_NOTE_DETAIL);



                //update the courselist from DB
                //refresh data in recycler view
                noteList = null;
                noteList = new ArrayList<>();
                updateNoteList();

                //add new term
                //TODO figure out where the user will choose whether or not this is the current term
                //note newNote = new Note(TermID, 000, title, startDate);
                //TODO save item to database
                // Inserts the data from the Course obj to the database
                if (myDb.insertNoteData(
                        CourseID + "",
                        title,
                        noteDetails)) {
                    updateNoteList();
                } else {
                    Toast.makeText(this, "Could not insert new Note into database", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

    private void updateNoteList() {
        noteList = myDb.getAllNotesByCourseIDAsNoteArrayList(CourseID);
        //termAdapter.notifyDataSetChanged();
        if (noteList == null) {
            noteList = new ArrayList<>();
        }
        NoteAdapter = new NotesAdapter(noteList, NoteListView.this);
        NoteAdapter.setRecyclerClickListener(this);
        RecycleListView.setAdapter(NoteAdapter);
    }

    @Override
    protected void onResume() {
        //TODO this should really be onActivityResult
        //refresh data in recycler view
        noteList = null;
        noteList = new ArrayList<>();
        updateNoteList();
        super.onResume();
    }

}

