package fr.insa.whatodo.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event {

    protected int id;
    protected String name;
    protected String summary;
    protected String url;
    protected String startTime;
    protected String endTime;
    protected String startDate;
    protected String endDate;
    protected String price;
    protected int minAge;
    protected String address;
    protected List<Category> categories;
    protected List<Tag> tags;
    protected String imageURL;

    protected DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Event(int id, String name, String summary, String url, String startTime, String endTime, String startDate, String endDate, String price, int minAge, String address, List<Tag> tags, List<Category> categories, String imageURL) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.minAge = minAge;
        this.address = address;
        this.tags = tags;
        this.categories = categories;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
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

    public String getSummary() {
        return summary;
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

    public int getMinAge() {
        return minAge;
    }

    public String getAddress() {
        return address;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
