
package com.Group1DevopsCoursework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * --------------------------------------------------------------
 * File: Database_Connection.java
 * Description: This class establishes and manages a connection
 *              to the MySQL database used in the project.
 * --------------------------------------------------------------
 */


public class Database_Connection {

    // Database connection object
    private Connection con = null;

    /**
     * Establishes a connection to the MySQL database.
     * Includes retry logic to ensure stability in case of connection issues.
     */
    public void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver. Please ensure the JDBC driver is configured.");
            System.exit(-1); // Exit if driver not found
        }

        // Retry connection up to 20 times
        int retries = 20;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... Attempt " + (i + 1));

            try {
                // Wait 10 seconds between retries to avoid overloading
                Thread.sleep(10000);

                // Attempt to connect
                con = DriverManager.getConnection(
                        "jdbc:mysql://world-db:3306/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected to world database.");
                break; // Exit loop if connection is successful

            } catch (SQLException sqle) {
                System.out.println("Failed to connect: " + sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Connection attempt interrupted: " + ie.getMessage());
            }
        }
    }

    /**
     * Returns the active database connection.
     * @return Connection object or null if not connected.
     */
    public Connection getConnection() {
        return con;
    }

    /**
     * Closes the database connection safely.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection closed successfully.");
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("No active connection to close.");
        }
    }
}
