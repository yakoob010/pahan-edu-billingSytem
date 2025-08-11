package dev.yakoob.pahanaedu.business.dashboard.servlet;

import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.business.order.service.OrderService;
import dev.yakoob.pahanaedu.business.order.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "dashboard", urlPatterns = "/")
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerCount = customerService.getCustomerCount();
        int itemCount = itemService.getItemCount();
        int orderCount = orderService.getOrderCount();

        req.setAttribute("customerCount", customerCount);
        req.setAttribute("itemCount", itemCount);
        req.setAttribute("orderCount", orderCount);
        req.setAttribute("pageTitle", "Pahana Edu Billing System");
        req.setAttribute("body", "../dashboard/view.jsp");

        req.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp").forward(req, resp);
    }
}
