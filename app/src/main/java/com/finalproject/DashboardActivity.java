package com.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.databinding.FragmentDashboardActivityBinding;

public class DashboardActivity extends Fragment {
    FragmentDashboardActivityBinding binding;
    public DashboardActivity() {
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
        binding = FragmentDashboardActivityBinding.bind(inflater.inflate(R.layout.fragment_dashboard_activity, container, false));
        View view = binding.getRoot();
        binding.ibtnSet.setOnClickListener(this::onSettingClick);
        return view;
    }
    public void onSettingClick(View view) {
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_dashboardActivity_to_settingsActivity);
    }
}