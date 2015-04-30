package fr.insa.whatodo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.Event;

/**
 * Created by Benjamin on 11/03/2015.
 */
public class EventAdapter<T> extends ArrayAdapter {

    ImageLoader imageLoader;

    public EventAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Event event = (Event) getItem(position);
        final ViewHolder vh;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
            vh = new ViewHolder();
            vh.imageItem = (ImageView) convertView.findViewById(R.id.event_list_item_picture);
            vh.textItemTitle = (TextView) convertView.findViewById(R.id.event_list_item_title);
            vh.textItemDate = (TextView) convertView.findViewById(R.id.event_list_item_date);
            vh.textItemPrice = (TextView) convertView.findViewById(R.id.event_list_item_price);
            vh.textItemPlace = (TextView) convertView.findViewById(R.id.event_list_item_place);
            vh.textItemSummary = (TextView) convertView.findViewById(R.id.event_list_item_summary);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(event.getImageEvent().replace("127.0.0.1:8001", "dfournier.ovh"), vh.imageItem);
        vh.textItemTitle.setText(event.getName());
        try {
            vh.textItemDate.setText(event.getDateAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vh.textItemPrice.setText(event.getPrice());
        vh.textItemPlace.setText(event.getFullAddress());
        vh.textItemSummary.setText(event.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }

    static class ViewHolder {
        ImageView imageItem;
        TextView textItemTitle;
        TextView textItemDate;
        TextView textItemPrice;
        TextView textItemPlace;
        TextView textItemSummary;
    }
}

