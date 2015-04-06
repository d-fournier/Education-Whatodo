package fr.insa.whatodo.models;

/**
 * Created by Benjamin on 31/03/2015.
 */
public class City {
    protected String name;
    protected String ZIPcode;

    public City(String ZIPcode, String name) {
        this.name = name;
        this.ZIPcode = ZIPcode;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return ZIPcode;
    }
}
