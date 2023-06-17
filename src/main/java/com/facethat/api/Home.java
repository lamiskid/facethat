package com.facethat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Home {

  private long name;
  private String description;

  public Home(long id, String description){
    this.name =id;
    this.description = description;
  }

  @JsonProperty
  public long getName() {
    return name;
  }

  public void setName(long id) {
    this.name = id;
  }

  @JsonProperty
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
