package dev.yakoob.pahanaedu.persistence.customer.dao;

import dev.yakoob.pahanaedu.business.customer.model.Customer;

import java.util.List;

public interface CustomerDAO {

    void save(Customer customer);

    Customer findById(int id);

    List<Customer> findAll();

    void update(int id, Customer customer);

    void delete(int id);

    int getCount();

}
