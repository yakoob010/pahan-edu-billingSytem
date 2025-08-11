package dev.yakoob.pahanaedu.business.item.servlet;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;
import dev.yakoob.pahanaedu.business.item.mapper.ItemMapper;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import dev.yakoob.pahanaedu.util.validation.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "edit-item", urlPatterns = "/item/edit")
public class EditItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() {
        itemService = new ItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code != null && !code.isEmpty()) {
            ItemDTO item = itemService.getItemByCode(code);

            if (item != null) {
                req.setAttribute("item", item);
                req.setAttribute("pageTitle", "Edit Item");
                req.setAttribute("body", "../item/edit-item.jsp");

                req.getRequestDispatcher("/WEB-INF/views/layout/layout.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("flash_error", "Couldn't find the item by code: " + code);
                resp.sendRedirect(req.getContextPath() + "/item");
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid item code!");
            resp.sendRedirect(req.getContextPath() + "/item");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code != null && !code.isEmpty()) {
            ItemDTO itemDTO = ItemMapper.buildItemDTOFromRequest(req);
            String validationError = Validation.validateItemDTO(itemDTO);

            if (validationError == null) {
                itemService.updateItem(code, itemDTO);
                req.getSession().setAttribute("flash_success", "Item updated successfully!");
            } else {
                req.getSession().setAttribute("flash_error", validationError);
            }
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid item code!");
        }

        resp.sendRedirect(req.getContextPath() + "/item");
    }
}

