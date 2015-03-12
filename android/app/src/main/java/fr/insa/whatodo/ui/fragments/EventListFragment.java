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
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.EventAdapter;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventListFragment extends Fragment {

    public EventListFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        ArrayList<Event> eventList = new ArrayList<>(); //TODO Il faudra la mettre dans une classe statique !

      for(int i=1; i<20; i++)
      {
          eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher),new Date(), "Evenement "+i, 10+i+" euros", "Campus", "C'est cool venez"));
      }

        //Initialiser l'adapter
        EventAdapter<Event> adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);


        ListView eventListView = (ListView) rootView.findViewById(R.id.event_list);

        //L'appliquer sur la listView
        eventListView.setAdapter(adapter);

        return rootView;
    }
}
