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
import com.example.timswguschedulertracker.classesforobjects.Note;
import com.example.timswguschedulertracker.screensandviewscontrollers.NoteListView;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    private List<Note> vlist;
    private Context vContext;
    private LayoutInflater vInflator;
    private Note vNote;
    private NotesAdapter.RecyclerClickListener recyclerClickListener;

    /****************************
     * Constructor for Object.  *
     ****************************/

    public NotesAdapter(List<Note> vlist, Context vContext ) {
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

        View v = vInflator.inflate(R.layout.note_list_item,parent,false);

        return new NotesAdapter.ViewHolder(v);
    }

    // Responsible to give values to each vie in a layout
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotesAdapter.ViewHolder
                viewHolder=(NotesAdapter.ViewHolder)holder;
        vNote = vlist.get(position);

        viewHolder.noteTitle.setText(vNote.getNotesTitle());
        viewHolder.noteDescription.setText(vNote.getNotesText());

    }

    // Responsible for how many elements are in the recycle view
    @Override
    public int getItemCount() {
        return vlist.size();
    }

    public void setRecyclerClickListener(NoteListView noteListView) {

        this.recyclerClickListener = recyclerClickListener;
    }

    public void setRecyclerClickListener(NotesAdapter.RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteTitle;

        TextView noteDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById( R.id.noteTitle );
            noteDescription = itemView.findViewById( R.id.noteDescription );
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (recyclerClickListener!=null){
                recyclerClickListener.onClickPerformed(getAdapterPosition());
            }
        }

    }

    public interface RecyclerClickListener{
        void onClickPerformed(int postion);
    }
}




