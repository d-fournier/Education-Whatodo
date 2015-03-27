package fr.insa.whatodo.utils;

import java.util.ArrayList;

import fr.insa.whatodo.models.Event;

/**
 * Created by Benjamin on 12/03/2015.
 */
public class  Search {

    public static ArrayList<Event> searchByTitle(ArrayList<Event> listEvent, String titleToSearch)
    {
        ArrayList<Event> returnList = new ArrayList<>();

        for(Event e : listEvent)
        {
            if(e.getName().toLowerCase().contains(titleToSearch.toLowerCase()))
            {
                returnList.add(e);
            }
        }
        if(returnList.size()==0 && titleToSearch.equals(""))
        {
            return null;
        }else
        {
            return returnList;
        }

    }
}
