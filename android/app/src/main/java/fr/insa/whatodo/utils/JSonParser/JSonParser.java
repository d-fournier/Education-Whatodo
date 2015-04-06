package fr.insa.whatodo.utils.JSonParser;



import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import fr.insa.whatodo.models.Category;
import fr.insa.whatodo.models.Event;

/**
 * Created by William on 29/03/2015.
 */
public class JSonParser{

    public JSonParser() {
    }

    public ArrayList<Event> parseEvents(InputStream source) {


        Gson gson = new Gson();

        Reader reader = new InputStreamReader(source);

        JSonEventsAnswer response = gson.fromJson(reader, JSonEventsAnswer.class);

        System.out.println("Il y a "+response.getCount()+" évènements !");

        return response.getResults();

    }

    public ArrayList<Category> parseCategories(InputStream source) {


        Gson gson = new Gson();

        Reader reader = new InputStreamReader(source);

        JSonCategoriesAnswer response = gson.fromJson(reader, JSonCategoriesAnswer.class);

        System.out.println("Il y a "+response.getCount()+" catégories !");

        return response.getResults();

    }

}
