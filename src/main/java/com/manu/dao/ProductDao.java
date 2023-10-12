package com.manu.dao;

import com.manu.config.ConnectionGenerator;
import com.manu.dto.Product;
import com.manu.dto.PurchaseDto;
import com.manu.exception.ResourceNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);

    public void createTable() {
        Connection connection = ConnectionGenerator.connection;

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
        Connection connection = ConnectionGenerator.connection;

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
        Connection connection = ConnectionGenerator.connection;

        String query = "select * from product where product_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            boolean flag = false;

            ResultSet resultSet = statement.executeQuery();

            // Print column headers in a table format
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");
            System.out.println("| Product ID | Product Name             | Available Quantity   | Price    | Product QR             |");
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            while (resultSet.next()) {
                flag = true;
                int productId = resultSet.getInt("product_id");
                String product_name = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");
                String productQr = resultSet.getString("product_qr");

                // Format and print each record in a table format
                System.out.printf("| %-10d | %-24s | %-20d | %-8.2f | %-22s |%n", productId, product_name, availableQuantity, price, productQr);
            }

            // Print a closing line for the table
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            statement.close();

            if (!flag) {
                logger.warn("throws resource not found exception");
                throw new ResourceNotFound("Product: " + productName + " not found");
            }
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAll() {
        Connection connection = ConnectionGenerator.createConnection();
        String query = "select * from product";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            boolean flag = false;

            // Print column headers in a table format
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");
            System.out.println("| Product ID | Product Name             | Available Quantity   | Price    | Product QR             |");
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            while (resultSet.next()) {
                flag = true;
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");
                String productQr = resultSet.getString("product_qr");

                // Format and print each record in a table format
                System.out.printf("| %-10d | %-24s | %-20d | %-8.2f | %-22s |%n", productId, productName, availableQuantity, price, productQr);
            }

            // Print a closing line for the table
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            if (!flag) {
                throw new ResourceNotFound("Product not found (empty table)");
            }

            statement.close();
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteByName(String productName) {
        Connection connection = ConnectionGenerator.connection;

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
        Connection connection = ConnectionGenerator.connection;

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
        Connection connection = ConnectionGenerator.connection;

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
        Connection connection = ConnectionGenerator.connection;
        String query = "select * from product where product_name like ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + key + "%");
            boolean flag = false;
            ResultSet resultSet = statement.executeQuery();

            // Print column headers in a table format
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");
            System.out.println("| Product ID | Product Name             | Available Quantity   | Price    | Product QR             |");
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            while (resultSet.next()) {
                flag = true;
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                int availableQuantity = resultSet.getInt("available_quantity");
                double price = resultSet.getDouble("price");
                String productQr = resultSet.getString("product_qr");

                // Format and print each record in a table format
                System.out.printf("| %-10d | %-24s | %-20d | %-8.2f | %-22s |%n", productId, productName, availableQuantity, price, productQr);
            }

            // Print a closing line for the table
            System.out.println("+------------+--------------------------+----------------------+----------+------------------------+");

            if (!flag) {
                throw new ResourceNotFound("Product for the specified key match not found");
            }

            statement.close();
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public double purchaseProduct(PurchaseDto purchaseDto) {
        Connection connection = ConnectionGenerator.connection;
        String query = "select * from product where product_qr=?";
        double invoicePrice = 0.0;

        try {
            boolean flag = false;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, purchaseDto.getProductQr());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                flag = true;
                double price = resultSet.getDouble("price");
                double totalPrice = purchaseDto.getQuantity() * price;
                int availableQuantity = resultSet.getInt("available_quantity");

                if (availableQuantity >= purchaseDto.getQuantity()) {
                    availableQuantity -= purchaseDto.getQuantity();
                    String productName = resultSet.getString("product_name");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                    String insertQuery = "INSERT INTO billing (total_price, product_qr, purchased_quantity, purchasedTime) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setDouble(1, totalPrice);
                    insertStatement.setString(2, purchaseDto.getProductQr());
                    insertStatement.setInt(3, purchaseDto.getQuantity());
                    insertStatement.setTimestamp(4, timestamp);
                    int insertResult = insertStatement.executeUpdate();

                    if (insertResult == 1) {
                        updateQuantity(productName, availableQuantity);
                        logger.info("Product purchased successfully");
                        invoicePrice = totalPrice;
                    }
                    insertStatement.close();
                } else {
                    logger.info("Requested quantity not available");
                }
            }

            if (!flag) {
                throw new ResourceNotFound("Product not found (empty table)");
            }
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoicePrice;
    }

    public void viewPurchaseHistory() {
        Connection connection = ConnectionGenerator.connection;

        String query = "select * from billing";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            boolean flag = false;

            // Print column headers for the invoice
            System.out.println("Invoice for this purchase session:");
            System.out.println("===================================");
            System.out.printf("%-12s | %-10s | %-8s%n", "Product QR", "Quantity", "Price");
            System.out.println("===================================");

            double totalInvoicePrice = 0.0;

            while (resultSet.next()) {
                flag = true;
                String productQr = resultSet.getString("product_qr");
                int purchasedQuantity = resultSet.getInt("purchased_quantity");
                double price = resultSet.getDouble("total_price");

                // Format and print each line of the invoice
                System.out.printf("%-12s | %-10d | %-8.2f%n", productQr, purchasedQuantity, price);
                totalInvoicePrice += price;
            }

            // Print the total invoice price
            System.out.println("===================================");
            System.out.printf("Total Invoice Price: %.2f%n", totalInvoicePrice);

            statement.close();

            if (!flag) {
                throw new ResourceNotFound("Billing table is empty");
            }
        } catch (ResourceNotFound e) {
            logger.error(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

