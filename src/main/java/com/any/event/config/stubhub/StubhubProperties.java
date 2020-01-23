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
public class StubhubProperties {

    private String accessUrl;
    private String eventUrl;
    private String key;
    private String secret;
    private String userName;
    private String password;

    public String accessUrl() {
        return this.accessUrl;
    }

    public String eventUrl() {
        return this.eventUrl;
    }

    public String key(){
        return this.key;
    }

    public String secret() {
        return this.secret;
    }

    public String userName() {
        return this.userName;
    }

    public String password() {
        return this.password;
    }

}
