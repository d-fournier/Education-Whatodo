package fr.insa.whatodo.model;

/**
 * Created by Segolene on 12/03/2015.
 */
public class DistanceFilter extends Filter {

    protected int maxDistance;

    public DistanceFilter() {

        super(FilterType.DISTANCE);
        maxDistance=50;
    }

    public int getValue() {
        return maxDistance;
    }

    public void setDistance(int dist){
        if(dist==5 || dist==30 || dist==50)
        {
            maxDistance=dist;
        }
    }
}
