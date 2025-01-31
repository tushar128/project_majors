package Bk.com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String bloodGroup = request.getParameter("bloodGroup");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, email, blood_group) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, bloodGroup);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().println("Registration successful!");
            } else {
                response.getWriter().println("Registration failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error!");
        }
    }
}
