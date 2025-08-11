package dev.yakoob.pahanaedu.business.order.mapper;

import dev.yakoob.pahanaedu.business.order.dto.OrderDTO;
import dev.yakoob.pahanaedu.business.order.dto.OrderItemDTO;
import dev.yakoob.pahanaedu.business.order.model.Order;
import dev.yakoob.pahanaedu.business.order.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;

        List<OrderItemDTO> itemDTOs = order.getOrderItems()
                .stream()
                .map(OrderMapper::toItemDTO)
                .collect(Collectors.toList());

        return new OrderDTO.Builder()
                .setOrderId(order.getOrderId())
                .setCustomerId(order.getCustomerId())
                .setDate(order.getDate())
                .setTotalAmount(order.getTotalAmount())
                .setOrderItems(itemDTOs)
                .build();
    }

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) return null;

        List<OrderItem> items = dto.getOrderItems()
                .stream()
                .map(OrderMapper::toItemEntity)
                .collect(Collectors.toList());

        return new Order.Builder()
                .setOrderId(dto.getOrderId())
                .setCustomerId(dto.getCustomerId())
                .setDate(dto.getDate())
                .setTotalAmount(dto.getTotalAmount())
                .setOrderItems(items)
                .build();
    }

    public static OrderItemDTO toItemDTO(OrderItem item) {
        if (item == null) return null;

        return new OrderItemDTO.Builder()
                .itemCode(item.getItemCode())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
    }

    public static OrderItem toItemEntity(OrderItemDTO dto) {
        if (dto == null) return null;

        return new OrderItem.Builder()
                .itemCode(dto.getItemCode())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .build();
    }
}
