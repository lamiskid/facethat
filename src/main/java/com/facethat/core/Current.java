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

    public Current() {
    }

    public Current(String temp_c, String temp_f) {
        this.temp_c = temp_c;
        this.temp_f = temp_f;
    }


    public String getTemp_c() {
        return temp_c;
    }

    public String getTemp_f() {
        return temp_f;
    }

    public Integer getIs_day() {
        return is_day;
    }
}
