/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csit228g3.gaviola_final_project;

import com.csit228g3.gaviola_final_project.MySQLConnection.Query;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dstc
 */
public class DBHelper {

    public Query query;

    public DBHelper() {
        try {
            query = new MySQLConnection.Query();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCustomer(String first_name, String last_name, String phone_number) throws SQLException {
        String values = String.format("NULL, '%s', '%s', '%s'", first_name, last_name, phone_number);
        query.update("INSERT INTO tblcustomer (id, first_name, last_name, phone_number) values (" + values + ")");
    }

    public ResultSet getCustomers() throws SQLException {
        return query.execute("SELECT * FROM tblcustomer");
    }

    public ResultSet getCustomerById(int id) throws SQLException {
        return query.execute("SELECT * FROM tblcustomer WHERE id = " + id);
    }

    public ResultSet getCustomerByFirstName(String first_name) throws SQLException {
        return query.execute("SELECT * FROM tblcustomer WHERE first_name = '" + first_name + "'");
    }

    public ResultSet getCustomerByLastName(String last_name) throws SQLException {
        return query.execute("SELECT * FROM tblcustomer WHERE last_name = '" + last_name + "'");
    }

    public ResultSet getCustomerByPhoneNumber(String phone_number) throws SQLException {
        return query.execute("SELECT * FROM tblcustomer WHERE phone_number = '" + phone_number + "'");
    }

    public void deleteCustomer(int id) throws SQLException {
        query.update("DELETE FROM tblcustomer WHERE id = " + id);
    }

    public void updateCustomer(int id, String first_name, String last_name, String phone_number) throws SQLException {
        String sql = String.format("UPDATE tblcustomer SET first_name = '%s', last_name = '%s', phone_number = '%s' WHERE id = %d",
                first_name, last_name, phone_number, id);
        query.update(sql);
    }

    public void close() throws SQLException {
        query.close();
    }
}
