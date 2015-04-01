package fr.insa.whatodo.model;

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


    public int getBeginHours() {
        return beginHours;
    }

    public int getBeginMinutes() {
        return beginMinutes;
    }

    public int getEndHours() {
        return endHours;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setBeginHours(int beginHours, int beginMinutes) {

        this.beginHours = beginHours;
        this.beginMinutes = beginMinutes;
    }



    public boolean setEndHours(int endHours, int endMinutes) {
        if(endHours>beginHours || (endHours==beginHours && endMinutes >beginMinutes)){
            this.endHours = endHours;
            return true;
        }
        return false;
    }


}
