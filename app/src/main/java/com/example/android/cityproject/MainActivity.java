package com.example.android.cityproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.cityproject.city.CityListFragment;
import com.example.android.cityproject.model.City;

public class MainActivity extends AppCompatActivity implements CityListFragment.OnCitySelectedListener{

    private MapFragment mapFragment;
    private boolean isDualMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        isDualMode = getResources().getBoolean(R.bool.is_dual_mode);
    }


    @Override
    public void onCitySelected(City city) {
        if (isDualMode) {
            mapFragment.updateCityLocationOnMap(city);
        } else {
            mapFragment = MapFragment.newInstance(city);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mapFragment, mapFragment.getTag())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
