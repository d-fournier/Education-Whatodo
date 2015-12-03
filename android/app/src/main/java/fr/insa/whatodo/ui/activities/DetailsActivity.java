package fr.insa.whatodo.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Category;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.Tag;
import fr.insa.whatodo.model.User;
import fr.insa.whatodo.utils.JSonParser.JSonParser;
import fr.insa.whatodo.utils.MultipartUtility;

/**
 * Created by Benjamin on 03/04/2015.
 */

public class DetailsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String USER_URL_ME = "http://dfournier.ovh/auth/me?format=json";
    private static final String USER_URL = "http://dfournier.ovh/api/user/";

    ImageLoader imageLoader;
    Event event;
    Button buttonFavs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = ImageLoader.getInstance();

        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_home));
        }

        setContentView(R.layout.event_details);
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("event");

        buttonFavs = (Button) findViewById(R.id.button_fav);
        buttonFavs.setOnClickListener(this);
        ImageView image = (ImageView) findViewById(R.id.image_details);
        TextView title = (TextView) findViewById(R.id.title_details);
        TextView date = (TextView) findViewById(R.id.date_details);
        TextView place = (TextView) findViewById(R.id.place_details);
        TextView time = (TextView) findViewById(R.id.time_details);
        TextView summary = (TextView) findViewById(R.id.summary_details);
        TextView categories = (TextView) findViewById(R.id.categories_details);
        TextView creator = (TextView) findViewById(R.id.creator_details);
        TextView price = (TextView) findViewById(R.id.price_details);
        TextView url = (TextView) findViewById(R.id.url_details);
        TextView agemin = (TextView) findViewById(R.id.agemin_details);
        TextView tags = (TextView) findViewById(R.id.tags_details);

        imageLoader.displayImage(event.getImageEvent().replace("127.0.0.1:8001", "dfournier.ovh"), image);

        title.setText(event.getName());
        try {
            date.setText("Date : " + event.getDateAsString());
            time.setText("Heure : " + event.getTimeAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        place.setText("Lieu : " + event.getFullAddress());
        summary.setText("Description : " + event.getDescription());
        price.setText("Prix : " + event.getPrice().replace(".",",")+" €");
        url.setText("Site : " + event.getUrl());
        agemin.setText("Age Minimum : " + event.getMinAge());

        String sCategories = "";
        for (Category c : event.getCategories()) {
            sCategories += c.getName() + ", ";
        }
        try {
            sCategories = sCategories.substring(0, sCategories.lastIndexOf(","));
        } catch (StringIndexOutOfBoundsException e) {
            sCategories = "Aucune";
        }

        String sTags = "";
        for (Tag t : event.getTags()) {
            sTags += t.getName() + ", ";
        }
        try {
            sTags = sTags.substring(0, sTags.lastIndexOf(","));
        } catch (StringIndexOutOfBoundsException e) {
            sTags = "Aucun";
        }

        categories.setText("Catégories : " + sCategories);
        tags.setText("Tags : " + sTags);

    }

    @Override
    public void onClick(View v) {
        AsyncTask<Void, Void, List<String>> task = new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected void onPostExecute(List<String> response) {
                super.onPostExecute(response);
                if(response == null)
                {
                    Toast.makeText(DetailsActivity.this, getResources().getString(R.string.event_no_added), Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(DetailsActivity.this, getResources().getString(R.string.event_added), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected List<String> doInBackground(Void... params) {
                HttpURLConnection urlConnection;
                //J'envoie la requete au serveur
                try {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);
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

                    MultipartUtility multipart = new MultipartUtility(USER_URL+u.getId()+"/", "UTF-8", "token "+prefs.getString("token",""), "PUT");
                    for(String id : u.getEvents())
                    {
                        multipart.addFormField("events", id);
                    }

                    multipart.addFormField("events", ""+event.getId());
                    multipart.addFormField("username", ""+u.getName());
                    multipart.addFormField("email", ""+u.getMail());

                    for(String id : u.getCategories())
                    {
                        multipart.addFormField("categories", id);
                    }

                    for(String id : u.getCities())
                    {
                        multipart.addFormField("cities", id);
                    }

                    List<String> response = multipart.finish();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();

    }
}
