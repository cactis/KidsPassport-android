package com.airfont.kidspassport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ctslin on 12/7/14.
 */
public class ChildAdapter extends BaseAdapter {
  private static final Logger logger = LoggerFactory.getLogger(ChildAdapter.class);

  //private Context context;
  private LayoutInflater inflater;
  private List list = null;
  private Object fullname;

  public ChildAdapter(Context context, List<List> list) {
    //this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.list = list;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
    //return Long.valueOf(list.get(position).get("id"));
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    convertView = inflater.inflate(R.layout.childs_list_cell, parent, false);
    Child child = (Child) getItem(position);
    ((TextView) convertView.findViewById(R.id.fullname)).setText(child.fullname);
    ((TextView) convertView.findViewById(R.id.gender)).setText(child.gender);
    ((TextView) convertView.findViewById(R.id.nickname)).setText(child.nickname);
    ((TextView) convertView.findViewById(R.id.birthday)).setText(child.birthday);
    return convertView;
  }
}

//import android.content.Context;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.TextView;
//
//public class ChildAdapter extends CursorAdapter{
//  public ChildAdapter(Context context, Cursor cursor){
//    super(context, cursor, 0);
//  }
//
//  @Override
//  public View newView(Context context, Cursor cursor, ViewGroup parent){
//    return LayoutInflater.from(context).inflate(R.layout.childs_list_cell, parent, false);
//  }
//
//  @Override
//  public void bindView(View view, Context context, Cursor cursor){
//    TextView fullname = (TextView) view.findViewById(R.id.fullname);
//    fullname.setText(cursor.getString(cursor.getColumnIndexOrThrow("fullname")));
//  }
//}
