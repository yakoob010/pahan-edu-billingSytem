package dev.yakoob.pahanaedu.business.customer.servlet;

import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "delete-customer", urlPatterns = "/customer/delete")
public class DeleteCustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            try {
                int customerId = Integer.parseInt(id);
                customerService.deleteCustomer(customerId);
                req.getSession().setAttribute("flash_success", "Customer deleted successfully!");
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("flash_error", "Invalid customer id format!");
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid customer id!");
        }
        resp.sendRedirect(req.getContextPath() + "/customer");
    }
}
