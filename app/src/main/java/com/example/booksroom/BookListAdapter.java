package com.example.booksroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private final LayoutInflater mInflater;
    private List<Book> mBooks;
    private Context context;


    BookListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BookListAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent,false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookViewHolder holder, int position) {
        if(mBooks != null ){
            Book currentBook = mBooks.get(position);

            holder.bookItemViewTitulo.setText(currentBook.getmBookTitulo());

            if (currentBook.getmAutor()!=null){
                if(currentBook.getmAutor().equals("")){
                    holder.bookItemViewAutor.setVisibility(View.GONE);
                }
                else {
                    holder.bookItemViewAutor.setVisibility(View.VISIBLE);
                    holder.bookItemViewAutor.setText(currentBook.getmAutor());
                }
            }

            if (currentBook.getmEditorial()!=null){
                if(currentBook.getmEditorial().equals("")){
                    holder.bookItemViewEditorial.setVisibility(View.GONE);
                }
                else {
                    holder.bookItemViewEditorial.setVisibility(View.VISIBLE);
                    holder.bookItemViewEditorial.setText(currentBook.getmEditorial());
                }
            }

            if (currentBook.getmAño()!=null){
                if(currentBook.getmAño().equals("")){
                    holder.bookItemViewAño.setVisibility(View.GONE);
                }
                else {
                    holder.bookItemViewAño.setVisibility(View.VISIBLE);
                    holder.bookItemViewAño.setText(currentBook.getmAño());
                }
            }

            if(currentBook.getComentarioFavorito()!=null){
                if(currentBook.getComentarioFavorito().equals("")){
                    holder.bookItemViewComment.setVisibility(View.GONE);
                }
                else {

                    holder.bookItemViewComment.setVisibility(View.VISIBLE);
                    holder.bookItemViewComment.setText("Top Comment : " + currentBook.getComentarioFavorito());
                    holder.bookItemViewComment.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                }
            }



        }
        else {
            holder.bookItemViewTitulo.setText(" no  elements ");
        }
    }

    @Override
    public int getItemCount() {
        if(mBooks != null ) return mBooks.size();
        else return 0;
    }

    void setmBooks(List<Book> books){
        mBooks = books;
        notifyDataSetChanged();
    }

    void addBook(Book book){
        mBooks.add(book);
    }


    public Book getBookAtPosition(int position){
        return mBooks.get(position);
    }

    public boolean hasBook(Book bookTemp) {
        boolean b = false;
        for (int i = 0; i< mBooks.size();i++){
            Book book = mBooks.get(i);
            b = compareBooks(book,bookTemp);
            if(b) return true;
        }
        return false;

//        return mBooks.contains(bookTemp);
    }

    private boolean compareBooks(Book book, Book bookTemp) {
        if(book.getmBookTitulo().equals(bookTemp.getmBookTitulo())){
            if(book.getmAutor().equals(bookTemp.getmAutor())){
                if(book.getmEditorial().equals(bookTemp.getmEditorial())){
                    if(book.getmAño().equals(bookTemp.getmAño())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void updateBookAtPosition(int position, Book bookTemp) {
        mBooks.get(position).setmAutor(bookTemp.getmAutor());
        mBooks.get(position).setmEditorial(bookTemp.getmEditorial());
        mBooks.get(position).setmAño(bookTemp.getmAño());
        mBooks.get(position).setmBookTitulo(bookTemp.getmBookTitulo());

        notifyDataSetChanged();

    }


    class BookViewHolder extends RecyclerView.ViewHolder {

        private final TextView bookItemViewTitulo;
        private final TextView bookItemViewAutor;
        private final TextView bookItemViewEditorial;
        private final TextView bookItemViewAño;
        private final TextView bookItemViewComment;

        private BookViewHolder(View itemView){
            super(itemView);
            bookItemViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            bookItemViewAutor = itemView.findViewById(R.id.textViewAutor);
            bookItemViewEditorial = itemView.findViewById(R.id.textViewEditorial);
            bookItemViewAño = itemView.findViewById(R.id.textViewAño);
            bookItemViewComment = itemView.findViewById(R.id.textViewFavorito);
            bookItemViewComment.setVisibility(View.GONE);
        }

    }

}
