package fr.insa.whatodo.model;

/**
 * Created by Segolene on 12/03/2015.
 */
public abstract class Filter {

    public enum FilterType {CATEGORIE, TAG, LIEU, DISTANCE, PRIX, DATE,AGE,HORAIRE};
    protected FilterType mType;

    public Filter(FilterType type)
    {
        mType=type;
    }

    public FilterType getType()
    {
        return mType;
    }

    public abstract Object getValue();
}
