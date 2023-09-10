package com.highradius.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/DataLoadingServlet")
public class DataLoadingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hrc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Preet@9899";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set CORS headers to allow requests from any origin
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Rest of your servlet code
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a statement
            statement = connection.createStatement();

            // Execute the query
            String sql = "SELECT * FROM h2h_oap LIMIT 100";
            resultSet = statement.executeQuery(sql);

            // Create a Gson object
            Gson gson = new Gson();

            // Create a JSON array to hold the invoice data
            JsonArray jsonArray = new JsonArray();

            while (resultSet.next()) {
                // Create a JSON object for each invoice
                JsonObject invoiceJson = new JsonObject();
                invoiceJson.addProperty("Sl_no", resultSet.getInt("Sl_No"));
                invoiceJson.addProperty("customer_id", resultSet.getInt("CUSTOMER_ORDER_ID"));
                invoiceJson.addProperty("sales_org", resultSet.getString("SALES_ORG"));
                invoiceJson.addProperty("distribution_channel", resultSet.getString("DISTRIBUTION_CHANNEL"));
                invoiceJson.addProperty("company_code", resultSet.getString("COMPANY_CODE"));
                invoiceJson.addProperty("order_currency", resultSet.getString("ORDER_CURRENCY"));
                invoiceJson.addProperty("amount_usd", resultSet.getDouble("AMOUNT_IN_USD"));
                invoiceJson.addProperty("order_amount", resultSet.getFloat("ORDER_AMOUNT"));
                invoiceJson.addProperty("customer_number", resultSet.getInt("CUSTOMER_NUMBER"));
                invoiceJson.addProperty("order_creation_date", resultSet.getString("ORDER_CREATION_DATE"));

                // Add the invoice object to the JSON array
                jsonArray.add(invoiceJson);
            }

            // Convert the JSON array to a string
            String jsonOutput = gson.toJson(jsonArray);

            // Write the JSON string to the response PrintWriter
            out.println(jsonOutput);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources
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


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}