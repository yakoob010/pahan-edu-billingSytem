package dev.yakoob.pahanaedu.business.customer.servlet;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;
import dev.yakoob.pahanaedu.business.customer.mapper.CustomerMapper;
import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static dev.yakoob.pahanaedu.util.validation.Validation.validateCustomerDTO;

@WebServlet(name = "edit-customer", urlPatterns = "/customer/edit")
public class EditCustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            try {
                int customerId = Integer.parseInt(id);
                CustomerDTO customer = customerService.getCustomerById(customerId);

                if (customer != null) {
                    req.setAttribute("customer", customer);
                    req.setAttribute("pageTitle", "Edit Customer");
                    req.setAttribute("body", "../customer/edit-customer.jsp");
                    req.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp").forward(req, resp);
                } else {
                    req.getSession().setAttribute("flash_error", "Couldn't find the customer by: " + id);
                    resp.sendRedirect(req.getContextPath() + "/customer");
                }
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("flash_error", "Invalid customer id format!");
                resp.sendRedirect(req.getContextPath() + "/customer");
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid customer id!");
            resp.sendRedirect(req.getContextPath() + "/customer");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            try {
                int customerId = Integer.parseInt(id);
                CustomerDTO customerDTO = CustomerMapper.buildCustomerDTOFromRequest(req);
                String validationError = validateCustomerDTO(customerDTO);

                if (validationError == null) {
                    customerService.updateCustomer(customerId, customerDTO);
                    req.getSession().setAttribute("flash_success", "Customer updated successfully!");
                } else {
                    req.getSession().setAttribute("flash_error", validationError);
                }
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("flash_error", "Invalid customer id format!");
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid customer id!");
        }
        resp.sendRedirect(req.getContextPath() + "/customer");
    }
}
