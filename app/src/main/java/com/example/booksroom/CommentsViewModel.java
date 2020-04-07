package com.example.booksroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {
    private CommentsRepository mRepository;
    private LiveData<List<Comments>> mAllComments;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CommentsRepository(application);
        mAllComments = mRepository.getAllComments();

    }
    public void insert(Comments comments){
        mRepository.insert(comments);
    }

    LiveData<List<Comments>> getmAllComments() {
        return mAllComments;
    }

    LiveData<List<Comments>> getmAllCommentsByID(int bookID) {
        LiveData<List<Comments>> commentsTemp = mRepository.getAllCommentsByID(bookID);
        return commentsTemp;
    }

    public void deleteComment(Comments comments){
        mRepository.delete(comments);
    }

    public void deleteAllComments(){
        mRepository.deleteAllComments();
    }

    public void deleteAllCommentsByBookID(int bookID){
        mRepository.deleteAllCommentsByBookID(bookID);
    }


    public void sortByAlphabetic(String order){
        mRepository.sortAlphabetic(order);
    }


}
