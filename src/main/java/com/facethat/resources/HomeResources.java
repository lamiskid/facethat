package com.facethat.resources;

import com.codahale.metrics.SlidingTimeWindowReservoir;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {



  private final String template;
  private final String defaultName;

  public HomeResources(String template, String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
  }


  @GET
  public Response  getHomeDetails(){

    return Response
        .ok(""+template+" This is a  simple weather App"+defaultName+"")
        .build();
  }


}
