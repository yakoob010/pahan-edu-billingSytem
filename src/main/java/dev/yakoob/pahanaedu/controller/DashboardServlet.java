package dev.yakoob.pahanaedu.controller;

import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.business.order.service.OrderService;
import dev.yakoob.pahanaedu.business.order.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    private CustomerService customerService;
    private ItemService itemService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
        itemService = new ItemServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isAdmin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Load dashboard counts
        try {
            int customerCount = customerService.getAllCustomers().size();
            int itemCount = itemService.getAllItems().size();
            int orderCount = orderService.getAllOrders().size();

            request.setAttribute("customerCount", customerCount);
            request.setAttribute("itemCount", itemCount);
            request.setAttribute("orderCount", orderCount);
        } catch (Exception e) {
            // Set default values if there's an error
            request.setAttribute("customerCount", 0);
            request.setAttribute("itemCount", 0);
            request.setAttribute("orderCount", 0);
        }

        // Forward to dashboard page
        request.getRequestDispatcher("/WEB-INF/views/dashboard/view.jsp").forward(request, response);
    }
}
