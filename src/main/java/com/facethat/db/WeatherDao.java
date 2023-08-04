package com.facethat.db;

import com.facethat.core.dto.Current;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface WeatherDao {

    @SqlUpdate("CREATE TABLE  IF NOT EXISTS weather " +
            "(id int primary key auto_increment," +
            " name varchar(100)," +
            " temp_c float, " +
            "temp_f float , " +
            "is_day int ,date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)")
    void createWeatherTable();

    @SqlUpdate("INSERT INTO weather (name,temp_c,temp_f,is_day) VALUES (:name,:temp_c,:temp_f,:is_day)")
    void insert(
            @Bind("name") String name,
            @Bind("temp_c") String temp_c,
            @Bind("temp_f") String temp_f,
            @Bind("is_day") Integer isDay);


    @SqlQuery("SELECT * FROM weather WHERE name = :name")
    Current getCity(@Bind("name") String name);

    @SqlUpdate("DELETE  FROM weather WHERE date < now() - interval 1 DAY")
    void deleteCityGreaterThanADay();

    @SqlQuery("SELECT DATE(date) FROM weather WHERE name = :name")
    String getTimeSavedToDatabase(@Bind("name") String name);

    @SqlQuery("DELETE FROM weather WHERE name = :name")
    String deleteCity(@Bind("name") String name);


}

