package fr.insa.whatodo.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Date;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.ui.fragments.CustomMapFragment;
import fr.insa.whatodo.ui.fragments.EventListFragment;
import fr.insa.whatodo.ui.fragments.NavigationDrawerFragment;
import fr.insa.whatodo.ui.fragments.PlaceholderFragment;
import fr.insa.whatodo.utils.Search;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private SearchView searchBar;
    private EventListFragment eventListFragment;
    private CustomMapFragment mapFragment;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fillEventList();

        eventListFragment = EventListFragment.newInstance(eventList);
        mapFragment = CustomMapFragment.newInstance(eventList);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_home_container, eventListFragment).commit();
        searchBar = (SearchView) findViewById(R.id.home_search_bar);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                eventListFragment.updateListView(Search.searchByTitle(eventListFragment.getEventList(), query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    eventListFragment.updateListView(Search.searchByTitle(eventListFragment.getEventList(), newText));
                }
                return false;
            }
        });

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_home_container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
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
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
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
            case (R.id.action_earth_list):
                // update the main content by replacing fragments
                if (item.getTitle().equals(getApplicationContext().getString(R.string.action_earth))) //Go to the map view
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, mapFragment).commit();
                    item.setTitle(R.string.action_list);
                } else if (item.getTitle().equals(getApplicationContext().getString(R.string.action_list))) //Go to the list view
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
                    item.setTitle(R.string.action_earth);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillEventList() {
        eventList = new ArrayList<>();
        eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), "Pas chez moi", "10 euros", "19 Rue Marcel Dutarte 69100 Villeurbanne", "C'est cool venez"));
        eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), "Chez moi", "10 euros", "3 Rue du Château d'Eau 70100 Beaujeu", "C'est cool venez"));

    }

}
