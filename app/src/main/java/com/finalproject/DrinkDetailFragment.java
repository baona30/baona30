package com.finalproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.finalproject.databinding.FragmentDrinkDetailBinding;

import java.util.ArrayList;
import java.util.Locale;

public class DrinkDetailFragment extends Fragment implements DrinkItemAdapter.ClickItem {
    private static final String KEY = "user";
    private User user;
    String drinkType;
    int imgId;
    int hour, minute;
    ArrayList<DrinkItem> drinkItems = new ArrayList<>();
    FragmentDrinkDetailBinding binding;
    DrinkItemAdapter adapter;
    ArrayAdapter<CharSequence> sp_adapter;
    RecyclerView recyclerView;
    public DrinkDetailFragment() {
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
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        drinkItems = drinkItemDb.getAllDrink(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDrinkDetailBinding.bind(inflater.inflate(R.layout.fragment_drink_detail, container, false));
        View view = binding.getRoot();
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list2);
        adapter = new DrinkItemAdapter(drinkItems,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        // set user info
        String s = MainActivity.user.name+" - weight: " + MainActivity.user.weight
                + " pounds- Amount/day: "+ MainActivity.user.amount+"ounces";
        binding.txtInfo.setText(s);
        sp_adapter = ArrayAdapter.createFromResource(context,R.array.drinktype, android.R.layout.simple_spinner_item);
        sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(sp_adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int pos, long id) {
                // An item was selected. You can retrieve the selected item using// adapterView.getItemAtPosition(pos)
                imgId = MainActivity.imageID(pos);
                drinkType = adapterView.getItemAtPosition(pos).toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
        });
        view.findViewById(R.id.btn_time).setOnClickListener(this::onTimeClick);
        view.findViewById(R.id.btn_add).setOnClickListener(this::onBtnClick);
        view.findViewById(R.id.btn_edit).setOnClickListener(this::onBtnClick);
        view.findViewById(R.id.btn_reset).setOnClickListener(this::onResetClick);
        swipeToDelete();
        return view;
    }

    void onBtnClick(View view) {
        // Check if all fields cannot be empty
        if (binding.amount.getText().toString().isEmpty()
                || binding.unit.getText().toString().isEmpty() || binding.time.getText().toString().isEmpty()){
            Message.warning("Fields cannot be empty! Please try again.",getContext());
            return;
        }
        int id = Integer.parseInt(String.valueOf(binding.txtId.getText()));
    //    String type = String.valueOf(binding.type.getText());
        String a = String.valueOf(binding.amount.getText());
        int amount = Integer.parseInt(a);
        String time = String.valueOf(binding.time.getText());
        DrinkItem drinkItem = new DrinkItem(id,MainActivity.user.email,drinkType,amount,"oz",time,imgId);
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        if(view.getId()==R.id.btn_add){
            drinkItemDb.addDrink(drinkItem);
            Toast.makeText(getContext(), "Add new successful", Toast.LENGTH_LONG).show();
        }
        if(view.getId()==R.id.btn_edit) {
            drinkItemDb.updateDrink(drinkItem);
            Toast.makeText(getContext(), "Update successful", Toast.LENGTH_LONG).show();
        }
        refresh(view);
    }
    void onResetClick(View view) {
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        drinkItemDb.deleteAll(user);
        drinkItemDb.createDrinkList(user);
        refresh(view);
    }
    void onTimeClick(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                String txtTime = String.format(Locale.getDefault(),"%02d:%02d",hour,minute);
                binding.time.setText(txtTime);
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),style,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void onClick(int position) {
        getVar(position);
    }
    public void getVar(int position){
        DrinkItem drinkItem = drinkItems.get(position);
        binding.amount.setText(String.valueOf(drinkItem.amount));
        binding.unit.setText(drinkItem.unit);
        binding.time.setText(drinkItem.time);
        binding.txtId.setText(String.valueOf(drinkItem.id));
        int spinnerPosition = sp_adapter.getPosition(drinkItem.type);
        binding.spinner.setSelection(spinnerPosition);
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
                        DrinkItem drinkItem = drinkItems.get(position);
                        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
                        drinkItemDb.deleteDrink(drinkItem);
                        // remove drink item in database
                        drinkItems.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouch = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouch.attachToRecyclerView(recyclerView);
    }
    public void refresh(View view){
        DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
        drinkItems = drinkItemDb.getAllDrink(user);
        adapter = new DrinkItemAdapter(drinkItems,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}