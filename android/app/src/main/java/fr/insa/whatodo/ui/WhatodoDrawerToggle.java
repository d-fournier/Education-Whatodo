package fr.insa.whatodo.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.view.Gravity;
import android.view.View;

import fr.insa.whatodo.R;
import fr.insa.whatodo.ui.activities.HomeActivity;


/**
 * Created by Segolene on 22/04/2015.
 */
public class WhatodoDrawerToggle extends ActionBarDrawerToggle {

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private HomeActivity mActivity;
    private boolean mUserLearnedFilterDrawer;
    DrawerLayout mDrawerLayout;

    public WhatodoDrawerToggle(HomeActivity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);

        mActivity=activity;
        mDrawerLayout=drawerLayout;

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        mUserLearnedFilterDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
    }


    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);

        if(drawerView.getId()== R.id.filters_drawer){
            mActivity.updateEventList();
        }

        mActivity.supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);

        switch (drawerView.getId()){
            case R.id.navigation_drawer :
                if(mDrawerLayout.isDrawerOpen(Gravity.END)){
                    mDrawerLayout.closeDrawer(Gravity.END);
                }
                break;

            case R.id.filters_drawer :
                if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                    mDrawerLayout.closeDrawer(Gravity.START);
                }

                if (!mUserLearnedFilterDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedFilterDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(mActivity);
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                break;
        }

        mActivity.supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
    }

public void openFilterDrawerIfNotLearned(){
    if (!mUserLearnedFilterDrawer) {
        mDrawerLayout.openDrawer(Gravity.END);
    }
}

}
