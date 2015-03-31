package fr.insa.whatodo.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event {

    private static SimpleDateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat df_time = new SimpleDateFormat("HH-mm-ss");
    protected int id;
    protected String name;
    protected String summary;
    protected String url;
    protected Date startTime;
    protected Date endTime;
    protected Date startDate;
    protected Date endDate;
    protected String price;
    protected int minAge;
    protected String address;
    protected City city;
    protected List<Category> categories;
    protected List<Tag> tags;
    protected String imageURL;

    public Event(int id, String name, String summary, String url, String startTime, String endTime, String startDate, String endDate, String price, int minAge, String address, City city, List<Tag> tags, List<Category> categories, String imageURL) throws ParseException {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.url = url;
        this.startTime = df_time.parse(startTime);
        this.endTime = df_time.parse(endTime);
        this.startDate = df_date.parse(startDate);
        this.endDate = df_date.parse(endDate);
        this.price = price;
        this.minAge = minAge;
        this.address = address;
        this.city = city;
        this.tags = tags;
        this.categories = categories;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDateAsString() {
        return endDate == null ? df_date.format(startDate) : "Du " + df_date.format(startDate) + " au " + df_date.format(endDate);
    }
    public String getTimeAsString() {
        return endDate == null ? df_time.format(startTime) : "De " + df_time.format(startTime) + " à " + df_time.format(endTime);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
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

    public String getFullAddress() {
        return address+" "+city.getCode()+" "+city.getName();
    }

    public City getCity() {
        return city;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
