package shoppingsystem.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import shoppingsystem.interfaces.CartInterface;

public class Cart implements CartInterface{
            private Connection connection;

            // Private static instance variable
            private static Cart instance;

            // Private constructor to prevent instantiation from outside
            private Cart() {}

            // Static method to get the singleton instance
            public static Cart getInstance() {
                if (instance == null) {
                    instance = new Cart();
                }
                return instance;
            }

            public void initializeConnection() throws SQLException {
                this.connection = DatabaseConnection.getConnection();
            }
            public double calculateTotalPrice(int quantity, double price) {
            return quantity * price;
            }

            @Override
            public void addItem(int productId, int quantity) {
            try {
                this.initializeConnection();

                // Check if the item already exists in the cart
                String checkQuery = "SELECT * FROM cart_items WHERE product_id = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setInt(1, productId);
                ResultSet checkResult = checkStatement.executeQuery();

                if (checkResult.next()) {

                            String priceQuery = "SELECT price FROM products WHERE id = ?";
                            PreparedStatement priceStatement = connection.prepareStatement(priceQuery);
                            priceStatement.setInt(1, productId);
                            ResultSet priceResult = priceStatement.executeQuery();
                            double price = 0.0;
                            if (priceResult.next()) {
                                price = priceResult.getDouble("price");

                            } else {
                                System.out.println("Product not found in the catalog.");
                                return;
                            }
                            // If the item exists, update its quantity
                            int existingQuantity = checkResult.getInt("quantity");
                            int newQuantity = existingQuantity + quantity;
                            double currentPrice = calculateTotalPrice(newQuantity, price);

                            String updateQuery = "UPDATE cart_items SET quantity = ?, price = ? WHERE product_id = ?";
                            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                            updateStatement.setInt(1, newQuantity);
                            updateStatement.setDouble(2, currentPrice);
                            updateStatement.setInt(3, productId);
                            updateStatement.executeUpdate();

                            System.out.println("Item quantity updated successfully!");
                } else {
                            // If the item does not exist, insert it into the cart_items table
                            String productQuery = "SELECT name, description, price FROM products WHERE id = ?";
                            PreparedStatement productStatement = connection.prepareStatement(productQuery);
                            productStatement.setInt(1, productId);
                            ResultSet productResult = productStatement.executeQuery();

                            if (productResult.next()) {
                                String productName = productResult.getString("name");
                                String productDescription = productResult.getString("description");
                                double price = productResult.getDouble("price");
                                double currentPrice = calculateTotalPrice(quantity, price);

                                String cartQuery = "INSERT INTO cart_items (product_id, name, description, quantity, price) VALUES (?, ?, ?, ?, ?)";
                                PreparedStatement cartStatement = connection.prepareStatement(cartQuery);
                                cartStatement.setInt(1, productId);
                                cartStatement.setString(2, productName);
                                cartStatement.setString(3, productDescription);
                                cartStatement.setInt(4, quantity);
                                cartStatement.setDouble(5, currentPrice);
                                cartStatement.executeUpdate();

                                System.out.println("Item added to cart successfully!");
                            } else {
                                System.out.println("Product not found with ID: " + productId);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                }






            public double calculateTotalPriceofCart() {
                double totalPrice = 0.0;

                try {
                        this.initializeConnection();
                        String query = "SELECT price FROM cart_items";
                        PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                            double price = resultSet.getDouble("price");
                            totalPrice += price;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                    }

                    return totalPrice;
                }
            @Override
            public List<CartItem> getItems() throws SQLException {
                    List<CartItem> items = new ArrayList<>();

                    try {
                        this.initializeConnection();
                        String query = "SELECT * FROM cart_items";
                        PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                                int productId = resultSet.getInt("product_id");
                                int quantity = resultSet.getInt("quantity");
                                double price = resultSet.getDouble("price");
                                String name = resultSet.getString("name");
                                String description = resultSet.getString("description");
                                CartItem item = new CartItem(productId, quantity, price, name, description);
                                items.add(item);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                    }

                    return items;
                }

            @Override
            public void removeItem(int productId) {
                try {
                    initializeConnection();
                    String query = "DELETE FROM cart_items WHERE product_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, productId);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void editItem( int productId, int newQuantity) {
                try {
                        initializeConnection();

                        // Get the original price of the product
                        String priceQuery = "SELECT price FROM products WHERE id = ?";
                        PreparedStatement priceStatement = connection.prepareStatement(priceQuery);
                        priceStatement.setInt(1, productId);
                        ResultSet priceResult = priceStatement.executeQuery();
                        double price = 0.0;
                        if (priceResult.next()) {
                            price = priceResult.getDouble("price");
                        } else {
                            System.out.println("Product not found in the catalog.");
                            return;
                        }

                        // Update the quantity and calculate the new price
                        String query = "UPDATE cart_items SET quantity = ?, price = ? WHERE product_id = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, newQuantity);
                        statement.setDouble(2, newQuantity * price); // Calculate new price based on quantity
                        statement.setInt(3, productId);
                        statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void clearCart() {
                    try {
                        this.initializeConnection();
                        String query = "DELETE FROM cart_items";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.executeUpdate();
                        System.out.println("Cart cleared successfully.");
                    } catch (SQLException e) {
                        System.err.println("Failed to clear the cart: " + e.getMessage());
                    }
                }
            }


