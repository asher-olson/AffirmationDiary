package com.asherolson.gaslightingdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntryActivity extends AppCompatActivity {

    private Button btSetPw;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private TextView tvMessage;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //if password set, go to login
        sharedPref = getApplicationContext().getSharedPreferences("diary-values", 0);
        String password = sharedPref.getString("password", "");
        if(!password.equals("")){
            Intent intent = new Intent(EntryActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_entry);

        btSetPw = (Button) findViewById(R.id.bt_set_pw);
        etPassword = (EditText) findViewById(R.id.et_create_pw);
        etConfirmPassword = (EditText) findViewById(R.id.et_reenter_pw);
        tvMessage = (TextView) findViewById(R.id.tv_entry_msg);


        btSetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check both input fields
                String original = etPassword.getText().toString();
                String confirm = etConfirmPassword.getText().toString();

                //reject if too short
                if(original.length() < 5){
                    tvMessage.setText(R.string.password_short);
                    tvMessage.setVisibility(View.VISIBLE);
                }
                //reject if confirm doesn't match
                else if(!original.equals(confirm)){
                    tvMessage.setText(R.string.password_no_match);
                    tvMessage.setVisibility(View.VISIBLE);
                } else {
                    //password good, save and go to main activity
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("password", original);
                    editor.apply();

                    Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
