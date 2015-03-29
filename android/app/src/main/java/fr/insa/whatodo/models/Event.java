package fr.insa.whatodo.models;

import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event {

    protected int id;
    protected String name;
    protected String description;
    protected String url;
    protected String startTime;
    protected String endTime;
    protected String startDate;
    protected String endDate;
    protected String price;
    protected int min_age;
    protected List<Address> addresses;
    protected List<Category> categories;
    protected List<Tag> tags;
    protected Drawable image;

    protected DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Event(int id, String name, String description, String url, String startTime, String endTime, String startDate, String endDate, String price, int min_age, List<Address> addresses, List<Tag> tags, List<Category> categories, Drawable image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.min_age = min_age;
        this.addresses = addresses;
        this.tags = tags;
        this.categories = categories;
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public String getDateAsString() {
        return endDate == null ? df.format(startDate) : "Du " + df.format(startDate) + " au " + df.format(endDate);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPrice() {
        return price;
    }

    public int getMin_age() {
        return min_age;
    }

    public List<Address> getAddress() {
        return addresses;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
