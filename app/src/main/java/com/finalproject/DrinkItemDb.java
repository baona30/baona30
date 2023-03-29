package com.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrinkItemDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Mool.db";
    static final int VERSION = 1;
    static final String TABLE_DRINK = "dailydrink";
    static final String ID = "id";
    static final String USER = "user";
    static final String TYPE = "type";
    static final String AMOUNT = "amount";
    static final String UNIT = "unit";
    static final String TIME = "time";
    static final String IMGID = "imageId";


    private Context context;

    public DrinkItemDb(@Nullable Context context){super(context, DATABASE_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db)  {
        //db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINK);
        onCreate(db);
    }
    public void addDrink(DrinkItem drink){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, drink.user);
        contentValues.put(TYPE, drink.type);
        contentValues.put(AMOUNT, drink.amount);
        contentValues.put(UNIT, drink.unit);
        contentValues.put(TIME, drink.time);
        contentValues.put(IMGID, drink.imageId);
        db.insert(TABLE_DRINK, null, contentValues);
        db.close();
    }
    public int updateDrink(DrinkItem drinkItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, drinkItem.user);
        contentValues.put(TYPE, drinkItem.type);
        contentValues.put(AMOUNT,drinkItem.amount);
        contentValues.put(UNIT, drinkItem.unit);
        contentValues.put(TIME, drinkItem.time);
        contentValues.put(IMGID, drinkItem.imageId);
        return db.update(TABLE_DRINK,contentValues,ID+" = ?",new String[] { String.valueOf(drinkItem.id) });
    }

    // Deleting single drink item
    public void deleteDrink(DrinkItem drink) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DRINK, ID + " = ?",
                new String[] { String.valueOf(drink.id) });
        db.close();
    }

    // Deleting all drink items
    public void deleteAll(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DRINK, USER + " = ?",
                new String[] { String.valueOf(user.email) });
        db.close();
    }
    // code to get all drinks in a list view
    public ArrayList<DrinkItem> getAllDrink(User user) {
        ArrayList<DrinkItem> drinkList = new ArrayList<DrinkItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DRINK + " WHERE user = ?";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user.email});
//        int imageType = R.drawable.water;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                String drinkType = cursor.getString(2);
//                if (drinkType.equals("tea") || drinkType.equals("Tea")) imageType = R.drawable.tea;

//                else if (drinkType.equals("coffee") || drinkType.equals("Coffee")) imageType = R.drawable.coffee;

//                else imageType = R.drawable.water;

                DrinkItem drinkItem = new DrinkItem();
//                drinkItem.imageId = imageType;
                drinkItem.id = Integer.parseInt(cursor.getString(0));
                drinkItem.user = cursor.getString(1);
                drinkItem.type = cursor.getString(2);
                drinkItem.amount = Integer.parseInt(cursor.getString(3));
                drinkItem.unit = cursor.getString(4);
                drinkItem.time = cursor.getString(5);
                drinkItem.imageId = Integer.parseInt(cursor.getString(6));
                // Adding contact to list
                drinkList.add(drinkItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return drinkList;
    }
    public void createDrinkList(User user){
        DrinkItem drinkItem;
        int itemAmount = user.amount/6;
        int lastItemAmount = user.amount - itemAmount*5;
        String curr_time = "5:30";
        String next_time;
        int imgId = MainActivity.imageID(0);
        for(int i=1; i<=6; i++){
            next_time = drinkTime(curr_time);
            if(i==6) itemAmount = lastItemAmount;
            drinkItem = new DrinkItem(0,user.email,"Water",itemAmount,"oz",next_time,imgId);
            addDrink(drinkItem);
            curr_time = next_time;
        }
    }
    public String drinkTime(String currentTime){
        int this_time =Integer.parseInt(currentTime.replace(":",""));
        int next_time;
        if (this_time%100 == 0){
            next_time = this_time + 230;
        }
        else {
            next_time = this_time + 270;
        }
        String s = String.format("%02d:%02d",next_time/100,next_time%100);
        return s;
    }
}
