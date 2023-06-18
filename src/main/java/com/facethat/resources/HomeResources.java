package com.facethat.resources;

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
  public Response  getHomeDetails(){

    return Response
        .ok(defaultName+"\n"+template+" This is a  simple weather App")
        .build();
  }

  @GET
  @Path("/weather")
  public  Response getAClient(){

    Client client  = ClientBuilder.newClient();

    Object result = client.
        target("https://api.weatherapi.com/v1/current.json?key="+apiKey+"&q=lagos")
        .request().get(Object.class);

    return Response
        .ok(result)
        .build();


  }


}
