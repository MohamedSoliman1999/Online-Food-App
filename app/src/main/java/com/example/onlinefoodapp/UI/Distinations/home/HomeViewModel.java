package com.example.onlinefoodapp.UI.Distinations.home;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.onlinefoodapp.App;
import com.example.onlinefoodapp.Models.Allmenu;
import com.example.onlinefoodapp.Models.FoodData;
import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.Models.Recommended;
import com.example.onlinefoodapp.Network.RetrofitClient;
import com.example.onlinefoodapp.Repostiry.Repostiry;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends ViewModel {
    public MutableLiveData<List<Popular>> popularList=new MutableLiveData<>();
    public MutableLiveData<List<Recommended>>recommendedList=new MutableLiveData<>();
    public MutableLiveData<List<Allmenu>>allmenuList=new MutableLiveData<>();
    @Override
    protected void onCleared() {
        super.onCleared();
    }
    public void getAllData(){
        popularList.setValue(Repostiry.getInstance().getPopularList());
        recommendedList.setValue(Repostiry.getInstance().getRecommendedList());
        allmenuList.setValue(Repostiry.getInstance().getAllmenuList());
       /* Call<List<FoodData>> call= RetrofitClient.getINSTANCE().getData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
                if(response.isSuccessful()&&response!=null)
                {
                    //foodDataList.setValue(response.body().get(0));
                    popularList.setValue(response.body().get(0).getPopular());
                    recommendedList.setValue(response.body().get(0).getRecommended());
                    allmenuList.setValue(response.body().get(0).getAllmenu());
                }else{
                    Toast.makeText(App.getAppINSTANCE().getApplicationContext(),"Cant Load the data",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable t) {
                Toast.makeText(App.getAppINSTANCE().getApplicationContext(),"Server is not Reponding",Toast.LENGTH_LONG).show();
            }
        });*/
    }
}