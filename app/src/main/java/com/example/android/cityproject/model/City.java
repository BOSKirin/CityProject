package com.example.android.cityproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class City implements Parcelable, Comparable<City> {

    private String name;
    private String country;

    @SerializedName("_id")
    private Integer id;
    @SerializedName("coord")
    private double latitude;
    private double longitude;


    public City(){

    }

    public City(String name, String country){
        this.name = name;
        this.country = country;
    }

    protected City(Parcel in) {
        name = in.readString();
        country = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(country);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }

        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int compareTo(City o) {
        if(name.equals(o.name)){
            return  country.compareToIgnoreCase(o.country);
        }
        return name.compareToIgnoreCase(o.name);
    }
}
