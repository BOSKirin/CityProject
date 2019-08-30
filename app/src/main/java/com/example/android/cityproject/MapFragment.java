package com.example.android.cityproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cityproject.model.City;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private static final String CITY_EXTRA = "city_extra";

    private GoogleMap mMap;
    private City city;


    public static MapFragment newInstance(City city) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelable(CITY_EXTRA, city);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle args = getArguments();
        if (args != null) {
            city = args.getParcelable(CITY_EXTRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        View headerBar = rootView.findViewById(R.id.header_bar);
        if (getResources().getBoolean(R.bool.is_dual_mode)) {
            headerBar.setVisibility(View.GONE);
        }
        final View backButton = headerBar.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showCityLocationOnMap();
    }

    public void updateCityLocationOnMap(City city) {
        this.city = city;
        showCityLocationOnMap();
    }

    private void showCityLocationOnMap() {
        if (city == null) {
            return;
        }
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng location = new LatLng(city.getLatitude(), city.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(city.getName()));
        marker.setTag(city);
        builder.include(location);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 5f));
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

}
