package fr.insa.whatodo.utils.JSonParser;

import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.models.Tag;

/**
 * Created by William on 29/03/2015.
 */
public class JSonTagsAnswer {

    protected int count;
    protected String next;
    protected String previous;
    protected ArrayList<Tag> results;

    public int getCount() {
        return count;
    }

    public ArrayList<Tag> getResults() {
        return results;
    }
}
