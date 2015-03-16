package fr.insa.whatodo.ui.model;

/**
 * Created by Segolene on 12/03/2015.
 */
public class DistanceFilter extends Filter {

    protected int maxDistance;

    public DistanceFilter() {

        super(FilterType.DISTANCE);
        maxDistance=30;
    }

    @Override
    public Object getValue() {
        return maxDistance;
    }
}
