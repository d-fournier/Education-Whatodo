package fr.insa.whatodo.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.AgeFilter;
import fr.insa.whatodo.model.Category;
import fr.insa.whatodo.model.CategoryFilter;
import fr.insa.whatodo.model.DateFilter;
import fr.insa.whatodo.model.DistanceFilter;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.HourFilter;
import fr.insa.whatodo.model.PlaceFilter;
import fr.insa.whatodo.model.PriceFilter;
import fr.insa.whatodo.model.Tag;
import fr.insa.whatodo.model.TagFilter;
import fr.insa.whatodo.model.User;
import fr.insa.whatodo.services.DatabaseServices;
import fr.insa.whatodo.ui.fragments.CustomMapFragment;
import fr.insa.whatodo.ui.fragments.DownloadFragment;
import fr.insa.whatodo.ui.fragments.EventListFragment;
import fr.insa.whatodo.ui.fragments.FiltersFragment;
import fr.insa.whatodo.ui.fragments.NavigationDrawerFragment;
import fr.insa.whatodo.ui.fragments.ProfileViewFragment;
import fr.insa.whatodo.utils.EventDatabaseHelper;
import fr.insa.whatodo.utils.JSonParser.JSonParser;
import fr.insa.whatodo.utils.OnListChangedListener;
import fr.insa.whatodo.utils.Search;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, FiltersFragment.NavigationDrawerCallbacks {

    private static final String DOWNLOAD_EVENTS_URL = "http://dfournier.ovh/api/event/?format=json";
    private static final String DOWNLOAD_CATEGORIES_URL = "http://dfournier.ovh/api/category/?format=json";
    private static final String DOWNLOAD_TAGS_URL = "http://dfournier.ovh/api/tag/?format=json";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private SearchView searchBar;

    /**
     * Fragments of the activity
     */
    private EventListFragment eventListFragment;
    private CustomMapFragment mapFragment;
    private DownloadFragment downloadFragment;
    private ProfileViewFragment profileFragment;
    private FiltersFragment mFiltersFragment;

    private ArrayList<Event> eventList;
    private List<String> cityNamesList;
    private List<String> tagNamesList;
    private List<OnListChangedListener> mListeners;
    private List<Event> mDisplayedEvents;
    private EventDatabaseHelper mDbHelper;
    SQLiteDatabase write_db = null;
    SQLiteDatabase read_db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mFiltersFragment = (FiltersFragment)
                getSupportFragmentManager().findFragmentById(R.id.filters_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mDbHelper = new EventDatabaseHelper(getApplicationContext());
        eventList = new ArrayList<>();
        mListeners = new ArrayList<>();
        downloadFragment = new DownloadFragment();
        profileFragment = ProfileViewFragment.newInstance(new User("Nom", "passwd", "email@email.com", null, 24));
        eventListFragment = EventListFragment.newInstance(eventList);
        mapFragment = CustomMapFragment.newInstance(eventList);

        new GetEventsTask().execute(null, null, null);

        searchBar = (SearchView) findViewById(R.id.home_search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDisplayedEvents = Search.searchByTitle(eventList, query);
                notifyListChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    mDisplayedEvents = Search.searchByTitle(eventList, newText);
                    notifyListChanged();
                }
                return false;
            }
        });


        mTitle = getTitle();

        // Set up the drawer.

        mFiltersFragment.setUp(
                R.id.filters_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (position) {
                case (0):
                    searchBar.setVisibility(View.VISIBLE);
                    if (eventList.isEmpty()) {
                        HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, downloadFragment).commit();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
                    }
                    break;
                case (1):
                    searchBar.setVisibility(View.GONE);
                    fragmentManager.beginTransaction().replace(R.id.fragment_home_container, profileFragment).commit();
                    break;
            }
        } catch (NullPointerException e) {
            //On passe la première fois
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.principal_view);
                break;
            case 2:
                mTitle = getString(R.string.profile_view);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Whatodo");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen() && !mFiltersFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_settings):
                //TODO Il faut mettre les settings ici !
                break;
            case (R.id.action_earth):
                // update the main content by replacing fragments
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                boolean isWifiConn = networkInfo.isConnected();
                networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileConn = networkInfo.isConnected();
                if (isWifiConn || isMobileConn) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, mapFragment).commit();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.action_list):
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
                break;
            case (R.id.action_refresh):
                new GetEventsTask().execute(null, null, null);
                updateEventList();
                break;
            case  (R.id.action_filters) :
                DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
                if(drawerLayout.isDrawerOpen(Gravity.END))
                {
                    drawerLayout.closeDrawer(Gravity.END);
                }else{
                    drawerLayout.openDrawer(Gravity.END);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDateButtonClicked(View v){
        mFiltersFragment.onDateButtonClicked(v);
    }

    public void onHourButtonClicked(View v){mFiltersFragment.onHourButtonClicked(v);}

    public void onCheckBoxClicked(View v)  {mFiltersFragment.onCheckBoxClicked(v); }

    private void notifyListChanged() {
        for (OnListChangedListener list : mListeners) {
            list.onListChanged(mDisplayedEvents);
        }
    }

    public void addOnListChangedListener(OnListChangedListener list) {
        mListeners.add(list);
    }

    public void removeOnListChangedListener(OnListChangedListener list) {
        mListeners.remove(list);
    }

    public List<String> getCityNamesList() {
        return cityNamesList;
    }
    public List<String> getTagNamesList(){return tagNamesList;}

    public class GetEventsTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(HomeActivity.this, null, getString(R.string.download));
            if (write_db == null || read_db == null) {
                write_db = mDbHelper.getWritableDatabase();
                read_db = mDbHelper.getReadableDatabase();
            }
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConn = networkInfo.isConnected();
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean isMobileConn = networkInfo.isConnected();
            if (isWifiConn || isMobileConn) {
                //On ne fait rien, on est bien connecté à internet
            } else {
                //Pas de connexion internet
                eventList = (ArrayList) DatabaseServices.getAllEvents(read_db);
                if (!eventList.isEmpty()) {
                    eventListFragment = EventListFragment.newInstance(eventList);
                    HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
                } else {
                    HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, downloadFragment).commit();

                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                this.cancel(true);
            }
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            if (!eventList.isEmpty()) {
                eventListFragment = EventListFragment.newInstance(eventList);
                mapFragment = CustomMapFragment.newInstance(eventList);
                HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
            } else {
                HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, downloadFragment).commit();
            }
            dialog.dismiss();
        }

        protected Void doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            List<Category> categoriesList;
            List<Tag> tagsList;

            try {
                // Construct the URL for the server query
                URL event_url = new URL(DOWNLOAD_EVENTS_URL);
                URL categories_url = new URL(DOWNLOAD_CATEGORIES_URL);
                URL tags_url = new URL(DOWNLOAD_TAGS_URL);

                urlConnection = (HttpURLConnection) event_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStreamEvents = urlConnection.getInputStream();

                urlConnection = (HttpURLConnection) categories_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStreamCategories = urlConnection.getInputStream();

                urlConnection = (HttpURLConnection) tags_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStreamTags = urlConnection.getInputStream();

                JSonParser parser = new JSonParser();
                eventList = parser.parseEvents(inputStreamEvents);
                tagsList = parser.parseTags(inputStreamTags);
                categoriesList = parser.parseCategories(inputStreamCategories);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                if(!prefs.getBoolean("firstTime", false)) {
                    DatabaseServices.putAllCitiesInDatabase(getApplicationContext(), write_db);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                }

                DatabaseServices.updateEventTable(eventList, write_db);
                DatabaseServices.updateCategoryTable(categoriesList, write_db);
                DatabaseServices.updateTagTable(tagsList, write_db);

                cityNamesList = DatabaseServices.getAllCityNames(read_db);
                tagNamesList = DatabaseServices.getAllTagsNames(read_db);

                return null;
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }


    public void updateEventList(){

        CategoryFilter categoryFilter=mFiltersFragment.getCategoryFilter();
        TagFilter tagFilter=mFiltersFragment.getTagFilter();
        PlaceFilter placeFilter=mFiltersFragment.getPlaceFilter();
        DistanceFilter distanceFilter=mFiltersFragment.getDistanceFilter();
        PriceFilter priceFilter=mFiltersFragment.getPriceFilter();
        DateFilter dateFilter=mFiltersFragment.getDateFilter();
        AgeFilter ageFilter=mFiltersFragment.getAgeFilter();
        HourFilter hourFilter=mFiltersFragment.getHourFilter();

        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        String dateMin=format.format(dateFilter.getDates()[0]);
        String dateMax=format.format(dateFilter.getDates()[1]);
        String hourMin=String.format("%02d:%02d:00", hourFilter.getBeginHours(),hourFilter.getBeginMinutes());
        String hourMax=String.format("%02d:%02d:00", hourFilter.getEndHours(),hourFilter.getEndMinutes());


        //TODO : catégories, tags
        String filtersUrl= DOWNLOAD_EVENTS_URL+"&distance="+distanceFilter.getValue()+"&min_date="+dateMin+"&max_date="+dateMax
                +"&legal_age="+ageFilter.is18orMore()+"&min_hour="+hourMin+"&max_hour="+hourMax;
        if(priceFilter.getValue()!=-1){
            filtersUrl+="&max_price=" + priceFilter.getValue();
        }


        if(placeFilter.isSendMyPosition() && (placeFilter.getLatitude()!=0 || placeFilter.getLongitude()!=0) ){
            // Envoi des coordonnées
            // TODO : envoi position
            // filtersUrl+="&longitude="+placeFilter.getLongitude()+"&latitude="+placeFilter.getLatitude();
        }else if(!placeFilter.getTown().isEmpty()){
            // Envoi de la ville
            String townAndPostCode=placeFilter.getTown();
            int lastSpace=townAndPostCode.lastIndexOf(" ");
            String town= new String(townAndPostCode.substring(0, lastSpace));
            String postCode= new String(townAndPostCode.substring(lastSpace+1));
            String id;
            try{
                Integer.parseInt(postCode);
                id=DatabaseServices.getCityId(town,postCode, read_db);
            }catch(NumberFormatException e){
                town=townAndPostCode;
                id=DatabaseServices.getCityId(town,null, read_db);
            }
            if(id!=null) filtersUrl+="&city="+id;
        }

        AsyncTask<String,Integer,ArrayList<Event>> task=new AsyncTask<String, Integer, ArrayList<Event>>() {
            @Override
            protected ArrayList<Event> doInBackground(String...filtersUrl) {
                // Log.d("DEBUT", "debut doInBackground"); -> permet de débugger, allez savoir pourquoi...
                InputStream inputStreamEvents;
                HttpURLConnection urlConnection=null;
                ArrayList<Event> liste=null;
                try{
                    URL event_url = new URL(filtersUrl[0]);
                    urlConnection = (HttpURLConnection) event_url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    inputStreamEvents = urlConnection.getInputStream();
                    JSonParser parser = new JSonParser();
                    liste=parser.parseEvents(inputStreamEvents);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return liste;
            }

            protected void onPostExecute(ArrayList<Event> list){
                eventList=list;
                eventListFragment.onListChanged(eventList);
            }
        };

        task.execute(filtersUrl);



    }

}
