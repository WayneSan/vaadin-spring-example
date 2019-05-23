package com.example.vaadin.spring.view;

import com.example.vaadin.spring.model.Customer;
import com.example.vaadin.spring.model.CustomerStatus;
import com.example.vaadin.spring.service.CustomerService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CustomerForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    private DatePicker birthDate = new DatePicker("Birthday");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Customer> binder = new Binder<>(Customer.class);

    private MainView mainView;
    private CustomerService service;

    public CustomerForm(MainView mainView, CustomerService service) {
        this.mainView = mainView;
        this.service = service;

        status.setItems(CustomerStatus.values());

        save.addClickListener(this::save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(this::delete);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        add(firstName, lastName, status, birthDate, buttons);

        binder.bindInstanceFields(this);
    }

    void setCustomer(Customer customer) {
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private void save(ClickEvent<Button> event) {
        Customer customer = binder.getBean();
        service.save(customer);
        mainView.updateList();
        setCustomer(null);
    }

    private void delete(ClickEvent<Button> event) {
        Customer customer = binder.getBean();
        service.delete(customer);
        mainView.updateList();
        setCustomer(null);
    }
}
