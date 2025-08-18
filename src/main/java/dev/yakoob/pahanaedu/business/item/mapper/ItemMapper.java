package dev.yakoob.pahanaedu.business.item.mapper;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;
import dev.yakoob.pahanaedu.business.item.model.Item;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {
        if (item == null) return null;

        return new ItemDTO.Builder()
                .setItemCode(item.getItemCode()) // Integer
                .setItemName(item.getItemName())
                .setCategory(item.getCategory())
                .setDescription(item.getDescription())
                .setUnitPrice(item.getUnitPrice())
                .setStockQuantity(item.getStockQuantity())
                .setPublisher(item.getPublisher())
                .setAuthor(item.getAuthor())
                .build();
    }

    public static Item toEntity(ItemDTO dto) {
        if (dto == null) return null;

        return new Item.Builder()
                .setItemCode(dto.getItemCode()) // Integer
                .setItemName(dto.getItemName())
                .setCategory(dto.getCategory())
                .setDescription(dto.getDescription())
                .setUnitPrice(dto.getUnitPrice())
                .setStockQuantity(dto.getStockQuantity())
                .setPublisher(dto.getPublisher())
                .setAuthor(dto.getAuthor())
                .build();
    }

    public static ItemDTO buildItemDTOFromRequest(HttpServletRequest req) {
        // Do not set itemCode for new items (auto-incremented by DB)
        String itemName = req.getParameter("itemName");
        String category = req.getParameter("category");
        String description = req.getParameter("description");
        String unitPriceStr = req.getParameter("unitPrice");
        String stockQuantityStr = req.getParameter("stockQuantity");
        String publisher = req.getParameter("publisher");
        String author = req.getParameter("author");

        Double unitPrice = null;
        Integer stockQuantity = null;
        try {
            if (unitPriceStr != null && !unitPriceStr.isEmpty()) {
                unitPrice = Double.parseDouble(unitPriceStr);
            }
        } catch (NumberFormatException e) {
            unitPrice = null;
        }
        try {
            if (stockQuantityStr != null && !stockQuantityStr.isEmpty()) {
                stockQuantity = Integer.parseInt(stockQuantityStr);
            }
        } catch (NumberFormatException e) {
            stockQuantity = null;
        }

        return new ItemDTO.Builder()
                .setItemName(itemName)
                .setCategory(category)
                .setDescription(description)
                .setUnitPrice(unitPrice)
                .setStockQuantity(stockQuantity)
                .setPublisher(publisher != null && !publisher.isEmpty() ? publisher : "N/A")
                .setAuthor(author != null && !author.isEmpty() ? author : "N/A")
                .build();
    }

    public static Item mapToItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("item_code"),
                rs.getString("item_name"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getDouble("unit_price"),
                rs.getInt("stock_quantity"),
                rs.getString("publisher"),
                rs.getString("author")
        );
    }
}