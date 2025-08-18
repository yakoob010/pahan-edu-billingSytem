package dev.yakoob.pahanaedu.business.customer.servlet;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;
import dev.yakoob.pahanaedu.business.customer.mapper.CustomerMapper;
import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static dev.yakoob.pahanaedu.util.validation.Validation.validateCustomerDTO;

public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        req.setAttribute("customers", customerDTOList);
        req.setAttribute("pageTitle", "Customer Management");

        // Forward to the view
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/view.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDTO customerDTO = CustomerMapper.buildCustomerDTOFromRequest(req);
        String validationError = validateCustomerDTO(customerDTO);

        if (validationError == null) {
            customerService.saveCustomer(customerDTO);
            req.getSession().setAttribute("flash_success", "Customer created successfully!");
        } else {
            req.getSession().setAttribute("flash_error", validationError);
        }

        resp.sendRedirect(req.getContextPath() + "/customer");
    }

}
