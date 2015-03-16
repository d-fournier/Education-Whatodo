package fr.insa.whatodo.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.insa.whatodo.R;

/**
 * Created by Benjamin on 16/03/2015.
 */
public class MapFragment extends Fragment {

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }
}
