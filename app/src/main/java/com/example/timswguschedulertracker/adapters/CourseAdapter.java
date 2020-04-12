package com.example.timswguschedulertracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timswguschedulertracker.R;
import com.example.timswguschedulertracker.classesforobjects.Course;
import com.example.timswguschedulertracker.classesforobjects.Term;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private List<Course> vlist;
    private Context vContext;
    private LayoutInflater vInflator;
    private Course vCourse;
    private CourseAdapter.RecyclerClickListener recyclerClickListener;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public CourseAdapter(List<Course> vlist, Context vContext) {
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

        View v = vInflator.inflate(R.layout.course_list_item, parent, false);
        return new CourseAdapter.ViewHolder(v);
    }

    // Responsible to give values to each vie in a layout
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CourseAdapter.ViewHolder ViewHolder=(CourseAdapter.ViewHolder)holder;
        vCourse = vlist.get(position);

        ViewHolder.courseTitle.setText(vCourse.getCourseTitle());
        ViewHolder.courseStart.setText(vCourse.getStartDate());
        ViewHolder.courseEnd.setText(vCourse.getEndDate());
        ViewHolder.courseStatus.setText(vCourse.getStatus());
    }

    // responsible for how many elements are in the recycle view
    @Override
    public int getItemCount() {
        return vlist.size();
    }
    /****************************
     * View holder binding the data ??*
     ****************************/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView courseTitle;
        TextView courseStart;
        TextView courseEnd;
        TextView courseStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle=(TextView)itemView.findViewById(R.id.courseTitle);
            courseStart=(TextView)itemView.findViewById(R.id.startDate);
            courseEnd=(TextView)itemView.findViewById(R.id.endDate);
            courseStatus=(TextView)itemView.findViewById(R.id.courseStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerClickListener!=null){
                recyclerClickListener.onClickPerformed(getAdapterPosition());
            }
        }
    }

    public void setRecyclerClickListener(CourseAdapter.RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public interface RecyclerClickListener{
        void onClickPerformed(int postion);

    }


}
