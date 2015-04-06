package fr.insa.whatodo.utils.JSonParser;

import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.models.City;

/**
 * Created by William on 29/03/2015.
 */
public class JSonCitiesAnswer {

    protected int count;
    protected String next;
    protected String previous;
    protected ArrayList<City> results;

    public int getCount() {
        return count;
    }

    public ArrayList<City> getResults() {
        return results;
    }
}
