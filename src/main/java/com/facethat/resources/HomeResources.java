package com.facethat.resources;

import com.facethat.core.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {


    private final String template;
    private final String defaultName;

    private final String apiKey;

    public HomeResources(String template, String defaultName, String apiKey) {
        this.template = template;
        this.defaultName = defaultName;
        this.apiKey = apiKey;
    }


    @GET
    public Response getHomeDetails() {

        return Response.ok(defaultName + "\n" + template + " This is a  simple weather App")
                       .build();
    }

    @GET
    @Path("/weather")
    public Response getAClient() throws JsonProcessingException {

        Client client = ClientBuilder.newClient();
        ///Fetch Weather data form remote api
        Weather result = client.target("https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=lagos").request().get(Weather.class);

        ///Converts Weather  object to Json (as String)d
        ObjectMapper mapper = new ObjectMapper();
        String convertedObject = mapper.writeValueAsString(result);

        ///Convert Json(as String) String to weather Json
        Weather weather = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(convertedObject, Weather.class);


        return Response.ok(weather).build();


    }


}
