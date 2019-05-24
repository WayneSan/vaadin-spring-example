package com.example.vaadin.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class VaadinSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VaadinSpringApplication.class, args);
    }

}
