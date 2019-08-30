package com.example.android.cityproject.city;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cityproject.R;
import com.example.android.cityproject.model.City;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class CityListFragment extends Fragment implements CityInterface.CityListView, CityListAdapter.OnCityClickListener{

    private static final String QUERY_STR ="query_str";

    private CityListAdapter adapter;
    private TextView emptyView;
    private View progressView;
    private SearchView searchView;
    private OnCitySelectedListener onCitySelectedListener;
    private CityInterface.CityListPresenter cityListPresenter;
    private String query;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.city_list_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        emptyView = rootView.findViewById(R.id.empty_view);
        progressView = rootView.findViewById(R.id.progress_view);
        searchView = rootView.findViewById(R.id.search_view);
        if (cityListPresenter == null) {
            cityListPresenter = new CityPresenterImpl(this, getActivity());
            cityListPresenter.fetchCityList();
        }
        if (adapter == null) {
            adapter = new CityListAdapter( this);
        }
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY_STR);
            searchView.setQuery(query, false);
        }
        searchView.clearFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        setUpSearchView();

        return rootView;
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCityClick(City city) {
        onCitySelectedListener.onCitySelected(city);
    }

    @Override
    public void setCityList(ArrayList<City> cityList) {
        adapter.updateCityList(cityList);
    }

    @Override
    public void showError() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(getString(R.string.loading_error_text));
    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    public interface OnCitySelectedListener {
        void onCitySelected(City city);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY_STR, searchView.getQuery().toString());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onCitySelectedListener = (OnCitySelectedListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnCityClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
