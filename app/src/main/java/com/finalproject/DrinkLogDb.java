package com.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrinkLogDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Mool.db";
    static final int VERSION = 1;
    static final String TABLE_LOG = "drinklog";
    static final String ID = "id";
    static final String USER = "user";
    static final String TYPE = "type";
    static final String AMOUNT = "amount";
    static final String UNIT = "unit";
    static final String TIME = "time";
    static final String DATE = "date";
    static final String DONE = "done";
    static final String IMGID = "imageId";

    private Context context;

    public DrinkLogDb(@Nullable Context context){super(context, DATABASE_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db)  {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        onCreate(db);
    }
    public void addLog(DrinkLog drinkLog){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, drinkLog.user);
        contentValues.put(TYPE, drinkLog.type);
        contentValues.put(AMOUNT, drinkLog.amount);
        contentValues.put(UNIT, drinkLog.unit);
        contentValues.put(TIME, drinkLog.time);
        contentValues.put(DATE, drinkLog.date);
        contentValues.put(DONE, drinkLog.done);
        contentValues.put(IMGID, drinkLog.imageId);
        db.insert(TABLE_LOG, null, contentValues);
        db.close();
    }
    public int updateLog(DrinkLog drinkLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, drinkLog.user);
        contentValues.put(TYPE, drinkLog.type);
        contentValues.put(AMOUNT,drinkLog.amount);
        contentValues.put(UNIT, drinkLog.unit);
        contentValues.put(TIME, drinkLog.time);
        contentValues.put(DATE, drinkLog.date);
        contentValues.put(DONE, drinkLog.done);
        contentValues.put(IMGID, drinkLog.imageId);
        return db.update(TABLE_LOG,contentValues,ID+" = ?",new String[] { String.valueOf(drinkLog.id) });
    }

    public int updateDone(DrinkLog drinkLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DONE, drinkLog.done);
        return db.update(TABLE_LOG,contentValues,ID+" = ?",new String[] { String.valueOf(drinkLog.id) });
    }
    // Deleting single Log
    public void deleteLog(DrinkLog drinkLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOG, ID + " = ?",
                new String[] { String.valueOf(drinkLog.id) });
        db.close();
    }
    // get all the list of cups in current date
    public ArrayList<DrinkLog> getUserLog(User user, String date) {
        ArrayList<DrinkLog> logList = new ArrayList<DrinkLog>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE user = ? and date = ?";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user.email,date});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DrinkLog drinkLog = new DrinkLog();
//                drinkLog.imageId = R.drawable.water;
                drinkLog.user = cursor.getString(1);
                drinkLog.type = cursor.getString(2);
                drinkLog.amount = Integer.parseInt(cursor.getString(3));
                drinkLog.unit = cursor.getString(4);
                drinkLog.time = cursor.getString(5);
                drinkLog.id = Integer.parseInt(cursor.getString(0));
                drinkLog.date= cursor.getString(6);
                drinkLog.done = Integer.parseInt(cursor.getString(7));
                drinkLog.imageId = Integer.parseInt(cursor.getString(8));
                logList.add(drinkLog);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return log list
        return logList;
    }
    // Create new log list for the first time login in a day
    public void createLogList(ArrayList<DrinkItem> drinkList){
        DrinkLog drinkLog;
        for(int i=0; i<drinkList.size(); i++){
            DrinkItem drinkItem = drinkList.get(i);
            int id = 0;
            String user = drinkItem.user;
            String type = drinkItem.type;
            int amount = drinkItem.amount;
            String time = drinkItem.time;
            String unit = drinkItem.unit;
            int imageId = drinkItem.imageId;
            String date = MainActivity.today();
            int done = 0;
            drinkLog = new DrinkLog(id,user,type,amount,time,unit,imageId,date,done);
            addLog(drinkLog);
        }
    }
}
