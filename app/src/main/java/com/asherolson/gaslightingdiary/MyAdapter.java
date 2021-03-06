package com.asherolson.gaslightingdiary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mContext;
    List<DiaryEntry> diaryList;

    public MyAdapter (List<DiaryEntry> list, Context context) {
        diaryList = list;
        mContext = context;
    }


    public DiaryEntry getItem(int position) {
        return diaryList.get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDate.setText(diaryList.get(position).getDate());
        //probably better way to keep preview on 1 line but this is good for now
        String text = diaryList.get(position).getText();
//        if(text.length() >= 23){
//            text = text.substring(0, 22) + "...";
//        }
        holder.tvPreview.setText(text);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch edit activity to display full contents of entry
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("entry_content", diaryList.get(position).getText());
                intent.putExtra("entry_id", diaryList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MainActivity.deleteEntry(diaryList.get(position).getId());
                return false;
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvPreview;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            tvPreview = itemView.findViewById(R.id.tv_item_preview);
            parentLayout = itemView.findViewById(R.id.list_item);
        }
    }

}

