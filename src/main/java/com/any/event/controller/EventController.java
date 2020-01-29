package com.any.event.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @GetMapping("/api/hello")
    public String hello() {
        return "this is msg from controller";
    }


}
