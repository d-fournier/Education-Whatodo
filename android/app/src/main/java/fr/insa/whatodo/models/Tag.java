package fr.insa.whatodo.models;

import java.io.Serializable;

/**
 * Created by Benjamin on 26/03/2015.
 */
public class Tag implements Serializable{
    int id;
    String name;

    public Tag(int id, String name) {
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
