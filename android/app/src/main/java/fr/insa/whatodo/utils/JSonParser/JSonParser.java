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

    public JSonParser() {
    }

    public ArrayList<Event> readJsonStream(InputStream source) {


        Gson gson = new Gson();

        Reader reader = new InputStreamReader(source);

        JSonEventsAnswer response = gson.fromJson(reader, JSonEventsAnswer.class);

        System.out.println("Il y a "+response.getCount()+" évènements !");

        return response.getResults();

    }

}
