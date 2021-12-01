package com.asherolson.gaslightingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAdd;
    private static List<DiaryEntry> entries;
    private static SharedPreferences sharedPref;
    private Context context;

    //private Thread affirmationThread;

    public static final String[] AFFIRMATIONS = new String[] {
            "I will not be robbed at gunpoint",
            "I are the goat",
            "I will not die while scrolling",
            "I will not collapse while working out",
            "I will not suddenly pass out",
            "Mentally, I'm not insanely stupid",
            "Covid has not ruined my life",
            "I will not die poor and alone",
            "Humanity is not doomed",
            "I am treating my anxiety with sugar",
            "I am not a saint and that's okay",
            "I have not been acting weird lately",
            "Love is not torture",
            "I did not cause my parent's divorce",
            "Having fun is not an impossible task",
            "Life is not a nightmare",
            "Eye contact is not scary",
            "I will party, I will not cry"
    };

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
