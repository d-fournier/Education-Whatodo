package fr.insa.whatodo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.EventAdapter;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventListFragment extends Fragment {

    ArrayList<Event> eventList;
    EventAdapter<Event> adapter;
    ListView eventListView;


    public EventListFragment() {
    }

    public static EventListFragment newInstance(ArrayList<Event> list) {
        EventListFragment eventFragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putSerializable("EventList", list);
        eventFragment.setArguments(args);
        return eventFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        eventList = (ArrayList<Event>) getArguments().getSerializable("EventList");

        //Initialiser l'adapter
        adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);
        eventListView = (ListView) rootView.findViewById(R.id.event_list);
        //L'appliquer sur la listView
        eventListView.setAdapter(adapter);

        return rootView;
    }

    public void updateListView(ArrayList<Event> listEvent) {
        if (listEvent == null) {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);
        } else {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, listEvent);

        }
        eventListView.setAdapter(adapter);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }
}
