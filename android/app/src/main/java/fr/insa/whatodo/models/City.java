package fr.insa.whatodo.models;

/**
 * Created by Benjamin on 31/03/2015.
 */
public class City {
    protected String name;
    protected String code;

    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
