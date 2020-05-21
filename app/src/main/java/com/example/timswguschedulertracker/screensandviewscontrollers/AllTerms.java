package com.example.timswguschedulertracker.screensandviewscontrollers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.adapters.TermAdapter;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.DBProvider;
import com.example.timswguschedulertracker.classesforobjects.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class AllTerms extends AppCompatActivity implements TermAdapter.RecyclerClickListener {

    //private RecyclerView RecycleListView;
    TermAdapter termAdapter;
    private ArrayList<Term> termList = new ArrayList<Term>();
    RecyclerView RecycleListView;
    FloatingActionButton addTermButton;
    private DBProvider db;
    DBOpenHelper myDb;
    private static int REQ_CODE_ADDTERM = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);

        addTermButton = findViewById(R.id.addTermButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        myDb = new DBOpenHelper(this);


        termList = myDb.getAllDataAsTermArrayList();
        if (termList != null){
            termAdapter = new TermAdapter(termList, AllTerms.this);
            termAdapter.setRecyclerClickListener(this);
            RecycleListView.setAdapter(termAdapter);

        } else {
            Toast.makeText(this, "No terms in the databse", Toast.LENGTH_SHORT).show();
        }


        RecycleListView.setLayoutManager(new LinearLayoutManager(AllTerms.this));

        /*********************************************
         * Changing screens and views with buttons.  *
         *********************************************/

        //Event that happens after add button is clicked.
        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTermAddView();
            }
        });

    }

    //Event that happens after a term is selected in list.
    @Override
    public void onClickPerformed(int position) {
        Log.e("Position clicked"," "+ position);

        Term clickedTerm = termList.get(position);

        //show detailed view for the term that was clicked on
        Intent intent = new Intent(this, TermDetailView.class);
        //Sends ID to Details Screen.
        intent.putExtra("ID", clickedTerm.getTermId() );
        /*intent.putExtra("title",clickedTerm.getTermTitle());
        intent.putExtra("startDate",clickedTerm.getStartDate());
        intent.putExtra("endDate",clickedTerm.getEndDate());*/
        startActivity(intent);

    }
    /****************************************
     * Methods and Actions that do things  *
     ****************************************/

    //Method for changing view
    private void showTermDetailView() {

        Intent intent = new Intent(this, TermDetailView.class);

        // to pass a key intent.putExtra("name",name);
        startActivity(intent);


    }

    //Method for changing view
    private void showTermAddView() {

        Intent intent = new Intent(this, TermCreateView.class);

        // to pass a key intent.putExtra("name",name);
        startActivityForResult(intent, REQ_CODE_ADDTERM);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void TestData() {
        String localDate = LocalDate.now().toString();
        boolean isInserted = myDb.insertData(
                "Summer Term",
                "2020-06-01",
                "2020-12-31",
                "", localDate);

        if (isInserted = true)
            Toast.makeText(AllTerms.this, "Data Inserted",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AllTerms.this, "Data not Inserted",
                    Toast.LENGTH_LONG).show();

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check to see which activity the data is coming back from
        /*if (requestCode == REQ_CODE_ADDTERM){

            //check to see what the result type is
            if (resultCode == RESULT_OK){

                //extract information from the data Intent (this is passed into this function as the third argument)
                Bundle extras = data.getExtras();
                String title = extras.getString("title");
                String startDate = extras.getString(TermCreateView.EXTRA_TERM_STARTDATE);
                String endDate = extras.getString(TermCreateView.EXTRA_TERM_ENDDATE);


                //add new term
                //TODO figure out where the user will choose whether or not this is the current term
                Term newTerm = new Term(000, title, startDate,endDate, false);

                String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                // Inserts the data from the term obj to the database
                if (myDb.insertData(title,startDate,endDate,"false",currentDate)){
                    updateTermList();
                } else {
                    Toast.makeText(this, "Could not insert new term into database", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
    }

    private void  updateTermList(){
        termList = myDb.getAllDataAsTermArrayList();
        //termAdapter.notifyDataSetChanged();
        if (termList == null) {
            termList = new ArrayList<>();
        }

        if (termList != null) {
            termAdapter = new TermAdapter(termList, AllTerms.this);
            termAdapter.setRecyclerClickListener(this);
            RecycleListView.setAdapter(termAdapter);
        }
    }


    @Override
    protected void onResume() {
        //TODO this should really be onActivityResult
        //refresh data in recycler view
        termList = null;
        termList = new ArrayList<>();
        updateTermList();
        super.onResume();
    }
}


