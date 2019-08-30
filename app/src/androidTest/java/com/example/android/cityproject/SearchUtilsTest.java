package com.example.android.cityproject;

import com.example.android.cityproject.model.City;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SearchUtilsTest {

    @Test
    public void filterCityList_emptyQuery_returnsInputList() {

        ArrayList<City> unfilteredCityList = createFakeCityList();
        ArrayList<City> filteredCityList = SearchUtils.filterCityList("", unfilteredCityList);
    }

    @Test
    public void filterCityList_withValidQuery_returnSubListWithMultipleElement() {
        ArrayList<City> unfilteredCityList = createFakeCityList();
        City sydney = unfilteredCityList.get(4);

        ArrayList<City> filteredCityList = SearchUtils.filterCityList("A", unfilteredCityList);

        Assert.assertEquals(filteredCityList.size(), 4);
        Assert.assertFalse(filteredCityList.contains(sydney));
    }

    @Test
    public void filterCityList_withValidQuery_returnSubListWithSingleElement() {
        ArrayList<City> unfilteredCityList = createFakeCityList();
        City sydney = unfilteredCityList.get(4);

        ArrayList<City> filteredCityList = SearchUtils.filterCityList("S", unfilteredCityList);

        Assert.assertEquals(filteredCityList.size(), 1);
        Assert.assertTrue(filteredCityList.contains(sydney));
    }

    @Test
    public void filterCityList_withInValidQuery_returnEmptyList() {
        ArrayList<City> unfilteredCityList = createFakeCityList();

        ArrayList<City> filteredCityList = SearchUtils.filterCityList("B", unfilteredCityList);

        Assert.assertEquals(filteredCityList.size(), 0);
    }

    private static ArrayList<City> createFakeCityList() {
        ArrayList<City> cityList = new ArrayList<>();
        cityList.add(new City("Alabama", "US"));
        cityList.add(new City("Albuquerque", "US"));
        cityList.add(new City("Anaheim", "US"));
        cityList.add(new City("Arizona", "US"));
        cityList.add(new City("Sydney", "AU"));
        return cityList;
    }

}
