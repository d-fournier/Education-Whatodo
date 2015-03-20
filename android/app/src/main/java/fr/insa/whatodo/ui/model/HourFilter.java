package fr.insa.whatodo.ui.model;

/**
 * Created by Segolene on 16/03/2015.
 */
public class HourFilter extends Filter {

    protected int beginHours;
    protected int beginMinutes;
    protected int endHours;
    protected int endMinutes;

    public HourFilter() {
        super(FilterType.AGE);
        beginHours=0;
        beginMinutes=0;
        endHours=23;
        endMinutes=59;
    }

    @Override
    public Object getValue() {
        int[] values={beginHours, beginMinutes, endHours, endMinutes};
        return values;
    }
}
