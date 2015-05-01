package fr.insa.whatodo.ui.fragments;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.User;
import fr.insa.whatodo.ui.activities.HomeActivity;
import fr.insa.whatodo.utils.JSonParser.JSonParser;
import fr.insa.whatodo.utils.Utils;

/**
 * Created by William on 16/03/2015.
 */
public class ProfileViewFragment extends Fragment {

    private static final String USER_URL = "http://dfournier.ovh/auth/me?format=json";

    protected ImageView imageItem;
    protected TextView textItemName;
    protected TextView textItemMail;
    protected TextView textItemAge;
    protected TextView textItemPlaces;
    protected TextView textItemInterests;
    protected User user;

/*    public static ProfileViewFragment newInstance(User user) {
        ProfileViewFragment f = new ProfileViewFragment();
        //Bundle allows you to
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        f.setArguments(args);
        return f;
    }
*/
    public static ProfileViewFragment newInstance() {

        ProfileViewFragment f = new ProfileViewFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_view, container, false);
        Bundle args = this.getArguments();
        imageItem = (ImageView) rootView.findViewById(R.id.profile_view_picture);
        textItemName = (TextView) rootView.findViewById(R.id.profile_view_name);
        textItemMail = (TextView) rootView.findViewById(R.id.profile_view_mail);
        textItemAge = (TextView) rootView.findViewById(R.id.profile_view_age);
        textItemPlaces = (TextView) rootView.findViewById(R.id.profile_view_favourite_places);
        textItemInterests = (TextView) rootView.findViewById(R.id.profile_view_interests);

        if(args != null)
        {
            user = (User) args.getSerializable("user");
        }

        if(user != null)
        {
            updateUser(user);
        }else{
            if (Utils.checkConnectivity(getActivity())) {
                task.execute();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                return rootView;
            }
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
        @Override
        protected void onPostExecute(User u) {
            super.onPostExecute(u);
            updateUser(u);
        }

        @Override
        protected User doInBackground(Void... params) {
            HttpURLConnection urlConnection;
            //J'envoie la requete au serveur
            try {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                URL user_url = new URL(USER_URL);
                urlConnection = (HttpURLConnection) user_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "token " + prefs.getString("token", ""));
                InputStream is_user = urlConnection.getInputStream();
                JSonParser parser = new JSonParser();
                User u = parser.parseUser(is_user);
                urlConnection.disconnect();
                return u;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    protected void updateUser(User user) {
        this.user = user;
        if(user == null)
        {
            imageItem.setImageDrawable(null);
            textItemName.setText(getString(R.string.no_user));
            textItemMail.setText("");
            textItemAge.setText("");
            textItemPlaces.setText("");
            textItemInterests.setText("");

        }else{
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
        }

    }

}
