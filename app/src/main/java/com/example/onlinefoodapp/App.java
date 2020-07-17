package com.example.onlinefoodapp;

import android.app.Application;

import com.example.onlinefoodapp.Repostiry.Repostiry;

public class App extends Application {
    private static App AppINSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        AppINSTANCE = this;
        Repostiry.getInstance().getAllData();
    }
    public synchronized static App getAppINSTANCE(){
        synchronized (App.class) {
            if(AppINSTANCE==null){
                AppINSTANCE=new App();
            }
        }
        return AppINSTANCE;
    }
}
