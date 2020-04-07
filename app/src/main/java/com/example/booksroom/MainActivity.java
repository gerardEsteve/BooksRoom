package com.example.booksroom;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    public static final int BOOK_MODIFY_REQUEST_CODE = 2;
    private BookViewModel bookViewModel;
    BookListAdapter bookListAdapter;
    TextView textViewTitulo;
    TextView textViewAutor;
    TextView textViewEditorial;
    TextView textViewAño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewAutor = findViewById(R.id.textViewAutor);
        textViewEditorial = findViewById(R.id.textViewEditorial);
        textViewAño = findViewById(R.id.textViewAño);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //TODO do something
                Intent intent = new Intent(MainActivity.this, NewBookActivity.class);
                startActivityForResult(intent,NEW_BOOK_ACTIVITY_REQUEST_CODE);
            }
        });



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        bookListAdapter = new BookListAdapter(this);
        recyclerView.setAdapter(bookListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getmAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                bookListAdapter.setmBooks(books);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                // aqui no entra porque dragDirs es 0
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){
                    int position = viewHolder.getAdapterPosition();
                    Book book = bookListAdapter.getBookAtPosition(position);
                    int bookID = book.getId();
                    Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
                    intent.putExtra("autor",book.getmAutor());
                    intent.putExtra("titulo",book.getmBookTitulo());
                    intent.putExtra("editorial",book.getmEditorial());
                    intent.putExtra("año",book.getmAño());
                    intent.putExtra("position",position);
                    intent.putExtra("bookID",bookID);
                    intent.putExtra("posicionComentarioFavorito",book.getPositionComentarioFavorito());

                    startActivityForResult(intent,BOOK_MODIFY_REQUEST_CODE);
                }
                if(direction == ItemTouchHelper.LEFT){
                    final int position = viewHolder.getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Deseas eliminar el Libro ?");
                    builder.setMessage("Eliminar el libro seleccionado ? Esta acción no se puede deshacer.");
                    builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Book book = bookListAdapter.getBookAtPosition(position);
                            Toast.makeText(getApplicationContext(),"estamos borrando",Toast.LENGTH_LONG).show();
                            bookViewModel.deleteBook(book);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bookListAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    builder.show();


                }

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {
        //TODO aqui
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String sFiltro = data.getStringExtra(NewBookActivity.EXTRA_REPLY_TITULO);
            //todo buscar en la api el sFiltro

              new RecuperarDatos(/*textViewTitulo,textViewAutor,textViewEditorial,textViewAño*/bookListAdapter,bookViewModel).execute(sFiltro);

            /*
            book.setmAutor(data.getStringExtra(NewBookActivity.EXTRA_REPLY_AUTOR));
            book.setmEditorial(data.getStringExtra(NewBookActivity.EXTRA_REPLY_EDITORIAL));
            book.setmAño(data.getStringExtra(NewBookActivity.EXTRA_REPLY_AÑO));*/
            // todo aqui hay que recibir el libro que hemos obtenido de la api
//            bookViewModel.insert(book);

        }
        else if (requestCode == BOOK_MODIFY_REQUEST_CODE && resultCode == RESULT_OK){

            String titulo = data.getStringExtra(ModifyActivity.EXTRA_REPLY_TITULO_M);
            String autor = data.getStringExtra(ModifyActivity.EXTRA_REPLY_AUTOR_M);
            String editorial = data.getStringExtra(ModifyActivity.EXTRA_REPLY_EDITORIAL_M);
            String año = data.getStringExtra(ModifyActivity.EXTRA_REPLY_AÑO_M);
            int position = data.getIntExtra("position",0);

            String comentarioFavorito = data.getStringExtra("favorito");
            int posFav = data.getIntExtra("positionFavorite",0);

            Book b = bookListAdapter.getBookAtPosition(position);
            b.setmBookTitulo(titulo);
            b.setmAño(año);
            b.setmEditorial(editorial);
            b.setmAutor(autor);
            b.setComentarioFavorito(comentarioFavorito);
            b.setPositionComentarioFavorito(posFav);
            bookListAdapter.updateBookAtPosition(position,b);
            bookViewModel.updateBook(b);

        }
        else {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
