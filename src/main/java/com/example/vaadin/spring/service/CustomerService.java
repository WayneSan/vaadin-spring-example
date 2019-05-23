package com.example.vaadin.spring.service;

import java.util.List;

import com.example.vaadin.spring.model.Customer;

public interface CustomerService {
    List<Customer> findAll();

    List<Customer> findAll(String stringFilter);

    List<Customer> findAll(String stringFilter, int start, int maxresults);

    long count();

    void delete(Customer value);

    void save(Customer entry);
}
