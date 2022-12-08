package com.example.receptionsystem.Activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.receptionsystem.Adaptor.HistoryRecycleAdapter;
import com.example.receptionsystem.ApiCalling;
import com.example.receptionsystem.Model.HistoryRequestModel;
import com.example.receptionsystem.Model.HistoryResponse;
import com.example.receptionsystem.Model.HistoryVisitorResponse;
import com.example.receptionsystem.R;
import com.example.receptionsystem.SharedPrefManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryActivity extends AppCompatActivity {
    private SharedPrefManager sharedPrefManager;
    String accessToken, UserName;
    RecyclerView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        historyList = findViewById(R.id.historyList);
        sharedPrefManager = new SharedPrefManager(this);
        accessToken = "Bearer " + sharedPrefManager.getStringValue("accessToken");
        UserName = sharedPrefManager.getStringValue("username");
        historyList.setLayoutManager(new LinearLayoutManager(this));
        getResponse();
    }


    private void getResponse() {
        HistoryRequestModel historyRequestModel = new HistoryRequestModel();
        historyRequestModel.setUserId(UserName);
        ApiCalling.getService().getHistoryResponse(accessToken, historyRequestModel).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful() & response.code() == 200) {
                    HistoryResponse historyResponse = response.body();
                    List<HistoryVisitorResponse> historyResponseVisitorResponse = historyResponse.getVisitorResponse();
                    Log.d("HistoryResponse", "onResponse: Response is  "+historyResponseVisitorResponse.toString());
                    HistoryRecycleAdapter adapter = new HistoryRecycleAdapter(historyResponseVisitorResponse);
                    historyList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("TAG", t.getMessage());
            }
        });
    }
}
