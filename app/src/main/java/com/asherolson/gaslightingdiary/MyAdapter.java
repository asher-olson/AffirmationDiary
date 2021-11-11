package com.asherolson.gaslightingdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context mContext;
    List<DiaryEntry> mList;

    public MyAdapter (List<DiaryEntry> list, Context context) {
        mList = list;
        mContext = context;
    }


    public int getCount() {
        return mList.size();
    }


    public DiaryEntry getItem(int position) {
        return mList.get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // This method is called to draw each row of the list

    public View getView(int position, View convertView, ViewGroup parent) {

        // here you inflate the layout you want for the row
        //final View view = View.inflate(mContext, R.layout.list_item, null);
        //View view = null;
        // you bind the layout with the content of your list
        // for each element of your list of notes, the adapter will create a row and affect the right title
//        final TextView noteTitle= (TextView)view.findViewById(R.id.note_title);
//        noteTitle.setText(mList.getItem(position));
        MyViewHolder holder;

        if (convertView == null) { // if convertView is null
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,
                    parent, false);
            holder = new MyViewHolder();
            // initialize views
            convertView.setTag(holder);  // set tag on view
        } else {
            holder = (MyViewHolder) convertView.getTag();
            // if not null get tag
            // no need to initialize
        }

        //update views here
        return convertView;

        //return view;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView leftIcon;
        TextView upperLabel;
        TextView lowerLabel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

