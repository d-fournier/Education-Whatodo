package fr.insa.whatodo.ui.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Segolene on 13/03/2015.
 */
public class DateFilter extends Filter {

    protected Date dateMin;
    protected Date dateMax;

    public DateFilter() {
        super(FilterType.DATE);
        dateMin=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(dateMin);
        calendar.add(Calendar.YEAR,1);
        dateMax=calendar.getTime();
    }

    @Override
    public Object getValue() {
        Date[] values={dateMin, dateMax};
        return values;
    }
}
