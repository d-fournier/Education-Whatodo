package fr.insa.whatodo.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class Event implements Serializable {

    private static SimpleDateFormat df_date_parse = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat df_date_format = new SimpleDateFormat("dd MMMM yyyy");
    private static SimpleDateFormat df_time_format = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat df_time_parse = new SimpleDateFormat("HH:mm:ss");
    protected int id;
    protected String name;
    protected String description;
    protected String url;
    protected String startTime;
    protected String endTime;
    protected String startDate;
    protected String endDate;
    protected String price;
    protected int minAge;
    protected String address;
    protected City city;
    protected List<Category> categories;
    protected List<Tag> tags;
    protected String imageEvent;

    public Event(int id, String name, String description, String url, String startTime, String endTime, String startDate, String endDate, String price, int minAge, String address, City city, List<Tag> tags, List<Category> categories, String imageEvent) throws ParseException {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.minAge = minAge;
        this.address = address;
        this.city = city;
        this.tags = tags;
        this.categories = categories;
        this.imageEvent = imageEvent;
    }

    public String getImageEvent() {
        return imageEvent;
    }

    public String getDateAsString() throws ParseException {
        return endDate.equals("") ? df_date_format.format(df_date_parse.parse(startDate)) :
                "Du " + df_date_format.format(df_date_parse.parse(startDate)) + " au " + df_date_format.format(df_date_parse.parse(endDate));
    }

    public String getTimeAsString() throws ParseException {
        return endTime.equals("") ? df_time_format.format(df_time_parse.parse(startTime)) :
                "De " + df_time_format.format(df_time_parse.parse(startTime)) + " à " + df_time_format.format(df_time_parse.parse(endTime));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() throws ParseException {
        return df_time_parse.parse(startTime);

    }

    public Date getEndTime() throws ParseException {
        return df_time_parse.parse(endTime);
    }

    public Date getStartDate() throws ParseException {
        return df_date_parse.parse(startDate);
    }

    public Date getEndDate() throws ParseException {
        return df_date_parse.parse(endDate);

    }

    public String getDescription() {
        return description;
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
        return address + " " + city.getCode() + " " + city.getName();
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

    public void setImageEvent(String imageEvent) {
        this.imageEvent = imageEvent;
    }
}
