package dev.yakoob.pahanaedu.business.item.servlet;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;
import dev.yakoob.pahanaedu.business.item.mapper.ItemMapper;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.util.validation.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditItemServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(EditItemServlet.class.getName());
    private ItemService itemService;

    @Override
    public void init() {
        itemService = new ItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        LOGGER.info("EditItemServlet doGet - code: " + code);

        if (code != null && !code.isEmpty()) {
            ItemDTO item = itemService.getItemByCode(code);
            LOGGER.info("Retrieved item: " + (item != null ? item.toString() : "null"));

            if (item != null) {
                req.setAttribute("item", item);
                req.setAttribute("pageTitle", "Edit Item");
                req.setAttribute("body", "../item/edit-item.jsp");

                req.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp").forward(req, resp);
            } else {
                String error = "Couldn't find the item by code: " + code;
                LOGGER.warning(error);
                req.getSession().setAttribute("flash_error", error);
                resp.sendRedirect(req.getContextPath() + "/item");
            }
        } else {
            String error = "Missing or not a valid item code!";
            LOGGER.warning(error);
            req.getSession().setAttribute("flash_error", error);
            resp.sendRedirect(req.getContextPath() + "/item");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Log all parameters for debugging
        LOGGER.info("=== EditItemServlet doPost ===");
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            LOGGER.info(paramName + " = " + req.getParameter(paramName));
        }

        String code = req.getParameter("code");
        LOGGER.info("Updating item with code: " + code);

        if (code != null && !code.isEmpty()) {
            try {
                ItemDTO itemDTO = ItemMapper.buildItemDTOFromRequest(req);
                LOGGER.info("Built ItemDTO: " + itemDTO);

                String validationError = Validation.validateItemDTO(itemDTO);

                if (validationError == null) {
                    LOGGER.info("Updating item in service layer...");
                    itemService.updateItem(code, itemDTO);
                    LOGGER.info("Item update successful");
                    req.getSession().setAttribute("flash_success", "Item updated successfully!");
                } else {
                    LOGGER.warning("Validation failed: " + validationError);
                    req.getSession().setAttribute("flash_error", validationError);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error updating item: " + e.getMessage(), e);
                req.getSession().setAttribute("flash_error", "Error updating item: " + e.getMessage());
            }
        } else {
            String error = "Missing or not a valid item code!";
            LOGGER.warning(error);
            req.getSession().setAttribute("flash_error", error);
        }

        resp.sendRedirect(req.getContextPath() + "/item");
    }
}
