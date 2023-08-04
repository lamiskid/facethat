package com.facethat.core.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {


    @JsonProperty
    private Current current;


    public Weather() {
    }

    public Weather(Current current) {

        this.current = current;
    }

    public Current getCurrent() {
        return current;
    }
}
