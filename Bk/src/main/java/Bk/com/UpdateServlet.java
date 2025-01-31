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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String newEmail = request.getParameter("email");
        String newBloodGroup = request.getParameter("bloodGroup");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET email = ?, blood_group = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newEmail);
            stmt.setString(2, newBloodGroup);
            stmt.setString(3, username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.getWriter().println("User information updated successfully!");
            } else {
                response.getWriter().println("No user found with the given username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error updating user information!");
        }
    }
}
