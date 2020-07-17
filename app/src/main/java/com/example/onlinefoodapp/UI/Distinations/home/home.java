package com.example.onlinefoodapp.UI.Distinations.home;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlinefoodapp.Models.Allmenu;
import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.Models.Recommended;
import com.example.onlinefoodapp.Models.SliderItem;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.Distinations.SliderAdapterExample;
import com.example.onlinefoodapp.UI.Main.Adapters.AllMenuAdapter;
import com.example.onlinefoodapp.UI.Main.Adapters.PopularAdapter;
import com.example.onlinefoodapp.UI.Main.Adapters.RecommendedAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class home extends Fragment {
    HomeViewModel homeViewModel;
    RecyclerView allMenuRecyclerView;
    RecyclerView popularRecyclerView;
    RecyclerView recommendedRecyclerView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;

    //slider
    SliderView sliderView;
    SliderAdapterExample adapter;
    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity() ,R.color.homeStatusBar));
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        popularRecyclerView =fragmentView.findViewById(R.id.popular_recycler);
        allMenuRecyclerView = fragmentView.findViewById(R.id.all_menu_recycler);
        sliderView = fragmentView.findViewById(R.id.imageSlider);
        getStarWithMVVM();
        setupSlider();
        renewItems();
        return fragmentView;
    }

    public void getStarWithMVVM(){
        homeViewModel=  ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getAllData();
        //setPopularData();
        //setRecomendedData();
        setAllMenu();
    }
    public void setPopularData(){

        popularAdapter = new PopularAdapter( getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);
        homeViewModel.popularList.observe(getActivity(), new Observer<List<Popular>>() {
            @Override
            public void onChanged(List<Popular> populars) {
                popularAdapter.setList(populars);
            }
        });
    }
    public void setRecomendedData(){
        recommendedAdapter = new RecommendedAdapter( getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        homeViewModel.recommendedList.observe(getActivity(), new Observer<List<Recommended>>() {
            @Override
            public void onChanged(List<Recommended> recommendeds) {
                recommendedAdapter.setList(recommendeds);
            }
        });
    }
    public void setAllMenu(){
        allMenuAdapter = new AllMenuAdapter(getActivity(),0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        homeViewModel.allmenuList.observe(getActivity(), new Observer<List<Allmenu>>() {
            @Override
            public void onChanged(List<Allmenu> allmenus) {
                allMenuAdapter.setList(allmenus);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void setupSlider(){

        adapter = new SliderAdapterExample(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
}
    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("14 Spanish dishes you should try");
            if(i==4){
                sliderItem.setImageUrl("https://content.skyscnr.com/m/68728a902d354b72/original/GettyImages-475527971.jpg");
            }
            else if (i % 2 == 0) {
                sliderItem.setImageUrl("https://cdn.cnn.com/cnnnext/dam/assets/160929095729-essential-spanish-dish-fideua-brindisa.jpg");
            } else {
                sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS2LFtY80dfpNAf6kqy_8RX3shd7cPUp0OsTQ&usqp=CAU");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

    }
}
