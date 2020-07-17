package com.example.onlinefoodapp.Repostiry;

import android.widget.Toast;

import com.example.onlinefoodapp.App;
import com.example.onlinefoodapp.Models.Allmenu;
import com.example.onlinefoodapp.Models.FoodData;
import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.Models.Recommended;
import com.example.onlinefoodapp.Network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repostiry {
    private static Repostiry INSTANCE;
    private List<Popular> popularList=new ArrayList<>();
    private List<Recommended>recommendedList=new ArrayList<>();
    private List<Allmenu>allmenuList=new ArrayList<>();

    public Repostiry() {
        getAllData();
    }
    public static synchronized Repostiry getInstance(){
        if(INSTANCE==null){
            INSTANCE=new Repostiry();
        }
        return INSTANCE;
    }
    public List<Popular> getPopularList() {
        return popularList;
    }

    public List<Recommended> getRecommendedList() {
        return recommendedList;
    }

    public List<Allmenu> getAllmenuList() {
        return allmenuList;
    }
    public void getAllData(){
        Call<List<FoodData>> call= RetrofitClient.getINSTANCE().getData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
                if(response.isSuccessful()&&response!=null)
                {
                    //foodDataList.setValue(response.body().get(0));
                    popularList=response.body().get(0).getPopular();
                    recommendedList=response.body().get(0).getRecommended();
                    allmenuList=response.body().get(0).getAllmenu();
                }else{
                    Toast.makeText(App.getAppINSTANCE().getApplicationContext(),"Cant Load the data",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable t) {
                Toast.makeText(App.getAppINSTANCE().getApplicationContext(),"Server is not Reponding",Toast.LENGTH_LONG).show();
            }
        });
    }
}
