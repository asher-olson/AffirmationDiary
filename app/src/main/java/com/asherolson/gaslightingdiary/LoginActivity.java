package com.asherolson.gaslightingdiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private Button button;
    private TextView message;
    private Context context;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        password = (EditText) findViewById(R.id.et_password);
        button = (Button) findViewById(R.id.bt_login);
        message = (TextView) findViewById(R.id.tv_login_display_msg);

        context = getApplicationContext();
        sharedPref = context.getSharedPreferences("diary-values", 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = password.getText().toString();
                String compare = sharedPref.getString("password", "");

                if(pw.equals(compare)){
                    //go to main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //show alert that password incorrect
                    message.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
