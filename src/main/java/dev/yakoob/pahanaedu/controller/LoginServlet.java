package dev.yakoob.pahanaedu.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // Hardcoded admin credentials (in a real application, these should be stored securely in a database)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If already logged in, redirect to dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isAdmin") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            // Create session and set admin attribute
            HttpSession session = request.getSession();
            session.setAttribute("isAdmin", true);
            session.setAttribute("username", username);

            // Redirect to dashboard on successful login
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            // Set error message and forward back to login page
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
}
