package com.manu.service;

import com.manu.dao.ProductDao;
import com.manu.dto.Product;
import com.manu.exception.ResourceNotFound;

import java.util.Scanner;

public class ProductDriver {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        boolean flag = true;

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Grocery Management system");

        do {
            System.out.println("##################################################################");
            System.out.println("choices are");
            System.out.println("1.create product table");
            System.out.println("2.Insert the data into product");
            System.out.println("3.Get the product by product Name");
            System.out.println("4.Get All Products");
            System.out.println("5.Get the Procuct By likesearch");
            System.out.println("6.update the product quantity");
            System.out.println("7.update the product Price");
            System.out.println("8.delete the product");
            System.out.println("9.exit");
            System.out.println();
            System.out.println("enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    productDao.createTable();
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Enter product name");
                    String name = sc.nextLine();

                    System.out.println("Enter product quantity");
                    int quantity = Integer.parseInt(sc.nextLine());

                    System.out.println("Enter product price");
                    double price = Double.parseDouble(sc.nextLine());

                    Product product = new Product(name, quantity, price);
                    productDao.insertTable(product);
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("enter product name");
                    String productName = sc.nextLine();
                    productDao.getByName(productName);
                    break;
                case 4:
                    productDao.getAll();
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("enter the key(productName) to search");
                    String key = sc.nextLine();
                    productDao.getByNameLike(key);
                    break;
                case 6:
                    try {
                        sc.nextLine();
                        System.out.println("enter the productname and product quantity to update");
                        String pro = sc.nextLine();
                        Integer updateQuantity = sc.nextInt();
                        productDao.updateQuantity(pro, updateQuantity);
                    } catch (ResourceNotFound e) {
                        System.out.println(e.getLocalizedMessage());
                    }

                    break;
                case 7:
                    try {
                        sc.nextLine();
                        System.out.println("enter the product name and product price to update");
                        String prod = sc.nextLine();
                        Double pri = sc.nextDouble();
                        productDao.updatePrice(prod, pri);
                    } catch (ResourceNotFound e) {
                        System.out.println(e.getLocalizedMessage());
                    }

                    break;
                case 8:
                    try {
                        sc.nextLine();
                        System.out.println("enter the product name to delete");
                        String productNaame = sc.nextLine();
                        productDao.deleteByName(productNaame);

                    } catch (ResourceNotFound e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;

                case 9:
                    flag = false;
                    break;
                default:
                    System.out.println("invalid choice");

            }


        } while (flag);
    }
}
