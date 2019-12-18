package com.any.event.config.stubhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
*
*   @author seongwou
*   @since 2019-12-18 오전 10:53
*
**/
@Configuration
@PropertySource(value = "classpath:/properties/stubhub-test.properties")
@Profile("test")
public class StubhubTestPropertiesImpl implements StubhubProperties {

    private final Environment environment;

    @Autowired
    public StubhubTestPropertiesImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String accessUrl() {
        return environment.getProperty("stubhub.access.url");
    }

    @Override
    public String eventUrl() {
        return environment.getProperty("stubhub.event.url");
    }

    @Override
    public String key() {
        return environment.getProperty("stubhub.api.key");
    }

    @Override
    public String secret() {
        return environment.getProperty("stubhub.api.secret");
    }

    @Override
    public String userName() {
        return environment.getProperty("stubhub.username");
    }

    @Override
    public String password() {
        return environment.getProperty("stubhub.password");
    }
}
