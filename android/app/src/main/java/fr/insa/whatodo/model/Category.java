package fr.insa.whatodo.model;

import java.io.Serializable;

/**
 * Created by Benjamin on 26/03/2015.
 */
public class Category implements Serializable{
    int id;
    String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
