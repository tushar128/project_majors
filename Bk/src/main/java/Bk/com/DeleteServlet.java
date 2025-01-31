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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                response.getWriter().println("User deleted successfully!");
            } else {
                response.getWriter().println("No user found with the given username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error deleting user!");
        }
    }
}
