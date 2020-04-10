package com.example.timswguschedulertracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private List<Term> vlist;
    private Context vContext;
    private LayoutInflater vInflator;
    private Term vTerm;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public TermAdapter(List<Term> vlist, Context vContext) {
        this.vlist = vlist;
        this.vContext = vContext;
        vInflator = LayoutInflater.from(vContext);
    }

    /****************************
     * Methods for adapter *
     ****************************/

    // Responsible for telling what each cell is suppose to look like.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = vInflator.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(v);
    }

    // Responsible to give values to each vie in a layout
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder ViewHolder=(ViewHolder)holder;
        vTerm = vlist.get(position);

        ViewHolder.termTitle.setText(vTerm.getTermTitle());
        ViewHolder.termStart.setText(vTerm.getStartDate());
        ViewHolder.termEnd.setText(vTerm.getEndDate());
    }

    // responsible for how many elements are in the recycle view
    @Override
    public int getItemCount() {
        return vlist.size();
    }

    /****************************
     * View holder binding the data ??*
     ****************************/

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView termTitle;
        TextView termStart;
        TextView termEnd;

        public ViewHolder(@NonNull View itemView) {
        super(itemView);
            termTitle=(TextView)itemView.findViewById(R.id.termLabel);
            termStart=(TextView)itemView.findViewById(R.id.startDate);
            termEnd=(TextView)itemView.findViewById(R.id.endDate);
    }
}

}
