package dev.yakoob.pahanaedu.business.customer.mapper;

import dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO;
import dev.yakoob.pahanaedu.business.customer.model.Customer;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        return new CustomerDTO.Builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .address(customer.getAddress())
                .mobileNumber(customer.getMobileNumber())
                .unitsConsumed(customer.getUnitsConsumed())
                .registrationDate(customer.getRegistrationDate())
                .email(customer.getEmail())
                .build();
    }

    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;

        return new Customer.Builder()
                .customerId(dto.getCustomerId())
                .name(dto.getName())
                .address(dto.getAddress())
                .mobileNumber(dto.getMobileNumber())
                .unitsConsumed(dto.getUnitsConsumed())
                .registrationDate(dto.getRegistrationDate())
                .email(dto.getEmail())
                .build();
    }

    public static CustomerDTO buildCustomerDTOFromRequest(HttpServletRequest req) {
        // For updates, the ID will be passed in the request
        String idParam = req.getParameter("id");
        int customerId = 0;

        if (idParam != null && !idParam.isEmpty()) {
            try {
                customerId = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                // If ID is invalid, it will be treated as a new customer
                customerId = (int)(System.currentTimeMillis() % 1000000);
            }
        } else {
            // Generate a new ID for new customers
            customerId = (int)(System.currentTimeMillis() % 1000000);
        }

        return new CustomerDTO.Builder()
                .customerId(customerId)
                .name(req.getParameter("name"))
                .address(req.getParameter("address"))
                .mobileNumber(req.getParameter("mobileNumber"))
                .email(req.getParameter("email"))
                .registrationDate(java.time.LocalDate.now()) // This will be ignored for updates
                .build();
    }

    public static Customer mapToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("mobile_number"),
                rs.getInt("units_consumed"),
                rs.getDate("registration_date").toLocalDate(),
                rs.getString("email")
        );
    }
}
