package dev.yakoob.pahanaedu.business.order.service.impl;

import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;
import dev.yakoob.pahanaedu.business.order.mapper.OrderMapper;
import dev.yakoob.pahanaedu.business.order.model.Order;
import dev.yakoob.pahanaedu.business.order.service.OrderService;
import dev.yakoob.pahanaedu.persistence.order.dao.OrderDAO;
import dev.yakoob.pahanaedu.persistence.order.dao.impl.OrderDAOImpl;

import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public boolean saveOrder(OrderDTO orderDTO) {
        // Do not set orderId here; let the DB auto-generate it
        orderDTO.setDate(LocalDate.now());
        return orderDAO.save(OrderMapper.toEntity(orderDTO));
    }

    @Override
    public int getOrderCount() {
        return orderDAO.getCount();
    }

    @Override
    public boolean deleteOrder(int orderId) {
        return orderDAO.delete(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAll();
    }
}
