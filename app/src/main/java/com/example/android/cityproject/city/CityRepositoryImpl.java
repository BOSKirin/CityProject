package com.example.android.cityproject.city;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android.cityproject.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public class CityRepositoryImpl implements CityInterface.CityRepository {
    private static final String TAG = CityRepositoryImpl.class.getSimpleName();
    private final CityInterface.CityListPresenter presenter;
    private final WeakReference<Context> context;
    private static final String FILE_NAME = "cities.json";
    private ArrayList<City> cityList;

    public CityRepositoryImpl(@NonNull CityInterface.CityListPresenter presenter, @NonNull Context context){
        this.presenter = presenter;
        this.context = new WeakReference<>(context);
    }

    @Override
    public void getCityList() {

        if (cityList == null) {
            String citiesJson = getCityListInfoFromAssets();
            if(citiesJson != null && !citiesJson.isEmpty()) {
                cityList = parseCityListJson(citiesJson);
            }
        }
        if (!cityList.isEmpty()){
            presenter.onSuccess(cityList);
            return;
        }
        presenter.onFail();
    }

    private ArrayList<City> parseCityListJson(String citiesJson) {
        ArrayList<City> cityList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(citiesJson);
            for(int idx = 0 ; idx <jsonArray.length() ; idx++) {
                JSONObject jsonObject = jsonArray.getJSONObject(idx);
                City city = new City();
                city.setName(jsonObject.getString("name"));
                city.setCountry(jsonObject.getString("country"));
                city.setId(jsonObject.getInt("_id"));
                city.setId(jsonObject.getInt("_id"));
                JSONObject coordJsonObject = jsonObject.getJSONObject("coord");
                city.setLatitude(coordJsonObject.getDouble("lat"));
                city.setLongitude(coordJsonObject.getDouble("lon"));
                cityList.add(city);
            }
            Collections.sort(cityList);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return cityList;
    }

    private String getCityListInfoFromAssets() {

        if(context.get() != null){
            try{
                AssetManager manager = context.get().getAssets();
                InputStream file = manager.open(FILE_NAME);
                byte[] formArray = new byte[file.available()];
                file.read(formArray);
                file.close();
                return new String(formArray);
            }catch (IOException ex){

                Log.e(TAG, ex.getLocalizedMessage(), ex);
            }
        }

        return null;
    }

}
