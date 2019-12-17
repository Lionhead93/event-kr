package com.any.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter @Setter
@ToString @AllArgsConstructor @NoArgsConstructor
public class EventDTO {

    @JsonProperty(required = true, value = "id")
    private Long id;

    @JsonProperty(required = true, value = "name")
    private String name;

    @JsonProperty(required = true, value = "description")
    private String description;

    @JsonProperty(required = true, value = "eventDateLocal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private LocalDateTime eventDateLocal;

    @JsonProperty(required = true, value = "lastUpdatedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private LocalDateTime lastUpdatedDate;
}
