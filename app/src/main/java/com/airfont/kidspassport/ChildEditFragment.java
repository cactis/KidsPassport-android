package com.airfont.kidspassport;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.thedeanda.lorem.Lorem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChildEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildEditFragment extends Fragment {

  private static Logger logger = LoggerFactory.getLogger(ChildEditFragment.class);

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ChildEditFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ChildEditFragment newInstance(String param1, String param2) {
    ChildEditFragment fragment = new ChildEditFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public ChildEditFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_child_edit, container, false);
    Button button = (Button)view.findViewById(R.id.child_edit_submit);
    final EditText fullname = (EditText)view.findViewById(R.id.fullname);
    final EditText gender = (EditText)view.findViewById(R.id.gender);


    gender.setText("女");

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        logger.info("submit it!");
        fullname.setText(Lorem.getNameFemale(), TextView.BufferType.EDITABLE);

        Hashtable<String, String> attributes = new Hashtable<String, String>();
        attributes.put("fullname", fullname.getText().toString());
        attributes.put("gender", gender.getText().toString());
        Log.v("gender", gender.getText().toString());
        //attributes.put("gender", view.findViewById(R.id.gender));
        logger.info(String.valueOf(attributes));

        Child child = new Child();
        Child result = child.update_attributes(attributes);
        logger.info(result.fullname);
        getFragmentManager().popBackStack();
      }
    });
    return view;
  }

  // TODO: Rename method, update argument and hook method into UI event

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
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
    public void onFragmentInteraction(Uri uri);
  }

}
