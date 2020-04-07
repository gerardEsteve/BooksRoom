package com.example.booksroom;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    // URL a la que llamaremos
    private static final String BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?";
    // Parametro para la busqueda en al URL
    private static final String URL_PARAM = "q";
    // Parametro para limitar la busqueda a n elementos
    private static final String MAX_RESULTS = "maxResults";
    // Parametro para filtrar el tipo de impresion
    private static final String PRINT_TYPE = "printType";

    static String getData(String query){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJSONString = null;

        try{
            Uri miUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(URL_PARAM, query)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(miUri.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect(); // La connexion

            // REcogemos los datos que nos deveulve la conezion
            InputStream iStream = urlConnection.getInputStream();
            // Crear un elemento que podamos leer, BufferedReader
            reader = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sBuilder = new StringBuilder();
            String linia;
            while ((linia = reader.readLine()) != null){
                sBuilder.append(linia);
                sBuilder.append("\n");
            }
            if (sBuilder.length() == 0)
                return null;
            dataJSONString = sBuilder.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return dataJSONString;
    }
}
