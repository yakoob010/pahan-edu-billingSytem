package dev.yakoob.pahanaedu.persistence.item.dao.impl;

import dev.yakoob.pahanaedu.business.item.mapper.ItemMapper;
import dev.yakoob.pahanaedu.business.item.model.Item;
import dev.yakoob.pahanaedu.persistence.item.dao.ItemDAO;
import dev.yakoob.pahanaedu.util.db.DBConnection;
import dev.yakoob.pahanaedu.util.db.SqlQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemDAOImpl implements ItemDAO {

    private static final Logger LOGGER = Logger.getLogger(ItemDAOImpl.class.getName());

    @Override
    public void save(Item item) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Item.INSERT)
        ) {
            pstm.setString(1, item.getItemName());
            pstm.setString(2, item.getDescription());
            pstm.setString(3, item.getCategory());
            if (item.getUnitPrice() != null) {
                pstm.setDouble(4, item.getUnitPrice());
            } else {
                pstm.setNull(4, java.sql.Types.DOUBLE);
            }
            if (item.getStockQuantity() != null) {
                pstm.setInt(5, item.getStockQuantity());
            } else {
                pstm.setNull(5, java.sql.Types.INTEGER);
            }
            pstm.setString(6, item.getPublisher());
            pstm.setString(7, item.getAuthor());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item findById(String itemCode) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Item.FIND_BY_ID)
        ) {
            pstm.setString(1, itemCode);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return ItemMapper.mapToItem(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Item.FIND_ALL);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                items.add(ItemMapper.mapToItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void update(String itemCode, Item item) {
        String updateSql = SqlQueries.Item.UPDATE;
        LOGGER.info("Executing SQL: " + updateSql);
        LOGGER.info("Parameters: itemCode=" + itemCode + ", item=" + item);

        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(updateSql)
        ) {
            pstm.setString(1, item.getItemName());
            pstm.setString(2, item.getDescription());
            pstm.setString(3, item.getCategory());

            if (item.getUnitPrice() != null) {
                pstm.setDouble(4, item.getUnitPrice());
            } else {
                pstm.setNull(4, java.sql.Types.DOUBLE);
            }

            if (item.getStockQuantity() != null) {
                pstm.setInt(5, item.getStockQuantity());
            } else {
                pstm.setNull(5, java.sql.Types.INTEGER);
            }

            pstm.setString(6, item.getPublisher());
            pstm.setString(7, item.getAuthor());
            pstm.setString(8, itemCode);

            LOGGER.info("Executing update with itemCode: " + itemCode);
            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated == 0) {
                String errorMsg = "No rows were updated. Item with code: " + itemCode + " not found.";
                LOGGER.warning(errorMsg);
                throw new SQLException(errorMsg);
            } else {
                LOGGER.info("Successfully updated " + rowsUpdated + " row(s)");
            }
        } catch (SQLException e) {
            String errorMsg = "Error updating item with code " + itemCode + ": " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    @Override
    public void delete(String itemCode) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Item.DELETE)
        ) {
            pstm.setString(1, itemCode);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Item.COUNT)
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
