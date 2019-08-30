package com.example.android.cityproject.city;

import com.example.android.cityproject.model.City;

import java.util.ArrayList;

public interface CityInterface {
    interface CityRepository {
        void getCityList();
    }

    interface CityListPresenter {
        void fetchCityList();
        void onSuccess(ArrayList<City> cityList);
        void onFail();
    }

    interface CityListView {
        void setCityList(ArrayList<City> cityList);
        void showError();
        void showProgress();
        void hideProgress();
    }
}
