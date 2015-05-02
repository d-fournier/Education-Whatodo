package fr.insa.whatodo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 16/03/2015.
 */
public class User implements Serializable{

    protected int id;
    protected String imageUser;
    protected String username;
    protected String email;
    protected ArrayList<String> cities;
    protected ArrayList<String> categories;
    protected ArrayList<String> events;
    protected int age;


    public User(String image, String name, String mail, ArrayList<String> cities, int age, int id, ArrayList<String> events, ArrayList<String> categories) {
        this.id = id;
        this.imageUser = image;
        this.username = name;
        this.email = mail;
        this.cities = cities;
        this.age = age;
        this.events = events;
        this.categories = categories;
    }

    public String getName() {
        return username;
    }

    public String getMail() {
        return email;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public int getAge() {
        return age;
    }

    public String getImageUrl() {
        return imageUser;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }
}
