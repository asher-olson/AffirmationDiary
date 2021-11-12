package com.asherolson.gaslightingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText etEntry;
    private Button btSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        etEntry = (EditText) findViewById(R.id.et_entry_content);
        btSave = (Button) findViewById(R.id.bt_save_entry);

        getIncomingIntent();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save text entry into shared pref


                //go to main activity
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("entry_content")){
            etEntry.setText(getIntent().getStringExtra("entry_content"));
        }
    }
}
