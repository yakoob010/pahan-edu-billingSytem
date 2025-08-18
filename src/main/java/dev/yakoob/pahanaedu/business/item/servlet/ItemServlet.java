package dev.yakoob.pahanaedu.business.item.servlet;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;
import dev.yakoob.pahanaedu.business.item.mapper.ItemMapper;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static dev.yakoob.pahanaedu.util.validation.Validation.validateItemDTO;

public class ItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        try {
            itemService = new ItemServiceImpl();
            System.out.println("ItemServlet: ItemService initialized successfully");
        } catch (Exception e) {
            System.err.println("Warning: ItemService initialization failed. Database operations may not work.");
            e.printStackTrace();
            // Don't throw exception to allow servlet to still register
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ItemServlet doGet() called - servlet is being reached!");

        List<ItemDTO> itemList = new ArrayList<>();

        try {
            if (itemService != null) {
                itemList = itemService.getAllItems();
                System.out.println("ItemServlet: Items loaded successfully - " + itemList.size() + " items");
            } else {
                System.out.println("ItemServlet: ItemService is null, using empty list");
            }
        } catch (Exception e) {
            System.err.println("Error loading items: " + e.getMessage());
            e.printStackTrace();
            // Continue with empty list
        }

        req.setAttribute("items", itemList);
        req.setAttribute("pageTitle", "Item Management");

        // Forward to the view
        System.out.println("ItemServlet: Attempting JSP forward to /WEB-INF/views/item/view.jsp");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/item/view.jsp");
        dispatcher.forward(req, resp);
        System.out.println("ItemServlet: JSP forward completed successfully");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ItemServlet doPost() called - servlet is being reached!");

        try {
            if (itemService != null) {
                System.out.println("ItemServlet: ItemService is available, proceeding with item creation");
                ItemDTO itemDTO = ItemMapper.buildItemDTOFromRequest(req);
                String validationError = validateItemDTO(itemDTO);

                if (validationError == null) {
                    itemService.saveItem(itemDTO);
                    req.getSession().setAttribute("flash_success", "Item created successfully!");
                    System.out.println("ItemServlet: Item created successfully");
                } else {
                    req.getSession().setAttribute("flash_error", validationError);
                    System.out.println("ItemServlet: Item creation failed due to validation error: " + validationError);
                }
            } else {
                req.getSession().setAttribute("flash_error", "Service not available. Please check database connection.");
                System.out.println("ItemServlet: ItemService is null, cannot create item");
            }
        } catch (Exception e) {
            System.err.println("Error saving item: " + e.getMessage());
            e.printStackTrace();
            req.getSession().setAttribute("flash_error", "Error saving item: " + e.getMessage());
            System.out.println("ItemServlet: Item creation failed due to exception: " + e.getMessage());
        }

        System.out.println("ItemServlet: Redirecting to /item");
        resp.sendRedirect(req.getContextPath() + "/item");
        System.out.println("ItemServlet: Redirect completed successfully");
    }
}
