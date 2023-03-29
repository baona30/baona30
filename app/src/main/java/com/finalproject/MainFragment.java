package com.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.databinding.ActivityMainBinding;
import com.finalproject.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        view.findViewById(R.id.btnStart).setOnClickListener(v ->
                controller.navigate(R.id.action_mainFragment_to_registerActivity));
        view.findViewById(R.id.Login).setOnClickListener(v ->
                controller.navigate(R.id.action_mainFragment_to_loginActivity));

        return view;
    }
}