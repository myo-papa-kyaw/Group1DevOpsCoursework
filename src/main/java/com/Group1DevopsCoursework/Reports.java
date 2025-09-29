package com.Group1DevopsCoursework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reports {

    public final Database_Connection db;

    public Reports(Database_Connection db) {
        this.db = db;
    }

    // ==============================
    // ===== COUNTRY REPORTS ========
    // ==============================

    public void getAllCountries() {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "All Countries", null);
    }

    public void getCountriesByContinent(String continent) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "Countries in Continent: " + continent, continent);
    }

    public void getCountriesByRegion(String region) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "Countries in Region: " + region, region);
    }

    private void executeCountryQuery(String sql, String title, String parameter) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            if (parameter != null) {
                ps.setString(1, parameter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- " + title + " -----");
                System.out.printf("%-5s %-50s %-15s %-25s %-15s %-20s%n",
                        "Code", "Name", "Continent", "Region", "Population", "Capital");
                System.out.println("-".repeat(135));

                while (rs.next()) {
                    System.out.printf("%-5s %-50s %-15s %-25s %-15d %-20s%n",
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getInt("Population"),
                            rs.getString("CapitalName") != null ? rs.getString("CapitalName") : "N/A");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching countries: " + e.getMessage());
        }
    }

    // ==============================
    // === CAPITAL CITY REPORTS =====
    // ==============================

    public void getAllCapitalCitiesWorld() {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "All Capital Cities in the World", null, null);
    }

    public void getCapitalCitiesByContinent(String continent) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "Capital Cities in Continent: " + continent, continent, null);
    }

    public void getCapitalCitiesByRegion(String region) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "Capital Cities in Region: " + region, null, region);
    }

    public void getTopNPopulatedCapitalCitiesWorld(int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in the World", n, null, null);
    }

    public void getTopNPopulatedCapitalCitiesContinent(String continent, int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in " + continent, n, continent, null);
    }

    public void getTopNPopulatedCapitalCitiesRegion(String region, int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in " + region, n, null, region);
    }

    private void executeCapitalCityQuery(String sql, String title, String continent, String region) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (continent != null) ps.setString(paramIndex++, continent);
            if (region != null) ps.setString(paramIndex, region);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- " + title + " -----");
                System.out.printf("%-30s %-30s %-15s %-15s %-25s%n",
                        "Capital City", "Country", "Population", "Continent", "Region");
                System.out.println("-".repeat(115));

                while (rs.next()) {
                    System.out.printf("%-30s %-30s %-15d %-15s %-25s%n",
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population"),
                            rs.getString("Continent"),
                            rs.getString("Region"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching capital cities: " + e.getMessage());
        }
    }

    private void executeTopNCapitalCityQuery(String sql, String title, int n, String continent, String region) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (continent != null) ps.setString(paramIndex++, continent);
            if (region != null) ps.setString(paramIndex++, region);
            ps.setInt(paramIndex, n);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- " + title + " -----");
                System.out.printf("%-30s %-30s %-15s %-15s %-25s%n",
                        "Capital City", "Country", "Population", "Continent", "Region");
                System.out.println("-".repeat(115));

                while (rs.next()) {
                    System.out.printf("%-30s %-30s %-15d %-15s %-25s%n",
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getInt("Population"),
                            rs.getString("Continent"),
                            rs.getString("Region"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching top N capital cities: " + e.getMessage());
        }
    }

    // ==============================
    // ===== CITY REPORTS ===========
    // ==============================

    public void getTopNPopulatedCitiesWorld(int n) {
        String sql = "SELECT city.Name, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        executeTopNCityQuery(sql, "Top " + n + " Cities in the World", n, null, null, null);
    }

    public void getTopNPopulatedCitiesContinent(String continent, int n) {
        String sql = "SELECT city.Name, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        executeTopNCityQuery(sql, "Top " + n + " Cities in " + continent, n, continent, null, null);
    }

    public void getTopNPopulatedCitiesRegion(String region, int n) {
        String sql = "SELECT city.Name, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        executeTopNCityQuery(sql, "Top " + n + " Cities in " + region, n, null, region, null);
    }

    public void getTopNPopulatedCitiesCountry(String country, int n) {
        String sql = "SELECT city.Name, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        executeTopNCityQuery(sql, "Top " + n + " Cities in " + country, n, null, null, country);
    }

    private void executeTopNCityQuery(String sql, String title, int n, String continent, String region, String country) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (continent != null) ps.setString(paramIndex++, continent);
            if (region != null) ps.setString(paramIndex++, region);
            if (country != null) ps.setString(paramIndex++, country);
            ps.setInt(paramIndex, n);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- " + title + " -----");
                System.out.printf("%-30s %-30s %-15s %-15s %-25s%n",
                        "City", "Country", "Population", "Continent", "Region");
                System.out.println("-".repeat(115));

                while (rs.next()) {
                    System.out.printf("%-30s %-30s %-15d %-15s %-25s%n",
                            rs.getString("Name"),
                            rs.getString("CountryName"),
                            rs.getInt("Population"),
                            rs.getString("Continent"),
                            rs.getString("Region"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching city data: " + e.getMessage());
        }
    }

    // ==============================
    // ===== POPULATION REPORTS =====
    // ==============================

    public void getPopulationByContinent() {
        String sql = "SELECT Continent, SUM(Population) AS TotalPopulation FROM country GROUP BY Continent";
        executePopulationQuery(sql, "Population by Continent");
    }

    public void getPopulationByRegion() {
        String sql = "SELECT Region, SUM(Population) AS TotalPopulation FROM country GROUP BY Region";
        executePopulationQuery(sql, "Population by Region");
    }

    public void getPopulationByCountry() {
        String sql = "SELECT Name, Population FROM country ORDER BY Population DESC";
        executePopulationQuery(sql, "Population by Country");
    }

    private void executePopulationQuery(String sql, String title) {
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n----- " + title + " -----");
            System.out.printf("%-30s %-20s%n", "Name", "Population");
            System.out.println("-".repeat(50));

            while (rs.next()) {
                System.out.printf("%-30s %-20d%n",
                        rs.getString(1),
                        rs.getLong(2));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching population data: " + e.getMessage());
        }
    }
}
