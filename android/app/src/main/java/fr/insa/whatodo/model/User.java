package fr.insa.whatodo.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William on 16/03/2015.
 */
public class User implements Serializable{

    protected Drawable image;
    protected String name;
    protected String password;
    protected String mail;
    protected ArrayList<String> cities;
    protected int age;

    public User(String name, String password, String mail, ArrayList<String> cities, int age) {
        //this.image = defaultImage; //For users without image
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.cities = cities;
        this.age = age;
    }

    public User(Drawable image, String name, String password, String mail, ArrayList<String> cities, int age) {
        this.image = image;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.cities = cities;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public int getAge() {
        return age;
    }

    public Drawable getImage() {
        return image;
    }
}
