package com.example.onlinefoodapp.Network;

import com.example.onlinefoodapp.Models.FoodData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/foodapp/";
//---------------------------------------
    private static ApiInterface apiInterface;
    private static RetrofitClient INSTANCE;
    private RetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface=retrofit.create(ApiInterface.class);
    }
    public static synchronized RetrofitClient getINSTANCE() {
        if(INSTANCE==null) {
            INSTANCE=new RetrofitClient();
        }
        return INSTANCE;
    }
    public Call<List<FoodData>> getData() {
        return apiInterface.getAllData();
    }
}
