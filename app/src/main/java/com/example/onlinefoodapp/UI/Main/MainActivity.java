package com.example.onlinefoodapp.UI.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.Distinations.Recommend.Recommend;
import com.example.onlinefoodapp.UI.Distinations.account;
import com.example.onlinefoodapp.UI.Distinations.Card.card;
import com.example.onlinefoodapp.databinding.ActivityMainBinding;
import com.example.onlinefoodapp.UI.Distinations.Favourite.favourit;
import com.example.onlinefoodapp.UI.Distinations.home.home;
import com.example.onlinefoodapp.UI.Distinations.splash;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    FragmentManager fragmentManager;
    private Timer mTimer;
    private TimerTask mTimerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_main);
        //Inialize bottom nav
        setSplash(savedInstanceState);
        navigationBottomBar();

    }
public void navigationBottomBar(){
    binding.bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int i) {
            Fragment fragment=null;
            switch (i){
                case R.id.home2: fragment=new home();
                    break;
                case R.id.account2:fragment=new account();
                    break;
                case R.id.favourite2:fragment=new favourit();
                    break;
                case R.id.card2:fragment=new card();
                    break;
                case R.id.RecommendItem:fragment=new Recommend();
                    break;
                default:
            }
            if(fragment!=null){
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutContainer,fragment)
                        .commit();
            }
        }
    });

}
public void setSplash(Bundle savedInstanceState){
    if (savedInstanceState == null) {
        binding.bottomNav.setVisibility(View.GONE);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutContainer, new splash()).commit();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                home h=new home();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutContainer,h)
                        .commit();
                if(binding.bottomNav.getVisibility()==View.GONE){
                    binding.bottomNav.setVisibility(View.VISIBLE);
                    binding.bottomNav.setItemSelected(R.id.home2,true);
                }
            }
        }, 1500);
    }
}
}
