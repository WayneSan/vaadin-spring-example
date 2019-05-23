package com.example.vaadin.spring.view;

import com.example.vaadin.spring.model.Customer;
import com.example.vaadin.spring.service.CustomerService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private CustomerService service;
    private Grid<Customer> grid = new Grid<>(Customer.class);

    public MainView(@Autowired CustomerService service) {
        this.service = service;

        grid.setColumns("firstName", "lastName", "status");

        add(grid);

        setSizeFull();

        updateList();
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

}
