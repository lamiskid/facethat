package com.facethat.core.service;

import com.facethat.core.dto.Current;
import com.facethat.core.dto.Weather;
import com.facethat.db.WeatherDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.ClientBuilder;


public class WeatherService {

    private final WeatherDao weatherDao;

    private final String apiKey;

    public WeatherService(WeatherDao weatherDao, String apiKey) {
        this.weatherDao = weatherDao;
        this.apiKey = apiKey;
    }


    public Current getWeatherCondition(String city) throws JsonProcessingException {

        Current cacheData = getCacheData(city);
        if (cacheData!=null)
            return cacheData;
        else {

            Weather weather = requestApiData(city);

            ObjectMapper mapper = new ObjectMapper();

            Weather weatherToCurrent =
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
                          readValue(mapper.writeValueAsString(weather), Weather.class);


            Current current = weatherToCurrent.getCurrent();

            cacheDataToDb(current, city);
            return current;
        }

    }

    private Weather requestApiData(String city) {

        Weather weather = ClientBuilder
                .newClient().
                target("https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city + "")
                .request()
                .get(Weather.class);

        return weather;


    }

    private Current getCacheData(String city) {

        weatherDao.deleteCityGreaterThanADay();
        
        Current dbCity = weatherDao.getCity(city);
        return dbCity;
    }

    private void cacheDataToDb(Current current, String city) {

        if (weatherDao.getCity(city)==null) {
            weatherDao.insert(city, current.getTemp_c(), current.getTemp_f(), current.getIs_day());
        }


    }


}
