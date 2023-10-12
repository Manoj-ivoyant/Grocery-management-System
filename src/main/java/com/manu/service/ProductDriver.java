package com.manu.service;

import com.manu.config.ConnectionGenerator;
import com.manu.dao.ProductDao;
import com.manu.dto.Product;
import com.manu.dto.PurchaseDto;
import com.manu.exception.ResourceNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ProductDriver {
    private static final Logger logger = LoggerFactory.getLogger(ProductDriver.class);

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        boolean flag = true;

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Grocery Management system");

        do {
            System.out.println("##################################################################");
            System.out.println("choices are");
            System.out.println("1.check connection status");
            System.out.println("2.open the connection");
            System.out.println("3.create tables");
            System.out.println("4.Insert the data into product");
            System.out.println("5.Get the product by product Name");
            System.out.println("6.Get All Products");
            System.out.println("7.Get the Product By likesearch");
            System.out.println("8.update the product quantity");
            System.out.println("9.update the product Price");
            System.out.println("10.delete the product");
            System.out.println("11.purchase the product");
            System.out.println("12.view purchase history");
            System.out.println("13.close the connection");
            System.out.println("14.exit");

            System.out.println();
            System.out.println("enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    if (ConnectionGenerator.connection != null) {
                        System.out.println("connection is in opened state");
                    } else {
                        System.out.println("connection is in closed state");
                    }
                    break;
                case 2:
                    ConnectionGenerator.createConnection();
                    if (ConnectionGenerator.connection != null) {
                        System.out.println("connection established successfull");
                    }
                    break;
                case 3:
                    if (ConnectionGenerator.connection != null) {
                        productDao.createTable();
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }
                    break;
                case 4:
                    if (ConnectionGenerator.connection != null) {

                        sc.nextLine();
                        System.out.println("Enter product name");
                        String name = sc.nextLine();


                        System.out.println("Enter product quantity");
                        int quantity = Integer.parseInt(sc.nextLine());

                        System.out.println("Enter product price");
                        double price = Double.parseDouble(sc.nextLine());

                        System.out.println("Enter product Qr");
                        String productQr = sc.nextLine();

                        Product product = new Product(name, quantity, price, productQr);
                        productDao.insertTable(product);
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }
                    break;
                case 5:
                    if (ConnectionGenerator.connection != null) {
                        sc.nextLine();
                        System.out.println("enter product name");
                        String productName = sc.nextLine();
                        productDao.getByName(productName);
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }


                    break;
                case 6:
                    if (ConnectionGenerator.connection != null) {
                        productDao.getAll();
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }

                    break;
                case 7:
                    if (ConnectionGenerator.connection != null) {
                        sc.nextLine();
                        System.out.println("enter the key(productName) to search");
                        String key = sc.nextLine();
                        productDao.getByNameLike(key);
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }

                    break;
                case 8:
                    if (ConnectionGenerator.connection != null) {
                        try {
                            sc.nextLine();
                            System.out.println("enter the productname and product quantity to update");
                            String pro = sc.nextLine();
                            Integer updateQuantity = sc.nextInt();
                            productDao.updateQuantity(pro, updateQuantity);
                        } catch (ResourceNotFound e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }

                    break;
                case 9:
                    if (ConnectionGenerator.connection != null) {
                        try {
                            sc.nextLine();
                            System.out.println("enter the product name and product price to update");
                            String prod = sc.nextLine();
                            Double pri = sc.nextDouble();
                            productDao.updatePrice(prod, pri);
                        } catch (ResourceNotFound e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }

                    break;
                case 10:
                    if (ConnectionGenerator.connection != null) {
                        try {
                            sc.nextLine();
                            System.out.println("enter the product name to delete");
                            String productNaame = sc.nextLine();
                            productDao.deleteByName(productNaame);

                        } catch (ResourceNotFound e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }
                    break;
                case 11:
                    if (ConnectionGenerator.connection != null) {
                        double totalInvoicePrice = 0.0;
                        boolean continuePurchasing = true;

                        while (continuePurchasing) {
                            sc.nextLine();
                            System.out.println("Enter product QR");
                            String productQR = sc.nextLine();
                            System.out.println("Enter product quantity to purchase");
                            int purchasingQuantity = Integer.parseInt(sc.nextLine());

                            PurchaseDto purchaseDto = new PurchaseDto(productQR, purchasingQuantity);
                            double purchasePrice = productDao.purchaseProduct(purchaseDto);

                            totalInvoicePrice += purchasePrice;
                            //sc.nextLine();
                            System.out.println("Do you wish to purchase another product? (y/n)");
                            String c = sc.nextLine();

                            if (c.equalsIgnoreCase("n")) {
                                continuePurchasing = false;
                            }
                        }

                        // Provide invoice for the current purchase session
                        System.out.println("Invoice for this purchase session:");
                        System.out.println("================================");
                        System.out.println("Inovice price:" + totalInvoicePrice);
                        System.out.println("================================");
                    } else {
                        logger.error("Connection closed. Choose choice 2 to get a connection.");
                    }
                    break;


                case 12:
                    if (ConnectionGenerator.connection != null) {
                        productDao.viewPurchaseHistory();
                    } else {
                        logger.error("connection closed choose choice 2 to get connection ");
                    }
                    break;
                case 13:
                    if (ConnectionGenerator.connection != null) {
                        ConnectionGenerator.connection = null;
                        System.out.println("Connection closed sucessfully");
                    }
                    break;
                case 14:
                    flag = false;
                    break;
                default:
                    System.out.println("invalid choice");
            }
        } while (flag);
    }
}
