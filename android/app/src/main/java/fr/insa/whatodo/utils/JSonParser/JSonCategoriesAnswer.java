package fr.insa.whatodo.utils.JSonParser;

import java.util.ArrayList;

import fr.insa.whatodo.model.Category;

/**
 * Created by William on 29/03/2015.
 */
public class JSonCategoriesAnswer {

    protected int count;
    protected String next;
    protected String previous;
    protected ArrayList<Category> results;

    public int getCount() {
        return count;
    }

    public ArrayList<Category> getResults() {
        return results;
    }
}
