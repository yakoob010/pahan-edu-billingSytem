package dev.yakoob.pahanaedu.business.customer.service.impl;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;
import dev.yakoob.pahanaedu.business.customer.mapper.CustomerMapper;
import dev.yakoob.pahanaedu.business.customer.model.Customer;
import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.persistence.customer.dao.CustomerDAO;
import dev.yakoob.pahanaedu.persistence.customer.dao.impl.CustomerDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDAO.save(CustomerMapper.toEntity(customerDTO));
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        return CustomerMapper.toDTO(customerDAO.findById(id));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerDAO.findAll();

        for (Customer customer : customerList) {
            customerDTOList.add(CustomerMapper.toDTO(customer));
        }
        return customerDTOList;
    }

    @Override
    public void updateCustomer(int id, CustomerDTO customerDTO) {
        customerDAO.update(id, CustomerMapper.toEntity(customerDTO));
    }

    @Override
    public void deleteCustomer(int id) {
        customerDAO.delete(id);
    }

    @Override
    public int getCustomerCount() {
        return customerDAO.getCount();
    }
}
