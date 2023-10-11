package com.manu.dao;

import com.manu.config.ConnectionGenerator;
import com.manu.dto.Product;
import com.manu.dto.PurchaseDto;
import com.manu.exception.ResourceNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class ProductDao {
    private static final Logger logger= LoggerFactory.getLogger(ProductDao.class);

    public void createTable() {

        Connection connection = ConnectionGenerator.createConnection();
        try {
            String query = "CREATE TABLE product (" + "product_id INT AUTO_INCREMENT PRIMARY KEY, " + "product_name VARCHAR(40) NOT NULL UNIQUE, " + "available_quantity INT, " + "price DOUBLE, " + "product_qr VARCHAR(10) NOT NULL UNIQUE " + ")";
            String query2 = "CREATE TABLE billing (" + "bill_id INT AUTO_INCREMENT PRIMARY KEY," + "total_price DOUBLE NOT NULL," + "product_qr VARCHAR(10) NOT NULL," + "purchased_quantity INT NOT NULL," + "purchasedTime TIMESTAMP NOT NULL" + ")";

            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.execute(query2);
            logger.info("Table created successfully");
            statement.close();


        } catch (SQLSyntaxErrorException e) {
            logger.error("table already created");
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }


    }

    public void insertTable(Product product) {
        Connection connection = ConnectionGenerator.createConnection();
        try {
            String query = "INSERT INTO product (product_name, available_quantity, price,product_qr) VALUES (?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getAvailableQuantity());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getProductQr());
            int result = statement.executeUpdate();
            if (result == 1) {
                //System.out.println("product inserted sucessfully");
                logger.info("product inserted successfully");
            }
            statement.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            //System.out.println("product already exist");
            logger.error("product already exist");
        } catch (SQLException e) {
            logger.warn(e.getLocalizedMessage());
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
                String productQr = resultSet.getString("product_qr");

                // Process the retrieved data as needed
                logger.info("Product ID: " + productId);
                logger.info("Product Name: " + productname);
                logger.info("Available Quantity: " + availableQuantity);
                logger.info("Price: " + price);
                logger.info("product Qr " + productQr);
            }
            statement.close();
            if (!flag) {
                logger.warn("throws resource not found exception");
                throw new ResourceNotFound("product:" + productName + " not found");
            }

        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
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
                String productQr = resultSet.getString("product_qr");

                // Process the retrieved data as needed
                logger.info("Product ID: " + productId);
                logger.info("Product Name: " + productname);
                logger.info("Available Quantity: " + availableQuantity);
                logger.info("Price: " + price);
                logger.info("product Qr " + productQr);

            }
            statement.close();
            if (!flag) {
                throw new ResourceNotFound("product not found(empty table)");
            }


        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
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
                logger.info("product with productName:" + productName + " deleted successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
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
                logger.info("product updated successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
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
                logger.info("product updated successfully");
            } else {
                throw new ResourceNotFound("Product " + productName + " not found");
            }
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
                String productQr = resultSet.getString("product_qr");
                // Process the retrieved data as needed
                logger.info("Product ID: " + productId);
                logger.info("Product Name: " + productname);
                logger.info("Available Quantity: " + availableQuantity);
                logger.info("Price: " + price);
                logger.info("product Qr " + productQr);
            }
            if (!flag) {
                throw new ResourceNotFound("product for specified key match not found");
            }
            statement.close();
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void purchaseProduct(PurchaseDto purchaseDto) {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "select *from product where product_qr=?";

        try {
            boolean flag = false;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, purchaseDto.getProductQr());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = true;
                // int quantity=resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");
                Double totalPrice = purchaseDto.getQuantity() * price;
                Integer quantity = resultSet.getInt("available_quantity");
                if (quantity >= purchaseDto.getQuantity()) {
                    quantity = quantity - purchaseDto.getQuantity();
                    String productName = resultSet.getString("product_name");

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String query2 = "INSERT INTO billing (total_price, product_qr, purchased_quantity,purchasedTime) VALUES (?, ?, ?,?)";
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    statement2.setDouble(1, totalPrice);
                    statement2.setString(2, purchaseDto.getProductQr());
                    statement2.setInt(3, purchaseDto.getQuantity());
                    statement2.setTimestamp(4, timestamp);
                    int res = statement2.executeUpdate();
                    if (res == 1) {
                        updateQuantity(productName, quantity);
                        logger.info("product purchased successfully");
                    }
                    statement2.close();

                } else {
                    logger.info("requested quantity not available");
                }
            }
            if (!flag) {
                throw new ResourceNotFound("product not found(empty table)");
            }
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewPurchaseHistory() {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "select *from billing";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            boolean flag = false;
            while (resultSet.next()) {
                flag = true;
                // Retrieve and process the data from the result set
                Integer billingId = resultSet.getInt("bill_id");
                Double price = resultSet.getDouble("total_price");
                String productQr = resultSet.getString("product_qr");
                Integer purchasedQuantity = resultSet.getInt("purchased_quantity");
                Timestamp timestamp = resultSet.getTimestamp("purchasedTime");

                // Process the retrieved data as needed
                logger.info("Invoice ID: " + billingId);
                logger.info("ProductQr: " + productQr);
                logger.info("purchased Quantity: " + purchasedQuantity);
                logger.info("total price: " + price);
                logger.info("purchased at: " + timestamp);

            }
            statement.close();
            if (!flag) {
                throw new ResourceNotFound("Billing table empty ");
            }
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
