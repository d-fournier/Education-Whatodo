package fr.insa.whatodo.models;

/**
 * Created by William on 29/03/2015.
 */
public class Address {
    private String localisation;

    public Address(String localisation) {
        this.localisation = localisation;
    }

    public String getLocalisation() {
        return localisation;
    }
}
