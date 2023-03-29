package com.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.uninstall){
            // TODO: finish it, and declare permission in manifest (project 1)
            Intent delete = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + getPackageName()));
            startActivity(delete);
        }
        else {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            assert navHostFragment != null;
            NavController controller = navHostFragment.getNavController();
            // using a NavController
            if(item.getItemId() == R.id.logout) System.exit(0);
            if(item.getItemId() == R.id.drinklist) {
                //Check if login done
                if(user !=null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    controller.navigate(R.id.action_global_drinkDetailFragment, bundle);}
                else {
                    Message.warning("Please login first",this);
                    controller.navigate(R.id.action_global_loginActivity);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
     public static String today(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }
    public static int imageID(int pos){
        int imgId = 0;
        switch (pos){
            case 0:
                imgId = R.drawable.water;
                break;
            case 1:
                imgId = R.drawable.coffee;
                break;
            case 2:
                imgId = R.drawable.tea;
                break;
            case 3:
                imgId = R.drawable.juice;
                break;
        }
        return imgId;
    }

}