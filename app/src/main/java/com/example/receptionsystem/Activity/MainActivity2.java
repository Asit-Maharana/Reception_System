package com.example.receptionsystem.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.receptionsystem.ApiCalling;
import com.example.receptionsystem.Model.ModelClass;
import com.example.receptionsystem.R;
import com.example.receptionsystem.SharedPrefManager;
import com.example.receptionsystem.Model.TokenModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    TextInputEditText username, password;
    Button btnLogin;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //View LoginPage = findViewById(R.id.LoginPage);
        //LoginPage.setBackgroundColor(Color.WHITE);
        sharedPrefManager = new SharedPrefManager(this);
        Log.v("TAG", "username " + sharedPrefManager.getStringValue("username"));
        Log.v("TAG", " password " + sharedPrefManager.getStringValue("password"));

        username = findViewById(R.id.Name);
        password = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.Login);
        username.setText(sharedPrefManager.getStringValue("username"));
        password.setText(sharedPrefManager.getStringValue("password"));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(MainActivity2.this, "Username / Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    //proceed to login
                    Log.d("TAG", "onClick: name is " + username.getText().toString() + "password is " + password.getText().toString());
                    login();
                }
            }
        });
    }

    public void login() {
        ModelClass modelClass = new ModelClass();
        Log.d("TAG", "login: UserName is"+username.getText().toString()+" Password is "+password.getText().toString());
        modelClass.setUsername(username.getText().toString());
        modelClass.setPassword(password.getText().toString());
        Log.d("TAG", "login: Successful entered in login");
        Call<TokenModel> getPostsCall = ApiCalling.getService().getPostsCall(modelClass);
        Log.d("TAG", "IDPWD--"+getPostsCall);
        getPostsCall.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful() & response.code() == 200) {
                    TokenModel model = response.body();
                    assert model != null;
                    String token = model.getToken();
                    Log.d("response", " " + token);
                    Toast.makeText(MainActivity2.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                    sharedPrefManager.setStringValue("username", username.getText().toString());
                    sharedPrefManager.setStringValue("password", password.getText().toString());
                    sharedPrefManager.setStringValue("accessToken", token);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(i);
            }
        });
    }
}