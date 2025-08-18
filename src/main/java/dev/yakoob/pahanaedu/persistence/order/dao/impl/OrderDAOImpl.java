package dev.yakoob.pahanaedu.persistence.order.dao.impl;

import dev.yakoob.pahanaedu.business.order.model.Order;
import dev.yakoob.pahanaedu.business.order.model.OrderItem;
import dev.yakoob.pahanaedu.persistence.order.dao.OrderDAO;
import dev.yakoob.pahanaedu.util.db.DBConnection;
import dev.yakoob.pahanaedu.util.db.SqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean save(Order order) {
        Connection connection = null;
        PreparedStatement orderPstm = null;
        PreparedStatement orderItemPstm = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Insert order into `orders` table
            orderPstm = connection.prepareStatement(SqlQueries.Order.INSERT, Statement.RETURN_GENERATED_KEYS);
            orderPstm.setDate(1, Date.valueOf(order.getDate()));
            orderPstm.setString(2, order.getCustomerId());
            orderPstm.setDouble(3, order.getTotalAmount());
            orderPstm.executeUpdate();

            // Retrieve generated order_id
            int generatedOrderId = -1;
            try (ResultSet generatedKeys = orderPstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedOrderId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            // Insert order items into `order_item` table
            orderItemPstm = connection.prepareStatement(SqlQueries.OrderItem.INSERT);
            for (OrderItem item : order.getOrderItems()) {
                orderItemPstm.setInt(1, generatedOrderId);
                orderItemPstm.setString(2, item.getItemCode());
                orderItemPstm.setInt(3, item.getQuantity());
                orderItemPstm.setDouble(4, item.getUnitPrice());
                orderItemPstm.addBatch();
            }

            orderItemPstm.executeBatch(); // Execute all item insertions
            connection.commit(); // Commit transaction
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (orderPstm != null) orderPstm.close();
                if (orderItemPstm != null) orderItemPstm.close();
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getCount() {
        try (
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SqlQueries.Order.COUNT)
        ) {
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean delete(int orderId) {
        Connection connection = null;
        PreparedStatement deleteOrderItemsPstm = null;
        PreparedStatement deleteOrderPstm = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Delete order items first (foreign key constraint)
            deleteOrderItemsPstm = connection.prepareStatement("DELETE FROM order_item WHERE order_id = ?");
            deleteOrderItemsPstm.setInt(1, orderId);
            deleteOrderItemsPstm.executeUpdate();

            // Then delete the order
            deleteOrderPstm = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?");
            deleteOrderPstm.setInt(1, orderId);
            int affectedRows = deleteOrderPstm.executeUpdate();

            connection.commit();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (deleteOrderItemsPstm != null) deleteOrderItemsPstm.close();
                if (deleteOrderPstm != null) deleteOrderPstm.close();
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders");
            ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                Order order = new Order.Builder()
                    .setOrderId(String.valueOf(rs.getInt("order_id")))
                    .setDate(rs.getDate("order_date").toLocalDate())
                    .setCustomerId(rs.getString("customer_id"))
                    .setTotalAmount(rs.getDouble("total_amount"))
                    .build();
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
