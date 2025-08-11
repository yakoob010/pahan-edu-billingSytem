package dev.yakoob.pahanaedu.business.order.servlet;

import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;
import dev.yakoob.pahanaedu.business.order.dto.OrderItemDTO;
import dev.yakoob.pahanaedu.business.order.service.OrderService;
import dev.yakoob.pahanaedu.business.order.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "order", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

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

        req.setAttribute("customers", customerService.getAllCustomers());
        req.setAttribute("items", itemService.getAllItems());
        req.setAttribute("pageTitle", "Place Order");
        req.setAttribute("body", "../order/view.jsp");

        req.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        String total = req.getParameter("total");

        // Prepare to collect order items
        List<OrderItemDTO> orderItems = new ArrayList<>();

        int index = 0;
        while (true) {
            String code = req.getParameter("items[" + index + "].code");
            String quantityStr = req.getParameter("items[" + index + "].quantity");
            String priceStr = req.getParameter("items[" + index + "].price");

            if (code == null || quantityStr == null || priceStr == null) {
                break;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                OrderItemDTO item = new OrderItemDTO.Builder()
                        .itemCode(code)
                        .quantity(quantity)
                        .unitPrice(price)
                        .build();

                orderItems.add(item);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            index++;
        }

        if (customerId != null && total != null && !orderItems.isEmpty()){
            OrderDTO orderDTO = new OrderDTO.Builder()
                    .setCustomerId(customerId)
                    .setOrderItems(orderItems)
                    .setTotalAmount(Double.valueOf(total))
                    .build();

            boolean isOrderSaved = orderService.saveOrder(orderDTO);
            if (isOrderSaved) {
                req.getSession().setAttribute("flash_success", "Order Placed successfully!");
            } else {
                req.getSession().setAttribute("flash_error", "Failed to place the order");
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not valid data!");
        }

        resp.sendRedirect(req.getContextPath() + "/order");
    }
}
