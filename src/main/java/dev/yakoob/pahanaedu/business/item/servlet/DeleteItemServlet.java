package dev.yakoob.pahanaedu.business.item.servlet;

import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.business.item.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() {
        itemService = new ItemServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        if (code != null && !code.isEmpty()) {
            itemService.deleteItem(code);
            req.getSession().setAttribute("flash_success", "Item deleted successfully!");
        } else {
            req.getSession().setAttribute("flash_error", "Missing or not a valid item code!");
        }
        resp.sendRedirect(req.getContextPath() + "/item");
    }
}
