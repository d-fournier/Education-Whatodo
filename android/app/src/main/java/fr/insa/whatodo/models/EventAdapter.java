package fr.insa.whatodo.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import fr.insa.whatodo.R;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventAdapter<T> extends ArrayAdapter {



    public EventAdapter(Context context, int resource, List<T> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Event event = (Event) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }
        // Lookup view for data population
        TextView textNoImage = (TextView) convertView.findViewById(R.id.event_list_item_no_image);
        ImageView imageItem = (ImageView) convertView.findViewById(R.id.event_list_item_picture);
        TextView textItemTitle = (TextView) convertView.findViewById(R.id.event_list_item_title);
        TextView textItemDate = (TextView) convertView.findViewById(R.id.event_list_item_date);
        TextView textItemPrice = (TextView) convertView.findViewById(R.id.event_list_item_price);
        TextView textItemPlace = (TextView) convertView.findViewById(R.id.event_list_item_place);
        TextView textItemSummary = (TextView) convertView.findViewById(R.id.event_list_item_summary);

        // Populate the data into the template view using the data object
       /*  if(event.getImage() == null) {
            imageItem.setVisibility(View.GONE);
        }else{
            textNoImage.setVisibility(View.GONE);
        }
            imageItem.setImageDrawable(event.getImage());*/
            imageItem.setVisibility(View.GONE);// TODO A MODIFIER AVEC IMAGELOADER !
            textItemTitle.setText(event.getName());
        try {
            textItemDate.setText(event.getDateAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textItemPrice.setText(event.getPrice());
            textItemPlace.setText(event.getFullAddress());
            textItemSummary.setText(event.getSummary());



        // Return the completed view to render on screen
        return convertView;
    }
}

