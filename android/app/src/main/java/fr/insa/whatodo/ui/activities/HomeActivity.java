package fr.insa.whatodo.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.ui.fragments.CustomMapFragment;
import fr.insa.whatodo.ui.fragments.DownloadFailedFragment;
import fr.insa.whatodo.ui.fragments.EventListFragment;
import fr.insa.whatodo.ui.fragments.NavigationDrawerFragment;
import fr.insa.whatodo.utils.OnListChangedListener;
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
    private DownloadFailedFragment downloadFragment;
    private ArrayList<Event> eventList;
    private List<OnListChangedListener> mListeners;
    private List<Event> mDisplayedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fillEventList();

        mListeners = new ArrayList<>();
        eventListFragment = EventListFragment.newInstance(eventList);
        mapFragment = CustomMapFragment.newInstance(eventList);
        downloadFragment = new DownloadFailedFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_home_container, downloadFragment).commit();
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
        switch (position) {
            case (0):
                //    fragmentManager.beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
                break;
            case (1):

                break;
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
            case (R.id.action_refresh) :
                new GetEventsTask().execute("http://dfournier.ovh/api/event/?format=json", null, "");
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void fillEventList() {
        eventList = new ArrayList<>();

/*      eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), null, "Evt 1", "10 euros", "19 Rue Marcel Dutarte 69100 Villeurbanne", "C'est cool venez"));
        eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), new Date(115,05,21), "Evenement 2", "10 euros", "3 Rue du Château d'Eau 70100 Beaujeu", "C'est cool venez"));
        eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), null, "Evt 3", "10 euros", "820 S Michigan Ave Chicago IL 60605-7102", "C'est cool venez"));
        eventList.add(new Event(getResources().getDrawable(R.drawable.ic_launcher), new Date(), new Date(115, 06, 31), "Evenement 4", "10 euros", "69, rue Farabi Marrakech Maroc", "C'est cool venez"));
*/
    }

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

    public class GetEventsTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConn = networkInfo.isConnected();
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean isMobileConn = networkInfo.isConnected();
            if (isWifiConn || isMobileConn) {
                //On ne fait rien, on est bien connecté à internet
                dialog = ProgressDialog.show(HomeActivity.this, null, getString(R.string.download));
            } else {
                //Pas de connexion internet
                HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, downloadFragment).commit();
                this.cancel(true);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            dialog = ProgressDialog.show(HomeActivity.this, null, getString(R.string.parse));
            //TODO Il faut parser la string ici !
            dialog.dismiss();
            HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_container, eventListFragment).commit();
        }

        protected String doInBackground(String... urls) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String eventJsonStr = null;

            try {
                // Construct the URL for the server query
                URL url = new URL(urls[0]);

                // Create the request to the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                eventJsonStr = buffer.toString();
            } catch (IOException e) {
                return "network_error";
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Getting Events", "Error closing stream", e);
                    }
                }
            }
            return eventJsonStr;
        }

    }
}
