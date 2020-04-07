package com.example.booksroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewBookActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_TITULO = "com.example.booksroom.REPLY_TITULO";
    public static final String EXTRA_REPLY_AUTOR = "com.example.booksroom.REPLY_AUTOR";
    public static final String EXTRA_REPLY_EDITORIAL = "com.example.booksroom.REPLY_EDITORIAL";
    public static final String EXTRA_REPLY_AÑO = "com.example.booksroom.REPLY_AÑO";
    private EditText editTextBookView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        editTextBookView = findViewById(R.id.edit_book);


        // todo A PARTIR DE AQUI CAMBIAR EL CODIGO PARA BUSCAR EN LA API Y DEVOLVER EL LIBRO
        final Button buttonBuscar = findViewById(R.id.button_buscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(editTextBookView.getText())){
                    setResult(RESULT_CANCELED,replyIntent);
                }
                else {
                    String sFiltro = editTextBookView.getText().toString();
//                    new RecuperarDatos(miLista,adapter.mainActivity).execute(sFiltro);
                    //String book = editTextBookView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_TITULO,sFiltro);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }
}
