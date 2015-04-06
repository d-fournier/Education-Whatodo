package fr.insa.whatodo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Category;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.Tag;

/**
 * Created by Benjamin on 03/04/2015.
 */
public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        intent.removeExtra("event");

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

        //TODO image avec ImageLoader
        title.setText(event.getName());
        try {
            date.setText("Date : " + event.getDateAsString());
            time.setText("Heure : " + event.getTimeAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        place.setText("Lieu : " + event.getAddress());
        summary.setText("Description : " + event.getDescription());
        price.setText("Prix : " + event.getPrice());
        url.setText("Site : " + event.getUrl());
        agemin.setText("Age Minimum : " + event.getMinAge());

        String sCategories = "";
        for(Category c : event.getCategories())
        {
            sCategories += c.getName()+ ", ";
        }
        sCategories = sCategories.substring(0, sCategories.lastIndexOf(","));

        String sTags = "";
        for(Tag t : event.getTags())
        {
            sTags += t.getName()+ ", ";
        }
        sTags = sTags.substring(0,sTags.lastIndexOf(","));

        categories.setText("Catégories : " + sCategories);
        tags.setText("Tags : " + sTags);

    }
}
