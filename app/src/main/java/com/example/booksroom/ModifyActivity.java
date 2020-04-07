package com.example.booksroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyActivity extends AppCompatActivity {
    @BindView(R.id.textViewTitulo)
    TextView textViewTitulo;
    @BindView(R.id.textViewAutor)
    TextView textViewAutor;
    @BindView(R.id.textViewEditorial)
    TextView textViewEditorial;
    @BindView(R.id.textViewAño)
    TextView textViewAno;
    @BindView(R.id.recyclerViewCiudades)
    RecyclerView recyclerViewCiudades;

//    TextView textViewTitulo,textViewAutor,textViewEditorial,textViewAño;

    Button buttonSave, buttonAddComment, buttonSort, buttonClear;

    CommentsListAdapter commentsListAdapter;
    private CommentsViewModel commentsViewModel;
    String titulo,autor,editorial,año;

    private BookViewModel bookViewModel;
    BookListAdapter bookListAdapter;


    public static final String EXTRA_REPLY_TITULO_M = "com.example.booksroom.REPLY_TITULO_M";
    public static final String EXTRA_REPLY_AUTOR_M = "com.example.booksroom.REPLY_AUTOR_M";
    public static final String EXTRA_REPLY_EDITORIAL_M = "com.example.booksroom.REPLY_EDITORIAL_M";
    public static final String EXTRA_REPLY_AÑO_M = "com.example.booksroom.REPLY_AÑO_M";

    public boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        Intent prevIntent = getIntent();
        titulo = prevIntent.getStringExtra("titulo");
        autor = prevIntent.getStringExtra("autor");
        editorial = prevIntent.getStringExtra("editorial");
        año = prevIntent.getStringExtra("año");
        final int position = prevIntent.getIntExtra("position",0);
        final int bookID = prevIntent.getIntExtra("bookID",0);


        buttonSave = findViewById(R.id.btnSave);
        buttonAddComment = findViewById(R.id.btnAddComment);
        buttonSort = findViewById(R.id.btnSort);
        buttonClear = findViewById(R.id.btnClearComments);

        textViewAno.setText(año);
        textViewEditorial.setText(editorial);
        textViewAutor.setText(autor);
        textViewTitulo.setText(titulo);

       /* if(año.equals("")) textViewAno.setVisibility(View.GONE);
        if(autor.equals("")) textViewAutor.setVisibility(View.GONE);
        if(editorial.equals("")) textViewEditorial.setVisibility(View.GONE);
        if(titulo.equals("")) textViewTitulo.setVisibility(View.GONE);*/


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update de un libro en la BD
                // notifyDataSetChanged (?)
                titulo = textViewTitulo.getText().toString();
                autor = textViewAutor.getText().toString();
                editorial = textViewEditorial.getText().toString();
                año = textViewAno.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY_TITULO_M,titulo);
                replyIntent.putExtra(EXTRA_REPLY_AUTOR_M,autor);
                replyIntent.putExtra(EXTRA_REPLY_EDITORIAL_M,editorial);
                replyIntent.putExtra(EXTRA_REPLY_AÑO_M,año);
                replyIntent.putExtra("position",position);
                int pos = commentsListAdapter.getSelectedPosition();
                String comentarioFavorito="";
                if(pos>=0){
                    comentarioFavorito = commentsListAdapter.getCommentAtPosition(pos).getmCommentDesc();
                }
                replyIntent.putExtra("positionFavorite",pos);
                replyIntent.putExtra("favorito",comentarioFavorito);

                setResult(RESULT_OK,replyIntent);
                finish();

            }
        });
        commentsListAdapter = new CommentsListAdapter(this);
        recyclerViewCiudades.setAdapter(commentsListAdapter);
        recyclerViewCiudades.setLayoutManager(new LinearLayoutManager(this));

        int pos = prevIntent.getIntExtra("posicionComentarioFavorito",0);

        commentsListAdapter.setSelectedPosition(pos);
        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        commentsViewModel.getmAllCommentsByID(bookID).observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(List<Comments> comments) {
                commentsListAdapter.setmComments(comments);
            }
        });

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // añadir un comentario a la lista y a la base de datos
                String descripcion = getRandomDescription();
                Comments comments = new Comments(descripcion, titulo);
                comments.setIdBook(bookID);
                commentsListAdapter.addComment(comments);
                commentsListAdapter.notifyDataSetChanged();
                commentsViewModel.insert(comments);
            }
        });


        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(asc){
                    commentsViewModel.sortByAlphabetic("ASC");
                    commentsListAdapter.sortBy("ASC");
                    asc = false;
                }
                else {
                    commentsViewModel.sortByAlphabetic("DES");
                    commentsListAdapter.sortBy("DES");

                    asc=true;
                }
                commentsListAdapter.notifyDataSetChanged();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsViewModel.deleteAllCommentsByBookID(bookID);
                commentsListAdapter.setSelectedPosition(-1);
//                bookListAdapter.getBookAtPosition(bookID).setComentarioFavorito("");
                commentsListAdapter.deleteAllByBookID(bookID);
                commentsListAdapter.notifyDataSetChanged();
            }
        });

    }

    private String getRandomDescription() {
        String descripcion = "";
        String[] descripciones = {"muy bueno", "gran obra", "increible", "me hizo llorar"};
        descripcion = descripciones[new Random().nextInt(descripciones.length)];

        return descripcion;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_sort){
            if(asc){
                commentsViewModel.sortByAlphabetic("ASC");
                commentsListAdapter.sortBy("ASC");
                asc = false;
            }
            else {
                commentsViewModel.sortByAlphabetic("DES");
                commentsListAdapter.sortBy("DES");

                asc=true;
            }
            commentsListAdapter.notifyDataSetChanged();
        }



        return super.onOptionsItemSelected(item);
    }





}
