package com.any.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${stubhub.api.key}")
    private String STUB_KEY;
    @Value("${stubhub.api.secret}")
    private String STUB_SECRET;
    @Value("${stubhub.access.url}")
    private String STUB_ACESS_URL;
    @Value("${stubhub.username}")
    private String STUB_USERNAME;
    @Value("${stubhub.password}")
    private String STUB_PASSWORD;
    private String STUB_TOKEN;
    private final RestTemplate restTemplate;
    @Autowired
    public StubhubAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init(){
        log.info("init Time : ########{}##########",System.currentTimeMillis());
        getAcessTokenRequest();
    }

    private void getAcessTokenRequest(){
        String fm = String.format("%s:%s",STUB_KEY,STUB_SECRET);
        log.info("format : #############{}##############", fm);
        String encodeString = Base64.getEncoder().encodeToString(fm.getBytes());
        log.info("encodeString : {}",encodeString);

        HttpEntity<?> reqBody = apiClientHttpEntity(encodeString);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(STUB_ACESS_URL)
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
        reqBody.put("username",STUB_USERNAME);
        reqBody.put("password",STUB_PASSWORD);
        return new HttpEntity<Object>(reqBody, requestHeaders);
    }

    public String getAcessToken() {
        return this.STUB_TOKEN;
    }

}
