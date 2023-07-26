package com.facethat.resources;

import com.facethat.core.Current;
import com.facethat.core.Weather;
import com.facethat.db.WeatherDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {


    private final String template;
    private final String defaultName;

    private final String apiKey;

    private final WeatherDao weatherDao;

    public HomeResources(String template, String defaultName, String apiKey, WeatherDao weatherDao) {
        this.template = template;
        this.defaultName = defaultName;
        this.apiKey = apiKey;
        this.weatherDao = weatherDao;
    }


    @GET
    public Response getHomeDetails() {

        return Response.ok(defaultName + "\n" + template + " This is a  simple weather App")
                       .build();
    }

    @GET
    @Path("/weather/{q}")
    public Response getWeather(@QueryParam("city") String city) throws JsonProcessingException {


        Current dbCity = weatherDao.getCity(city);

        ///Get the time a weather condition of a place is saved into the database
        String timeSavedToDatabase = weatherDao.getTimeSavedToDatabase(city);
        //Convert timeSavedToDatabase into local_day_time
        LocalDate localDateTime = LocalDate.parse(timeSavedToDatabase);


        ////The time limit which a weather data condition can stay in the database
        LocalDate timeLimit = LocalDate.now().plusDays(1);


        /// if the time is greater than a day, the city data should be deleted
        if (LocalDate.now().plusDays(1).isAfter(timeLimit)) {
            weatherDao.deleteCity(city);
        }

        // Return weather data, if the data exist in the database
        if (dbCity!=null && !localDateTime.equals(timeLimit)) {

            return Response.ok(dbCity).build();

        } else {
            Client client = ClientBuilder.newClient();
            ///Fetch Weather data form remote api
            Weather result = client.target("https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city + "")
                                   .request().get(Weather.class);

            Current current = result.getCurrent();

            ///Converts Weather  object to Json (as String)
            ObjectMapper mapper = new ObjectMapper();
            String convertedObject = mapper.writeValueAsString(result);


            ///Convert Json(as String) String to weather Json
            Weather weather = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(convertedObject, Weather.class);
            weatherDao.insert(city, current.getTemp_c(), current.getTemp_f(), current.getIs_day());
            return Response.ok(weather).build();
        }
    }


}
