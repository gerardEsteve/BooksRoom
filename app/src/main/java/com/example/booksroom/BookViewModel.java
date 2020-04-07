package com.example.booksroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BookRepository mRepository;
    private LiveData<List<Book>> mAllBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();

    }
    public void insert(Book book){
        mRepository.insert(book);
    }

    LiveData<List<Book>> getmAllBooks() {
        return mAllBooks;
    }

    public void deleteBook(Book book){
        mRepository.delete(book);
    }

    public void deleteAllBooks(){
        mRepository.deleteAllBooks();
    }

    public void updateBook(Book b) {
  /*      mAllBooks.getValue().get(position).setmBookTitulo(b.getmBookTitulo());
        mAllBooks.getValue().get(position).setmAño(b.getmAño());
        mAllBooks.getValue().get(position).setmAutor(b.getmAutor());
        mAllBooks.getValue().get(position).setmEditorial(b.getmEditorial());*/
        mRepository.updateBook(b);
    }
}
