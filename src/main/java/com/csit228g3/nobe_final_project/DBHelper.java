/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csit228g3.nobe_final_project;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author LENOVO
 */
public class DBHelper {

    private static final String DB_URL = "jdbc:mysql://localhost/dbims"; // replace with your database URL
    private static final String USER = "root"; // replace with your username
    private static final String PASS = ""; // replace with your password
    private Connection conn;
    private Statement stmt;

    public static Connection getConnection() throws SQLException {
        // Register JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Open a connection
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }

    public DBHelper() {
        try {

            conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addProduct(String name, double price, int category_id, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        String values = String.format("NULL, '%s', %f, %d, '%s'", name, price, category_id, parsedDate);
        executeUpdate("INSERT INTO tblproduct (id, name, price, category_id, date) values (" + values + ")");
    }

    public void addCategory(String name) {
        executeUpdate("INSERT INTO tblcategory (id, name) VALUES (NULL, '" + name + "')");
    }

    public void editProduct(int id, String name, double price, int cateogry_id, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        String sql = String.format("UPDATE tblproduct SET name = '%s', price = %f, category_id = '%d', date = '%s' WHERE id = %d",
                name, price, cateogry_id, parsedDate, id);
        executeUpdate(sql);
    }

    public ResultSet getProducts() {
        return executeQuery("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p inner join tblcategory c"
                + " ON p.category_id = c.id");
    }

    public void deleteProduct(int id) {
        executeUpdate("DELETE FROM tblproduct WHERE id = " + id);
    }

    public ResultSet getProductsById(int id) {
        return executeQuery("SELECT p.id, p.name, price, c.name as category, category_id, date "
                + "FROM tblproduct p INNER JOIN tblcategory c ON p.category_id = c.id "
                + "WHERE p.id = " + id);
    }

    public ResultSet getProductsByName(String name) {
        return executeQuery("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p INNER JOIN tblcategory c ON p.category_id = c.id "
                + "WHERE p.name LIKE '%" + name + "%'");
    }

    public ResultSet getProductsByCategory(String category) {
        return executeQuery("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p inner join tblcategory c "
                + "ON p.category_id = c.id "
                + "WHERE c.name LIKE '%" + category + "%'");
    }

    public ResultSet getCategories() {
        return executeQuery("SELECT * FROM tblcategory");
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
