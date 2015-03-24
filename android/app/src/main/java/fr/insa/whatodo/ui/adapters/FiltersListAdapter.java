package fr.insa.whatodo.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.insa.whatodo.R;
import fr.insa.whatodo.ui.fragments.FiltersFragment;
import fr.insa.whatodo.model.AgeFilter;
import fr.insa.whatodo.model.CategoryFilter;
import fr.insa.whatodo.model.DateFilter;
import fr.insa.whatodo.model.DistanceFilter;
import fr.insa.whatodo.model.Filter;
import fr.insa.whatodo.model.HourFilter;
import fr.insa.whatodo.model.PlaceFilter;
import fr.insa.whatodo.model.PriceFilter;
import fr.insa.whatodo.model.TagFilter;


public class FiltersListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {

    //context variable
    private Context context;
    private FiltersFragment fragment;

    public LayoutInflater inflater;


    public FiltersListAdapter(Activity act, FiltersFragment fr) {
        this.context = act;
        fragment=fr;
        inflater=act.getLayoutInflater();
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
                child= fragment.getCategoryFilter();
                break;
            case 1:
                child=fragment.getTagFilter();
                break;
            case 2:
                child=fragment.getPlaceFilter();
                break;
            case 3:
                child=fragment.getDistanceFilter();
                break;
            case 4:
                child=fragment.getPriceFilter();
                break;
            case 5 :
                child=fragment.getDateFilter();
                break;
            case 6 :
                child=fragment.getAgeFilter();
                break;
            case 7:
                child=fragment.getHourFilter();
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
                ArrayList<CategoryFilter.Category> categories=(ArrayList<CategoryFilter.Category>)fragment.getCategoryFilter().getValue();
                for(int i=0; i<categories.size(); i++)
                {
                    switch(categories.get(i))
                    {
                        case SPECTACLE :
                            CheckBox cb1=(CheckBox)convertView.findViewById(R.id.checkBoxSpectacle);
                            cb1.setChecked(true);
                            break;
                        case CONCERT :
                            CheckBox cb2=(CheckBox)convertView.findViewById(R.id.checkBoxConcert);
                            cb2.setChecked(true);
                            break;
                        case THEATRE :
                            CheckBox cb3=(CheckBox)convertView.findViewById(R.id.checkBoxTheatre);
                            cb3.setChecked(true);
                            break;
                        case CONFERENCE :
                            CheckBox cb4=(CheckBox)convertView.findViewById(R.id.checkBoxConference);
                            cb4.setChecked(true);
                            break;
                        case DEBAT :
                            CheckBox cb5=(CheckBox)convertView.findViewById(R.id.checkBoxDebat);
                            cb5.setChecked(true);
                            break;
                        case EXPOSITION :
                            CheckBox cb6=(CheckBox)convertView.findViewById(R.id.checkBoxExposition);
                            cb6.setChecked(true);
                            break;
                        case SOIREE :
                            CheckBox cb7=(CheckBox)convertView.findViewById(R.id.checkBoxSoiree);
                            cb7.setChecked(true);
                            break;
                        case SPORT :
                            CheckBox cb8=(CheckBox)convertView.findViewById(R.id.checkBoxSport);
                            cb8.setChecked(true);
                            break;
                        case PROJECTIONVIDEO :
                            CheckBox cb9=(CheckBox)convertView.findViewById(R.id.checkBoxProjection);
                            cb9.setChecked(true);
                            break;
                    }
                }
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
//                notifyDataSetChanged();
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
