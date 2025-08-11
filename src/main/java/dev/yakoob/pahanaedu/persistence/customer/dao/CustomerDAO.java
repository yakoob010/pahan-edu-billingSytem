package dev.yakoob.pahanaedu.persistence.customer.dao;

import dev.yakoob.pahanaedu.business.customer.model.Customer;

import java.util.List;

public interface CustomerDAO {

    void save(Customer customer);

    Customer findById(String id);

    List<Customer> findAll();

    void update(String id, Customer customer);

    void delete(String id);

    int getCount();

}
