package fr.insa.whatodo.model;

/**
 * Created by Segolene on 13/03/2015.
 */
public class PriceFilter extends Filter {

    protected float maxPrice;

    public PriceFilter() {
        super(FilterType.PRIX);
        maxPrice=-1;
    }

    public float getValue() {
        return maxPrice;
    }

    public void setValue(float price)
    {
        if(price>=0)
        {
            maxPrice=price;
        }
    }
}
