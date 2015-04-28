package fr.insa.whatodo.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.User;

/**
 * Created by William on 16/03/2015.
 */
public class ProfileViewFragment extends Fragment {


    public static ProfileViewFragment newInstance(User user) {
        /*
        * Replace the usual constructor
        */
        ProfileViewFragment f = new ProfileViewFragment();
        //Bundle allows you to
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        f.setArguments(args);


        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_view, container, false);
        Bundle args = this.getArguments();
        ImageView imageItem = (ImageView) rootView.findViewById(R.id.profile_view_picture);
        TextView textItemName = (TextView) rootView.findViewById(R.id.profile_view_name);
        TextView textItemMail = (TextView) rootView.findViewById(R.id.profile_view_mail);
        TextView textItemAge = (TextView) rootView.findViewById(R.id.profile_view_age);
        TextView textItemPlaces = (TextView) rootView.findViewById(R.id.profile_view_favourite_places);
        TextView textItemInterests = (TextView) rootView.findViewById(R.id.profile_view_interests);

        User user = (User) args.getSerializable("user");
        imageItem.setImageDrawable(user.getImage());
        textItemName.setText(user.getName());
        textItemMail.setText(user.getMail());
        textItemAge.setText("" + user.getAge());
        String cities = "";
        try {
            for (String city : user.getCities()) {
                cities += city + ", ";
            }
            cities = cities.substring(0, cities.length() - 2);
        } catch (NullPointerException e) {

        }
        textItemPlaces.setText(cities);
        textItemInterests.setText("Bla bla bla interests");


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int i = menu.size();
        inflater.inflate(R.menu.profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
