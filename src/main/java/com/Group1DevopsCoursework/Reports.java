package com.Group1DevopsCoursework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reports {

    private final Connection con;

    public Reports(Database_Connection db) {
        this.con = db.getConnection(); // use one shared connection
    }

    private void handleSQLException(SQLException e) {
        System.err.println("Database error: " + e.getMessage());
    }

    // 1. World Population
    public void getWorldPopulation() {
        String sql = "SELECT SUM(Population) AS total_population FROM country";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "World", "Population");
            System.out.println("--------------------------------------------------");

            if (rs.next()) {
                long population = rs.getLong("total_population");
                System.out.printf("%-25s %,15d%n", "World", population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 2. Continent Population
    public void getContinentPopulation() {
        String sql = "SELECT Continent, SUM(Population) AS total_population " +
                "FROM country GROUP BY Continent ORDER BY total_population DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "Continent", "Population");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                String continent = rs.getString("Continent");
                long population = rs.getLong("total_population");
                System.out.printf("%-25s %,15d%n", continent, population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 3. Region Population
    public void getRegionPopulation() {
        String sql = "SELECT Region, SUM(Population) AS total_population " +
                "FROM country GROUP BY Region ORDER BY total_population DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "Region", "Population");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                String region = rs.getString("Region");
                long population = rs.getLong("total_population");
                System.out.printf("%-25s %,15d%n", region, population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 4. Country Population
    public void getCountryPopulation() {
        String sql = "SELECT Name, Population FROM country ORDER BY Population DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "Country", "Population");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                String country = rs.getString("Name");
                long population = rs.getLong("Population");
                System.out.printf("%-25s %,15d%n", country, population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 5. District Population
    public void getDistrictPopulation() {
        String sql = "SELECT District, SUM(Population) AS total_population " +
                "FROM city GROUP BY District ORDER BY total_population DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "District", "Population");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                String district = rs.getString("District");
                long population = rs.getLong("total_population");
                System.out.printf("%-25s %,15d%n", district, population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 6. City Population
    public void getCityPopulation() {
        String sql = "SELECT Name, Population FROM city ORDER BY Population DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-25s %15s%n", "City", "Population");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                String city = rs.getString("Name");
                long population = rs.getLong("Population");
                System.out.printf("%-25s %,15d%n", city, population);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
}
