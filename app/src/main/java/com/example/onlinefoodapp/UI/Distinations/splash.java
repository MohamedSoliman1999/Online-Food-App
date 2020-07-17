package com.example.onlinefoodapp.UI.Distinations;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.Repostiry.Repostiry;


public class splash extends Fragment {

    public splash() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repostiry.getInstance().getAllData();
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity() ,R.color.splashStatusBar));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}