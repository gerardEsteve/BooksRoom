package com.example.booksroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Query("DELETE FROM book_table")
    void deleteAll();

    //book es una primary key ?
    @Query("SELECT * FROM book_table ORDER BY book ASC")
    LiveData<List<Book>> getAllBooks();

    @Delete
    void deleteBook(Book book);

    @Update
    void updateBook(Book book);
}
