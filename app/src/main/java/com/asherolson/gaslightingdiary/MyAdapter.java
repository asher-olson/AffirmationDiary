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
        String text = diaryList.get(position).getText().substring(0, 22) + "...";
        holder.tvPreview.setText(text);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvPreview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            tvPreview = itemView.findViewById(R.id.tv_item_preview);
        }
    }

}

