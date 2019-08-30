# CityProject
Backbase coding assign
# Introduction
    Outline of the app
        1.	One main activity contains two fragments, one for map, one for city list.
        2.	By default, the app orientation is portrait, but the app can be rotated to landscape
        3.	Loading data of cities when the app start
        4.	Searching the city based on the typed in letter(s)
        5.	Show the marker of the city selected from the city list on Google Map
        6.	Unit test is created based on the following criteria:
            We define a prefix string as: a substring that matches the initial characters of the target string. For instance, assume the following entries:

            * Alabama, US
            * Albuquerque, US
            * Anaheim, US
            * Arizona, US
            * Sydney, AU

            If the given prefix is "A", all cities but Sydney should appear. Contrariwise, if the given prefix is "s", the only result should be "Sydney, AU".
            If the given prefix is "Al", "Alabama, US" and "Albuquerque, US" are the only results.
            If the prefix given is "Alb" then the only result is "Albuquerque, US"
        7.	It will show the city name when click the marker on the map

# Search Problem
    1. Fetching data from cities.json file
    2. Associate with SearchView and RecyclerView to achieve searching query and showing citites data list
    3. Since the search is case insensitive, in CityListAdapter, compare the letters of city name from the left to right to see if there is matched case, then show the filtered results

# Libraries
    Please see the dependencies

# Note
    minSdkVersion 19
    targetSdkVersion 28
