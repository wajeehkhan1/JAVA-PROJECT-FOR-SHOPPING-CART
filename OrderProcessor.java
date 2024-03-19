package shoppingsystem.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import shoppingsystem.Interfaces.OrderProcessorInterface;


public class OrderProcessor implements OrderProcessorInterface {
    private MockPaymentGateway paymentGateway;
    private Logger logger;
    private Connection connection;

    public OrderProcessor(MockPaymentGateway paymentGateway, Logger logger) {
        this.paymentGateway = paymentGateway;
        this.logger = logger;
        
    }
    public void initializeConnection() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    @Override
    public boolean placeOrder(Order order, int user_id,List<CartItem> items,double totalPrice) {
            // Validate the order
            if (order == null || order.getProducts().isEmpty()) {
                logger.log("Invalid order. No products in the order.");
                return false;
            }

            // Simulate payment processing
            boolean paymentResult = paymentGateway.processPayment(totalPrice);

            // If payment was successful, process the order
            if (paymentResult) {
                // Insert order into the database
                try {
                    initializeConnection();
                    String insertQuery = "INSERT INTO orders (total_price, user_id) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                    statement.setDouble(1, totalPrice);
                    statement.setInt(2, user_id);
                    statement.executeUpdate();

                    // Retrieve the auto-generated keys (the order ID)
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                            int orderId = generatedKeys.getInt(1);

                            logger.log("Order placed successfully. Order ID: " + orderId + ", Total price: $" + totalPrice);
                            for (CartItem item : items) {
                                saveOrderItem(orderId, item);
                            }

                            return true;
                    } else {
                        logger.log("Failed to retrieve auto-generated key for the order.");
                        return false;
                    }
                } catch (SQLException e) {
                    logger.log("Failed to insert order into the database: " + e.getMessage());
                    return false;
                }
            } else {
                logger.log("Payment processing failed. Order could not be placed.");
                return false;
            }
}

        public void saveOrderItem(int orderId, CartItem item) {
                try {
                    initializeConnection();
                    String query = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, orderId);
                    statement.setInt(2, item.getProductId());
                    statement.setInt(3, item.getQuantity());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    
                }
}

}
