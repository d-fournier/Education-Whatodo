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

    @Override
    public Object getValue() {
        return tags;
    }
}