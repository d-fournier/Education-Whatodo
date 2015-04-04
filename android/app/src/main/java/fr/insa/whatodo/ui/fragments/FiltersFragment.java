package fr.insa.whatodo.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
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
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;
    private FiltersListAdapter mFiltersListAdapter;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

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

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

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
        mFiltersListAdapter=new FiltersListAdapter(getActivity(), this);
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

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_filters,             /* nav drawer image to replace 'Up' caret */
                R.string.filters_open,  /* "open drawer" description for accessibility */
                R.string.filters_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };


        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

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

//        if (item.getItemId() == R.id.action_example) {
//            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
//            return true;
//        }

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
            if(dateFilter.setDateMax(gc.getTime()))
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button lastDateButton = (Button) getActivity().findViewById(R.id.lastDateText);
                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        lastDateButton.setText(format.format(dateFilter.getDates()[1]));
                    }
                });
            } else{
            //TODO : message d'erreur
            }
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
            if(hourFilter.setEndHours(hourOfDay, minute)){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button lastHourButton=(Button)getActivity().findViewById(R.id.lastHourText);
                        lastHourButton.setText(hourFilter.getEndHours()+" : "+hourFilter.getEndMinutes());
                    }
                });
            }else{
                //TODO : message d'erreur
            }
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
                case R.id.checkBoxProjection :
                    categoryFilter.addCategory(CategoryFilter.Category.PROJECTIONVIDEO);
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
                case R.id.checkBoxProjection :
                    categoryFilter.removeCategory(CategoryFilter.Category.PROJECTIONVIDEO);
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

    public void openFilters(){
        mDrawerLayout.openDrawer(Gravity.END);
    }

    public void closeFilters(){
        mDrawerLayout.closeDrawer(Gravity.END);
    }
}