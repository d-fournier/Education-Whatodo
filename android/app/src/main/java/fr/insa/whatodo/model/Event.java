package fr.insa.whatodo.model;

import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event {

    protected Drawable image;
    protected Date date;
    protected String title;
    protected String price; //TODO A voir
    protected String place;
    protected String summary;

    protected DateFormat df;

    public Event(Drawable image, Date date, String title, String price, String place, String summary) {
        this.image = image;
        this.date = date;
        this.title = title;
        this.price = price;
        this.place = place;
        this.summary = summary;
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public Drawable getImage() {
        return image;
    }

    public String getDateAsString() {
        return df.format(date);
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
