package com.example.timswguschedulertracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Assessment;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private List<Assessment> vlist;
    private Context vContext;
    private LayoutInflater vInflator;
    private Assessment vAssessment;
    private AssessmentAdapter.RecyclerClickListener recyclerClickListener;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public AssessmentAdapter(List<Assessment> vlist, Context vContext) {
        this.vlist = vlist;
        this.vContext = vContext;
        vInflator = LayoutInflater.from(vContext);
    }

    /****************************
     * Methods for adapter      *
     ****************************/

    // Responsible for telling what each cell is suppose to look like.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = vInflator.inflate(R.layout.term_list_item, parent, false);
        return new AssessmentAdapter.ViewHolder(v);
    }

    // Responsible to give values to each value in a layout.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssessmentAdapter.ViewHolder ViewHolder=(AssessmentAdapter.ViewHolder)holder;
        vAssessment = vlist.get(position);
        ViewHolder.AssessmentTitle.setText(vAssessment.getAssessmentTitle());
        ViewHolder.EndDate.setText(vAssessment.getEndDate());
        ViewHolder.AssessmentType.setText(vAssessment.getAssessmentType());
    }

    // Responsible for how many elements are in the recycle view.
    @Override
    public int getItemCount() {
        return vlist.size();
    }

    /****************************
     * View holder binding the data ??*
     ****************************/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView AssessmentTitle;
        TextView courseStart;
        TextView EndDate;
        TextView AssessmentType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AssessmentTitle=(TextView)itemView.findViewById(R.id.courseTitle);
            courseStart=(TextView)itemView.findViewById(R.id.startDate);
            EndDate=(TextView)itemView.findViewById(R.id.endDate);
            AssessmentType=(TextView)itemView.findViewById(R.id.courseStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerClickListener!=null){
                recyclerClickListener.onClickPerformed(getAdapterPosition());
            }
        }
    }

    public void setRecyclerClickListener(AssessmentAdapter.RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public interface RecyclerClickListener{
        void onClickPerformed(int postion);

    }


}
