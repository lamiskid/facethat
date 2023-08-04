package com.facethat;

import com.facethat.core.dto.Current;
import com.facethat.core.service.WeatherService;
import com.facethat.db.WeatherDao;
import com.facethat.resources.HomeResources;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;


public class FaceThatApplication extends Application<FaceThatConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FaceThatApplication().run(args);
    }

    @Override
    public String getName() {
        return "facethat";
    }

    @Override
    public void initialize(final Bootstrap<FaceThatConfiguration> bootstrap) {
        // TODO: application initialization
        // Enable variable substitution with environment variables

        EnvironmentVariableSubstitutor substitutor = new EnvironmentVariableSubstitutor(false);
        SubstitutingSourceProvider provider =
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), substitutor);
        bootstrap.setConfigurationSourceProvider(provider);
    }

    @Override
    public void run(final FaceThatConfiguration configuration,
                    final Environment environment) {


        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        final WeatherDao dao = jdbi.onDemand(WeatherDao.class);


        jdbi.registerRowMapper(Current.class,
                (rs, ctx) ->
                        new Current(rs.getString(3),
                                rs.getString(4), rs.getInt(5)));

        WeatherService weatherService = new WeatherService(dao, configuration.getApiKey());

        HomeResources resource = new HomeResources(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                weatherService);
        dao.createWeatherTable();
        environment.jersey().register(resource);
        environment.jersey().register(dao);
    }

}
