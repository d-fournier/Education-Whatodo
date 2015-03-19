package fr.insa.whatodo.ui.fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Event;

/**
 * Created by Benjamin on 16/03/2015.
 */
public class CustomMapFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment s_mapFragment;
    ArrayList<Event> listEvent;

    public CustomMapFragment() {
    }

    public static CustomMapFragment newInstance(ArrayList<Event> list) {
        CustomMapFragment c_mapFragment = new CustomMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("EventList", list);
        c_mapFragment.setArguments(args);
        return c_mapFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        s_mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment));
        if (s_mapFragment == null) {
            s_mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map_container, s_mapFragment).commit();
        }
        s_mapFragment.getMapAsync(this);
        listEvent = (ArrayList<Event>) getArguments().getSerializable("EventList");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        putPinsOnMap(listEvent, googleMap);
    }

    public void putPinsOnMap(ArrayList<Event> l, GoogleMap map) {
        for (Event e : l) {
            map.addMarker(new MarkerOptions()
                    .position(getEventCoordinates(e))
                    .title(e.getTitle()));
        }
    }

    public LatLng getEventCoordinates(Event e) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        Address location;
        LatLng lat_lng = null;
        try {
            address = coder.getFromLocationName(e.getPlace(), 2);
            location = address.get(0);
            lat_lng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return lat_lng;
    }
}
