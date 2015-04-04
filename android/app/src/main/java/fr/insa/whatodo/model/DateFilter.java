package fr.insa.whatodo.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Segolene on 13/03/2015.
 */
public class DateFilter extends Filter {

    protected Date dateMin;
    protected Date dateMax;
    protected boolean semaine;
    protected boolean weekend;

    public DateFilter() {
        super(FilterType.DATE);
        dateMin=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(dateMin);
        calendar.add(Calendar.YEAR,1);
        dateMax=calendar.getTime();
        semaine=true;
        weekend=true;
    }

    public Date[] getDates() {
        Date[] dates={dateMin, dateMax};
        return dates;
    }

    public boolean allowWeekDays()
    {
        return semaine;
    }

    public boolean allowWeekEnds()
    {
        return weekend;
    }

    public void setAllowWeekDays(boolean allow){
        semaine=allow;
    }

    public void setAllowWeekends(boolean allow){
        weekend=allow;
    }

    public void setDateMin(Date d)
    {
        dateMin=d;
    }

    public boolean setDateMax(Date d){
        if(d.after(dateMin)){
            dateMax=d;
            return true;
        }
        return false;
    }
}
