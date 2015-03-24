package fr.insa.whatodo.models;

import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event {

    protected Drawable image;
    protected Date startDate;
    protected Date endDate;
    protected String title;
    protected String price; //TODO A voir
    protected String place;
    protected String summary;

    protected DateFormat df;

    public Event(Drawable image, Date startDate, Date endDate, String title, String price, String place, String summary) {
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.price = price;
        this.place = place;
        this.summary = summary;
        df = new SimpleDateFormat("dd/MM/yyyy");
    }

    public Drawable getImage() {
        return image;
    }

    public String getDateAsString() {
        return endDate == null ? df.format(startDate) : "Du " + df.format(startDate) + " au " + df.format(endDate);
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPlace() {
        return place;
    }

    public String getSummary() {
        return summary;
    }
}
