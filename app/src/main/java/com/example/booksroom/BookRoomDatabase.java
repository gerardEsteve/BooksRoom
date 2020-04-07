package com.example.booksroom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Book.class,Comments.class}, version = 7, exportSchema = false)
public abstract class BookRoomDatabase extends RoomDatabase {

    public abstract BookDAO bookDao();
    private static BookRoomDatabase INSTANCE;

    public abstract CommentsDAO commentsDAO();

    public static BookRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (BookRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookRoomDatabase.class, "book_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
            return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private final BookDAO mDao;
        private final CommentsDAO mDaoComments;
        PopulateDbAsync(BookRoomDatabase db){
            mDao = db.bookDao();
            mDaoComments = db.commentsDAO();
        }

        @Override
        protected Void doInBackground(final Void... params){
//            mDao.deleteAll();
            LiveData<List<Book>> myList = mDao.getAllBooks();

            if(myList!=null && myList.getValue()!=null){

                int size = myList.getValue().size();
                for(int i = 0;i<=size-1;i++){
                    Book book = myList.getValue().get(i);
                    mDao.insert(book);
                }
            }


            LiveData<List<Comments>> myCommentsList = mDaoComments.getAllComments();

            if(myCommentsList != null && myCommentsList.getValue()!=null){

                int size = myCommentsList.getValue().size();
                for (int i = 0;i < size; i++){
                    Comments comment = myCommentsList.getValue().get(i);
                    mDaoComments.insert(comment);
                }
            }

            return null;
        }
    }

}
