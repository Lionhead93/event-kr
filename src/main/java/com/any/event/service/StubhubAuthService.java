package com.any.event.service;

import com.any.event.config.stubhub.StubhubProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
public class StubhubAuthService {

    private String STUB_TOKEN;
    private final RestTemplate restTemplate;
    private final StubhubProperties stubhubProperties;
    @Autowired
    public StubhubAuthService(RestTemplate restTemplate, StubhubProperties stubhubProperties) {
        this.restTemplate = restTemplate;
        this.stubhubProperties = stubhubProperties;
    }

    //@PostConstruct
    public void init(){
        log.info("init Time : ########{}##########",System.currentTimeMillis());
        getAcessTokenRequest();
    }

    private void getAcessTokenRequest(){
        String fm = String.format("%s:%s",stubhubProperties.key(),stubhubProperties.secret());
        log.info("format : #############{}##############", fm);
        String encodeString = Base64.getEncoder().encodeToString(fm.getBytes());
        log.info("encodeString : {}",encodeString);

        HttpEntity<?> reqBody = apiClientHttpEntity(encodeString);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(stubhubProperties.accessUrl())
                .queryParam("grant_type","client_credentials");

        ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<HashMap<String, Object>>() {};
        ResponseEntity<HashMap<String, Object>> response = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, reqBody, responseType);

        log.info("response ==> {}", response);
        STUB_TOKEN = (String) Objects.requireNonNull(response.getBody()).get("access_token");
        log.info("Token ==> {}", STUB_TOKEN);
    }

    private HttpEntity<?> apiClientHttpEntity(String auth) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", String.format("Basic %s", auth));
        requestHeaders.set("Content-Type", "application/json");
        requestHeaders.set("Host", "api.stubhub.com");
        HashMap<String, String> reqBody = new HashMap<>();
        reqBody.put("username",stubhubProperties.userName());
        reqBody.put("password",stubhubProperties.password());
        return new HttpEntity<Object>(reqBody, requestHeaders);
    }

    public String getAcessToken() {
        return this.STUB_TOKEN;
    }

}
