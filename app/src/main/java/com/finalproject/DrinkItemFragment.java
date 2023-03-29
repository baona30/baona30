package com.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class DrinkItemFragment extends Fragment implements DrinkItemAdapter.ClickItem {
    private static final String KEY = "user";
    private User user;

    public DrinkItemFragment() {
    }

    ArrayList<DrinkItem> drinkItems = new ArrayList<>();
    //    ArrayList<String> data = new ArrayList<>();
    DrinkItemAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(KEY);
        }
        else{
             user = MainActivity.user;
        }
        // Get data from Table dailydrink of Mool.db
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        drinkItems = drinkItemDb.getAllDrink(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_item_list, container, false);
        /* Get data from Table dailydrink of Mool.db
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        data = (ArrayList<String>) drinkItemDb.getAllDrink(); */
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            adapter = new DrinkItemAdapter(drinkItems,this);
            recyclerView.setAdapter(adapter);
        }
        swipeToDelete();
        return view;
    }
    @Override
    public void onClick(int position){
        if (position == drinkItems.size()-1) {
            warn(position);
        }
        else goDetail(position);
    }
    public void warn(int position){
        // TODO: only need positive and negative button
        // if positive, goDetail(position) else do nothing
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Warning");
        builder.setMessage("This group paused their activities, are you sure you want to know them?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            goDetail(position);
        });
        builder.setNegativeButton("No", (dialog, id) -> {});
        builder.create().show();
    }
    public void goDetail(int position){
        DrinkItem player = drinkItems.get(position);
        // TODO: Use a navController to start navigation from Mainfragment to Detailfragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("player",player);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_drinkItemFragment_to_drinkDetailFragment,bundle);
    }

    public void swipeToDelete(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override public boolean onMove(@NonNull RecyclerView recyclerView,
                                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                                    @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = viewHolder.getLayoutPosition();
                        drinkItems.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouch = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouch.attachToRecyclerView(recyclerView);
    }

}