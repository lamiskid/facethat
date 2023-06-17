package com.facethat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Home {

  private long id;
  private String description;

  public Home(long id, String description){
    this.id =id;
    this.description = description;
  }

  @JsonProperty
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @JsonProperty
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
