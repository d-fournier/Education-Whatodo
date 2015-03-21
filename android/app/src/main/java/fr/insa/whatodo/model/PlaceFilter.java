package fr.insa.whatodo.model;

/**
 * Created by Segolene on 13/03/2015.
 */
public class PlaceFilter  extends Filter {

    protected String place;

    public PlaceFilter() {
        super(FilterType.LIEU);
    }

    @Override
    public Object getValue() {
        return place;
    }
}