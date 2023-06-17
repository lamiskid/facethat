package com.facethat;

import com.facethat.resources.HomeResources;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

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
  }

  @Override
  public void run(final FaceThatConfiguration configuration,
      final Environment environment) {
    HomeResources resource = new HomeResources(
        configuration.getTemplate(),
        configuration.getDefaultName()
    );
    environment.jersey().register(resource);
  }

}
