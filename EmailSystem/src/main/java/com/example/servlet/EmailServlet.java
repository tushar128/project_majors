package com.example.servlet;

import com.example.dao.EmailDao;
import com.example.model.Email;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/emails")
public class EmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Added for serialization

    private EmailDao emailDao = new EmailDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        List<Email> emails = emailDao.searchEmails(searchQuery);
        
        // Ensure emails list is not null
        if (emails == null) {
            emails = List.of(); // Return an empty list instead of null
        }

        String json = new Gson().toJson(emails);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        if (subject == null || body == null || subject.isEmpty() || body.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Subject and body cannot be empty");
            return;
        }

        Email email = new Email();
        email.setSubject(subject);
        email.setBody(body);
        emailDao.addEmail(email);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("Email added successfully");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            emailDao.deleteEmail(id);
            response.getWriter().write("Email deleted successfully");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid email ID");
        }
    }
}
