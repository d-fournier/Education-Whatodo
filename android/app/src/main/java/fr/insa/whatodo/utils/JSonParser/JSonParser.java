package fr.insa.whatodo.utils.JSonParser;

import android.app.Activity;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Event;

/**
 * Created by William on 29/03/2015.
 */
public class JSonParser{
    String url = "http://dfournier.ovh/api/event/?format=json";

    public JSonParser() {
    }

    public ArrayList<Event> readJsonStream() {

//        InputStream source = retrieveStream(url);

        Gson gson = new Gson();

//        Reader reader = new InputStreamReader(source);

        String test = "{\"count\":2,\"next\":null,\"previous\":null,\"results\":[{\"id\":1,\"name\":\"24H\",\"description\":\"Concert trop swag\",\"url\":\"http://127.0.0.1/\",\"startTime\":\"00:00:00\",\"endTime\":\"23:59:00\",\"startDate\":\"2015-01-01\",\"endDate\":\"2015-12-31\",\"price\":\"15.00\",\"min_age\":15,\"addresses\":[{\"localisation\":\"INSA\"}],\"categories\":[{\"id\":9,\"name\":\"TEST\"}],\"tags\":[{\"id\":1,\"name\":\"INSA\"},{\"id\":2,\"name\":\"Musique\"}]},{\"id\":2,\"name\":\"Apero\",\"description\":\"On boit, on est bien\",\"url\":\"http://127.0.0.1/\",\"startTime\":\"00:00:00\",\"endTime\":\"23:59:00\",\"startDate\":\"2015-01-01\",\"endDate\":\"2015-12-31\",\"price\":\"15.00\",\"min_age\":15,\"addresses\":[{\"localisation\":\"Chez Quentin\"}],\"categories\":[{\"id\":10,\"name\":\"Apero\"}],\"tags\":[{\"id\":3,\"name\":\"Apero\"},{\"id\":4,\"name\":\"Boisson\"}]}]}";

        JSonEventsAnswer response = gson.fromJson(test, JSonEventsAnswer.class);

        System.out.println("Il y a "+response.getCount()+" évènements !");

        return response.getResults();

//        System.out.println("L'évenement : " + response.getAddress());

//        Toast.makeText(this, response.query, Toast.LENGTH_SHORT).show();

//        List<Result> results = response.results;
//
//        for (Result result : results) {
//            Toast.makeText(this, result.fromUser, Toast.LENGTH_SHORT).show();
//        }

    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        }
        catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }

        return null;

    }
}
