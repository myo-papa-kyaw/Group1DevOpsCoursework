package com.Group1DevopsCoursework;

import java.sql.*;

public class Database_Connection {

    private Connection con = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 20;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... attempt " + (i+1));
            try {
                Thread.sleep(10000);
                con = DriverManager.getConnection(
                        "jdbc:mysql://world-db:3306/world?useSSL=false&allowPublicKeyRetrieval=true",
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

    public Connection getConnection() {
        return con;
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