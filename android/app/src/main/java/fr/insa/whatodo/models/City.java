package fr.insa.whatodo.models;

import java.io.Serializable;

/**
 * Created by Benjamin on 31/03/2015.
 */
public class City implements Serializable{
    protected String ZIPcode;
    protected String name;

    public City(String name, String code) {
        this.name = name;
        this.ZIPcode = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return ZIPcode;
    }
}
