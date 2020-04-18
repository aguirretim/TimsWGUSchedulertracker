package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.DBOpenHelper;
import com.example.timswguschedulertracker.classesforobjects.DBProvider;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import com.example.timswguschedulertracker.adapters.TermAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllTerms extends AppCompatActivity implements TermAdapter.RecyclerClickListener {

    //private RecyclerView RecycleListView;
    TermAdapter termAdapter;
    private ArrayList<Term> termList = new ArrayList<Term>();
    RecyclerView RecycleListView;
    FloatingActionButton addTermButton;
    private DBProvider db;
    DBOpenHelper myDb;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        myDb = new DBOpenHelper(this);

        addTermButton = findViewById(R.id.addTermButton);
        RecycleListView = (RecyclerView) findViewById(R.id.RecycleListView);

        termAdapter = new TermAdapter(termList, AllTerms.this);
        termAdapter.setRecyclerClickListener(this);

        RecycleListView.setAdapter(termAdapter);
        RecycleListView.setLayoutManager(new LinearLayoutManager(AllTerms.this));


        //Test object to display in list.
        // Term TestTerm = new Term(007, "Summer Term", "2020-06-01", "2020-12-31");
        Term TestTerm2 = new Term(8, "Spring Term", "2020-06-01", "2020-12-31");
        Term TestTerm3 = new Term(9, "Winter Term", "2020-06-01", "2020-12-31");
        Term TestTerm4 = new Term(011, "Fall Term", "2020-06-01", "2020-12-31");

        //TestData();


       // termList.add(TestTerm);
      //  termList.add(TestTerm2);
       // termList.add(TestTerm3);

        viewAllData();

    /*********************************************
     * Changing screens and views with buttons.  *
     *********************************************/

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showTermAddView();

            }

        };

        addTermButton.setOnClickListener(listener);

    }

    @Override
    public void onClickPerformed(int postion) {
        Log.e("Position clicked"," "+ postion);
        showTermDetailView();
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
        startActivity(intent);


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


    public void viewAllData() {


                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                           int id = Integer.parseInt(res.getString(0));
                           String title =res.getString(1);
                           String startDate= res.getString(2);
                           String endDate=res.getString(3);
                           Term holderTerm= new Term(id,title,startDate,endDate);
                           termList.add(holderTerm);
                        }



                        // Show all data
                       // showMessage("Data",buffer.toString());
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}


