package com.example.vaadin.spring.view;

import com.example.vaadin.spring.model.Customer;
import com.example.vaadin.spring.service.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private TextField filterText = new TextField();
    private Grid<Customer> grid = new Grid<>(Customer.class);

    private CustomerService service;
    private CustomerForm form;

    @Autowired
    public MainView(CustomerService service) {
        this.service = service;
        this.form = new CustomerForm(this, service);

        // =========== Toolbar ===========

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerBtn);

        // =========== Main Content ===========

        grid.setColumns("firstName", "lastName", "status");
        grid.asSingleSelect().addValueChangeListener(event ->
                form.setCustomer(grid.asSingleSelect().getValue()));

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();


        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        form.setCustomer(null);
    }

    void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
