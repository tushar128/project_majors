package Cafe.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT item_name FROM Menu";  // Query to get item names from the Menu
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            List<String> menuItems = new ArrayList<>();
            while (rs.next()) {
                menuItems.add(rs.getString("item_name"));
            }

            // Set the content type to HTML
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Place Order</title>");
            out.println("<link rel='stylesheet' href='styles.css'>"); // Link to external CSS
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='order-container'>");
            out.println("<h2>Place Order</h2>");
            out.println("<form action='placeOrder' method='post' class='order-form'>");
            out.println("<label for='customer_name'>Customer Name:</label>");
            out.println("<input type='text' id='customer_name' name='customer_name' required><br>");
            out.println("<label for='items'>Item:</label>");
            out.println("<select name='items' required>");
            for (String item : menuItems) {
                out.println("<option value='" + item + "'>" + item + "</option>");
            }
            out.println("</select><br>");
            out.println("<label for='quantities'>Quantity:</label>");
            out.println("<input type='number' name='quantities' required><br>");
            out.println("<button type='submit'>Place Order</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customer_name");
        String item = request.getParameter("items");
        String quantity = request.getParameter("quantities");

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT price FROM Menu WHERE item_name = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, item);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                double totalPrice = price * Integer.parseInt(quantity);

                String insertOrder = "INSERT INTO Orders (customer_name, total_price) VALUES (?, ?)";
                PreparedStatement pstOrder = con.prepareStatement(insertOrder);
                pstOrder.setString(1, customerName);
                pstOrder.setDouble(2, totalPrice);
                pstOrder.executeUpdate();

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<p class='success-message'>Order placed successfully! Total Price: " + totalPrice + "</p>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
