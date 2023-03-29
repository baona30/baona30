package com.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Mool.db";
    static final int VERSION = 1;
    static final String TABLE_USER = "user";
    static final String NAME = "name";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    static final String WEIGHT = "weight";
    static final String AMOUNT = "amount";

    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_USER + " (" 
                    + NAME + " TEXT NOT NULL, "
                    + EMAIL + " TEXT PRIMARY KEY NOT NULL, "
                    + PASSWORD + " TEXT NOT NULL, " 
                    + WEIGHT+ " INTEGER NOT NULL, "
                    + AMOUNT+" INTEGER NOT NULL);";


    static final String TABLE_DRINK = "dailydrink";
    static final String ID = "id";
    static final String USER = "user";
    static final String TYPE = "type";
    static final String UNIT = "unit";
    static final String TIME = "time";
    static final String IMGID = "imageId";

    static final String CREATE_TABLE2 =
            " CREATE TABLE " + TABLE_DRINK + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER + " TEXT NOT NULL, "
                    + TYPE + " TEXT NOT NULL, "
                    + AMOUNT + " INTEGER NOT NULL, "
                    + UNIT + " TEXT NOT NULL, "
                    + TIME+ " TEXT NOT NULL,"
                    + IMGID+ " INTEGER);";

    static final String TABLE_LOG = "drinklog";
    static final String DATE = "date";
    static final String DONE = "done";

    static final String CREATE_TABLE3 =
            " CREATE TABLE " + TABLE_LOG + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER + " TEXT NOT NULL, "
                    + TYPE + " TEXT NOT NULL, "
                    + AMOUNT + " INTEGER NOT NULL, "
                    + UNIT + " TEXT NOT NULL, "
                    + TIME+ " TEXT NOT NULL, "
                    + DATE + " TEXT NOT NULL, "
                    + DONE + " INTEGER NOT NULL,"
                    + IMGID + " INTEGER);";

    private Context context;
    public UserDb(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addUser(User user){
//        if (chk_register(user.name,user.email,user.password,confirm)) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, user.name);
            contentValues.put(EMAIL, user.email);
            contentValues.put(PASSWORD, user.password);
            contentValues.put(WEIGHT, user.weight);
            contentValues.put(AMOUNT, user.amount);
            db.insert(TABLE_USER, null, contentValues);
            db.close();
//        }
    }

    // code to get the single user
    public User getUser(String email) {
        User user;
        SQLiteDatabase db = getReadableDatabase();
        String[] colunms = new String[] {NAME,EMAIL,PASSWORD,WEIGHT,AMOUNT};
        Cursor cursor = db.query(TABLE_USER, colunms, EMAIL + "=?",
                new String[] { String.valueOf(email) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
            cursor.close();
        }
        else {
            user = new User("","","",0);
        }
        // return user
        return user;
    }

    // code to update the single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, user.name);
        contentValues.put(EMAIL, user.email);
        contentValues.put(PASSWORD, user.password);
        contentValues.put(WEIGHT, user.weight);
        contentValues.put(AMOUNT, user.amount);
        return db.update(TABLE_USER,contentValues,EMAIL+" = "+user.email,null);
    }

    // Deleting single contact
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, EMAIL + " = ?",
                new String[] { String.valueOf(user.email) });
        db.close();
    }

    // code to get all contacts in a list view
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.name = cursor.getString(0);
                user.email = cursor.getString(1);
                user.password = cursor.getString(2);
                user.weight = Integer.parseInt(cursor.getString(3));
                user.amount = Integer.parseInt(cursor.getString(4));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userList;
    }

}
