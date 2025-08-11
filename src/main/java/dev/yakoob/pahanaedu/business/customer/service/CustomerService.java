package dev.yakoob.pahanaedu.business.customer.service;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    void saveCustomer(CustomerDTO customer);

    CustomerDTO getCustomerById(String id);

    List<CustomerDTO> getAllCustomers();

    void updateCustomer(String id, CustomerDTO updatedCustomer);

    void deleteCustomer(String id);

    int getCustomerCount();

}
