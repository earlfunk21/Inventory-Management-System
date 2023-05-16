/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csit228g3.nobe_final_project;

import com.csit228g3.nobe_final_project.MySQLConnection.Query;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class DBHelper {
    public Query query;
    
    public DBHelper(){
        try {
            query = new MySQLConnection.Query();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addProduct(String name, double price, int category_id, String date) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        String values = String.format("NULL, '%s', %f, %d, '%s'", name, price, category_id, parsedDate);
        query.update("INSERT INTO tblproduct (id, name, price, category_id, date) values (" + values + ")");
    }
    
    public void addCategory(String name) throws SQLException{
        query.update("INSERT INTO tblcategory (id, name) VALUES (NULL, '" + name + "')");
    }
    
    public void editProduct(int id, String name, double price, int cateogry_id, String date) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        String sql = String.format("UPDATE tblproduct SET name = '%s', price = %f, category_id = '%d', date = '%s' WHERE id = %d",
                name, price, cateogry_id, parsedDate, id);
        query.update(sql);
    }
    
    public ResultSet getProducts() throws SQLException{
        return query.execute("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p inner join tblcategory c"
                + " ON p.category_id = c.id");
    }
    
    public void deleteProduct(int id) throws SQLException{
        query.update("DELETE FROM tblproduct WHERE id = " + id);
    }
    
    public ResultSet getProductsById(int id) throws SQLException{
        return query.execute("SELECT p.id, p.name, price, c.name as category, category_id, date "
                + "FROM tblproduct p INNER JOIN tblcategory c ON p.category_id = c.id "
                + "WHERE p.id = " + id);
    }
    
    public ResultSet getProductsByName(String name) throws SQLException{
        return query.execute("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p INNER JOIN tblcategory c ON p.category_id = c.id "
                + "WHERE p.name LIKE '%" + name + "%'");
    }
    
    public ResultSet getProductsByCategory(String category) throws SQLException{
        return query.execute("SELECT p.id, p.name, price, c.name as category, date "
                + "FROM tblproduct p inner join tblcategory c "
                + "ON p.category_id = c.id "
                + "WHERE c.name LIKE '%" + category + "%'");
    }
    
    public ResultSet getCategories() throws SQLException{
        return query.execute("SELECT * FROM tblcategory");
    }
    
    public void close() throws SQLException{
        query.close();
    }
  
    
}
