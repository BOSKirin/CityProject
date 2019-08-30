package com.example.android.cityproject.city;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.android.cityproject.model.City;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CityPresenterImpl implements CityInterface.CityListPresenter{

    private final WeakReference<CityInterface.CityListView> cityListViewRef;
    private final CityRepositoryImpl cityRepository;

    public CityPresenterImpl(CityInterface.CityListView cityListView, @NonNull Context context){
        this.cityListViewRef = new WeakReference<>(cityListView);
        this.cityRepository = new CityRepositoryImpl(this, context);
    }

    @Override
    public void fetchCityList() {

        CityInterface.CityListView aboutViewImpl = cityListViewRef.get();

        aboutViewImpl.showProgress();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cityRepository.getCityList();
            }
        }, 1000);
    }

    @Override
    public void onSuccess(ArrayList<City> cityList) {

        CityInterface.CityListView cityListView = cityListViewRef.get();

        if(cityListView != null){
            cityListView.hideProgress();
            cityListView.setCityList(cityList);
        }
    }

    @Override
    public void onFail() {

        CityInterface.CityListView cityListView = cityListViewRef.get();
        if (cityListView != null){
            cityListView.hideProgress();
            cityListView.showError();
        }
    }
}
