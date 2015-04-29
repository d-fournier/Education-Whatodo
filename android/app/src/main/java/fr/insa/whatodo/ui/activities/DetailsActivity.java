package fr.insa.whatodo.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Category;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.Tag;

/**
 * Created by Benjamin on 03/04/2015.
 */
public class DetailsActivity extends ActionBarActivity {

    ImageLoader imageLoader;

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
        Event event = (Event) intent.getSerializableExtra("event");

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

        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("bitmap"), 0, getIntent().getByteArrayExtra("bitmap").length);
            image.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

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
}
