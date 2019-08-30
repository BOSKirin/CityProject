package com.example.android.cityproject;

import androidx.annotation.NonNull;

import com.example.android.cityproject.model.City;

import java.util.ArrayList;

public class SearchUtils {
    @NonNull
    public static ArrayList<City> filterCityList(String query, ArrayList<City> cityList) {
        if (query.isEmpty()) {
            return cityList;
        }
        ArrayList<City> filteredList = new ArrayList<>();
        if (cityList != null) {
            for (City city : cityList) {
                if (city.getName().toLowerCase().startsWith(query.toLowerCase())) {
                    filteredList.add(city);
                }
            }
        }
        return filteredList;
    }

}
