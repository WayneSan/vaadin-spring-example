package com.example.vaadin.spring.model;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class MessageBean {

    public String getMessage() {
        return "Button was clicked at " + LocalTime.now();
    }
}
