package com.example.vaadin.spring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import com.example.vaadin.spring.model.Customer;
import com.example.vaadin.spring.model.CustomerStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final HashMap<Long, Customer> contacts = new HashMap<>();
    private long nextId = 0;

    /**
     * Sample data generation
     */
    @PostConstruct
    private void ensureTestData() {
        if (findAll().isEmpty()) {
            final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                    "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                    "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                    "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                    "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                    "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                    "Jaydan Jackson", "Bernard Nilsen" };
            Random r = new Random(0);
            for (String name : names) {
                String[] split = name.split(" ");
                Customer c = new Customer();
                c.setFirstName(split[0]);
                c.setLastName(split[1]);
                c.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);
                c.setBirthDate(LocalDate.now().minusDays(r.nextInt(365*100)));
                save(c);
            }
        }
    }

    /**
     * @return all available Customer objects.
     */
    @Override
    public synchronized List<Customer> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Customer's that match given filter.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @return list a Customer objects
     */
    @Override
    public synchronized List<Customer> findAll(String stringFilter) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                log.trace(null, ex);
            }
        }
        arrayList.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }

    /**
     * Finds all Customer's that match given filter and limits the resultset.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @param start
     *            the index of first result
     * @param maxresults
     *            maximum result count
     * @return list a Customer objects
     */
    @Override
    public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
        List<Customer> arrayList = findAll(stringFilter);
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all customers in the system
     */
    @Override
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes a customer from a system
     *
     * @param value
     *            the Customer to be deleted
     */
    @Override
    public synchronized void delete(Customer value) {
        contacts.remove(value.getId());
    }

    /**
     * Persists or updates customer in the system. Also assigns an identifier
     * for new Customer instances.
     *
     * @param entry
     */
    @Override
    public synchronized void save(Customer entry) {
        if (entry == null) {
            log.trace(
                    "Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }
}
