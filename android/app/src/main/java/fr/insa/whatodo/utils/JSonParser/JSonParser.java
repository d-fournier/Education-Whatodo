package fr.insa.whatodo.utils.JSonParser;



import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import fr.insa.whatodo.model.Category;
import fr.insa.whatodo.model.City;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.Tag;
import fr.insa.whatodo.model.User;

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

    public ArrayList<Tag> parseTags(InputStream source) {


        Gson gson = new Gson();

        Reader reader = new InputStreamReader(source);

        JSonTagsAnswer response = gson.fromJson(reader, JSonTagsAnswer.class);

        System.out.println("Il y a "+response.getCount()+" tags !");

        return response.getResults();

    }

    public User parseUser(InputStream source){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(source);
        return gson.fromJson(reader, User.class);

    }


}
