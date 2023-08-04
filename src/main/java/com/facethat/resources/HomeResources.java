package com.facethat.resources;

import com.facethat.core.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {
    private final String template;
    private final String defaultName;
    private final WeatherService weatherService;


    public HomeResources(String template, String defaultName,
                         WeatherService weatherService) {
        this.template = template;
        this.defaultName = defaultName;

        this.weatherService = weatherService;
    }


    @GET
    public Response getHomeDetails() {
        return Response.ok(defaultName + "\n" + template + " This is a  simple weather App").build();
    }

    @GET
    @Path("/weather/{q}")
    public Response getWeather(@QueryParam("city") String city) throws JsonProcessingException {
        return Response.ok(weatherService.getWeatherCondition(city)).build();
    }
}
