package com.example.booksroom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Book.class,
        parentColumns = "id",
        childColumns = "idBook",
        onDelete = ForeignKey.CASCADE), tableName = "book_comments", indices = @Index(name = "idBook_index",value = "idBook"))
public class Comments {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    public int id;

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    @ColumnInfo(name = "idBook")
    public int idBook;

    public Comments(@NonNull String mCommentDesc, @NonNull String bookTitle) {
        this.mCommentDesc = mCommentDesc;
        this.bookTitle = bookTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "desc")
    public String mCommentDesc;

    public String getmCommentDesc() {
        return mCommentDesc;
    }

    public void setmCommentDesc(String mCommentDesc) {
        this.mCommentDesc = mCommentDesc;
    }

    @NonNull
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(@NonNull String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @NonNull @ColumnInfo(name = "book")
    public String bookTitle;


}
