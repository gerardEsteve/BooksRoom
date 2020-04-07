package com.example.booksroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Comments comments);

    @Query("DELETE FROM book_comments")
    void deleteAll();

    @Query("DELETE FROM book_comments WHERE idBook = :bookID "  )
    void deleteAllByBookId(int bookID);

    //book es una primary key ?
    @Query("SELECT * FROM book_comments ORDER BY book ASC")
    LiveData<List<Comments>> getAllComments();

    @Query("SELECT * FROM book_comments WHERE idBook= :bookID ORDER BY book ASC")
    LiveData<List<Comments>> getAllCommentsByID(int bookID);

    @Delete
    void deleteComment(Comments comments);

    @Query("SELECT * FROM book_comments ORDER BY book ASC")
    LiveData<List<Comments>>  getAllCommentsAsc();

    @Query("SELECT * FROM book_comments ORDER BY book DESC")
    LiveData<List<Comments>>  getAllCommentsDesc();

}
