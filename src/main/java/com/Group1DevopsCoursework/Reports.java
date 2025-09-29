package com.Group1DevopsCoursework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reports {

    private final Database_Connection db;

    public Reports(Database_Connection db) {
        this.db = db;
    }

    // 1. All countries in the world ordered by population descending
    public void getAllCountries() {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "All Countries", null);
    }

    // 2. All countries in a continent ordered by population descending
    public void getCountriesByContinent(String continent) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "Countries in Continent: " + continent, continent);
    }

    // 3. All countries in a region ordered by population descending
    public void getCountriesByRegion(String region) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? " +
                "ORDER BY c.Population DESC";

        executeCountryQuery(sql, "Countries in Region: " + region, region);
    }

    // Helper method for country queries
    private void executeCountryQuery(String sql, String title, String parameter) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = db.con.prepareStatement(sql);
            if (parameter != null) {
                ps.setString(1, parameter);
            }
            rs = ps.executeQuery();

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

        } catch (SQLException e) {
            System.out.println("Error fetching countries: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
        }
    }

    // CAPITAL CITIES REPORTS

    // 1. All capital cities in the world organised by largest population to smallest
    public void getAllCapitalCitiesWorld() {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "All Capital Cities in the World", null, null);
    }

    // 2. All capital cities in a continent organised by largest population to smallest
    public void getCapitalCitiesByContinent(String continent) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "Capital Cities in Continent : " + continent, continent, null);
    }

    // 3. All capital cities in a region organised by largest population to smallest
    public void getCapitalCitiesByRegion(String region) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        executeCapitalCityQuery(sql, "Capital Cities in Region : " + region, null, region);
    }

    // 4. The top N populated capital cities in the world
    public void getTopNPopulatedCapitalCitiesWorld(int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in the World", n, null, null);
    }

    // 5. The top N populated capital cities in a continent
    public void getTopNPopulatedCapitalCitiesContinent(String continent, int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in " + continent, n, continent, null);
    }

    // 6. The top N populated capital cities in a region
    public void getTopNPopulatedCapitalCitiesRegion(String region, int n) {
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population, country.Continent, country.Region " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        executeTopNCapitalCityQuery(sql, "Top " + n + " Populated Capital Cities in " + region, n, null, region);
    }

    // Helper method for capital city queries (without LIMIT)
    private void executeCapitalCityQuery(String sql, String title, String continent, String region) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = db.con.prepareStatement(sql);
            int paramIndex = 1;
            if (continent != null) {
                ps.setString(paramIndex++, continent);
            }
            if (region != null) {
                ps.setString(paramIndex, region);
            }

            rs = ps.executeQuery();

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

        } catch (SQLException e) {
            System.out.println("Error fetching capital cities: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
        }
    }

    // Helper method for top N capital city queries (with LIMIT)
    private void executeTopNCapitalCityQuery(String sql, String title, int n, String continent, String region) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = db.con.prepareStatement(sql);
            int paramIndex = 1;

            if (continent != null) {
                ps.setString(paramIndex++, continent);
            }
            if (region != null) {
                ps.setString(paramIndex++, region);
            }
            ps.setInt(paramIndex, n);

            rs = ps.executeQuery();

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

        } catch (SQLException e) {
            System.out.println("Error fetching top N capital cities: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
        }
    }
}