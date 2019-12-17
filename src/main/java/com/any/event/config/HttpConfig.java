package com.any.event.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class HttpConfig {
    private static final int MAX_ATTEMPTS = 3;
    private static final long PERIOD_MILLISECONDS = 2000L;
    private static final int CONNECT_TIMEOUT = 3000;
    private static final int MAX_CONN_TOTAL = 100;
    private static final int MAX_CONN_PER_ROUTE = 5;

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectTimeout(CONNECT_TIMEOUT);
//        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setMaxConnTotal(MAX_CONN_TOTAL)
//                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
//                .build();
//        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }

    /**
     * http request 재시도가 필요할 경우 사용할 retryTemplate
     * 참고: retryTemplate 와 같은 기능을 가진  @Retryable,@Recover 를 사용해도 된다. 사용법은 재시도 할 메서드 위에 사용.
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        // 재시도 간격
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(PERIOD_MILLISECONDS);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        // 재시도 최대 횟수
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(MAX_ATTEMPTS);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }
}
