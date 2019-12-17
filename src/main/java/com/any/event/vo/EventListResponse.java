package com.any.event.vo;

import com.any.event.dto.EventDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @ToString
@Setter @NoArgsConstructor @AllArgsConstructor
public class EventListResponse {
    @JsonProperty(required = true, value = "numFound")
    private Long numFound;
    @JsonProperty(required = true, value = "events")
    private List<EventDTO> events;
}


