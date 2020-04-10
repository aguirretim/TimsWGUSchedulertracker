package com.example.timswguschedulertracker.screensandviewscontrollers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.util.ArrayList;

import com.example.timswguschedulertracker.adapters.TermAdapter;

public class AllTerms extends AppCompatActivity {

    //private RecyclerView RecycleListView;
    TermAdapter termAdapter;
    private ArrayList<Term> termList = new ArrayList<Term> ();
    RecyclerView RecycleListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        RecycleListView = (RecyclerView)findViewById(R.id.RecycleListView);

        termAdapter = new TermAdapter (termList,AllTerms.this);
        RecycleListView.setAdapter(termAdapter);
        RecycleListView.setLayoutManager(new LinearLayoutManager(AllTerms.this));



        //Test object to display in list.
        Term TestTerm= new Term(007,"Summer Term","2020-06-01","2020-12-31");
        Term TestTerm2= new Term(8,"super Term","2020-06-01","2020-12-31");
        Term TestTerm3= new Term(9,"crazy Term","2020-06-01","2020-12-31");
        Term TestTerm4= new Term(011,"impossible Term","2020-06-01","2020-12-31");


        termList.add(TestTerm);
        termList.add(TestTerm2);
        termList.add(TestTerm3);
        termList.add(TestTerm4);



        /*RecycleListView = findViewById(R.id.RecycleListView);

        RecycleListView.setLayoutManager( new LinearLayoutManager(this) );

        RecycleListView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return 0;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });*/

    }






}
