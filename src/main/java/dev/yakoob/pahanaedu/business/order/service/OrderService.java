package dev.yakoob.pahanaedu.business.order.service;

import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;

public interface OrderService {

    boolean saveOrder(OrderDTO orderDTO);

    int getOrderCount();

}
