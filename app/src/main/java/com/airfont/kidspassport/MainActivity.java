package com.airfont.kidspassport;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.content.Intent;

import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends Activity
  implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */
  private NavigationDrawerFragment mNavigationDrawerFragment;

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar bar = getActionBar();
    bar.setBackgroundDrawable(new ColorDrawable(0xDDdb1a1a));
    bar.setDisplayShowTitleEnabled(false);
    bar.setDisplayShowTitleEnabled(true);

    mNavigationDrawerFragment = (NavigationDrawerFragment)
      getFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();

    // Set up the drawer.
    mNavigationDrawerFragment.setUp(
      R.id.navigation_drawer,
      (DrawerLayout) findViewById(R.id.drawer_layout));
  }

  @Override
  public void onNavigationDrawerItemSelected(int position) {
    // update the main content by replacing fragments
    logger.info("onNavigationDrawerItemSelected");
    Fragment fragment = new Fragment();
//    Fragment fragment = PlaceholderFragment.newInstance(position + 1);
    switch (position) {
      case 0:
        fragment = PlaceholderFragment.newInstance(position + 1);
        break;
      case 1:
        mTitle = getString(R.string.title_section1);
        fragment = ChildFragment.newInstance("", "");
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        fragment = ParentFragment.newInstance("", "");
        break;
      case 3:
//        mTitle = getString(R.string.title_section3);
        Uri center_call = Uri.parse("tel:0800049880");
        Intent center_call_intent = new Intent(Intent.ACTION_DIAL, center_call);
        startActivity(center_call_intent);
        return;
      case 4:
//        mTitle = getString(R.string.title_section4);
        Uri police_call = Uri.parse("tel:119");
        Intent poclic_call_intent = new Intent(Intent.ACTION_DIAL, police_call);
        startActivity(poclic_call_intent);
        return;
      case 5:
        mTitle = getString(R.string.title_section5);
        fragment = InfoFragment.newInstance("", "");
        break;
      case 6:
        mTitle = getString(R.string.title_section6);
        fragment = AboutFragment.newInstance("", "");
    }
    FragmentManager fragmentManager = getFragmentManager();
    fragmentManager.beginTransaction()
      .replace(R.id.container, fragment)
      .commit();
  }

  public void onSectionAttached(int number) {
    logger.info("number", number);
  }

  public void restoreActionBar() {
    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (!mNavigationDrawerFragment.isDrawerOpen()) {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.
      getMenuInflater().inflate(R.menu.main, menu);
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

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
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
     * Returns a new instance of this fragment for the given section
     * number.
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      ((MainActivity) activity).onSectionAttached(
        getArguments().getInt(ARG_SECTION_NUMBER));
    }
  }

}
