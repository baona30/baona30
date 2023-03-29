package com.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finalproject.databinding.FragmentLoginActivityBinding;
import com.finalproject.databinding.FragmentRegisterActivityBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends Fragment {
    FragmentLoginActivityBinding binding;
    private UserDb userDb;
    Context context;

    public LoginActivity() {
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
        binding = FragmentLoginActivityBinding.bind(inflater.inflate(R.layout.fragment_login_activity,container,false));
        View view = binding.getRoot();
        context = view.getContext();
        view.findViewById(R.id.btnLogin).setOnClickListener(this::onLogClick);
    //               controller.navigate(R.id.action_loginActivity_to_settingsActivity));
        return view;
    }
    public void onLogClick(View view) {
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        // If all email and password matched, return true
        UserDb userDb = new UserDb(getActivity());
        User user = userDb.getUser(email);
        MainActivity.user = user;
        if (chk_user(user, email, password)) {
            String date = MainActivity.today();
            if(!chk_log(user,date)) {
                DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
                ArrayList<DrinkItem> drinkList = drinkItemDb.getAllDrink(user);
                DrinkLogDb drinkLogDb = new DrinkLogDb(getActivity());
                drinkLogDb.createLogList(drinkList);
            }
            Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user",user);
            NavController controller = NavHostFragment.findNavController(this);
            controller.navigate(R.id.action_loginActivity_to_drinkLogFragment,bundle);
        }
        else {
            Message.warning( "Login failed, Try again!",getContext());
        }
    }
    public boolean chk_user(User user, String email, String password) {
        if (user.email.compareTo(email)!=0 || user.password.compareTo(password)!=0 || email.isEmpty() || password.isEmpty() ){
            return false;
        }
        return true;
    }
    public boolean chk_log(User user, String date) {
        DrinkLogDb drinkLogDb = new DrinkLogDb(getActivity());
        ArrayList<DrinkLog> logList = drinkLogDb.getUserLog(user,date);
        return logList.size() > 0;
//        return (logList.get(0).user.equals(user.email) && logList.get(0).date.equals(date) );
    }
}