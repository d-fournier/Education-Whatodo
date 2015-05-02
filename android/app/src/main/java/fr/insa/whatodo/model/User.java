package fr.insa.whatodo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William on 16/03/2015.
 */
public class User implements Serializable{

    protected int id;
    protected String imageUrl;
    protected String username;
    protected String email;
    protected ArrayList<String> cities;
    protected int age;

    public User(String name, String mail, ArrayList<String> cities, int age, int id) {
        this.id = id;
        this.username = name;
        this.email = mail;
        this.cities = cities;
        this.age = age;
    }

    public User(String image, String name, String mail, ArrayList<String> cities, int age, int id) {
        this.id = id;
        this.imageUrl = image;
        this.username = name;
        this.email = mail;
        this.cities = cities;
        this.age = age;
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
        return imageUrl;
    }

    public int getId() {
        return id;
    }
}
