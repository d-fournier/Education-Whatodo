package fr.insa.whatodo.model;

import java.util.ArrayList;

/**
 * Created by Segolene on 13/03/2015.
 */
public class TagFilter extends Filter {

    protected ArrayList<String> tags;

    public TagFilter() {
        super(FilterType.TAG);
        tags=new ArrayList<String>();
    }

    public ArrayList<String> getValues() {
        return tags;
    }

    public void setValues(ArrayList<String> tagsList){
        tags=tagsList;
    }
}