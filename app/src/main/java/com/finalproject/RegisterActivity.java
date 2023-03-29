package com.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finalproject.databinding.FragmentMainBinding;
import com.finalproject.databinding.FragmentRegisterActivityBinding;

public class RegisterActivity extends Fragment {
    FragmentRegisterActivityBinding binding;
    private UserDb userDb;
    Context context;
    public RegisterActivity() {
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
        binding = FragmentRegisterActivityBinding.bind(inflater.inflate(R.layout.fragment_register_activity,container,false));
        View view = binding.getRoot();
        context = view.getContext();
        binding.btnRegister.setOnClickListener(this::onRegClick);
        return view;
    }
    public void onRegClick(View view) {
        String name = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        String confirm = binding.confirm.getText().toString();
        String w = binding.weight.getText().toString();
        int weight = 0;
        if(!w.isEmpty()){
            weight = Integer.parseInt(w);
        }
        // If all entries are valid, open database and add new user
        User user = new User(name,email,password,weight);
        UserDb userDb = new UserDb(getActivity());
        if (chk_register(name,email,password,confirm)) {
            userDb.addUser(user);
            userDb.close();
            DrinkItemDb drinkItemDb = new DrinkItemDb(getActivity());
            drinkItemDb.createDrinkList(user);
            drinkItemDb.close();
            Toast.makeText(context, "Register successful",
                    Toast.LENGTH_LONG).show();
            NavController controller = NavHostFragment.findNavController(this);
            controller.navigate(R.id.action_registerActivity_to_loginActivity);
        }
    }
    public boolean chk_register(String name, String email, String password, String confirm){
        // Toast a message for empty entry
        if (name.isEmpty()) {
            Toast.makeText(context, "Please enter a valid name",
                    Toast.LENGTH_LONG).show();
            //warn("Please enter a valid name");
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter a valid Email",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length()<6) {
            Toast.makeText(context, "Please enter a valid password with 6 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.compareTo(confirm) != 0) {
            Toast.makeText(context, "Confirm password is wrong!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}