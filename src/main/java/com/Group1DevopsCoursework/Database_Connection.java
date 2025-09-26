package com.Group1DevopsCoursework;

import java.sql.*;
import java.util.ArrayList;

public class Database_Connection {

    private Connection con = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 20;  // increase retries
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... attempt " + i);
            try {
                Thread.sleep(15000);  // wait 15 seconds for MySQL
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

    public ArrayList<Country> getAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(
                    "SELECT Code, Name, Continent, Region, Population FROM country ORDER BY Population DESC"
            );
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("Code");
                c.name = rset.getString("Name");
                c.continent = rset.getString("Continent");
                c.region = rset.getString("Region");
                c.population = rset.getInt("Population");
                countries.add(c);
            }
        } catch (Exception e) {
            System.out.println("Failed to get countries: " + e.getMessage());
        }
        return countries;
    }

    public void printCountries(ArrayList<Country> countries) {
        System.out.println(String.format("%-5s %-50s %-15s %-20s %-15s",
                "Code", "Name", "Continent", "Region", "Population"));
        for (Country c : countries) {
            System.out.println(String.format("%-5s %-50s %-15s %-20s %-15s",
                    c.code, c.name, c.continent, c.region, c.population));
        }
    }

    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();

        ArrayList<Country> countries = db.getAllCountries();
        db.printCountries(countries);

        db.disconnect();
    }
}
