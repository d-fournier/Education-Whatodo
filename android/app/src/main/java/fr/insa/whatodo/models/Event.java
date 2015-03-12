package fr.insa.whatodo.models;

import android.graphics.drawable.Drawable;

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

    public Event(Drawable image, Date date, String title, String price, String place, String summary) {
        this.image = image;
        this.date = date;
        this.title = title;
        this.price = price;
        this.place = place;
        this.summary = summary;
    }

    public Drawable getImage() {
        return image;
    }

    public String getDateAsString() {
        return date.toString();
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
