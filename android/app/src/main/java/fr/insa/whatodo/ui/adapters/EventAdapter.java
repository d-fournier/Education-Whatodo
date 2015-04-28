package fr.insa.whatodo.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
        if(!imageLoader.isInited())
        {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
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
        final TextView  textNoImage = (TextView) convertView.findViewById(R.id.event_list_item_no_image);
        final ImageView imageItem = (ImageView) convertView.findViewById(R.id.event_list_item_picture);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.download_progress_bar);
        TextView textItemTitle = (TextView) convertView.findViewById(R.id.event_list_item_title);
        TextView textItemDate = (TextView) convertView.findViewById(R.id.event_list_item_date);
        TextView textItemPrice = (TextView) convertView.findViewById(R.id.event_list_item_price);
        TextView textItemPlace = (TextView) convertView.findViewById(R.id.event_list_item_place);
        TextView textItemSummary = (TextView) convertView.findViewById(R.id.event_list_item_summary);

        if (event.getImageEvent() == null) {
            imageItem.setVisibility(View.GONE);
            textNoImage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            imageLoader.displayImage(event.getImageEvent(), imageItem, null, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    imageItem.setVisibility(View.GONE);
                    textNoImage.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    imageItem.setVisibility(View.GONE);
                    textNoImage.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageItem.setVisibility(View.VISIBLE);
                    textNoImage.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
        textItemTitle.setText(event.getName());
        try {
            textItemDate.setText(event.getDateAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textItemPrice.setText(event.getPrice());
        textItemPlace.setText(event.getFullAddress());
        textItemSummary.setText(event.getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}

