package com.example.receptionsystem.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.receptionsystem.R;
import com.example.receptionsystem.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {
    private SharedPrefManager sharedPrefManager;
    TextView tokenValue;
    String accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        tokenValue=findViewById(R.id.token);

        sharedPrefManager = new SharedPrefManager(this);
        Log.v("TAG", "username " + sharedPrefManager.getStringValue("username"));
        Log.v("TAG", " password " + sharedPrefManager.getStringValue("password"));


        accessToken = sharedPrefManager.getStringValue("accessToken");
        Log.d("Value "," Token value is  "+accessToken);
        tokenValue.setText(accessToken);

    }
}
