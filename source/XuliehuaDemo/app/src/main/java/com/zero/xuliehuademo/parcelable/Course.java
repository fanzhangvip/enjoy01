package com.zero.xuliehuademo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

    private String name;
    private float score;

    public Course() {
        System.out.println("_Course: empty");
    }

    public Course(String name, float score) {
        System.out.println("_Course: " + name + " " + score);
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "_Course{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.score);
    }

    protected Course(Parcel in) {
        this.name = in.readString();
        this.score = in.readFloat();
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
