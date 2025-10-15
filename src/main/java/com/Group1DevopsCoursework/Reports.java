

package com.Group1DevopsCoursework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------------------------
 * File: Reports.java
 * Description: This class retrieves and displays 32 Reports
 *              from the world database using SQL queries.
 * --------------------------------------------------------------
 */


public class Reports {

    private final Database_Connection db;

    public Reports(Database_Connection db) {
        this.db = db;
    }

    // ==============================================================
    // COUNTRY REPORTS
    // ==============================================================

    /**
     * Displays all countries in the world organized by largest to smallest population.
     */
    public void getAllCountriesInWorld() {
        String query = """
            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            ORDER BY country.Population DESC;
        """;
        executeAndDisplayCountry(query);
    }

    /**
     * Displays all countries in a given continent organized by largest to smallest population.
     */
    public void getCountriesByContinent(String continent) {
        String query = """
            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            WHERE country.Continent = ?
            ORDER BY country.Population DESC;
        """;
        executeAndDisplayCountry(query, continent);
    }

    /**
     * Displays all countries in a given region organized by largest to smallest population.
     */
    public void getCountriesByRegion(String region) {
        String query = """
            SELECT country.Code, country.Name, country.Continent,country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            WHERE country.Region = ?
            ORDER BY country.Population DESC;
        """;
        executeAndDisplayCountry(query, region);
    }

    /**
     * Displays top N populated countries in the world.
     */
    public void getTopNCountriesInWorld(int n) {
        String query = """
            SELECT country.Code, country.Name, country.Continent,
                   country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            ORDER BY country.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCountry(query, n);
    }

    /**
     * Displays top N populated countries in a continent.
     */
    public void getTopNCountriesInContinent(String continent, int n) {
        String query = """
            SELECT country.Code, country.Name, country.Continent,
                   country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            WHERE country.Continent = ?
            ORDER BY country.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCountry(query, continent, n);
    }

    /**
     * Displays top N populated countries in a region.
     */
    public void getTopNCountriesInRegion(String region, int n) {
        String query = """
            SELECT country.Code, country.Name, country.Continent,
                   country.Region, country.Population, city.Name AS Capital
            FROM country
            LEFT JOIN city ON country.Capital = city.ID
            WHERE country.Region = ?
            ORDER BY country.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCountry(query, region, n);
    }

    // ==============================================================
    // CITY REPORTS
    // ==============================================================

    /**
     * Displays all cities in the world organized by largest to smallest population.
     */
    public void getAllCitiesInWorld() {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCity(query);
    }

    /**
     * Displays all cities in a given continent organized by largest to smallest population.
     */
    public void getCitiesByContinent(String continent) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCity(query, continent);
    }

    /**
     * Displays all cities in a given region organized by largest to smallest population.
     */
    public void getCitiesByRegion(String region) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCity(query, region);
    }

    /**
     * Displays all cities in a given country organized by largest to smallest population.
     */
    public void getCitiesByCountry(String country) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCity(query, country);
    }

    /**
     * Displays all cities in a given district organized by largest to smallest population.
     */
    public void getCitiesByDistrict(String district) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCity(query, district);
    }

    /**
     * Displays top N populated cities in the world.
     */
    public void getTopNCitiesInWorld(int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCity(query, n);
    }

    /**
     * Displays top N populated cities in a given continent.
     */
    public void getTopNCitiesInContinent(String continent, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCity(query, continent, n);
    }

    /**
     * Displays top N populated cities in a given region.
     */
    public void getTopNCitiesInRegion(String region, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCity(query, region, n);
    }

    /**
     * Displays top N populated cities in a given country.
     */
    public void getTopNCitiesInCountry(String country, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCity(query, country, n);
    }

    /**
     * Displays top N populated cities in a given district.
     */
    public void getTopNCitiesInDistrict(String district, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName,
                   city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCity(query, district, n);
    }
    // ==================== CAPITAL CITY REPORTS ====================

    /** All capital cities in the world organised by largest population to smallest */
    public void getAllCapitalCitiesInWorld() {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCapital(query);
    }

    /** All capital cities in a continent organised by largest population to smallest */
    public void getCapitalCitiesByContinent(String continent) {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            WHERE country.Continent = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCapital(query, continent);
    }

    /** All capital cities in a region organised by largest population to smallest */
    public void getCapitalCitiesByRegion(String region) {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            WHERE country.Region = ?
            ORDER BY city.Population DESC;
        """;
        executeAndDisplayCapital(query, region);
    }

    /** Top N populated capital cities in the world */
    public void getTopNCapitalCitiesInWorld(int n) {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCapital(query, n);
    }

    /** Top N populated capital cities in a continent */
    public void getTopNCapitalCitiesInContinent(String continent, int n) {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCapital(query, continent, n);
    }

    /** Top N populated capital cities in a region */
    public void getTopNCapitalCitiesInRegion(String region, int n) {
        String query = """
            SELECT city.Name AS Name, country.Name AS Country, city.Population
            FROM city
            INNER JOIN country ON city.ID = country.Capital
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        executeAndDisplayCapital(query, region, n);
    }

    /** Population report for each continent */
    public void getPopulationByContinent() {
        String query = """
        SELECT c.Continent AS Name,
               SUM(c.Population) AS TotalPop,
               SUM(ci.Population) AS CityPop
        FROM country c
        LEFT JOIN city ci ON c.Code = ci.CountryCode
        GROUP BY c.Continent
        ORDER BY TotalPop DESC;
    """;
        executeAndDisplayPopulation(query, "Continent");
    }

    /** Population report for each region */
    public void getPopulationByRegion() {
        String query = """
        SELECT c.Region AS Name,
               SUM(c.Population) AS TotalPop,
               SUM(ci.Population) AS CityPop
        FROM country c
        LEFT JOIN city ci ON c.Code = ci.CountryCode
        GROUP BY c.Region
        ORDER BY TotalPop DESC;
    """;
        executeAndDisplayPopulation(query, "Region");
    }

    /** Population report for each country */
    public void getPopulationByCountry() {
        String query = """
        SELECT c.Name AS Name,
               c.Population AS TotalPop,
               SUM(ci.Population) AS CityPop
        FROM country c
        LEFT JOIN city ci ON c.Code = ci.CountryCode
        GROUP BY c.Code, c.Name, c.Population
        ORDER BY TotalPop DESC;
    """;
        executeAndDisplayPopulation(query, "Country");
    }

    /** Total population of the world */
    public long getWorldPopulation() {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country");
    }

    /** Total population of a continent */
    public long getPopulationOfContinent(String continent) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Continent = '" + continent + "'");
    }

    /** Total population of a region */
    public long getPopulationOfRegion(String region) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Region = '" + region + "'");
    }

    /** Total population of a country */
    public long getPopulationOfCountry(String country) {
        return getSinglePopulation("SELECT Population AS pop FROM country WHERE Name = '" + country + "'");
    }

    /** Total population of a district */
    public long getPopulationOfDistrict(String district) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM city WHERE District = '" + district + "'");
    }

    /** Total population of a city */
    public long getPopulationOfCity(String city) {
        return getSinglePopulation("SELECT Population AS pop FROM city WHERE Name = '" + city + "'");
    }

    // ==================== LANGUAGE REPORTS ====================
    /**
     * Displays the number of speakers for selected languages and their percentage of the world population.
     */
    public void getLanguageSpeakersReport() {
        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
        long worldPopulation = getWorldPopulation();

        System.out.printf("%-10s %-20s %-10s%n", "Language", "Speakers", "World %");
        System.out.println("------------------------------------------------------");

        for (String language : languages) {
            String query = "SELECT SUM(Population * Percentage / 100) AS Speakers " +
                    "FROM countrylanguage " +
                    "JOIN country ON countrylanguage.CountryCode = country.Code " +
                    "WHERE countrylanguage.Language = ?";
            long speakers = getSinglePopulationWithParam(query, language);
            double percentage = worldPopulation > 0 ? (speakers * 100.0 / worldPopulation) : 0;
            System.out.printf("%-10s %-20d %-10.2f%n", language, speakers, percentage);
        }
    }


    // ==============================================================
    // GENERIC EXECUTION METHODS
    // ==============================================================

    // For Country Reports
    private void executeAndDisplayCountry(String query, Object... params) {
        executeQuery(query, params, "Country");
    }

    // For City Reports
    private void executeAndDisplayCity(String query, Object... params) {
        executeQuery(query, params, "City");
    }

    /**
     * Executes SQL query and displays output formatted for Country or City.
     */
    private void executeQuery(String query, Object[] params, String type) {
        Connection con = db.getConnection();
        if (con == null) {
            System.out.println("No active database connection. Please connect first.");
            return;
        }

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                if (type.equals("Country")) {
                    System.out.printf("%-5s %-45s %-20s %-25s %-15s %-30s%n",
                            "Code", "Name", "Continent", "Region", "Population", "Capital");
                    System.out.println("------------------------------------------------------------------------------------------------------------------------");

                    while (rs.next()) {
                        System.out.printf("%-5s %-45s %-20s %-25s %-15d %-30s%n",
                                rs.getString("Code"),
                                rs.getString("Name"),
                                rs.getString("Continent"),
                                rs.getString("Region"),
                                rs.getInt("Population"),
                                rs.getString("Capital"));
                    }

                } else if (type.equals("City")) {
                    System.out.printf("%-35s %-45s %-25s %-15s%n",
                            "City Name", "Country", "District", "Population");
                    System.out.println("-----------------------------------------------------------------------------------------------------------");

                    while (rs.next()) {
                        System.out.printf("%-35s %-45s %-25s %-15d%n",
                                rs.getString("CityName"),
                                rs.getString("CountryName"),
                                rs.getString("District"),
                                rs.getInt("Population"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }
    /** Generic executor for Capital City reports */
    private void executeAndDisplayCapital(String query, Object... params) {
        Connection con = db.getConnection();
        if (con == null) {
            System.out.println("No active database connection. Please connect first.");
            return;
        }

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.printf("%-40s %-40s %-15s%n", "Name", "Country", "Population");
                System.out.println("-------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    System.out.printf("%-40s %-40s %-15d%n",
                            rs.getString("Name"),
                            rs.getString("Country"),
                            rs.getInt("Population"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving capital city data: " + e.getMessage());
        }
    }
    /** Generic method to execute and display population reports */
    private void executeAndDisplayPopulation(String query, String type) {
        Connection con = db.getConnection();
        if (con == null) {
            System.out.println("No active database connection. Please connect first.");
            return;
        }

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-20s %-15s %-15s %-10s %-15s %-10s%n",
                    type, "TotalPop", "CityPop", "City %", "NonCityPop", "NonCity %");
            System.out.println("------------------------------------------------------------------------------------");

            while (rs.next()) {
                String name = rs.getString("Name");
                long total = rs.getLong("TotalPop");
                long city = rs.getLong("CityPop");
                long nonCity = total - city;
                double cityPct = total > 0 ? (city * 100.0 / total) : 0.0;
                double nonCityPct = total > 0 ? (nonCity * 100.0 / total) : 0.0;

                System.out.printf("%-20s %-15d %-15d %-10.2f %-15d %-10.2f%n",
                        name, total, city, cityPct, nonCity, nonCityPct);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving population data: " + e.getMessage());
        }
    }

    /** Helper to get a single numeric population value */
    private long getSinglePopulation(String sql) {
        Connection con = db.getConnection();
        if (con == null) return 0;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving population data: " + e.getMessage());
        }
        return 0;
    }
    private long getSinglePopulationWithParam(String sql, String param) {
        Connection con = db.getConnection();
        if (con == null) return 0;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("Speakers");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving language population: " + e.getMessage());
        }
        return 0;
    }
}
