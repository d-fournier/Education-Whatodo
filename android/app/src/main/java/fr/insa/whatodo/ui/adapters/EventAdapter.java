package fr.insa.whatodo.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
            vh.textNoImage = (TextView) convertView.findViewById(R.id.event_list_item_no_image);
            vh. imageItem = (ImageView) convertView.findViewById(R.id.event_list_item_picture);
            vh. progressBar = (ProgressBar) convertView.findViewById(R.id.download_progress_bar);
            vh. textItemTitle = (TextView) convertView.findViewById(R.id.event_list_item_title);
            vh. textItemDate = (TextView) convertView.findViewById(R.id.event_list_item_date);
            vh. textItemPrice = (TextView) convertView.findViewById(R.id.event_list_item_price);
            vh. textItemPlace = (TextView) convertView.findViewById(R.id.event_list_item_place);
            vh. textItemSummary = (TextView) convertView.findViewById(R.id.event_list_item_summary);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (event.getImageEvent() == null) {
            vh.imageItem.setVisibility(View.GONE);
            vh.textNoImage.setVisibility(View.VISIBLE);
            vh.progressBar.setVisibility(View.GONE);
        }else {
            imageLoader.displayImage(event.getImageEvent().replace("127.0.0.1:8001", "dfournier.ovh"),  vh.imageItem, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    vh.imageItem.setVisibility(View.GONE);
                    vh.textNoImage.setVisibility(View.GONE);
                    vh. progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    vh.imageItem.setVisibility(View.GONE);
                    vh.textNoImage.setVisibility(View.VISIBLE);
                    vh.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    vh.imageItem.setVisibility(View.VISIBLE);
                    vh.textNoImage.setVisibility(View.GONE);
                    vh. progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
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
        TextView  textNoImage;
        ImageView imageItem ;
        ProgressBar progressBar ;
        TextView textItemTitle;
        TextView textItemDate;
        TextView textItemPrice;
        TextView textItemPlace;
        TextView textItemSummary;
    }
}

