package com.example.receptionsystem.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.receptionsystem.Adaptor.RecycleAdapter;
import com.example.receptionsystem.ApiCalling;
import com.example.receptionsystem.Interface.OnClickInterface;
import com.example.receptionsystem.R;
import com.example.receptionsystem.Model.ResponseData;
import com.example.receptionsystem.SharedPrefManager;
import com.example.receptionsystem.Model.VisitorExitModel;
import com.example.receptionsystem.Model.VisitorModel;
import com.example.receptionsystem.Model.VisitorResponse;
import com.example.receptionsystem.databinding.DialogLayoutBinding;
import com.example.receptionsystem.databinding.RegistrationPageBinding;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements OnClickInterface {
    RegistrationPageBinding registrationPageBinding;
    private SharedPrefManager sharedPrefManager;
    String accessToken, UserName;
    String time,visitorId;
    DialogLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationPageBinding = RegistrationPageBinding.inflate(getLayoutInflater());
        sharedPrefManager = new SharedPrefManager(this);
        accessToken = "Bearer " + sharedPrefManager.getStringValue("accessToken");
        UserName = sharedPrefManager.getStringValue("username");
        setContentView(registrationPageBinding.getRoot());
        registrationPageBinding.List.setLayoutManager(new LinearLayoutManager(this));
        ResponseDatas();
        registrationPageBinding.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
            }
        });
        registrationPageBinding.History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });
    }

    public void ResponseDatas() {
        Log.d("UserPassword", "ResponseData: Password is " + UserName + "  token is " + accessToken);
        VisitorModel visitorModel = new VisitorModel();
        visitorModel.setUserId(UserName);
        ApiCalling.getService().getResponse(accessToken, visitorModel).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                ResponseData responseData = response.body();
                List<VisitorResponse> visitorResponse = responseData.getVisitorResponse();
                RecycleAdapter adapter = new RecycleAdapter(visitorResponse, RegistrationActivity.this, RegistrationActivity.this::onClick);
                Log.d("TAG", "onResponse:Data is  " + visitorResponse.size());
                registrationPageBinding.List.setAdapter(adapter);
            }

          /*  private void onClick(List<VisitorResponse> visitorResponse,View view, int position) {

            }*/

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                t.printStackTrace();
                Log.e("TAG", t.getMessage());
            }
        });
    }


    @Override
    public void onClick(VisitorResponse visitorResponse) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(RegistrationActivity.this),
                R.layout.dialog_layout, null, false);
        registrationPageBinding.getRoot().setVisibility(View.VISIBLE);
        Dialog dialog = new Dialog(this);
       // dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());


        Log.v("TAG", "VISitor : " + visitorResponse.getVisitorId());
        binding.UserId.setText(visitorResponse.getVisitorId());
        binding.UserName.setText(visitorResponse.getName());
        binding.UserMobileNumber.setText(visitorResponse.getMobileNo());
        dialog.show();

        binding.currentTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                TimePickerDialog mTimePicker = new TimePickerDialog(RegistrationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TextView textView = RegistrationActivity.this.binding.currentTIme;
                        textView.setText(selectedHour + ":" + selectedMinute);
                    }
                }, mcurrentTime.get(11), mcurrentTime.get(12), true);
                mTimePicker.setTitle("Select Time");
                Log.d("mTimePicker", "onClick: Selected time is "+mTimePicker);
                mTimePicker.show();
            }
        });
        //time = binding.currentTIme.getText().toString();
        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("time", "onClick: current time is "+binding.currentTIme.getText().toString());
                VisitorExitModel visitorExitModel = new VisitorExitModel();
                visitorExitModel.setVisitorId(visitorResponse.getVisitorId());
                visitorExitModel.setOutDateTime(binding.currentTIme.getText().toString());
                ApiCalling.getService().setOutTime(accessToken, visitorExitModel).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() & response.code() == 200) {
                            Log.d("Exit", "onResponse: Exit Successful");
                            Toast.makeText(RegistrationActivity.this, "Exit Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("TAG", t.getMessage());
                    }
                });
                dialog.dismiss();
            }
        });
    }
}
