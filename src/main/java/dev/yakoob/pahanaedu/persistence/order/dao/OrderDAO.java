package dev.yakoob.pahanaedu.persistence.order.dao;

import dev.yakoob.pahanaedu.business.order.model.Order;
import java.util.List;

public interface OrderDAO {

    boolean save(Order order);

    int getCount();

    boolean delete(int orderId);

    List<Order> getAll();
}
