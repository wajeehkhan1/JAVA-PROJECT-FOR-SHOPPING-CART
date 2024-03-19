package shoppingsystem.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Connection connection;

    public UserManager() {
        
    }

    public void initializeConnection() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
        
    }
    public int login(String username, String password) {
            try {
                initializeConnection();
                // Query to retrieve the user_id associated with the username and password
                String query = "SELECT id FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    // Login successful, return the user_id
                    System.out.println("Login successful!");

                    System.out.println("----------------"+" Welcome " + username+"----------------" );
                    return userId;
                } else {
                    // Username/password combination not found
                    System.out.println("Incorrect username or password.");
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return -1; // Return -1 if login fails
}

    public boolean logout(String username) {
      
        System.out.println("Logged out user: " + username);
        return true;
    }

    public boolean createUser(String username, String password) {
            try {
                initializeConnection();

                // Insert the user into the database if the username doesn't already exist
                String insertQuery = "INSERT INTO users (username, password) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, username);

                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User created successfully: " + username);
                    return true;
                } else {
                    // Username already exists
                    System.out.println("Username already exists: " + username);
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
           
                return false;
            }
        }

}
