package com.highradius.implementation;

import com.highradius.model.Invoice;
import com.highradius.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvoiceDaoImpl implements InvoiceDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hrc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Preet@9899";

    private DatabaseConnection databaseConnection;

    public InvoiceDaoImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public List<Invoice> getInvoices() {
        return databaseConnection.getInvoices();
    }

    @Override
    public void insertInvoice(Invoice invoice) {
        databaseConnection.addInvoice(invoice);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "INSERT INTO h2h_oap (CUSTOMER_ORDER_ID, SALES_ORG, DISTRIBUTION_CHANNEL, CUSTOMER_NUMBER, COMPANY_CODE, ORDER_AMOUNT, ORDER_CURRENCY, AMOUNT_IN_USD, ORDER_CREATION_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            	
                statement.setInt(1, invoice.getCustomerOrderId());
                statement.setInt(2, invoice.getSalesOrg());
                statement.setString(3, invoice.getDistributionChannel());
                statement.setInt(4, invoice.getCustomerNumber());
                statement.setInt(5, invoice.getCompanyCode());
                statement.setDouble(6, invoice.getOrderAmount());
                statement.setString(7, invoice.getOrderCurrency());
                statement.setDouble(8, invoice.getAmountInUSD());
                statement.setString(9, invoice.getOrderCreation());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions during the database operation
        }
    }


    @Override
    public void updateInvoice(int id, Invoice invoice) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "UPDATE h2h_oap SET CUSTOMER_ORDER_ID = ?, SALES_ORG = ?, DISTRIBUTION_CHANNEL = ?, CUSTOMER_NUMBER = ?, COMPANY_CODE = ?, ORDER_AMOUNT = ?, ORDER_CURRENCY = ?, AMOUNT_IN_USD = ?, ORDER_CREATION_TIME = ? WHERE Sl_no = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, invoice.getCustomerOrderId());
                statement.setInt(2, invoice.getSalesOrg());
                statement.setString(3, invoice.getDistributionChannel());
                statement.setInt(4, invoice.getCustomerNumber());
                statement.setInt(5, invoice.getCompanyCode());
                statement.setDouble(6, invoice.getOrderAmount());
                statement.setString(7, invoice.getOrderCurrency());
                statement.setDouble(8, invoice.getAmountInUSD());
                statement.setString(9, invoice.getOrderCreation());
                statement.setInt(10, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions during the database operation
        }
    }


    @Override
    public void deleteInvoice(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "DELETE FROM h2h_oap WHERE Sl_no = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions during the database operation
        }
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
		return false;
        // Implement the addInvoice method logic here
        // You can add the implementation based on your requirements
    }
}