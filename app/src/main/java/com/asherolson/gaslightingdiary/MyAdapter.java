package com.asherolson.gaslightingdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    Context mContext;
    List<String> mList;

    public MyAdapter (Context context, List<String> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // This method is called to draw each row of the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // here you inflate the layout you want for the row
        //final View view = View.inflate(mContext, R.layout.list_item, null);
        //View view = null;
        // you bind the layout with the content of your list
        // for each element of your list of notes, the adapter will create a row and affect the right title
//        final TextView noteTitle= (TextView)view.findViewById(R.id.note_title);
//        noteTitle.setText(mList.getItem(position));
        ViewHolder holder;

        if (convertView == null) { // if convertView is null
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,
                    parent, false);
            holder = new ViewHolder();
            // initialize views
            convertView.setTag(holder);  // set tag on view
        } else {
            holder = (ViewHolder) convertView.getTag();
            // if not null get tag
            // no need to initialize
        }

        //update views here
        return convertView;

        //return view;
    }

    static class ViewHolder {
        ImageView leftIcon;
        TextView upperLabel;
        TextView lowerLabel;
    }

}

