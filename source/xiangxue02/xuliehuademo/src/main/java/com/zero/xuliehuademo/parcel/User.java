package com.zero.xuliehuademo.parcel;

import android.os.Bundle;
import android.os.Parcel;

public class User implements Parcelable {

    private String name;
    private int age;

    public User(Parcel in){
        name = in.readString();
        age = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Parcelable.Creator<User> CREATEOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}
