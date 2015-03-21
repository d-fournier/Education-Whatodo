package fr.insa.whatodo.model;

/**
 * Created by Segolene on 13/03/2015.
 */
public class PriceFilter extends Filter {

    protected int maxPrice;

    public PriceFilter() {
        super(FilterType.PRIX);
        maxPrice=0;
    }

    @Override
    public Object getValue() {
        return maxPrice;
    }
}
