package com.example.android.cityproject.city;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.cityproject.R;
import com.example.android.cityproject.model.City;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> implements Filterable {

    private ArrayList<City> cityList;
    private ArrayList<City> cityListFiltered;
    private OnCityClickListener onCityClickListener;
    private RecyclerView recyclerView;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (cityList == null) {
                    return filterResults;
                }
                String query = charSequence.toString();
                if (query.isEmpty()) {
                    cityListFiltered = cityList;
                } else {
                    ArrayList<City> filteredList = new ArrayList<>();
                    for (City city : cityList) {
                        if (city.getName().toLowerCase().startsWith(query.toLowerCase())) {
                            filteredList.add(city);
                        }
                    }
                    cityListFiltered = filteredList;
                }


                filterResults.count = cityListFiltered.size();
                filterResults.values = cityListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                cityListFiltered = (ArrayList<City>) filterResults.values;
                recyclerView.getRecycledViewPool().clear();
                notifyDataSetChanged();
            }
        };
    }

    public CityListAdapter(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }


    public void updateCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
        this.cityListFiltered = cityList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        CityViewHolder viewHolder = new CityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityListAdapter.CityViewHolder holder, int position) {
        if (cityListFiltered == null) {
            return;
        }
        final City city = cityListFiltered.get(position);
        holder.titleView.setText(city.getName() + " " + city.getCountry());
        holder.subTitleView.setText("Long -> " + city.getLongitude() + "Lat -> " + city.getLatitude());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityClickListener.onCityClick(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityListFiltered == null ? 0 : cityListFiltered.size();
    }

    /**
     * Listener for City click event
     */
    public interface OnCityClickListener{
        void onCityClick(City city);
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView subTitleView;
        CityViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            subTitleView = itemView.findViewById(R.id.subtitle);
        }
    }
}
