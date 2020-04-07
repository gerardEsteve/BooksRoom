package com.example.booksroom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull @ColumnInfo(name = "book")
    public String mBookTitulo;

    @ColumnInfo(name = "autor")
    public String mAutor;

    @ColumnInfo(name = "editorial")
    public String mEditorial;

    @ColumnInfo(name = "año")
    public String mAño;

    @ColumnInfo(name = "comentario")
    public String comentarioFavorito;

    public int getPositionComentarioFavorito() {
        return positionComentarioFavorito;
    }

    public void setPositionComentarioFavorito(int positionComentarioFavorito) {
        this.positionComentarioFavorito = positionComentarioFavorito;
    }

    @ColumnInfo(name = "posComentarioFavorito")
    public int positionComentarioFavorito;


    public Book(@NonNull String book ){
        this.mBookTitulo = book;
    }

    public Book(){}

    public String getmBookTitulo() {
        return mBookTitulo;
    }

    public void setmBookTitulo(String mBook) {
        this.mBookTitulo = mBook;
    }

    public String getmAutor() {
        return mAutor;
    }

    public void setmAutor(String mAutor) {
        this.mAutor = mAutor;
    }

    public String getmEditorial() {
        return mEditorial;
    }

    public void setmEditorial(String mEditorial) {
        this.mEditorial = mEditorial;
    }

    public String getmAño() {
        return mAño;
    }

    public void setmAño(String mAño) {
        this.mAño = mAño;
    }

    public void setComentarioFavorito(String comentarioFavorito){this.comentarioFavorito = comentarioFavorito;}

    public String getComentarioFavorito(){return comentarioFavorito;}
}
