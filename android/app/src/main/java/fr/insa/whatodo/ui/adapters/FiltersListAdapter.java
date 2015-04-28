package fr.insa.whatodo.ui.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.location.Location;
import android.location.LocationManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import fr.insa.whatodo.R;
import fr.insa.whatodo.ui.fragments.FiltersFragment;
import fr.insa.whatodo.model.CategoryFilter;
import fr.insa.whatodo.model.DateFilter;
import fr.insa.whatodo.model.Filter;
import fr.insa.whatodo.model.HourFilter;


public class FiltersListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {

    //context variable
    private Context context;
    private FiltersFragment fragment;

    public LayoutInflater inflater;


    private static String[] existingTags={"tag1","atag","tag2"};
    private static String[] existingTowns={"Lyon", "Paris", "St Etienne"};


    public FiltersListAdapter(Context c, FiltersFragment fr) {
        context = c;
        fragment=fr;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        View v=inflater.inflate(android.R.layout.simple_list_item_activated_1,null);
        TextView tv = (TextView)v.findViewById(android.R.id.text1);
        switch(groupPosition)
        {
            case 0 :
                tv.setText(R.string.categories);
                break;
            case 1 :
                tv.setText(R.string.tags);
                break;
            case 2 :
                tv.setText(R.string.place);
                break;
            case 3 :
                tv.setText(R.string.distance);
                break;
            case 4:
                tv.setText(R.string.price);
                break;
            case 5:
                tv.setText(R.string.date);
                break;
            case 6:
                tv.setText(R.string.age);
                break;
            case 7:
                tv.setText(R.string.hours);
                break;
        }

        tv.setPadding(55,15,0,15);
//        tv.setTextColor(Color.WHITE);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (groupPosition)
        {
            case 0:
                convertView=inflater.inflate(R.layout.fragment_category_filter,null);
                ArrayList<CategoryFilter.Category> categories=fragment.getCategoryFilter().getValue();
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
                MultiAutoCompleteTextView tagsView=(MultiAutoCompleteTextView) convertView.findViewById(R.id.TagsTextView);
                ArrayAdapter<String> tagsAdapter = new ArrayAdapter<String>(fragment.getActivity(),android.R.layout.simple_dropdown_item_1line, existingTags);
                tagsView.setAdapter(tagsAdapter);
                tagsView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                tagsView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String[] tagsTab=s.toString().split(",");
                        ArrayList<String> tagsList= new ArrayList<String>(Arrays.asList(tagsTab));
                        fragment.getTagFilter().setValues(tagsList);
                    }
                });
                String tags="";
                for(String tag : fragment.getTagFilter().getValues())
                {
                    tags+=","+tag;
                }
                tags=tags.replaceFirst(",","");
                tagsView.setText(tags);
                break;
            case 2:
                convertView=inflater.inflate(R.layout.fragment_place_filter,null);
                AutoCompleteTextView placeTextView=(AutoCompleteTextView)convertView.findViewById(R.id.PlaceTextField);
                ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(fragment.getActivity(), android.R.layout.simple_dropdown_item_1line, existingTowns);
                placeTextView.setAdapter(placeAdapter);
                ImageButton myLocation= (ImageButton) convertView.findViewById(R.id.imageMyLocation);
                placeTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        fragment.getPlaceFilter().setTown(s.toString());
                    }
                });
                myLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                        try{
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            fragment.getPlaceFilter().setLocation(location.getLongitude(),location.getLatitude());
                        }catch(Exception e){
                            //TODO : message d'erreur
                        }
                    }
                });
                placeTextView.setText(fragment.getPlaceFilter().getTown());
                break;
            case 3:
                convertView=inflater.inflate(R.layout.fragment_distance_filter,null);

                RadioGroup rgDistance=(RadioGroup)convertView.findViewById(R.id.radioGroupDistance);
                rgDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.moinsDe5km :
                                fragment.getDistanceFilter().setDistance(5);
                                break;
                            case R.id.moinsDe30km :
                                fragment.getDistanceFilter().setDistance(30);
                                break;
                            case R.id.moinsDe50km :
                                fragment.getDistanceFilter().setDistance(50);
                                break;
                        }
                    }
                });

                int distance=fragment.getDistanceFilter().getValue();
                if(distance==5)
                {
                    RadioButton rb=(RadioButton) convertView.findViewById(R.id.moinsDe5km);
                    rb.setChecked(true);
                }else if(distance==30)
                {
                    RadioButton rb=(RadioButton) convertView.findViewById(R.id.moinsDe30km);
                    rb.setChecked(true);
                }else
                {
                    RadioButton rb=(RadioButton) convertView.findViewById(R.id.moinsDe50km);
                    rb.setChecked(true);
                }

                break;
            case 4:
                convertView=inflater.inflate(R.layout.fragment_price_filter,null);
                EditText et=(EditText)convertView.findViewById(R.id.MaxPrice);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        fragment.getPriceFilter().setValue(Float.parseFloat(s.toString()));
                    }
                });
                float maxPrice= fragment.getPriceFilter().getValue();
                if(maxPrice >=0)
                {

                    et.setText(Float.toString(maxPrice));
                }
                break;
            case 5:
                convertView=inflater.inflate(R.layout.fragment_date_filter,null);
                Button firstDateButton=(Button)convertView.findViewById(R.id.firstDateText);
                Button lastDateButton=(Button)convertView.findViewById(R.id.lastDateText);
                CheckBox semaine=(CheckBox)convertView.findViewById(R.id.checkBoxSemaine);
                CheckBox weekend=(CheckBox)convertView.findViewById(R.id.checkBoxWeekEnd);

                DateFilter df=fragment.getDateFilter();
                SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                firstDateButton.setText(format.format(df.getDates()[0]));
                lastDateButton.setText(format.format(df.getDates()[1]));
                semaine.setChecked(df.allowWeekDays());
                weekend.setChecked(df.allowWeekEnds());

                semaine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            fragment.getDateFilter().setAllowWeekDays(isChecked);
                    }
                });
                weekend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        fragment.getDateFilter().setAllowWeekends(isChecked);
                    }
                });
                break;
            case 6:
                convertView=inflater.inflate(R.layout.fragment_age_filter,null);

                RadioGroup rg=(RadioGroup)convertView.findViewById(R.id.AgeRadioGroup);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId==R.id.radioMoinsDe18)
                        {
                            fragment.getAgeFilter().setValue(false);
                        }else
                        {
                            fragment.getAgeFilter().setValue(true);
                        }
                    }
                });

                boolean plusDe18=fragment.getAgeFilter().is18orMore();
                if(!plusDe18)
                {
                    RadioButton rb=(RadioButton)convertView.findViewById(R.id.radioMoinsDe18);
                    rb.setChecked(true);
                }else
                {
                    RadioButton rb=(RadioButton)convertView.findViewById(R.id.radioPlusDe18);
                    rb.setChecked(true);
                }
                break;
            case 7:
                convertView=inflater.inflate(R.layout.fragment_hour_filter,null);
                Button firstHourButton=(Button)convertView.findViewById(R.id.firstHourText);
                Button lastHourButton=(Button)convertView.findViewById(R.id.lastHourText);
                HourFilter hf=fragment.getHourFilter();
                firstHourButton.setText(hf.getBeginHours()+" : "+hf.getBeginMinutes());
                lastHourButton.setText(hf.getEndHours()+" : "+hf.getEndMinutes());
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
