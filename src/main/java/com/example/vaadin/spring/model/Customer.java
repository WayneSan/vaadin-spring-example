package com.example.vaadin.spring.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@Data
@EqualsAndHashCode
@SuppressWarnings("serial")
public class Customer implements Serializable, Cloneable {

    private Long id;

    private String firstName = "";

    private String lastName = "";

    private LocalDate birthDate;

    private CustomerStatus status;

    private String email = "";

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public Customer clone() throws CloneNotSupportedException {
        return (Customer) super.clone();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
