package com.airfont.kidspassport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.thedeanda.lorem.Lorem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;


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

  private EditText fullname;
  private EditText nickname;
  private EditText gender;
  private EditText birthday;
  private ImageView avatar;
  private Button submit;

  private Child child;

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
    setHasOptionsMenu(true);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_child_edit, container, false);

    fullname = (EditText) view.findViewById(R.id.fullname);
    nickname = (EditText) view.findViewById(R.id.nickname);
    birthday = (EditText) view.findViewById(R.id.birthday);
    gender = (EditText) view.findViewById(R.id.gender);
    avatar = (ImageView) view.findViewById(R.id.avatar);
    submit = (Button) view.findViewById(R.id.submit);

    avatar.setOnClickListener(new ButtonClicked());
    submit.setOnClickListener(new ButtonClicked());

    if (mParam1 == null) {
      child = new Child();
    } else {
      child = Child.find(Integer.valueOf(mParam1));
      fullname.setText(child.fullname);
      nickname.setText(child.nickname);
      birthday.setText(child.birthday);
      gender.setText(child.gender);
      avatar.setImageBitmap(BitmapFactory.decodeByteArray(child.avatar, 0, child.avatar.length));
    }

    return view;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.child_edit, menu);
    menu.findItem(R.id.cancel).setIcon(new IconDrawable(getActivity().getBaseContext(), Iconify.IconValue.fa_close).colorRes(android.R.color.white).actionBarSize());
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.cancel:
        getFragmentManager().popBackStack();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    logger.info("camera result");
    Bitmap bt = (Bitmap) data.getExtras().get("data");
    avatar.setImageBitmap(bt);
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

  private class ButtonClicked implements View.OnClickListener {
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.avatar:
          logger.info("image clicked");
          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intent, 0);
          break;
        case R.id.submit:
          logger.info("submit it!");
          if (fullname.getText() == null) {
            fullname.setText(Lorem.getNameFemale(), TextView.BufferType.EDITABLE);
            nickname.setText(Lorem.getFirstNameFemale(), TextView.BufferType.EDITABLE);
            birthday.setText("2005/03/27", TextView.BufferType.EDITABLE);
            gender.setText("女", TextView.BufferType.EDITABLE);
          }

          child.fullname = fullname.getText().toString();
          child.nickname = nickname.getText().toString();
          child.gender = gender.getText().toString();
          child.birthday = birthday.getText().toString();

          Bitmap bmp = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
          ByteArrayOutputStream stream = new ByteArrayOutputStream();
          bmp.compress(Bitmap.CompressFormat.JPEG, 75, stream);
          byte[] byteArray = stream.toByteArray();
          child.avatar = byteArray;
          child.save();

          getFragmentManager().popBackStack();
          break;
      }
    }
  }

}
