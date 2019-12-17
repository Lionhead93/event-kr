package com.any.event.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EventDTOTest {
    private String time;
    private LocalDate localDate;
    @BeforeEach
    void setUp() {
        this.time = "2019-12-23T20:00:00+0900";
    }
    @Test
    void setEventDateLocal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        LocalDateTime ld = LocalDateTime.parse(time,dtf);
        log.info(ld.toString());
    }
}