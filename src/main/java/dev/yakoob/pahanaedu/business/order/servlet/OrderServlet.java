package dev.yakoob.pahanaedu.business.order.servlet;

import dev.yakoob.pahanaedu.business.customer.service.CustomerService;
import dev.yakoob.pahanaedu.business.customer.service.impl.CustomerServiceImpl;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;
import dev.yakoob.pahanaedu.business.order.dto.OrderItemDTO;
import dev.yakoob.pahanaedu.business.order.model.Order;
import dev.yakoob.pahanaedu.business.order.service.OrderService;
import dev.yakoob.pahanaedu.business.order.service.impl.OrderServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderServlet extends HttpServlet {

    private CustomerService customerService;
    private ItemService itemService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        try {
            customerService = new CustomerServiceImpl();
            itemService = new ItemServiceImpl();
            orderService = new OrderServiceImpl();
            System.out.println("OrderServlet: All services initialized successfully");
        } catch (Exception e) {
            System.err.println("Warning: Service initialization failed: " + e.getMessage());
            e.printStackTrace();
            // Don't throw exception to allow servlet to still register
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("OrderServlet doGet() called - servlet is being reached!");

        // Set request attributes for customers, items, and recent orders
        try {
            if (customerService != null) {
                req.setAttribute("customers", customerService.getAllCustomers());
                System.out.println("OrderServlet: Customers loaded successfully");
            } else {
                req.setAttribute("customers", new ArrayList<>());
                System.out.println("OrderServlet: CustomerService is null, using empty list");
            }

            if (itemService != null) {
                req.setAttribute("items", itemService.getAllItems());
                System.out.println("OrderServlet: Items loaded successfully");
            } else {
                req.setAttribute("items", new ArrayList<>());
                System.out.println("OrderServlet: ItemService is null, using empty list");
            }

            // Load recent orders for display - using List<Order> not List<OrderDTO>
            if (orderService != null) {
                List<Order> recentOrders = orderService.getAllOrders();
                req.setAttribute("recentOrders", recentOrders);
                System.out.println("OrderServlet: Recent orders loaded successfully - " + recentOrders.size() + " orders");
            } else {
                req.setAttribute("recentOrders", new ArrayList<>());
                System.out.println("OrderServlet: OrderService is null, using empty orders list");
            }

        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("customers", new ArrayList<>());
            req.setAttribute("items", new ArrayList<>());
            req.setAttribute("recentOrders", new ArrayList<>());
        }

        req.setAttribute("pageTitle", "Create Invoice");

        // Forward to JSP view
        System.out.println("OrderServlet: Attempting JSP forward to /WEB-INF/views/order/view.jsp");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/order/view.jsp");
        dispatcher.forward(req, resp);
        System.out.println("OrderServlet: JSP forward completed successfully");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("OrderServlet doPost() called - processing order creation");

        try {
            if (orderService != null) {
                String customerId = req.getParameter("customerId");
                String total = req.getParameter("total");

                System.out.println("OrderServlet: Received customerId=" + customerId + ", total=" + total);

                // Debug: Print all parameters
                System.out.println("=== ALL FORM PARAMETERS ===");
                req.getParameterMap().forEach((key, values) -> {
                    System.out.println("Parameter: " + key + " = " + String.join(", ", values));
                });
                System.out.println("=== END PARAMETERS ===");

                // Prepare to collect order items
                List<OrderItemDTO> orderItems = new ArrayList<>();

                int index = 0;
                while (true) {
                    String code = req.getParameter("items[" + index + "].code");
                    String quantityStr = req.getParameter("items[" + index + "].quantity");
                    String priceStr = req.getParameter("items[" + index + "].price");

                    System.out.println("OrderServlet: Checking item " + index + " - code=" + code + ", quantity=" + quantityStr + ", price=" + priceStr);

                    if (code == null || quantityStr == null || priceStr == null) {
                        System.out.println("OrderServlet: No more items found at index " + index);
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
                        System.out.println("OrderServlet: Added item - code=" + code + ", qty=" + quantity + ", price=" + price);
                    } catch (NumberFormatException e) {
                        System.err.println("OrderServlet: Error parsing item data: " + e.getMessage());
                        e.printStackTrace();
                    }

                    index++;
                }

                System.out.println("OrderServlet: Final validation - customerId=" + customerId +
                        ", total=" + total + ", orderItems.size=" + orderItems.size());
                System.out.println("OrderServlet: Validation checks - customerId!=null: " + (customerId != null) +
                        ", total!=null: " + (total != null) + ", !orderItems.isEmpty(): " + (!orderItems.isEmpty()));

                if (customerId != null && total != null && !orderItems.isEmpty()) {
                    OrderDTO orderDTO = new OrderDTO.Builder()
                            .setCustomerId(customerId)
                            .setOrderItems(orderItems)
                            .setTotalAmount(Double.valueOf(total))
                            .build();

                    System.out.println("OrderServlet: Attempting to save order with " + orderItems.size() + " items");
                    boolean isOrderSaved = orderService.saveOrder(orderDTO);

                    if (isOrderSaved) {
                        req.getSession().setAttribute("flash_success", "Order placed successfully!");
                        System.out.println("OrderServlet: Order saved successfully to database");
                    } else {
                        req.getSession().setAttribute("flash_error", "Failed to place the order");
                        System.err.println("OrderServlet: Order save failed");
                    }
                } else {
                    req.getSession().setAttribute("flash_error", "Missing or invalid data!");
                    System.err.println("OrderServlet: Invalid order data - customerId=" + customerId +
                            ", total=" + total + ", items=" + orderItems.size());
                }
            } else {
                req.getSession().setAttribute("flash_error", "Service not available. Please check database connection.");
                System.err.println("OrderServlet: OrderService is null");
            }
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
            e.printStackTrace();
            req.getSession().setAttribute("flash_error", "Error processing order: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/order");
    }
}
