package dev.yakoob.pahanaedu.business.order.service;

import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;
import dev.yakoob.pahanaedu.business.order.model.Order;

import java.util.List;

public interface OrderService {

    boolean saveOrder(OrderDTO orderDTO);

    int getOrderCount();

    List<Order> getAllOrders();

    boolean deleteOrder(int orderId);

}
