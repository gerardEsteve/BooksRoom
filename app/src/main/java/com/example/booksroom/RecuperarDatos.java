package com.example.booksroom;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecuperarDatos extends AsyncTask<String, Void, String> {
/*    private WeakReference<TextView> wrTitulo;
    private WeakReference<TextView> wrAutor;
    private WeakReference<TextView> wrEditorial;
    private WeakReference<TextView> wrAño;*/
    private WeakReference<BookViewModel> wrBVM;
    private WeakReference<BookListAdapter> wrBLA;


    private WeakReference<MainActivity> weakActivity;


    RecuperarDatos(/*TextView tvTitulo, TextView tvAutor, TextView tvEditorial, TextView tvAño*/BookListAdapter bookListAdapter, BookViewModel bookViewModel){
        /*this.wrTitulo = new WeakReference<>(tvTitulo);
        this.wrAutor = new WeakReference<>(tvAutor);
        this.wrEditorial = new WeakReference<>(tvEditorial);
        this.wrAño = new WeakReference<>(tvAño);*/
        this.wrBLA = new WeakReference<>(bookListAdapter);
        this.wrBVM = new WeakReference<>(bookViewModel);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getData(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i= 0;
            String titulo = null;
            String autor = null;
            String editorial = null;
            String año = null;
            while(i < itemsArray.length()
                    && (titulo == null && autor == null)){
                JSONObject libro = itemsArray.getJSONObject(i);
                JSONObject info = libro.getJSONObject("volumeInfo");
                try{
                    titulo = info.getString("title");
                    autor = info.getString("authors");
                    editorial = info.getString("publisher");
                    año = info.getString("publishedDate");

                    //comparar el libro con los de la BD
                    // si ya lo tenemos buscar el siguiente
                    // poner los campos a null
                    Book bookTemp = setInfo(titulo,autor,editorial,año);
                    boolean bTemp = wrBLA.get().hasBook(bookTemp);
//                    LiveData<List<Book>> tempBooks =  wrBVM.get().getmAllBooks();
                    if(/*tempBooks.getValue().contains(bookTemp)*/bTemp){
                        titulo = null;
                        autor = null;
                        editorial = null;
                        año = null;
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }

            Book book = setInfo(titulo,autor,editorial,año);
            wrBLA.get().addBook(book);
            wrBVM.get().insert(book);

        } catch (JSONException e){

            e.printStackTrace();
        }
    }
    private Book setInfo(String titulo, String autor, String editorial, String año) {
        Book finalBook = new Book();
        if(titulo!=null){
            finalBook.setmBookTitulo(titulo);
        }
        if(autor!=null){
            finalBook.setmAutor(autor);
        }
        if(editorial!=null){
            finalBook.setmEditorial(editorial);
        }
        if(año!=null){
            finalBook.setmAño(año);
        }
        return finalBook;
    }
}
