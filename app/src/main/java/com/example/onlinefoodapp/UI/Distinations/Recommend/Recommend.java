package com.example.onlinefoodapp.UI.Distinations.Recommend;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.Models.Recommended;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.Distinations.home.HomeViewModel;
import com.example.onlinefoodapp.UI.Main.Adapters.PopularAdapter;
import com.example.onlinefoodapp.UI.Main.Adapters.RecommendedAdapter;

import java.util.List;

public class Recommend extends Fragment {

    private HomeViewModel mViewModel;
    RecyclerView recommendedRecyclerView;
    RecyclerView popularRecyclerView;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    public static Recommend newInstance() {
        return new Recommend();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.recommend_fragment, container, false);
        recommendedRecyclerView=fragmentView.findViewById(R.id.recommended_recycler);
        popularRecyclerView=fragmentView.findViewById(R.id.popular_recycler);
        getStarWithMVVM();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void getStarWithMVVM(){
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.getAllData();
        setRecomendedData();
        setPopularData();
    }
    public void setRecomendedData(){
        recommendedAdapter = new RecommendedAdapter( getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        mViewModel.recommendedList.observe(getActivity(), new Observer<List<Recommended>>() {
            @Override
            public void onChanged(List<Recommended> recommendeds) {
                recommendedAdapter.setList(recommendeds);
            }
        });
    }
    public void setPopularData(){

        popularAdapter = new PopularAdapter( getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);
        mViewModel.popularList.observe(getActivity(), new Observer<List<Popular>>() {
            @Override
            public void onChanged(List<Popular> populars) {
                popularAdapter.setList(populars);
            }
        });
    }
}