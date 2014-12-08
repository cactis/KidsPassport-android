package com.airfont.kidspassport;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.airfont.kidspassport.dummy.DummyContent;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ChildsListFragment extends Fragment implements AbsListView.OnItemClickListener {

  private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;
  /**
   * The fragment's ListView/GridView.
   */
  private ListAdapter mAdapter;
  private AbsListView mListView;
  private List childs;

  // TODO: Rename and change types of parameters
  public static ChildsListFragment newInstance(String param1, String param2) {
    ChildsListFragment fragment = new ChildsListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public ChildsListFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    //TODO: Change Adapter to display your content
    //mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
    //android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    //mAdapter = new ArrayAdapter<Child>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, childs);
    childs = Child.all();
    mAdapter = new ChildAdapter(getActivity(), childs);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_child_list, container, false);

    // Set the adapter
    mListView = (AbsListView) view.findViewById(android.R.id.list);
    ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

    //Set OnItemClickListener so we can be notified on item clicks
    mListView.setOnItemClickListener(this);

    View eventView = view.findViewById(R.id.eventView);
    eventView.setOnClickListener((new View.OnClickListener() {
      public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kids.org.tw/"));
        startActivity(browserIntent);
      }
    }));
    return view;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    logger.info(String.format("position: %s", position));
    logger.info(String.format("id: %s", id));
    Child child = (Child) mAdapter.getItem(position);
    logger.info(String.format("child id: %s", child.getId()));

    Fragment fragment = ChildEditFragment.newInstance(child.getId().toString(), null);
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.addToBackStack(null);
    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    ft.replace(R.id.container, fragment);
    ft.commit();

    if (null != mListener) {
      logger.info("here!");
      // Notify the active callbacks interface (the activity, if the
      // fragment is attached to one) that an item has been selected.
      //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.childs_list, menu);
    menu.findItem(R.id.add_child).setIcon(
      new IconDrawable(getActivity().getBaseContext(), Iconify.IconValue.fa_plus).colorRes(android.R.color.white).actionBarSize());
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.add_child:
        Fragment fragment = ChildEditFragment.newInstance(null, null);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onResume() {
    logger.info(String.format("onResume: count of childs is %s", Child.count()));
    ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    //mAdapter = new ArrayAdapter<Child>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, childs);

    childs = Child.all();
    mAdapter = new ChildAdapter(getActivity(), childs);
    ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

    super.onResume();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    //try {
    //  mListener = (OnFragmentInteractionListener) activity;
    //} catch (ClassCastException e) {
    //  throw new ClassCastException(activity.toString()
    //    + " must implement OnFragmentInteractionListener");
    //}
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * The default content for this Fragment has a TextView that is shown when
   * the list is empty. If you would like to change the text, call this method
   * to supply the text it should use.
   */
  public void setEmptyText(CharSequence emptyText) {
    View emptyView = mListView.getEmptyView();

    if (emptyView instanceof TextView) {
      ((TextView) emptyView).setText(emptyText);
    }
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    //public void onFragmentInteraction(String id);
    public void onFragmentInteraction(Uri uri);
  }

}
