package dev.yakoob.pahanaedu.persistence.order.dao.impl;

import dev.yakoob.pahanaedu.business.order.model.Order;
import dev.yakoob.pahanaedu.business.order.model.OrderItem;
import dev.yakoob.pahanaedu.persistence.order.dao.OrderDAO;
import dev.yakoob.pahanaedu.util.db.DBConnection;
import dev.yakoob.pahanaedu.util.db.SqlQueries;

import java.sql.*;

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
            orderPstm = connection.prepareStatement(SqlQueries.Order.INSERT);
            orderPstm.setString(1, order.getOrderId());
            orderPstm.setDate(2, Date.valueOf(order.getDate()));
            orderPstm.setString(3, order.getCustomerId());
            orderPstm.setDouble(4, order.getTotalAmount());
            orderPstm.executeUpdate();

            // Insert order items into `order_item` table
            orderItemPstm = connection.prepareStatement(SqlQueries.OrderItem.INSERT);
            for (OrderItem item : order.getOrderItems()) {
                orderItemPstm.setString(1, order.getOrderId());
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
}
