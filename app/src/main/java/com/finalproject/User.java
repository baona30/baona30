package com.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String name;
    String email;
    String password;
    int weight;
    int amount;

    public User(){   }

    public User(String name, String email, String password, int weight) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.amount = (weight*2)/3;
    }
    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        weight = in.readInt();
        amount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
