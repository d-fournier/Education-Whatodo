package fr.insa.whatodo.model;

/**
 * Created by Segolene on 16/03/2015.
 */
public class AgeFilter extends Filter {

    protected boolean plusDe18;

    public AgeFilter() {
        super(FilterType.AGE);
        plusDe18=true;
    }

    @Override
    public Object getValue() {
        return plusDe18;
    }
}
