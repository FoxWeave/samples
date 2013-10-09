package com.cloudbees.weave.sample.shopping.guice;

import com.cloudbees.weave.sample.shopping.CustomerService;
import com.cloudbees.weave.sample.shopping.ShipmentService;
import com.google.common.collect.ImmutableMap;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.map.ObjectMapper;

public class Module extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class);
        bind(CustomerService.class).asEagerSingleton(); // Need to do this, otherwise a new instance is created for each request!!
        bind(ShipmentService.class).asEagerSingleton(); // Need to do this, otherwise a new instance is created for each request!!
        bind(JacksonConfigurator.class);
        bind(ObjectMapper.class).toProvider(JacksonConfigurator.class).asEagerSingleton();

        ImmutableMap<String, String> guiceConfig = ImmutableMap.of(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        serve("/*").with(GuiceContainer.class, guiceConfig);
    }
}
