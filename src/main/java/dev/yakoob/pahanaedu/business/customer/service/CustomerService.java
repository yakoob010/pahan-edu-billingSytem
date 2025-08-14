package dev.yakoob.pahanaedu.business.customer.service;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    void saveCustomer(CustomerDTO customer);

    CustomerDTO getCustomerById(int id);

    List<CustomerDTO> getAllCustomers();

    void updateCustomer(int id, CustomerDTO updatedCustomer);

    void deleteCustomer(int id);

    int getCustomerCount();

}
