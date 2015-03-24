package fr.insa.whatodo.utils;

import java.util.List;

import fr.insa.whatodo.models.Event;

/**
 * Created by Benjamin on 24/03/2015.
 */
public interface OnListChangedListener {
    public void onListChanged(List<Event> newList);
}
