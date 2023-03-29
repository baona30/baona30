package com.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DrinkLog implements Parcelable{
    int id;
    String user;
    String type;
    int amount;
    String time;
    String unit;
    int imageId;
    String date;
    int done;
    public DrinkLog(){}

    public DrinkLog(int id, String user, String type, int amount, String time, String unit, int imageId, String date, int done) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.unit = unit;
        this.imageId = imageId;
        this.date = date;
        this.done = done;
    }
    protected DrinkLog(Parcel in) {
        user = in.readString();
        type = in.readString();
        amount = in.readInt();
        unit = in.readString();
        time = in.readString();
        imageId = in.readInt();
        date = in.readString();
        done = in.readInt();
    }
    public static final Creator<DrinkLog> CREATOR = new Creator<DrinkLog>() {
        @Override
        public DrinkLog createFromParcel(Parcel in) {
            return new DrinkLog(in);
        }
        @Override
        public DrinkLog[] newArray(int size) {
            return new DrinkLog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user);
        parcel.writeString(type);
        parcel.writeInt(amount);
        parcel.writeString(unit);
        parcel.writeString(time);
        parcel.writeInt(imageId);
        parcel.writeString(date);
        parcel.writeInt(done);
    }
}
