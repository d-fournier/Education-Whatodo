package fr.insa.whatodo.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.insa.whatodo.R;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        //Initialiser l'adapter


        ListView eventList = (ListView) rootView.findViewById(R.id.event_list);

        //L'appliquer sur la listView

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
