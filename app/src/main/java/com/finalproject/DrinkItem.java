package com.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class DrinkItem implements Parcelable {
    int id;
    String user;
    String type;
    int amount;
    String time;
    String unit;
    int imageId;

    public DrinkItem(){   }

    public DrinkItem(int id, String user, String type, int amount, String unit, String time, int imageId) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.unit = unit;
        this.time = time;
        this.imageId = imageId;
    }

    protected DrinkItem(Parcel in) {
        user = in.readString();
        type = in.readString();
        amount = in.readInt();
        unit = in.readString();
        time = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<DrinkItem> CREATOR = new Creator<DrinkItem>() {
        @Override
        public DrinkItem createFromParcel(Parcel in) {
            return new DrinkItem(in);
        }

        @Override
        public DrinkItem[] newArray(int size) {
            return new DrinkItem[size];
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
    }
}
