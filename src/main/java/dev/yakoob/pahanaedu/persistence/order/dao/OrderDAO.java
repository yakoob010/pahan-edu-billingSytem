package dev.yakoob.pahanaedu.persistence.order.dao;

import dev.yakoob.pahanaedu.business.order.model.Order;

public interface OrderDAO {

    boolean save(Order order);

    int getCount();

}
