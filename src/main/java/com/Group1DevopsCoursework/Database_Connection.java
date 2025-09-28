package com.Group1DevopsCoursework;

import java.sql.*;

public class Database_Connection {

    Connection con = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 15;  // increase retries
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... attempt " + i);
            try {
                Thread.sleep(10000);  // wait 15 seconds for MySQL
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33061/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example"
                );
                System.out.println("Successfully connected to world database");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect: " + sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }



}
