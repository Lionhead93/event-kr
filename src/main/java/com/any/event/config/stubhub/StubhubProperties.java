package com.any.event.config.stubhub;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
*
*   @author seongwou
*   @since 2019-12-18 오전 10:50
*
**/
@ConfigurationProperties(prefix = "stubhub")
@Component
public abstract class StubhubProperties {

    public abstract String accessUrl();

    public abstract String eventUrl();

    public abstract String key();

    public abstract String secret();

    public abstract String userName();

    public abstract String password();

}
