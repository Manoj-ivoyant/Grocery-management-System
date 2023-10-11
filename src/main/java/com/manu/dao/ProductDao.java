package com.manu.dao;

import com.manu.config.ConnectionGenerator;
import com.manu.dto.Product;
import com.manu.exception.ResourceNotFound;

import java.sql.*;

public class ProductDao {


    public void createTable() {

        Connection connection = ConnectionGenerator.createConnection();
        try {
            String query = "CREATE TABLE product ("
                    + "product_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "product_name VARCHAR(40) NOT NULL UNIQUE, "
                    + "available_quantity INT, "
                    + "price DOUBLE"
                    + ")";
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Table created sucessfully");
            connection.close();
            statement.close();


        } catch (SQLSyntaxErrorException e) {
            System.out.println("table already created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void insertTable(Product product) {
        Connection connection = ConnectionGenerator.createConnection();
        try {
            String query = "INSERT INTO product (product_name, available_quantity, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getAvailableQuantity());
            statement.setDouble(3, product.getPrice());
            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("product inserted sucessfully");
            }
            statement.close();
            connection.close();


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("product already exist");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getByName(String productName) {

        Connection connection = ConnectionGenerator.createConnection();
        String query = "select *from product where product_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            boolean flag = false;

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = true;
                // Retrieve and process the data from the result set
                int productId = resultSet.getInt("product_id");
                String productname = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");

                // Process the retrieved data as needed
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productname);
                System.out.println("Available Quantity: " + availableQuantity);
                System.out.println("Price: " + price);
            }
            connection.close();
            statement.close();
            if (!flag) {
                throw new ResourceNotFound("product:" + productName + " not found");
            }

        } catch (ResourceNotFound e) {
            System.out.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getAll() {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "select *from product";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            boolean flag = false;
            while (resultSet.next()) {
                flag = true;
                // Retrieve and process the data from the result set
                int productId = resultSet.getInt("product_id");
                String productname = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");

                // Process the retrieved data as needed
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productname);
                System.out.println("Available Quantity: " + availableQuantity);
                System.out.println("Price: " + price);

            }
            connection.close();
            statement.close();
            if (!flag) {
                throw new ResourceNotFound("product not found(empty table)");
            }


        } catch (ResourceNotFound e) {
            System.out.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteByName(String productName) {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "delete from product where product_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            int res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("product with productName:" + productName + " deleted successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateQuantity(String productName, Integer quantity) {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "update product set available_quantity=? where product_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setString(2, productName);
            int res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("product updated successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void updatePrice(String productName, Double price) {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "update product set price=? where product_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, price);
            statement.setString(2, productName);
            int res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("product updated successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void getByNameLike(String key) {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "select *from product where product_name like ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + key + "%");
            boolean flag = false;

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = true;
                // Retrieve and process the data from the result set
                int productId = resultSet.getInt("product_id");
                String productname = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");

                // Process the retrieved data as needed
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productname);
                System.out.println("Available Quantity: " + availableQuantity);
                System.out.println("Price: " + price);


            }
            if (!flag) {
                throw new ResourceNotFound("product for specified key match not found");
            }
            connection.close();
            statement.close();
        } catch (ResourceNotFound e) {
            System.out.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
