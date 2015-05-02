package fr.insa.whatodo.ui.fragments;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.User;
import fr.insa.whatodo.services.DatabaseServices;
import fr.insa.whatodo.utils.EventDatabaseHelper;
import fr.insa.whatodo.utils.JSonParser.JSonParser;
import fr.insa.whatodo.utils.Touch;
import fr.insa.whatodo.utils.Utils;

/**
 * Created by William on 16/03/2015.
 */
public class ProfileViewFragment extends Fragment implements View.OnClickListener{

    private static final String USER_URL_ME = "http://dfournier.ovh/auth/me?format=json";
    private static final String USER_URL = "http://dfournier.ovh/api/user/";

    protected ImageView imageItem;
    protected TextView textItemName;
    protected TextView textItemMail;
    protected TextView textItemAge;
    protected TextView textItemPlaces;
    protected EventListFragment eventList;
    protected User user;
    protected EventDatabaseHelper mDbHelper;
    protected SQLiteDatabase read_db = null;
    protected ImageLoader imageLoader;
    protected ArrayList<Event> events;

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
        mDbHelper = new EventDatabaseHelper(getActivity());
        read_db = mDbHelper.getReadableDatabase();
        imageLoader = ImageLoader.getInstance();

        eventList = (EventListFragment) getChildFragmentManager().findFragmentById(R.id.profileEventList);
        imageItem = (ImageView) rootView.findViewById(R.id.profile_view_picture);
        imageItem.setOnClickListener(this);
        textItemName = (TextView) rootView.findViewById(R.id.profile_view_name);
        textItemMail = (TextView) rootView.findViewById(R.id.profile_view_mail);
        textItemAge = (TextView) rootView.findViewById(R.id.profile_view_age);
        textItemPlaces = (TextView) rootView.findViewById(R.id.profile_view_favourite_places);

        if (args != null) {
            user = (User) args.getSerializable("user");
        }

        if (user != null) {
            updateUser(user);
        } else {
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
                URL user_url_me = new URL(USER_URL_ME);
                urlConnection = (HttpURLConnection) user_url_me.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "token " + prefs.getString("token", ""));
                InputStream is_user_me = urlConnection.getInputStream();
                JSonParser parser = new JSonParser();
                User u_me = parser.parseUser(is_user_me);

                URL user_url = new URL(USER_URL + u_me.getId());
                urlConnection = (HttpURLConnection) user_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "token " + prefs.getString("token", ""));
                InputStream is_user = urlConnection.getInputStream();
                parser = new JSonParser();
                User u = parser.parseUser(is_user);
                u.setImageUser(u.getImageUrl().replace("/user/"+u.getId()+"/media","/event/media"));

                events = new ArrayList<>();
                for (String eventId : u.getEvents()) {
                    URL event_url = new URL("http://dfournier.ovh/api/event/" + eventId);
                    urlConnection = (HttpURLConnection) event_url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Authorization", "token " + prefs.getString("token", ""));
                    InputStream is_event = urlConnection.getInputStream();
                    parser = new JSonParser();
                    Event e = parser.parseEvent(is_event);
                    e.setImageEvent(e.getImageEvent().replace(e.getId()+"/media","/media"));
                    events.add(e);

                }

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
        if (user == null) {
            imageItem.setImageDrawable(null);
            textItemName.setText(getString(R.string.no_user));
            textItemMail.setText("");
            textItemAge.setText("");
            textItemPlaces.setText("");

        } else {
            imageLoader.displayImage(user.getImageUrl().replace("127.0.0.1:8001", "dfournier.ovh"), imageItem);
            textItemName.setText(user.getName());
            textItemMail.setText(user.getMail());
            textItemAge.setText("" + user.getAge() + " ans");
            String cities = "";
            try {
                for (String city : user.getCities()) {
                    cities += DatabaseServices.findCityById(Integer.parseInt(city), read_db) + ", ";
                }
                cities = cities.substring(0, cities.length() - 2);
            } catch (NullPointerException e) {

            }
            textItemPlaces.setText(cities);
            eventList.onListChanged(events);
        }

    }

    @Override
    public void onClick(View v) {
        Dialog settingsDialog = new Dialog(getActivity());
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ImageView image = new ImageView(getActivity());
        imageLoader.displayImage(user.getImageUrl().replace("127.0.0.1:8001", "dfournier.ovh"), image);
        image.setScaleType(ImageView.ScaleType.MATRIX);
        image.setOnTouchListener(new Touch());
        settingsDialog.setContentView(image);
        settingsDialog.show();
    }
}
