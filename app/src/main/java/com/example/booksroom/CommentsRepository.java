package com.example.booksroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CommentsRepository {
    private CommentsDAO commentsDAO;
    private static LiveData<List<Comments>> allComments;

    CommentsRepository(Application application){
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        commentsDAO = db.commentsDAO();
        allComments = commentsDAO.getAllComments();
    }

    LiveData<List<Comments>> getAllComments(){
        return allComments;
    }

    LiveData<List<Comments>> getAllCommentsByID(int bookID){
        return commentsDAO.getAllCommentsByID(bookID);
    }

    public void insert(Comments comments){
        new insertAsyncTask(commentsDAO).execute(comments);
    }

    private static class insertAsyncTask extends AsyncTask<Comments, Void , Void>{
        private CommentsDAO mAsyncTaskDao;
        insertAsyncTask(CommentsDAO dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Comments... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Comments comments){
        new deleteAsyncTask(commentsDAO).execute(comments);
    }

    private static class deleteAsyncTask extends AsyncTask<Comments,Void,Void>{

        private CommentsDAO mAsyncTaskDao;
        deleteAsyncTask(CommentsDAO commentsDAO){
            mAsyncTaskDao = commentsDAO;
        }
        @Override
        protected Void doInBackground(Comments... comments) {
            mAsyncTaskDao.deleteComment(comments[0]);
            return null;
        }
    }


    public void deleteAllComments(){
        new deleteAllAsyncTask(commentsDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private CommentsDAO mAsyncTaskDao;
        deleteAllAsyncTask(CommentsDAO commentsDAO){
            mAsyncTaskDao = commentsDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    public void deleteAllCommentsByBookID(int bookID){
        new deleteAllByBookIDAsyncTask(commentsDAO).execute(bookID);
    }

    private static class deleteAllByBookIDAsyncTask extends AsyncTask<Integer,Void,Void>{

        private CommentsDAO mAsyncTaskDao;
        deleteAllByBookIDAsyncTask(CommentsDAO commentsDAO){
            mAsyncTaskDao = commentsDAO;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteAllByBookId(integers[0]);
            return null;
        }
    }



    public void sortAlphabetic(String order){
        new sortAlphabeticAsyncTask(commentsDAO).execute(order);
    }
    //PETA no se porque -> funciones anteriores del sort alphabetical cambiar el livedata por void
    private static class sortAlphabeticAsyncTask extends AsyncTask<String,Void,Void> {

        private CommentsDAO mAsyncTaskDao;
        sortAlphabeticAsyncTask(CommentsDAO commentsDao){
            mAsyncTaskDao = commentsDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String order = strings[0];
            if(order.equals("ASC")) mAsyncTaskDao.getAllCommentsAsc();
            else if (order.equals("DESC"))mAsyncTaskDao.getAllCommentsDesc();
            return null;
        }
    }


}
