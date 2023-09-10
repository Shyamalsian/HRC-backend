package com.highradius.connection;

import com.highradius.model.Invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static List<Invoice> invoices;

    static {
        invoices = new ArrayList<>();
    }

    public static List<Invoice> getInvoices() {
        return invoices;
    }

    public static void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String url = "jdbc:mysql://localhost:3306/hrc";
        String username = "root";
        String password = "Preet@9899";
        String sqlQuery = "SELECT * FROM h2h_oap LIMIT 10";

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                int customerId = resultSet.getInt("CUSTOMER_ORDER_ID");
                int salesOrg = resultSet.getInt("SALES_ORG");
                String distributionChannel = resultSet.getString("DISTRIBUTION_CHANNEL");
                int customerNumber=resultSet.getInt("CUSTOMER_NUMBER");
                int companyCode = resultSet.getInt("COMPANY_CODE");
                double orderAmount = resultSet.getDouble("ORDER_AMOUNT");
                String orderCurrency = resultSet.getString("ORDER_CURRENCY");
                
                double amountInUSD = resultSet.getDouble("AMOUNT_IN_USD");
                String orderCreation = resultSet.getString("ORDER_CREATION_TIME");

                Invoice invoice = new Invoice(customerId, salesOrg, distributionChannel,customerNumber, 
                		companyCode, orderAmount,orderCurrency, amountInUSD, orderCreation);
                invoices.add(invoice);
                System.out.println(invoice); // Print the retrieved invoice
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}