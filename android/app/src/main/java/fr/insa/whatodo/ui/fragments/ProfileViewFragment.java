package fr.insa.whatodo.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.User;

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
//        args.putString("name", user.getName());
//        args.putString("password", user.getPassword());
//        args.putString("mail", user.getMail());
//        args.putStringArrayList("cities", user.getCities());
//        args.putInt("age", user.getAge());
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

        User user = (User)args.getSerializable("user");

        imageItem.setImageDrawable(user.getImage());
        textItemName.setText(user.getName());
        textItemMail.setText(user.getMail());
        textItemAge.setText(""+user.getAge());
        textItemPlaces.setText(user.getCities().get(0));
        textItemInterests.setText("Bla bla bla interests");


        return rootView;
    }


}
