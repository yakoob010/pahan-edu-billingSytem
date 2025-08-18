package dev.yakoob.pahanaedu.persistence.customer.dao.impl;

import dev.yakoob.pahanaedu.business.customer.mapper.CustomerMapper;
import dev.yakoob.pahanaedu.business.customer.model.Customer;
import dev.yakoob.pahanaedu.persistence.customer.dao.CustomerDAO;
import dev.yakoob.pahanaedu.util.db.DBConnection;
import dev.yakoob.pahanaedu.util.db.SqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void save(Customer customer) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.INSERT)
        ) {
            pstm.setInt(1, customer.getCustomerId());
            pstm.setString(2, customer.getName());
            pstm.setString(3, customer.getAddress());
            pstm.setString(4, customer.getMobileNumber());
            pstm.setInt(5, customer.getUnitsConsumed());
            pstm.setDate(6, Date.valueOf(customer.getRegistrationDate()));
            pstm.setString(7, customer.getEmail());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(int id) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.FIND_BY_ID)
        ) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return CustomerMapper.mapToCustomer(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.FIND_ALL);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                customers.add(CustomerMapper.mapToCustomer(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void update(int id, Customer customer) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.UPDATE)
        ) {
            pstm.setString(1, customer.getName());
            pstm.setString(2, customer.getAddress());
            pstm.setString(3, customer.getMobileNumber());
            pstm.setString(4, customer.getEmail());
            pstm.setInt(5, id);

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.DELETE)
        ) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        try (
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(SqlQueries.Customer.COUNT)
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
