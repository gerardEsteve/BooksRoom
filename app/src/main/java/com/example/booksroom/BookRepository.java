package com.example.booksroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDAO bookDAO;
    private static LiveData<List<Book>> allBooks;

    BookRepository(Application application){
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        bookDAO = db.bookDao();
        allBooks = bookDAO.getAllBooks();
    }

    LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    public void insert(Book book){
        new insertAsyncTask(bookDAO).execute(book);
    }

    private static class insertAsyncTask extends AsyncTask<Book, Void , Void>{
        private BookDAO mAsyncTaskDao;
        insertAsyncTask(BookDAO dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Book... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateBook(Book b) {
        new updateAsyncTask(bookDAO).execute(b);
    }

    private static class updateAsyncTask extends AsyncTask<Book,Void,Void>{

        private BookDAO mAsyncTaskDao;
        updateAsyncTask(BookDAO bookDAO){
            mAsyncTaskDao = bookDAO;
        }
        @Override
        protected Void doInBackground(Book... books) {
            mAsyncTaskDao.updateBook(books[0]);
            return null;
        }
    }


    public void delete(Book book){
        new deleteAsyncTask(bookDAO).execute(book);
    }

    private static class deleteAsyncTask extends AsyncTask<Book,Void,Void>{

        private BookDAO mAsyncTaskDao;
        deleteAsyncTask(BookDAO bookDAO){
            mAsyncTaskDao = bookDAO;
        }
        @Override
        protected Void doInBackground(Book... books) {
            mAsyncTaskDao.deleteBook(books[0]);
            return null;
        }
    }


    public void deleteAllBooks(){
        new deleteAllAsyncTask(bookDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private BookDAO mAsyncTaskDao;
        deleteAllAsyncTask(BookDAO bookDAO){
            mAsyncTaskDao = bookDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
