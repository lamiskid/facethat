package com.facethat;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class facethatApplication extends Application<facethatConfiguration> {

  public static void main(final String[] args) throws Exception {
    new facethatApplication().run(args);
  }

  @Override
  public String getName() {
    return "facethat";
  }

  @Override
  public void initialize(final Bootstrap<facethatConfiguration> bootstrap) {
    // TODO: application initialization
  }

  @Override
  public void run(final facethatConfiguration configuration,
      final Environment environment) {
    // TODO: implement application
  }

}
