package shoppingsystem.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import shoppingsystem.interfaces.ProductCatalogInterface;

public class ProductCatalog implements ProductCatalogInterface {
    private Connection connection;

    public ProductCatalog() {
        // Default constructor
    }

    public void initializeConnection() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try {
                initializeConnection();
                String query = "SELECT * FROM products";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int productId = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    // Create a Product object and add it to the list
                    Product product = new Product(productId, productName, price,description);
                    products.add(product);
                }
        } catch (SQLException e) {
            e.printStackTrace();
         
            throw e;
        }
        return products;
    }

    @Override
    public void addProduct(Product product) {
        try {
            initializeConnection();
            String query = "INSERT INTO products (id, name, price, description) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    @Override
    public void removeProduct(int productId) {
        try {
            initializeConnection();
            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }

    public Product getProductById(int productId) {
        try {
            initializeConnection();
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                return new Product(productId, name, price, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return null; // Product not found or error occurred
    }
}
