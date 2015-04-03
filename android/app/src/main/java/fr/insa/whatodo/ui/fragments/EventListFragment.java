package fr.insa.whatodo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.ui.adapters.EventAdapter;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.EventAdapter;
import fr.insa.whatodo.ui.activities.HomeActivity;
import fr.insa.whatodo.utils.OnListChangedListener;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventListFragment extends Fragment implements OnListChangedListener {

    protected ArrayList<Event> eventList;
    protected EventAdapter<Event> adapter;
    protected ListView eventListView;

    public EventListFragment() {
    }

    public static EventListFragment newInstance(ArrayList<Event> list) {
        EventListFragment eventFragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putSerializable("EventList", list);
        eventFragment.setArguments(args);
        return eventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).addOnListChangedListener(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).removeOnListChangedListener(this);
        }
    }

    @Override
    public void onListChanged(List<Event> newList) {
        if (newList == null) {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, eventList);
        } else {
            adapter = new EventAdapter<>(getActivity(), R.layout.event_list_item, newList);
        }
        eventListView.setAdapter(adapter);
    }
}
