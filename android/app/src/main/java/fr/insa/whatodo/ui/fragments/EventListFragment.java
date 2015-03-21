package fr.insa.whatodo.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.EventAdapter;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventListFragment extends Fragment {

    ArrayList<Event> eventList;
    EventAdapter<Event> adapter;
    ListView eventListView;



    public EventListFragment()
    {
        eventList = new ArrayList<>(); //TODO Il faudra la mettre dans une classe statique !
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        for(int i=1; i<20; i++)
        {
            eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher),new Date(), "Evenement "+i, 10+i+" euros", "Campus", "C'est cool venez"));
        }
        //Initialiser l'adapter
        adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);
        eventListView = (ListView) rootView.findViewById(R.id.event_list);
        //L'appliquer sur la listView
        eventListView.setAdapter(adapter);

        return rootView;
    }

    public void updateListView(ArrayList<Event> listEvent)
    {
        if(listEvent==null)
        {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);
        }else
        {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, listEvent);

        }
        eventListView.setAdapter(adapter);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }
}
