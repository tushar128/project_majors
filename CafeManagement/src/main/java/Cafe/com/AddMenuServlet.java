package Cafe.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addMenu")
public class AddMenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemName = request.getParameter("item_name");
        String price = request.getParameter("price");

        try (Connection con = DBConnection.getConnection()) {
            String insertQuery = "INSERT INTO Menu (item_name, price) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, itemName);
            pst.setDouble(2, Double.parseDouble(price));
            pst.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<p class='success-message'>Menu item added successfully!</p>");
            out.println("<a href='placeOrder.html'>Back to Home</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
