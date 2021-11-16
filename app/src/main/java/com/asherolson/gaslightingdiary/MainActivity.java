package com.asherolson.gaslightingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAdd;
    private static List<DiaryEntry> entries;
    private static SharedPreferences sharedPref;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add_entry);

        recyclerView = (RecyclerView) findViewById(R.id.rv_diary_entries);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        context = getApplicationContext();

        sharedPref = context.getSharedPreferences("diary-values", 0);

        entries = new ArrayList<DiaryEntry>();

        readEntriesFromSharedPref();

        Collections.sort(entries);

//        entries.add(new DiaryEntry("1<!9!8>03-12-2020<!9!8>I ate a hotdog today"));
//        entries.add(new DiaryEntry("1<!9!8>06-26-2021<!9!8>I ate a burger today"));
//
//        for(int i = 0; i < 20; i++){
//            entries.add(new DiaryEntry(i + "<!9!8>12-19-2022<!9!8>I ate " + i + "burgers today"));
//        }


        mAdapter = new MyAdapter(entries, this);
        recyclerView.setAdapter(mAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get next id from shared pref and launch empty edit activity
                Intent intent = new Intent(context, EditActivity.class);

                int nextId = sharedPref.getInt("next-id", 0);

                intent.putExtra("entry_content", "");
                intent.putExtra("entry_id", nextId);

                //increment next id
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("next-id", (nextId + 1));
                editor.apply();

                startActivity(intent);
            }
        });

    }

    //reading entries is coupled with handling adding new entries from edit activity here
    private void readEntriesFromSharedPref(){
        Set<String> entrySet = sharedPref.getStringSet("diary_entries", new HashSet<String>());
        for(String entry: entrySet){
            entries.add(new DiaryEntry(entry));
        }

        //check for incoming intent
        if(getIntent().hasExtra("new_entry_content") && getIntent().hasExtra("entry_id")){
            //activity was launched from edit activity
            DiaryEntry entry = getEntryById(getIntent().getIntExtra("entry_id", -1));

            if(entry == null){
                //new entry needs to be created
                entry = new DiaryEntry();

                //generate date for new entry
                LocalDate date = LocalDate.now();
                String dateString = date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.getYear();

                entry.setDate(dateString);
                entry.setId(getIntent().getIntExtra("entry_id", -1));

                entries.add(entry);
            }
            //get new text from intent and set entry text to new text
            entry.setText(getIntent().getStringExtra("new_entry_content"));

            //keep shared pref consistent with entries
            saveEntries();
        }
    }

    private static void saveEntries(){
        SharedPreferences.Editor editor = sharedPref.edit();

        //get all entries, convert to strings, put in sharedPref
        HashSet<String> stringEntries = new HashSet<String>();
        for(DiaryEntry entry: entries){
            String s = entry.getId() + "<!9!8>" + entry.getDate() + "<!9!8>" + entry.getText();
            stringEntries.add(s);
        }

        editor.putStringSet("diary_entries", stringEntries);
        editor.apply();

    }

    private static DiaryEntry getEntryById(int id){
        for(DiaryEntry entry: entries){
            if(entry.getId() == id){
                return entry;
            }
        }
        return null;
    }

    public static void deleteEntry(int id){
        //delete entry with id from entries
        DiaryEntry entry = getEntryById(id);
        entries.remove(entry);
        saveEntries();
        mAdapter.notifyDataSetChanged();
    }
}