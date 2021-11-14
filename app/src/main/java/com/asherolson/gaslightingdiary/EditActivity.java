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
    private Button btSave, btCancel;
    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        etEntry = (EditText) findViewById(R.id.et_entry_content);
        btSave = (Button) findViewById(R.id.bt_save_entry);
        btCancel = (Button) findViewById(R.id.bt_cancel_entry);

        getIncomingIntent();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass new text and id back to main activity
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.putExtra("new_entry_content", etEntry.getText().toString());
                intent.putExtra("entry_id", id);
                startActivity(intent);
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //just go back to main activity without extras
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("entry_content") && getIntent().hasExtra("entry_id")){
            etEntry.setText(getIntent().getStringExtra("entry_content"));
            id = getIntent().getIntExtra("entry_id", -1);
        }
    }
}
