package com.asherolson.gaslightingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<DiaryEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_diary_entries);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //TODO: get string diary entries from sharedPref and populate entries
        entries = new ArrayList<DiaryEntry>();

        entries.add(new DiaryEntry("1<!9!8>03-12-2020<!9!8>I ate a hotdog today"));
        entries.add(new DiaryEntry("1<!9!8>06-26-2021<!9!8>I ate a burger today"));


        mAdapter = new MyAdapter(entries, this);
        recyclerView.setAdapter(mAdapter);


    }
}