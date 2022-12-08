package com.example.receptionsystem;

import com.example.receptionsystem.Model.HistoryRequestModel;
import com.example.receptionsystem.Model.HistoryResponse;
import com.example.receptionsystem.Model.ModelClass;
import com.example.receptionsystem.Model.RegistrationModel;
import com.example.receptionsystem.Model.RegistrationResponseModel;
import com.example.receptionsystem.Model.ResponseData;
import com.example.receptionsystem.Model.StatusResp;
import com.example.receptionsystem.Model.TokenModel;
import com.example.receptionsystem.Model.VisitorExitModel;
import com.example.receptionsystem.Model.VisitorModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class ApiCalling {
    private static final String url = "http://192.168.0.66:8181/VisitorMamagementApp/";
    public static postservice postservice = null;


    public static postservice getService() {
        if (postservice == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES);
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(interceptor);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            postservice = retrofit.create(postservice.class);
        }
        return postservice;
    }

    //.addConverterFactory(ScalarsConverterFactory.create())
    //http://192.168.0.131:9090/VisitorMamagementApp/visitor-api/registration
    // http://192.168.0.131:9090/VisitorMamagementApp/file/imageUpload
    // Call<String> getImage(@Header("Authorization") String authHeader, @Body MultipartBody.Part path);
   // http://192.168.0.71:8181/VisitorMamagementApp/visitor-api/visitors
    //http://192.168.0.66:8181/VisitorMamagementApp/visitor-api/visitorExit
    //http://192.168.0.66:8181/VisitorMamagementApp/visitor-api/visitorsHistory
    public interface postservice {
        @POST("auth/login")
        Call<TokenModel> getPostsCall(
                @Body ModelClass ModelClass);

        @POST("visitor-api/registration")
        Call<RegistrationResponseModel> getRegistration(
                @Header("Authorization") String authHeader, @Body RegistrationModel registrationModel);

        @Multipart
        @POST("file/imageUpload")
        Call<StatusResp> getImage(@Header("Authorization") String authHeader, @Part MultipartBody.Part path);

        @POST("visitor-api/visitors")
        Call<ResponseData> getResponse(@Header("Authorization")String authHeader, @Body VisitorModel visitorModel);

        @POST("visitor-api/visitorExit")
        Call<String> setOutTime(@Header("Authorization") String authHeader,@Body VisitorExitModel visitorExit);

        @POST("visitor-api/visitorsHistory")
        Call<HistoryResponse> getHistoryResponse(@Header("Authorization") String authHeader, @Body HistoryRequestModel historyRequestModel);
    }
}
