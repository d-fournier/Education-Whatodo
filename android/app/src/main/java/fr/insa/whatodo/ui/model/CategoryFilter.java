package fr.insa.whatodo.ui.model;

import java.util.ArrayList;

/**
 * Created by Segolene on 13/03/2015.
 */
public class CategoryFilter extends Filter {

    public enum Category {CONCERT, CONFERENCE, DEBAT, EXPOSITION, MATCHSPORTIF, PROJECTIONVIDEO, RENCONTRE, SOIREE, TABLERONDE};

    protected ArrayList<Category> categories;

    public CategoryFilter() {
        super(FilterType.CATEGORIE);
        categories=new ArrayList<Category>();
    }

    @Override
    public Object getValue() {
        return categories;
    }
}
