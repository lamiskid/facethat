package com.facethat.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {
    @JsonProperty
    private String temp_c;
    @JsonProperty
    private String temp_f;
    @JsonProperty
    private Integer is_day;


}
