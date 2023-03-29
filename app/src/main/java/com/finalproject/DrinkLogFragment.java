package com.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finalproject.databinding.FragmentDrinkLogBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DrinkLogFragment extends Fragment implements DrinkLogAdapter.ClickItem {
    private static final String KEY = "user";
    private User user;
    ArrayList<DrinkLog> logList = new ArrayList<>();
    ArrayList<DrinkLog> listLeft = new ArrayList<>();
    private String strDate ;
    FragmentDrinkLogBinding binding;
    //    ArrayList<String> data = new ArrayList<>();
    DrinkLogAdapter adapter;
    RecyclerView recyclerView;

    public DrinkLogFragment() {
        // Required empty public constructor
    }
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
        strDate = MainActivity.today();
        DrinkLogDb drinkLogDb = new DrinkLogDb(getActivity());
        logList = drinkLogDb.getUserLog(user,strDate);
        if(logList.size()==0) Message.warning("There is no drink schedule for this date, please create one",getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDrinkLogBinding.bind(inflater.inflate(R.layout.fragment_drink_log, container, false));
        View view = binding.getRoot();
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // set user info
        String hello = "Hi, "+MainActivity.user.name+'!';
        binding.txtHello.setText(hello);
        binding.txtToday.setText("Today is "+strDate);
        binding.ml.setOnClickListener( c -> u_convert("ml"));
        binding.oz.setOnClickListener( c -> u_convert("oz"));
        refresh();
        swipeToDelete();
        return view;
    }

    @Override
    public void onClick(int position) {
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
                        // delete drink item in database
                        DrinkLog drinkLog = listLeft.get(position);
                        DrinkLogDb drinkLogDb = new DrinkLogDb(getActivity());
                        // Mark item after drink
                        drinkLog.done = 1;
                        drinkLog.user = user.email;
                        drinkLogDb.updateDone(drinkLog);
                        // remove drink item in database
                        listLeft.remove(position);
                        adapter.notifyItemRemoved(position);
                        if(listLeft.size()==0) Message.info(getString(R.string.info1),getContext());
                        refresh();
                    }
                };
        ItemTouchHelper itemTouch = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouch.attachToRecyclerView(recyclerView);
    }
    public void refresh(){
        DrinkLogDb drinkLogDb = new DrinkLogDb(getActivity());
        logList = drinkLogDb.getUserLog(user,strDate);
        listLeft = logListLeft(logList);
        if(listLeft.size()==0) Message.info(getString(R.string.info1),getContext());
        adapter = new DrinkLogAdapter(listLeft,this);
        recyclerView.setAdapter(adapter);
        int sum = 0;
        int total = 0;
        int numcup = 0;
        for(int i=0;i<logList.size();i++){
            total += logList.get(i).amount;
            if (logList.get(i).done == 1) sum +=logList.get(i).amount;
            else numcup += 1;
        }
        String cupleft;
        if(numcup>1) cupleft = numcup + " cups left";
        else cupleft = numcup + " cup left";
        binding.txtLeft.setText(cupleft);
        float percent = sum*100/total;
        String s = String.format("%.2f",percent);
        binding.txtPercent.setText(s+"%");
     }
    public ArrayList<DrinkLog> logListLeft(ArrayList<DrinkLog> logList){
        ArrayList<DrinkLog> logListLeft = new ArrayList<>(logList);
        for (DrinkLog value : logList) {
            if(value.done == 1)
                logListLeft.remove(value);
        }
        return logListLeft;
    }
    public void u_convert(String s_unit){
        refresh();
        for(DrinkLog e:listLeft){
            e.unit = s_unit;
            e.amount *= unit(s_unit);
        }
        adapter.notifyDataSetChanged();
    }
    public float unit (String s_unit){
        float u_rate = 1.0F;
        if (s_unit == "ml" ) u_rate = 29.5735F;
        if (s_unit == "cup" ) u_rate = 0.125F;
        return u_rate;
    }
}