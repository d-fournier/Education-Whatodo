package fr.insa.whatodo.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import fr.insa.whatodo.R;
import fr.insa.whatodo.ui.fragments.FiltersFragment;
import fr.insa.whatodo.ui.model.AgeFilter;
import fr.insa.whatodo.ui.model.CategoryFilter;
import fr.insa.whatodo.ui.model.DateFilter;
import fr.insa.whatodo.ui.model.DistanceFilter;
import fr.insa.whatodo.ui.model.Filter;
import fr.insa.whatodo.ui.model.HourFilter;
import fr.insa.whatodo.ui.model.PlaceFilter;
import fr.insa.whatodo.ui.model.PriceFilter;
import fr.insa.whatodo.ui.model.TagFilter;


public class FiltersListAdapter implements ExpandableListAdapter {

    //context variable
    private Context context;
    private FiltersFragment fragment;

    public LayoutInflater inflater;

    //Filtres
    private CategoryFilter categoryFilter;
    private TagFilter tagFilter;
    private PlaceFilter placeFilter;
    private DistanceFilter distanceFilter;
    private PriceFilter priceFilter;
    private DateFilter dateFilter;
    private AgeFilter ageFilter;
    private HourFilter hourFilter;

    public FiltersListAdapter(Activity act, FiltersFragment fr) {
        this.context = act;
        fragment=fr;
        inflater=act.getLayoutInflater();
        categoryFilter=new CategoryFilter();
        tagFilter= new TagFilter();
        placeFilter= new PlaceFilter();
        distanceFilter= new DistanceFilter();
        priceFilter= new PriceFilter();
        dateFilter= new DateFilter();
        ageFilter= new AgeFilter();
        hourFilter=new HourFilter();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return 8;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Filter child=null;
        switch(groupPosition){
            case 0 :
                child=categoryFilter;
                break;
            case 1:
                child=tagFilter;
                break;
            case 2:
                child=placeFilter;
                break;
            case 3:
                child=distanceFilter;
                break;
            case 4:
                child=priceFilter;
                break;
            case 5 :
                child=dateFilter;
                break;
            case 6 :
                child=ageFilter;
                break;
            case 7:
                child=hourFilter;
                break;
        }
        return child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        switch(groupPosition)
        {
            case 0 :
                tv.setText("Catégories");
                break;
            case 1 :
                tv.setText("Tags");
                break;
            case 2 :
                tv.setText("Lieu");
                break;
            case 3 :
                tv.setText("Distance");
                break;
            case 4:
                tv.setText("Prix");
                break;
            case 5:
                tv.setText("Date");
                break;
            case 6:
                tv.setText("Age");
                break;
            case 7:
                tv.setText("Horaires");
                break;
        }
        tv.setPadding(55,15,0,15);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (groupPosition)
        {
            case 0:
                convertView=inflater.inflate(R.layout.fragment_category_filter,null);
                break;
            case 1 :
                convertView=inflater.inflate(R.layout.fragment_tag_filter,null);
                break;
            case 2:
                convertView=inflater.inflate(R.layout.fragment_place_filter,null);
                break;
            case 3:
                convertView=inflater.inflate(R.layout.fragment_distance_filter,null);
                break;
            case 4:
                convertView=inflater.inflate(R.layout.fragment_price_filter,null);
                break;
            case 5:
                convertView=inflater.inflate(R.layout.fragment_date_filter,null);
                ((Button)convertView.findViewById(R.id.firstDateText)).setText(fragment.getFirstDate());
                ((Button)convertView.findViewById(R.id.lastDateText)).setText(fragment.getLastDate());
                break;
            case 6:
                convertView=inflater.inflate(R.layout.fragment_age_filter,null);
                break;
            case 7:
                convertView=inflater.inflate(R.layout.fragment_hour_filter,null);
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

}
