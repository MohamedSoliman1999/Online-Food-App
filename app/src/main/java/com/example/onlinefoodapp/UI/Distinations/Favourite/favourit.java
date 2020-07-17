package com.example.onlinefoodapp.UI.Distinations.Favourite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinefoodapp.Models.Allmenu;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.Distinations.home.HomeViewModel;
import com.example.onlinefoodapp.UI.Main.Adapters.AllMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class favourit extends Fragment {
    private HomeViewModel mViewModel;
    RecyclerView favouriteMenuRecyclerView;
    AllMenuAdapter allMenuAdapter;

    public favourit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favourit, container, false);
        favouriteMenuRecyclerView=fragmentView.findViewById(R.id.favourite_menu_recycler);
        getStarWithMVVM();
        return fragmentView;
    }
    public void getStarWithMVVM(){
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.getAllData();
        setAllMenu();
    }
    public void setAllMenu(){
        allMenuAdapter = new AllMenuAdapter(getActivity(),1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favouriteMenuRecyclerView.setLayoutManager(layoutManager);
        favouriteMenuRecyclerView.setAdapter(allMenuAdapter);
        mViewModel.allmenuList.observe(getActivity(), new Observer<List<Allmenu>>() {
            @Override
            public void onChanged(List<Allmenu> allmenus) {
                List<Allmenu> temp=new ArrayList<>();
                Allmenu t=allmenus.get(2);
                t.setDeliveryCharges("Free");
                temp.add(t);
                allMenuAdapter.setList(temp);
            }
        });
    }
}