package com.cccsscheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends FragmentActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks  {

    public static Activity main;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public static int mNotificationsCount = 0;
    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        main = this;

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onBackPressed() {
        login.loginActivity.finish();
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position >= 10) {
            Toast.makeText(this, String.valueOf(position - 10) + "ff", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt("group_id", position - 10);
            Groups groups = new Groups();
            groups.setArguments(bundle);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, groups)
                    .commit();
            mTitle = mNavigationDrawerFragment.g_name;

        } else {
            if (position == 0) {
                Events event = new Events();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, event)
                        .commit();
                onSectionAttached(1);
                onSectionAttached(1);
            } else if (position == 1) {
                Note note = new Note();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, note)
                        .commit();
                onSectionAttached(2);
            }
        }
        restoreActionBar();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.events);
                break;
            case 2:
                mTitle = getString(R.string.notes);
                break;
            case 3:
                mTitle = getString(R.string.subcription);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_Notification);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        Utils.setBadgeCount(this, icon, mNotificationsCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {

            SharedPreferences sharedpreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            stopService(new Intent(this,MyService.class));
            finish();
        } else if (id == R.id.action_Notification) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Notification_Fragment notification = new Notification_Fragment();
            updateNotificationsBadge(0);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, notification)
                    .commit();
        }
        else if(id==R.id.action_profile){
            startActivity(new Intent(this,UserProfile.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                    ARG_SECTION_NUMBER));
        }
    }
}
