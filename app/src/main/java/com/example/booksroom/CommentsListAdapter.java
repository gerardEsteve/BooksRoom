package com.example.booksroom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentsViewHolder> {

    private final LayoutInflater mInflater;
    private List<Comments> mComments;
    private int selectedPosition = -1;
    Context context;

    CommentsListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;


    }

    @NonNull
    @Override
    public CommentsListAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_ciudades, parent,false);
        return new CommentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsListAdapter.CommentsViewHolder holder, final int position) {
     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
            }
        });*/
        if(mComments != null ) {
            Comments currentComment = mComments.get(position);
            if(position == selectedPosition){
                String desc = currentComment.getmCommentDesc();
                holder.textViewDescripcion2.setVisibility(View.VISIBLE);
                holder.textViewDescripcion2.setText(desc);
                holder.textViewDescripcion.setVisibility(View.GONE);
            }
            else {
                String desc = currentComment.getmCommentDesc();
                holder.textViewDescripcion.setVisibility(View.VISIBLE);
                holder.textViewDescripcion.setText(desc);
                holder.textViewDescripcion2.setVisibility(View.GONE);
            }




//            context.getResources().getDrawable(android.R.drawable.button_onoff_indicator_on);
        }
    }

    @Override
    public int getItemCount(){
        if(mComments != null ) return mComments.size();
        else return 0;
    }

    void setmComments(List<Comments> comments){
        mComments = comments;
        notifyDataSetChanged();
    }

    void addComment(Comments comments){
        mComments.add(comments);
    }


    public Comments getCommentAtPosition(int position){

        return mComments.get(position);
    }

    public boolean hasComment(Comments commentsTemp) {
        boolean b = false;
        for (int i = 0; i< mComments.size();i++){
            Comments comments = mComments.get(i);
            b = compareComments(comments,commentsTemp);
            if(b) return true;
        }
        return false;

//        return mBooks.contains(bookTemp);
    }

    private boolean compareComments(Comments comments, Comments commentsTemp) {
        return false;
    }

    public void sortBy(String asc) {

        if(asc.equals("ASC")){
            Collections.sort(mComments,new Comparator<Comments>() {
                @Override
                public int compare(Comments c1, Comments c2) {
                    return c1.getmCommentDesc().toString().compareToIgnoreCase(c2.getmCommentDesc().toString());
                }
            });
        }
        else {
            Collections.sort(mComments,new Comparator<Comments>() {
                @Override
                public int compare(Comments c1, Comments c2) {
                    return c1.getmCommentDesc().toString().compareToIgnoreCase(c2.getmCommentDesc().toString());
                }
            });
            Collections.reverse(mComments);

        }
        notifyDataSetChanged();
    }

    public void deleteAllByBookID(int bookID) {
        for (int i = 0;i < mComments.size();++i){
            if(mComments.get(i).getIdBook()==bookID){
                mComments.remove(mComments.get(i));
            }
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int pos) {
        selectedPosition = pos;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDescripcion;
        private TextView textViewDescripcion2;

        private CommentsViewHolder(final View itemView){
            super(itemView);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewDescripcion2 = itemView.findViewById(R.id.textViewDescripcionClicked);
            textViewDescripcion2.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

    }
}
