package fr.insa.whatodo.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.AgeFilter;
import fr.insa.whatodo.model.CategoryFilter;
import fr.insa.whatodo.model.DateFilter;
import fr.insa.whatodo.model.DistanceFilter;
import fr.insa.whatodo.model.HourFilter;
import fr.insa.whatodo.model.PlaceFilter;
import fr.insa.whatodo.model.PriceFilter;
import fr.insa.whatodo.model.TagFilter;
import fr.insa.whatodo.ui.WhatodoDrawerToggle;
import fr.insa.whatodo.ui.activities.HomeActivity;
import fr.insa.whatodo.ui.adapters.FiltersListAdapter;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class FiltersFragment extends Fragment implements View.OnClickListener {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private WhatodoDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;
    private FiltersListAdapter mFiltersListAdapter;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;

    //Filtres
    private CategoryFilter categoryFilter;
    private TagFilter tagFilter;
    private PlaceFilter placeFilter;
    private DistanceFilter distanceFilter;
    private PriceFilter priceFilter;
    private DateFilter dateFilter;
    private AgeFilter ageFilter;
    private HourFilter hourFilter;


    public FiltersFragment() {
        categoryFilter=new CategoryFilter();
        tagFilter= new TagFilter();
        placeFilter= new PlaceFilter();
        distanceFilter= new DistanceFilter();
        priceFilter= new PriceFilter();
        dateFilter= new DateFilter();
        ageFilter= new AgeFilter();
        hourFilter=new HourFilter();
    }

    public CategoryFilter getCategoryFilter() {
        return categoryFilter;
    }

    public TagFilter getTagFilter() {
        return tagFilter;
    }

    public PlaceFilter getPlaceFilter() {
        return placeFilter;
    }

    public DistanceFilter getDistanceFilter() {
        return distanceFilter;
    }

    public PriceFilter getPriceFilter() {
        return priceFilter;
    }

    public DateFilter getDateFilter() {
        return dateFilter;
    }

    public AgeFilter getAgeFilter() {
        return ageFilter;
    }

    public HourFilter getHourFilter() {
        return hourFilter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ExpandableListView) inflater.inflate(
                R.layout.fragment_filters, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mFiltersListAdapter=new FiltersListAdapter((HomeActivity)getActivity(), this);
        mDrawerListView.setAdapter(mFiltersListAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);


        return mDrawerListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle=new WhatodoDrawerToggle(
                (HomeActivity)getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.filters_open,  /* "open drawer" description for accessibility */
                R.string.filters_drawer_close  /* "close drawer" description for accessibility */
        );

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_filters, menu);

        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private DatePickerDialog.OnDateSetListener firstDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            GregorianCalendar gc=new GregorianCalendar(selectedYear,selectedMonth,selectedDay);
            dateFilter.setDateMin(gc.getTime());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Button firstDateButton = (Button) getActivity().findViewById(R.id.firstDateText);
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    firstDateButton.setText(format.format(dateFilter.getDates()[0]));
                }
            });
        }
    };

    private DatePickerDialog.OnDateSetListener lastDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            GregorianCalendar gc=new GregorianCalendar(selectedYear,selectedMonth,selectedDay);
            if(!dateFilter.setDateMax(gc.getTime()))
            {
                Toast.makeText(getActivity(), getResources().getString(R.string.max_date_before_min_date), Toast.LENGTH_SHORT).show();
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Button lastDateButton = (Button) getActivity().findViewById(R.id.lastDateText);
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    lastDateButton.setText(format.format(dateFilter.getDates()[1]));
                }
            });
        }
    };

    private TimePickerDialog.OnTimeSetListener firstTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourFilter.setBeginHours(hourOfDay, minute);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Button firstHourButton=(Button)getActivity().findViewById(R.id.firstHourText);
                    firstHourButton.setText(hourFilter.getBeginHours()+" : "+hourFilter.getBeginMinutes());
                }
            });
        }
    };

    private TimePickerDialog.OnTimeSetListener lastTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(!hourFilter.setEndHours(hourOfDay, minute)){
                Toast.makeText(getActivity(), getResources().getString(R.string.max_hour_before_min_hour), Toast.LENGTH_SHORT).show();
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Button lastHourButton=(Button)getActivity().findViewById(R.id.lastHourText);
                    lastHourButton.setText(hourFilter.getEndHours()+" : "+hourFilter.getEndMinutes());
                }
            });
        }
    };

    @Override
    public void onClick(View v) {

    }

    public void onDateButtonClicked(View v)
    {
        GregorianCalendar gc=new GregorianCalendar();
        if(v.getId()==R.id.firstDateText)
        {
            gc.setTime(dateFilter.getDates()[0]);
            DatePickerDialog d= new DatePickerDialog(this.getActivity(), firstDatePickerListener, gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
            d.show();
        }else if(v.getId()==R.id.lastDateText)
        {
            gc.setTime(dateFilter.getDates()[1]);
            DatePickerDialog d= new DatePickerDialog(this.getActivity(), lastDatePickerListener, gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
            d.show();
        }
    }

    public void onHourButtonClicked(View v){
        if(v.getId()==R.id.firstHourText)
        {
            TimePickerDialog d=new TimePickerDialog(getActivity(),firstTimePickerListener,hourFilter.getBeginHours(), hourFilter.getBeginMinutes(), true);
            d.show();
        }else if(v.getId()==R.id.lastHourText)
        {
            TimePickerDialog d=new TimePickerDialog(this.getActivity(), lastTimePickerListener, hourFilter.getEndHours(), hourFilter.getEndMinutes(), true);
            d.show();
        }
    }

    public void onCheckBoxClicked(View v)
    {
        CheckBox c = (CheckBox) v;
        if(c.isChecked())
        {
            switch (v.getId())
            {
                case R.id.checkBoxSpectacle :
                    categoryFilter.addCategory(CategoryFilter.Category.SPECTACLE);
                    break;
                case R.id.checkBoxConcert :
                    categoryFilter.addCategory(CategoryFilter.Category.CONCERT);
                    break;
                case R.id.checkBoxTheatre :
                    categoryFilter.addCategory(CategoryFilter.Category.THEATRE);
                    break;
                case R.id.checkBoxConference :
                    categoryFilter.addCategory(CategoryFilter.Category.CONFERENCE);
                    break;
                case R.id.checkBoxDebat :
                    categoryFilter.addCategory(CategoryFilter.Category.DEBAT);
                    break;
                case R.id.checkBoxExposition :
                    categoryFilter.addCategory(CategoryFilter.Category.EXPOSITION);
                    break;
                case R.id.checkBoxSoiree :
                    categoryFilter.addCategory(CategoryFilter.Category.SOIREE);
                    break;
                case R.id.checkBoxSport :
                    categoryFilter.addCategory(CategoryFilter.Category.SPORT);
                    break;
            }
        }
        else
        {
           switch (v.getId())
            {
                case R.id.checkBoxSpectacle :
                    categoryFilter.removeCategory(CategoryFilter.Category.SPECTACLE);
                    break;
                case R.id.checkBoxConcert :
                    categoryFilter.removeCategory(CategoryFilter.Category.CONCERT);
                    break;
                case R.id.checkBoxTheatre :
                    categoryFilter.removeCategory(CategoryFilter.Category.THEATRE);
                    break;
                case R.id.checkBoxConference :
                    categoryFilter.removeCategory(CategoryFilter.Category.CONFERENCE);
                    break;
                case R.id.checkBoxDebat :
                    categoryFilter.removeCategory(CategoryFilter.Category.DEBAT);
                    break;
                case R.id.checkBoxExposition :
                    categoryFilter.removeCategory(CategoryFilter.Category.EXPOSITION);
                    break;
                case R.id.checkBoxSoiree :
                    categoryFilter.removeCategory(CategoryFilter.Category.SOIREE);
                    break;
                case R.id.checkBoxSport :
                    categoryFilter.removeCategory(CategoryFilter.Category.SPORT);
                    break;
            }
        }

    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
