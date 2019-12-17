package com.any.event.service;

import com.any.event.dto.EventDTO;
import com.any.event.entity.Event;
import com.any.event.repository.EventRepository;
import com.any.event.vo.EventListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StubhubEventBatchService {

    @Value("${stubhub.event.url}")
    private String STUB_EVENT_URL;

    private final RestTemplate restTemplate;
    private final StubhubAuthService stubhubAuthService;
    private final EventRepository eventRepository;

    @Autowired
    public StubhubEventBatchService(RestTemplate restTemplate, StubhubAuthService stubhubAuthService, EventRepository eventRepository) {
        this.restTemplate = restTemplate;
        this.stubhubAuthService = stubhubAuthService;
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "30 * * * * ?")
    public void getEventList(){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(STUB_EVENT_URL)
                .queryParam("country","KR")
                .queryParam("rows","500")
                .queryParam("sort","eventDateLocal");
        HttpHeaders httpHeaders = new HttpHeaders();
        String Token = stubhubAuthService.getAcessToken();
        httpHeaders.add("Authorization",String.format("Bearer %s", Token));
        httpHeaders.add("Accept","application/json");
        httpHeaders.add("Referer","https://developer.stubhub.com/searchevent/apis/get/search/events/v3");
        HttpEntity<Object> reqEnt = new HttpEntity<Object>(null, httpHeaders);
        ResponseEntity<EventListResponse> response = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, reqEnt, EventListResponse.class);
        log.info("Event Response ==> {}", response.getBody());
        List<EventDTO> list = Objects.requireNonNull(response.getBody()).getEvents();
        log.info("Size ==> {}", list.size());
        //regEvent(list);
    }

    private void regEvent(List<EventDTO> list){
        List<Event> saveList = list.stream().map(
                eventDTO -> Event.builder()
                        .eventId(eventDTO.getId())
                        .description(eventDTO.getDescription())
                        .eventDateLocal(eventDTO.getEventDateLocal())
                        .lastUpdatedDate(eventDTO.getLastUpdatedDate())
                        .name(eventDTO.getName())
                        .build())
                .collect(Collectors.toList());
        List<Event> allList = eventRepository.findAll();

        eventRepository.saveAll(saveList);
    }


}
