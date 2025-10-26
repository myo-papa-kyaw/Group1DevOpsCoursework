///**
// * --------------------------------------------------------------
// * File: Reports.java
// * Description: This class retrieves and displays 32 Reports
// *              from the world database using SQL queries.
// * --------------------------------------------------------------
// */
//
//package com.Group1DevopsCoursework;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class Reports {
//
//    private final Database_Connection db;
//
//    public Reports(Database_Connection db) {
//        this.db = db;
//    }
//
//    // ==============================================================
//    // COUNTRY REPORTS
//    // ==============================================================
//
//    /**
//     * Displays all countries in the world organized by largest to smallest population.
//     */
//    public void getAllCountriesInWorld() {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query);
//    }
//
//    /**
//     * Displays all countries in a given continent organized by largest to smallest population.
//     */
//    public void getCountriesByContinent(String continent) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Continent = ?
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query, continent);
//    }
//
//    /**
//     * Displays all countries in a given region organized by largest to smallest population.
//     */
//    public void getCountriesByRegion(String region) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Region = ?
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query, region);
//    }
//
//    /**
//     * Displays top N populated countries in the world.
//     */
//    public void getTopNCountriesInWorld(int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, n);
//    }
//
//    /**
//     * Displays top N populated countries in a continent.
//     */
//    public void getTopNCountriesInContinent(String continent, int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Continent = ?
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, continent, n);
//    }
//
//    /**
//     * Displays top N populated countries in a region.
//     */
//    public void getTopNCountriesInRegion(String region, int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Region = ?
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, region, n);
//    }
//
//    // ==============================================================
//    // CITY REPORTS
//    // ==============================================================
//
//    /**
//     * Displays all cities in the world organized by largest to smallest population.
//     */
//    public void getAllCitiesInWorld() {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query);
//    }
//
//    /**
//     * Displays all cities in a given continent organized by largest to smallest population.
//     */
//    public void getCitiesByContinent(String continent) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, continent);
//    }
//
//    /**
//     * Displays all cities in a given region organized by largest to smallest population.
//     */
//    public void getCitiesByRegion(String region) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, region);
//    }
//
//    /**
//     * Displays all cities in a given country organized by largest to smallest population.
//     */
//    public void getCitiesByCountry(String country) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Name = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, country);
//    }
//
//    /**
//     * Displays all cities in a given district organized by largest to smallest population.
//     */
//    public void getCitiesByDistrict(String district) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE city.District = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, district);
//    }
//
//    /**
//     * Displays top N populated cities in the world.
//     */
//    public void getTopNCitiesInWorld(int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given continent.
//     */
//    public void getTopNCitiesInContinent(String continent, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, continent, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given region.
//     */
//    public void getTopNCitiesInRegion(String region, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, region, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given country.
//     */
//    public void getTopNCitiesInCountry(String country, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Name = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, country, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given district.
//     */
//    public void getTopNCitiesInDistrict(String district, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE city.District = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, district, n);
//    }
//
//
//    // ==================== CAPITAL CITY REPORTS ====================
//
//    /** All capital cities in the world organised by largest population to smallest */
//    public void getAllCapitalCitiesInWorld() {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query);
//    }
//
//    /** All capital cities in a continent organised by largest population to smallest */
//    public void getCapitalCitiesByContinent(String continent) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query, continent);
//    }
//
//    /** All capital cities in a region organised by largest population to smallest */
//    public void getCapitalCitiesByRegion(String region) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query, region);
//    }
//
//    /** Top N populated capital cities in the world */
//    public void getTopNCapitalCitiesInWorld(int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, n);
//    }
//
//    /** Top N populated capital cities in a continent */
//    public void getTopNCapitalCitiesInContinent(String continent, int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, continent, n);
//    }
//
//    /** Top N populated capital cities in a region */
//    public void getTopNCapitalCitiesInRegion(String region, int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, region, n);
//    }
//
//
//    // ================== POPULATION REPORTS ==================
//
//    /** Population report for each continent */
//    public void getPopulationByContinent(String asia) {
//        String query = """
//        SELECT c.Continent AS Name,
//               SUM(c.Population) AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Continent
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Continent");
//    }
//
//    /** Population report for each region */
//    public void getPopulationByRegion(String southeastAsia) {
//        String query = """
//        SELECT c.Region AS Name,
//               SUM(c.Population) AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Region
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Region");
//    }
//
//    /** Population report for each country */
//    public void getPopulationByCountry(String myanmar) {
//        String query = """
//        SELECT c.Name AS Name,
//               c.Population AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Code, c.Name, c.Population
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Country");
//    }
//
//    /** Total population of the world */
//    public long getWorldPopulation() {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country");
//    }
//
//    /** Total population of a continent */
//    public long getPopulationOfContinent(String continent) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Continent = '" + continent + "'");
//    }
//
//    /** Total population of a region */
//    public long getPopulationOfRegion(String region) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Region = '" + region + "'");
//    }
//
//    /** Total population of a country */
//    public long getPopulationOfCountry(String country) {
//        return getSinglePopulation("SELECT Population AS pop FROM country WHERE Name = '" + country + "'");
//    }
//
//    /** Total population of a district */
//    public long getPopulationOfDistrict(String district) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM city WHERE District = '" + district + "'");
//    }
//
//    /** Total population of a city */
//    public long getPopulationOfCity(String city) {
//        return getSinglePopulation("SELECT Population AS pop FROM city WHERE Name = '" + city + "'");
//    }
//
//
//    // ==================== LANGUAGE REPORTS ====================
//
//    /**
//     * Displays the number of speakers for selected languages and their percentage of the world population.
//     */
//    public void getLanguageSpeakersReport() {
//        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
//        long worldPopulation = getWorldPopulation();
//
//        System.out.printf("%-10s %-20s %-10s%n", "Language", "Speakers", "World %");
//        System.out.println("------------------------------------------------------");
//
//        for (String language : languages) {
//            String query = "SELECT SUM(Population * Percentage / 100) AS Speakers " +
//                    "FROM countrylanguage " +
//                    "JOIN country ON countrylanguage.CountryCode = country.Code " +
//                    "WHERE countrylanguage.Language = ?";
//            long speakers = getSinglePopulationWithParam(query, language);
//            double percentage = worldPopulation > 0 ? (speakers * 100.0 / worldPopulation) : 0;
//            System.out.printf("%-10s %-20d %-10.2f%n", language, speakers, percentage);
//        }
//    }
//
//
//    // ==============================================================
//    // GENERIC EXECUTION METHODS
//    // ==============================================================
//
//    // For Country Reports
//    private void executeAndDisplayCountry(String query, Object... params) {
//        executeQuery(query, params, "Country");
//    }
//
//    // For City Reports
//    private void executeAndDisplayCity(String query, Object... params) {
//        executeQuery(query, params, "City");
//    }
//
//    /**
//     * Executes SQL query and displays output formatted for Country or City.
//     */
//    private void executeQuery(String query, Object[] params, String type) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query)) {
//            for (int i = 0; i < params.length; i++) {
//                stmt.setObject(i + 1, params[i]);
//            }
//
//            try (ResultSet rs = stmt.executeQuery()) {
//
//                if (type.equals("Country")) {
//                    System.out.printf("%-5s %-45s %-20s %-25s %-15s %-30s%n",
//                            "Code", "Name", "Continent", "Region", "Population", "Capital");
//                    System.out.println("------------------------------------------------------------------------------------------------------------------------");
//
//                    while (rs.next()) {
//                        System.out.printf("%-5s %-45s %-20s %-25s %-15d %-30s%n",
//                                rs.getString("Code"),
//                                rs.getString("Name"),
//                                rs.getString("Continent"),
//                                rs.getString("Region"),
//                                rs.getInt("Population"),
//                                rs.getString("Capital"));
//                    }
//
//                } else if (type.equals("City")) {
//                    System.out.printf("%-35s %-45s %-25s %-15s%n",
//                            "City Name", "Country", "District", "Population");
//                    System.out.println("-----------------------------------------------------------------------------------------------------------");
//
//                    while (rs.next()) {
//                        System.out.printf("%-35s %-45s %-25s %-15d%n",
//                                rs.getString("CityName"),
//                                rs.getString("CountryName"),
//                                rs.getString("District"),
//                                rs.getInt("Population"));
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving data: " + e.getMessage());
//        }
//    }
//    /** Generic executor for Capital City reports */
//    private void executeAndDisplayCapital(String query, Object... params) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query)) {
//            for (int i = 0; i < params.length; i++) {
//                stmt.setObject(i + 1, params[i]);
//            }
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                System.out.printf("%-40s %-40s %-15s%n", "Name", "Country", "Population");
//                System.out.println("-------------------------------------------------------------------------------------------");
//
//                while (rs.next()) {
//                    System.out.printf("%-40s %-40s %-15d%n",
//                            rs.getString("Name"),
//                            rs.getString("Country"),
//                            rs.getInt("Population"));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving capital city data: " + e.getMessage());
//        }
//    }
//
//
//    /** Generic method to execute and display population reports */
//    private void executeAndDisplayPopulation(String query, String type) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            System.out.printf("%-20s %-15s %-15s %-10s %-15s %-10s%n",
//                    type, "TotalPop", "CityPop", "City %", "NonCityPop", "NonCity %");
//            System.out.println("------------------------------------------------------------------------------------");
//
//            while (rs.next()) {
//                String name = rs.getString("Name");
//                long total = rs.getLong("TotalPop");
//                long city = rs.getLong("CityPop");
//                long nonCity = total - city;
//                double cityPct = total > 0 ? (city * 100.0 / total) : 0.0;
//                double nonCityPct = total > 0 ? (nonCity * 100.0 / total) : 0.0;
//
//                System.out.printf("%-20s %-15d %-15d %-10.2f %-15d %-10.2f%n",
//                        name, total, city, cityPct, nonCity, nonCityPct);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving population data: " + e.getMessage());
//        }
//    }
//
//    /** Helper to get a single numeric population value */
//    private long getSinglePopulation(String sql) {
//        Connection con = db.getConnection();
//        if (con == null) return 0;
//
//        try (PreparedStatement stmt = con.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) {
//                return rs.getLong("pop");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving population data: " + e.getMessage());
//        }
//        return 0;
//    }
//    private long getSinglePopulationWithParam(String sql, String param) {
//        Connection con = db.getConnection();
//        if (con == null) return 0;
//
//        try (PreparedStatement stmt = con.prepareStatement(sql)) {
//            stmt.setString(1, param);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getLong("Speakers");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving language population: " + e.getMessage());
//        }
//        return 0;
//    }
//}


//package com.Group1DevopsCoursework;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
///**
// * --------------------------------------------------------------
// * File: Reports.java
// * Description: This class retrieves and displays 32 Reports
// *              from the world database using SQL queries.
// * --------------------------------------------------------------
// */
//public class Reports {
//
//    private final Database_Connection db;
//
//    public Reports(Database_Connection db) {
//        this.db = db;
//    }
//
//    // ==============================================================
//    // COUNTRY REPORTS
//    // ==============================================================
//
//    /**
//     * Displays all countries in the world organized by largest to smallest population.
//     */
//    public void getAllCountriesInWorld() {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query);
//    }
//
//    /**
//     * Displays all countries in a given continent organized by largest to smallest population.
//     */
//    public void getCountriesByContinent(String continent) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Continent = ?
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query, continent);
//    }
//
//    /**
//     * Displays all countries in a given region organized by largest to smallest population.
//     */
//    public void getCountriesByRegion(String region) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Region = ?
//            ORDER BY country.Population DESC;
//        """;
//        executeAndDisplayCountry(query, region);
//    }
//
//    /**
//     * Displays top N populated countries in the world.
//     */
//    public void getTopNCountriesInWorld(int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, n);
//    }
//
//    /**
//     * Displays top N populated countries in a continent.
//     */
//    public void getTopNCountriesInContinent(String continent, int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Continent = ?
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, continent, n);
//    }
//
//    /**
//     * Displays top N populated countries in a region.
//     */
//    public void getTopNCountriesInRegion(String region, int n) {
//        String query = """
//            SELECT country.Code, country.Name, country.Continent,
//                   country.Region, country.Population, city.Name AS Capital
//            FROM country
//            LEFT JOIN city ON country.Capital = city.ID
//            WHERE country.Region = ?
//            ORDER BY country.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCountry(query, region, n);
//    }
//
//    // ==============================================================
//    // CITY REPORTS
//    // ==============================================================
//
//    /**
//     * Displays all cities in the world organized by largest to smallest population.
//     */
//    public void getAllCitiesInWorld() {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query);
//    }
//
//    /**
//     * Displays all cities in a given continent organized by largest to smallest population.
//     */
//    public void getCitiesByContinent(String continent) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, continent);
//    }
//
//    /**
//     * Displays all cities in a given region organized by largest to smallest population.
//     */
//    public void getCitiesByRegion(String region) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, region);
//    }
//
//    /**
//     * Displays all cities in a given country organized by largest to smallest population.
//     */
//    public void getCitiesByCountry(String country) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Name = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, country);
//    }
//
//    /**
//     * Displays all cities in a given district organized by largest to smallest population.
//     */
//    public void getCitiesByDistrict(String district) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE city.District = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCity(query, district);
//    }
//
//    /**
//     * Displays top N populated cities in the world.
//     */
//    public void getTopNCitiesInWorld(int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given continent.
//     */
//    public void getTopNCitiesInContinent(String continent, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, continent, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given region.
//     */
//    public void getTopNCitiesInRegion(String region, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, region, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given country.
//     */
//    public void getTopNCitiesInCountry(String country, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE country.Name = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, country, n);
//    }
//
//    /**
//     * Displays top N populated cities in a given district.
//     */
//    public void getTopNCitiesInDistrict(String district, int n) {
//        String query = """
//            SELECT city.Name AS CityName, country.Name AS CountryName,
//                   city.District, city.Population
//            FROM city
//            JOIN country ON city.CountryCode = country.Code
//            WHERE city.District = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCity(query, district, n);
//    }
//    // ==================== CAPITAL CITY REPORTS ====================
//
//    /** All capital cities in the world organised by largest population to smallest */
//    public void getAllCapitalCitiesInWorld() {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query);
//    }
//
//    /** All capital cities in a continent organised by largest population to smallest */
//    public void getCapitalCitiesByContinent(String continent) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query, continent);
//    }
//
//    /** All capital cities in a region organised by largest population to smallest */
//    public void getCapitalCitiesByRegion(String region) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC;
//        """;
//        executeAndDisplayCapital(query, region);
//    }
//
//    /** Top N populated capital cities in the world */
//    public void getTopNCapitalCitiesInWorld(int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, n);
//    }
//
//    /** Top N populated capital cities in a continent */
//    public void getTopNCapitalCitiesInContinent(String continent, int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Continent = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, continent, n);
//    }
//
//    /** Top N populated capital cities in a region */
//    public void getTopNCapitalCitiesInRegion(String region, int n) {
//        String query = """
//            SELECT city.Name AS Name, country.Name AS Country, city.Population
//            FROM city
//            INNER JOIN country ON city.ID = country.Capital
//            WHERE country.Region = ?
//            ORDER BY city.Population DESC
//            LIMIT ?;
//        """;
//        executeAndDisplayCapital(query, region, n);
//    }
//
//    /** Population report for each continent */
//    public void getPopulationByContinent(String asia) {
//        String query = """
//        SELECT c.Continent AS Name,
//               SUM(c.Population) AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Continent
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Continent");
//    }
//
//    /** Population report for each region */
//    public void getPopulationByRegion(String southeastAsia) {
//        String query = """
//        SELECT c.Region AS Name,
//               SUM(c.Population) AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Region
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Region");
//    }
//
//    /** Population report for each country */
//    public void getPopulationByCountry(String myanmar) {
//        String query = """
//        SELECT c.Name AS Name,
//               c.Population AS TotalPop,
//               SUM(ci.Population) AS CityPop
//        FROM country c
//        LEFT JOIN city ci ON c.Code = ci.CountryCode
//        GROUP BY c.Code, c.Name, c.Population
//        ORDER BY TotalPop DESC;
//    """;
//        executeAndDisplayPopulation(query, "Country");
//    }
//
//    /** Total population of the world */
//    public long getWorldPopulation() {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country");
//    }
//
//    /** Total population of a continent */
//    public long getPopulationOfContinent(String continent) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Continent = '" + continent + "'");
//    }
//
//    /** Total population of a region */
//    public long getPopulationOfRegion(String region) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Region = '" + region + "'");
//    }
//
//    /** Total population of a country */
//    public long getPopulationOfCountry(String country) {
//        return getSinglePopulation("SELECT Population AS pop FROM country WHERE Name = '" + country + "'");
//    }
//
//    /** Total population of a district */
//    public long getPopulationOfDistrict(String district) {
//        return getSinglePopulation("SELECT SUM(Population) AS pop FROM city WHERE District = '" + district + "'");
//    }
//
//    /** Total population of a city */
//    public long getPopulationOfCity(String city) {
//        return getSinglePopulation("SELECT Population AS pop FROM city WHERE Name = '" + city + "'");
//    }
//
//    // ==================== LANGUAGE REPORTS ====================
//    /**
//     * Displays the number of speakers for selected languages and their percentage of the world population.
//     */
//    public void getLanguageSpeakersReport() {
//        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
//        long worldPopulation = getWorldPopulation();
//
//        System.out.printf("%-10s %-20s %-10s%n", "Language", "Speakers", "World %");
//        System.out.println("------------------------------------------------------");
//
//        for (String language : languages) {
//            String query = "SELECT SUM(Population * Percentage / 100) AS Speakers " +
//                    "FROM countrylanguage " +
//                    "JOIN country ON countrylanguage.CountryCode = country.Code " +
//                    "WHERE countrylanguage.Language = ?";
//            long speakers = getSinglePopulationWithParam(query, language);
//            double percentage = worldPopulation > 0 ? (speakers * 100.0 / worldPopulation) : 0;
//            System.out.printf("%-10s %-20d %-10.2f%n", language, speakers, percentage);
//        }
//    }
//
//
//    // ==============================================================
//    // GENERIC EXECUTION METHODS
//    // ==============================================================
//
//    // For Country Reports
//    void executeAndDisplayCountry(String query, Object... params) {
//        executeQuery(query, params, "Country");
//    }
//
//    // For City Reports
//    void executeAndDisplayCity(String query, Object... params) {
//        executeQuery(query, params, "City");
//    }
//
//    /**
//     * Executes SQL query and displays output formatted for Country or City.
//     */
//    private void executeQuery(String query, Object[] params, String type) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query)) {
//            for (int i = 0; i < params.length; i++) {
//                stmt.setObject(i + 1, params[i]);
//            }
//
//            try (ResultSet rs = stmt.executeQuery()) {
//
//                if (type.equals("Country")) {
//                    System.out.printf("%-5s %-45s %-20s %-25s %-15s %-30s%n",
//                            "Code", "Name", "Continent", "Region", "Population", "Capital");
//                    System.out.println("------------------------------------------------------------------------------------------------------------------------");
//
//                    while (rs.next()) {
//                        System.out.printf("%-5s %-45s %-20s %-25s %-15d %-30s%n",
//                                rs.getString("Code"),
//                                rs.getString("Name"),
//                                rs.getString("Continent"),
//                                rs.getString("Region"),
//                                rs.getInt("Population"),
//                                rs.getString("Capital"));
//                    }
//
//                } else if (type.equals("City")) {
//                    System.out.printf("%-35s %-45s %-25s %-15s%n",
//                            "City Name", "Country", "District", "Population");
//                    System.out.println("-----------------------------------------------------------------------------------------------------------");
//
//                    while (rs.next()) {
//                        System.out.printf("%-35s %-45s %-25s %-15d%n",
//                                rs.getString("CityName"),
//                                rs.getString("CountryName"),
//                                rs.getString("District"),
//                                rs.getInt("Population"));
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving data: " + e.getMessage());
//        }
//    }
//    /** Generic executor for Capital City reports */
//    void executeAndDisplayCapital(String query, Object... params) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query)) {
//            for (int i = 0; i < params.length; i++) {
//                stmt.setObject(i + 1, params[i]);
//            }
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                System.out.printf("%-40s %-40s %-15s%n", "Name", "Country", "Population");
//                System.out.println("-------------------------------------------------------------------------------------------");
//
//                while (rs.next()) {
//                    System.out.printf("%-40s %-40s %-15d%n",
//                            rs.getString("Name"),
//                            rs.getString("Country"),
//                            rs.getInt("Population"));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving capital city data: " + e.getMessage());
//        }
//    }
//    /** Generic method to execute and display population reports */
//    void executeAndDisplayPopulation(String query, String type) {
//        Connection con = db.getConnection();
//        if (con == null) {
//            System.out.println("No active database connection. Please connect first.");
//            return;
//        }
//
//        try (PreparedStatement stmt = con.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            System.out.printf("%-20s %-15s %-15s %-10s %-15s %-10s%n",
//                    type, "TotalPop", "CityPop", "City %", "NonCityPop", "NonCity %");
//            System.out.println("------------------------------------------------------------------------------------");
//
//            while (rs.next()) {
//                String name = rs.getString("Name");
//                long total = rs.getLong("TotalPop");
//                long city = rs.getLong("CityPop");
//                long nonCity = total - city;
//                double cityPct = total > 0 ? (city * 100.0 / total) : 0.0;
//                double nonCityPct = total > 0 ? (nonCity * 100.0 / total) : 0.0;
//
//                System.out.printf("%-20s %-15d %-15d %-10.2f %-15d %-10.2f%n",
//                        name, total, city, cityPct, nonCity, nonCityPct);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving population data: " + e.getMessage());
//        }
//    }
//
//    /** Helper to get a single numeric population value */
//    long getSinglePopulation(String sql) {
//        Connection con = db.getConnection();
//        if (con == null) return 0;
//
//        try (PreparedStatement stmt = con.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) {
//                return rs.getLong("pop");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving population data: " + e.getMessage());
//        }
//        return 0;
//    }
//    long getSinglePopulationWithParam(String sql, String param) {
//        Connection con = db.getConnection();
//        if (con == null) return 0;
//
//        try (PreparedStatement stmt = con.prepareStatement(sql)) {
//            stmt.setString(1, param);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getLong("Speakers");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving language population: " + e.getMessage());
//        }
//        return 0;
//    }
//}

package com.Group1DevopsCoursework;

import java.sql.*;
import java.util.ArrayList;

/**
 * Reports.java
 * Single-file implementation:
 *  - contains DB connect/disconnect
 *  - implements 32 report queries
 *  - contains print methods for results
 *  - contains simple model classes as static inner classes
 *
 * Usage:
 *  - compile: javac com/Group1DevopsCoursework/Reports.java
 *  - run:     java com.Group1DevopsCoursework.Reports
 *
 * NOTE: update DB credentials (user/password) and location if needed.
 */
public class Reports {

    // Database connection
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param location host:port (e.g. "localhost:3306")
     * @param delay milliseconds to wait before first attempt (can be 0)
     */
    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 15;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                Thread.sleep(10000);
                // Connect to database
                //con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
//                System.out.println("🔌 Disconnected from database.");
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // ======================================================
    // ================ QUERY / REPORT METHODS =============
    // ======================================================

    // ----------------- COUNTRY REPORTS --------------------

    public ArrayList<Country> getAllCountriesInWorld() {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                ORDER BY Population DESC;
                """;
        return runCountryQuery(sql);
    }

    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                WHERE Continent = ?
                ORDER BY Population DESC;
                """;
        return runCountryQueryWithString(sql, continent);
    }

    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                WHERE Region = ?
                ORDER BY Population DESC;
                """;
        return runCountryQueryWithString(sql, region);
    }

    public ArrayList<Country> getTopNCountriesInWorld(int n) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                ORDER BY Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithInt(sql, n);
    }

    public ArrayList<Country> getTopNCountriesInContinent(String continent, int n) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                WHERE Continent = ?
                ORDER BY Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithStringAndInt(sql, continent, n);
    }

    public ArrayList<Country> getTopNCountriesInRegion(String region, int n) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital
                FROM country
                WHERE Region = ?
                ORDER BY Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithStringAndInt(sql, region, n);
    }

    // ----------------- CITY REPORTS -----------------------

    public ArrayList<City> getAllCitiesInWorld() {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC;
                """;
        return runCityQuery(sql);
    }

    public ArrayList<City> getCitiesByContinent(String continent) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC;
                """;
        return runCityQueryWithString(sql, continent);
    }

    public ArrayList<City> getCitiesByRegion(String region) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC;
                """;
        return runCityQueryWithString(sql, region);
    }

    public ArrayList<City> getCitiesByCountry(String countryName) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Name = ?
                ORDER BY ci.Population DESC;
                """;
        return runCityQueryWithString(sql, countryName);
    }

    public ArrayList<City> getCitiesByDistrict(String district) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE ci.District = ?
                ORDER BY ci.Population DESC;
                """;
        return runCityQueryWithString(sql, district);
    }

    public ArrayList<City> getTopNCitiesInWorld(int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithInt(sql, n);
    }

    public ArrayList<City> getTopNCitiesInContinent(String continent, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, continent, n);
    }

    public ArrayList<City> getTopNCitiesInRegion(String region, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, region, n);
    }

    public ArrayList<City> getTopNCitiesInCountry(String countryName, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Name = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, countryName, n);
    }

    public ArrayList<City> getTopNCitiesInDistrict(String district, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE ci.District = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, district, n);
    }

    // ----------------- CAPITAL CITY REPORTS ----------------

    public ArrayList<CapitalCity> getAllCapitalCitiesInWorld() {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                ORDER BY ci.Population DESC;
                """;
        return runCapitalQuery(sql);
    }

    public ArrayList<CapitalCity> getCapitalCitiesByContinent(String continent) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC;
                """;
        return runCapitalQueryWithString(sql, continent);
    }

    public ArrayList<CapitalCity> getCapitalCitiesByRegion(String region) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Region = ?
                ORDER BY ci.Population DESC;
                """;
        return runCapitalQueryWithString(sql, region);
    }

    public ArrayList<CapitalCity> getTopNCapitalCitiesInWorld(int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithInt(sql, n);
    }

    public ArrayList<CapitalCity> getTopNCapitalCitiesInContinent(String continent, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithStringAndInt(sql, continent, n);
    }

    public ArrayList<CapitalCity> getTopNCapitalCitiesInRegion(String region, int n) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Region = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithStringAndInt(sql, region, n);
    }

    // ----------------- POPULATION REPORTS -------------------

    // Each returns Population objects containing totals and city/non-city counts & percentages

    public ArrayList<Population> getPopulationByContinent() {
        String sql = """
                SELECT c.Continent AS name,
                       SUM(c.Population) AS totalPop,
                       IFNULL(SUM(ci.Population),0) AS cityPop
                FROM country c
                LEFT JOIN city ci ON c.Code = ci.CountryCode
                GROUP BY c.Continent
                ORDER BY totalPop DESC;
                """;
        return runPopulationAggregationQuery(sql, "continent");
    }

    public ArrayList<Population> getPopulationByRegion() {
        String sql = """
                SELECT c.Region AS name,
                       SUM(c.Population) AS totalPop,
                       IFNULL(SUM(ci.Population),0) AS cityPop
                FROM country c
                LEFT JOIN city ci ON c.Code = ci.CountryCode
                GROUP BY c.Region
                ORDER BY totalPop DESC;
                """;
        return runPopulationAggregationQuery(sql, "region");
    }

    public ArrayList<Population> getPopulationByCountry() {
        String sql = """
                SELECT c.Name AS name,
                       c.Population AS totalPop,
                       IFNULL(SUM(ci.Population),0) AS cityPop
                FROM country c
                LEFT JOIN city ci ON c.Code = ci.CountryCode
                GROUP BY c.Code, c.Name, c.Population
                ORDER BY totalPop DESC;
                """;
        return runPopulationAggregationQuery(sql, "country");
    }

    // Single-population getters

    public long getWorldPopulation() {
        String sql = "SELECT SUM(Population) AS worldPop FROM country;";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getLong("worldPop");
        } catch (SQLException e) {
            System.out.println("Error getting world population: " + e.getMessage());
        }
        return 0L;
    }

    public long getPopulationOfContinent(String continent) {
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Continent = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of continent: " + e.getMessage());
        }
        return 0L;
    }

    public long getPopulationOfRegion(String region) {
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Region = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of region: " + e.getMessage());
        }
        return 0L;
    }

    public long getPopulationOfCountry(String countryName) {
        String sql = "SELECT Population AS pop FROM country WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, countryName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of country: " + e.getMessage());
        }
        return 0L;
    }

    public long getPopulationOfDistrict(String district) {
        String sql = "SELECT SUM(Population) AS pop FROM city WHERE District = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of district: " + e.getMessage());
        }
        return 0L;
    }

    public long getPopulationOfCity(String cityName) {
        String sql = "SELECT Population AS pop FROM city WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cityName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of city: " + e.getMessage());
        }
        return 0L;
    }

    // ----------------- LANGUAGE REPORT ---------------------

    /**
     * Returns languages Chinese, English, Hindi, Spanish, Arabic with
     * number of speakers and percentage of world.
     */
    public ArrayList<Language> getLanguageReport() {
        String sql = """
                SELECT cl.Language AS language,
                       SUM( (c.Population * cl.Percentage) / 100.0 ) AS speakers
                FROM countrylanguage cl
                JOIN country c ON cl.CountryCode = c.Code
                WHERE cl.Language IN ('Chinese','English','Hindi','Spanish','Arabic')
                GROUP BY cl.Language
                ORDER BY speakers DESC;
                """;

        ArrayList<Language> list = new ArrayList<>();
        long worldPop = getWorldPopulation();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Language l = new Language();
                l.language = rs.getString("language");
                // speakers may be fractional; round to long
                double speakersD = rs.getDouble("speakers");
                l.speakers = Math.round(speakersD);
                if (worldPop > 0) l.percentage = (speakersD / (double) worldPop) * 100.0;
                else l.percentage = 0.0;
                list.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Error getting language report: " + e.getMessage());
        }
        return list;
    }

    // ======================================================
    // ================ RUNNER & PRINT HELPERS =============
    // ======================================================

    // country helpers
    private ArrayList<Country> runCountryQuery(String sql) {
        ArrayList<Country> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Country c = new Country();
                c.code = rs.getString("Code");
                c.name = rs.getString("Name");
                c.continent = rs.getString("Continent");
                c.region = rs.getString("Region");
                c.population = rs.getInt("Population");
                // Capital stored as city id in world db; convert to string id
                c.capital = rs.getString("Capital");
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error running country query: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<Country> runCountryQueryWithString(String sql, String param) {
        ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.code = rs.getString("Code");
                    c.name = rs.getString("Name");
                    c.continent = rs.getString("Continent");
                    c.region = rs.getString("Region");
                    c.population = rs.getInt("Population");
                    c.capital = rs.getString("Capital");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running country query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<Country> runCountryQueryWithInt(String sql, int n) {
        ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.code = rs.getString("Code");
                    c.name = rs.getString("Name");
                    c.continent = rs.getString("Continent");
                    c.region = rs.getString("Region");
                    c.population = rs.getInt("Population");
                    c.capital = rs.getString("Capital");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running country query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<Country> runCountryQueryWithStringAndInt(String sql, String s, int n) {
        ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, s);
            pstmt.setInt(2, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.code = rs.getString("Code");
                    c.name = rs.getString("Name");
                    c.continent = rs.getString("Continent");
                    c.region = rs.getString("Region");
                    c.population = rs.getInt("Population");
                    c.capital = rs.getString("Capital");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running country query with string & int: " + e.getMessage());
        }
        return list;
    }

    // city helpers
    private ArrayList<City> runCityQuery(String sql) {
        ArrayList<City> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                City c = new City();
                c.name = rs.getString("Name");
                c.country = rs.getString("Country");
                c.district = rs.getString("District");
                c.population = rs.getInt("Population");
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error running city query: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<City> runCityQueryWithString(String sql, String param) {
        ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City c = new City();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.district = rs.getString("District");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running city query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<City> runCityQueryWithInt(String sql, int n) {
        ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City c = new City();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.district = rs.getString("District");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running city query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<City> runCityQueryWithStringAndInt(String sql, String s, int n) {
        ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, s);
            pstmt.setInt(2, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City c = new City();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.district = rs.getString("District");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running city query with string & int: " + e.getMessage());
        }
        return list;
    }

    // capital helpers
    private ArrayList<CapitalCity> runCapitalQuery(String sql) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CapitalCity c = new CapitalCity();
                c.name = rs.getString("Name");
                c.country = rs.getString("Country");
                c.population = rs.getInt("Population");
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<CapitalCity> runCapitalQueryWithString(String sql, String param) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity c = new CapitalCity();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<CapitalCity> runCapitalQueryWithInt(String sql, int n) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity c = new CapitalCity();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<CapitalCity> runCapitalQueryWithStringAndInt(String sql, String s, int n) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, s);
            pstmt.setInt(2, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity c = new CapitalCity();
                    c.name = rs.getString("Name");
                    c.country = rs.getString("Country");
                    c.population = rs.getInt("Population");
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query with string & int: " + e.getMessage());
        }
        return list;
    }

    // population aggregation helper (handles continent/region/country query shapes)
    private ArrayList<Population> runPopulationAggregationQuery(String sql, String level) {
        ArrayList<Population> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Population p = new Population();
                p.name = rs.getString("name");
                p.totalPopulation = rs.getLong("totalPop");
                p.cityPopulation = rs.getLong("cityPop");
                p.nonCityPopulation = p.totalPopulation - p.cityPopulation;
                if (p.totalPopulation > 0) {
                    p.cityPercentage = (p.cityPopulation * 100.0) / (double) p.totalPopulation;
                    p.nonCityPercentage = 100.0 - p.cityPercentage;
                } else {
                    p.cityPercentage = 0.0;
                    p.nonCityPercentage = 0.0;
                }
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error running population aggregation (" + level + "): " + e.getMessage());
        }
        return list;
    }

    // ======================================================
    // ================ PRINT METHODS (already provided) ====
    // ======================================================

    /**
     * Prints a list of Country reports.
     * @param countries The list of countries to print.
     */
    public void printCountries(ArrayList<Country> countries) {
        if (countries == null) {
            System.out.println("No countries");
            return;
        }
        System.out.println(String.format("%-10s %-20s %-20s %-20s %-15s %-10s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (Country c : countries) {
            if (c == null)
                continue;
            String output = String.format("%-10s %-20s %-20s %-20s %-15d %-10s",
                    c.code, c.name, c.continent, c.region, c.population, c.capital);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of City reports.
     * @param cities The list of cities to print.
     */
    public void printCities(ArrayList<City> cities) {
        if (cities == null) {
            System.out.println("No cities");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-20s %-15s",
                "Name", "Country", "District", "Population"));
        for (City ci : cities) {
            if (ci == null)
                continue;
            String output = String.format("%-20s %-20s %-20s %-15d",
                    ci.name, ci.country, ci.district, ci.population);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of Capital City reports.
     * @param capitals The list of capital cities to print.
     */
    public void printCapitals(ArrayList<CapitalCity> capitals) {
        if (capitals == null) {
            System.out.println("No capital cities");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-15s",
                "Name", "Country", "Population"));
        for (CapitalCity cap : capitals) {
            if (cap == null)
                continue;
            String output = String.format("%-20s %-20s %-15d",
                    cap.name, cap.country, cap.population);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of Population reports.
     * @param populations The list of populations to print.
     */
    public void printPopulations(ArrayList<Population> populations) {
        if (populations == null) {
            System.out.println("No population data");
            return;
        }
        System.out.println(String.format("%-30s %-15s %-15s %-10s %-10s",
                "Name", "Total Population", "City Population", "% in Cities", "% not in Cities"));
        for (Population p : populations) {
            if (p == null)
                continue;
            String output = String.format("%-30s %-15d %-15d %-10.2f %-10.2f",
                    p.name, p.totalPopulation, p.cityPopulation, p.cityPercentage, p.nonCityPercentage);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of Language reports.
     * @param languages The list of languages to print.
     */
    public void printLanguages(ArrayList<Language> languages) {
        if (languages == null) {
            System.out.println("No languages");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-20s",
                "Language", "Speakers", "% of World Population"));
        for (Language l : languages) {
            if (l == null)
                continue;
            String output = String.format("%-20s %-20d %-20.2f",
                    l.language, l.speakers, l.percentage);
            System.out.println(output);
        }
    }
    public static void main(String[] args) {
        Reports r = new Reports();

        // Connect to database
        if(args.length < 1){
            r.connect("localhost:33060", 0);
        }else{
            r.connect("db:3306", 10000);
        }


        // ---------------- Countries ----------------
        System.out.println("\n=== All countries in world (top by population) ===");
        r.printCountries(r.getAllCountriesInWorld());

        System.out.println("\n=== Countries in continent 'Asia' ===");
        r.printCountries(r.getCountriesByContinent("Asia"));

        System.out.println("\n=== Countries in region 'Eastern Europe' ===");
        r.printCountries(r.getCountriesByRegion("Eastern Europe"));

        System.out.println("\n=== Top 5 countries in world ===");
        r.printCountries(r.getTopNCountriesInWorld(5));

        System.out.println("\n=== Top 5 countries in continent 'Asia' ===");
        r.printCountries(r.getTopNCountriesInContinent("Asia", 5));

        System.out.println("\n=== Top 5 countries in region 'Southern Asia' ===");
        r.printCountries(r.getTopNCountriesInRegion("Southern Asia", 5));

        // ---------------- Cities ----------------
        System.out.println("\n=== All cities in world (top by population) ===");
        r.printCities(r.getAllCitiesInWorld());

        System.out.println("\n=== Cities in continent 'Asia' ===");
        r.printCities(r.getCitiesByContinent("Asia"));

        System.out.println("\n=== Cities in region 'Western Europe' ===");
        r.printCities(r.getCitiesByRegion("Western Europe"));

        System.out.println("\n=== Cities in country 'China' ===");
        r.printCities(r.getCitiesByCountry("China"));

        System.out.println("\n=== Cities in district 'California' ===");
        r.printCities(r.getCitiesByDistrict("California"));

        System.out.println("\n=== Top 5 cities in world ===");
        r.printCities(r.getTopNCitiesInWorld(5));

        System.out.println("\n=== Top 5 cities in continent 'Asia' ===");
        r.printCities(r.getTopNCitiesInContinent("Asia", 5));

        System.out.println("\n=== Top 5 cities in region 'Southern Asia' ===");
        r.printCities(r.getTopNCitiesInRegion("Southern Asia", 5));

        System.out.println("\n=== Top 5 cities in country 'India' ===");
        r.printCities(r.getTopNCitiesInCountry("India", 5));

        System.out.println("\n=== Top 5 cities in district 'Maharashtra' ===");
        r.printCities(r.getTopNCitiesInDistrict("Maharashtra", 5));

        // ---------------- Capitals ----------------
        System.out.println("\n=== All capital cities in world (top by population) ===");
        r.printCapitals(r.getAllCapitalCitiesInWorld());

        System.out.println("\n=== Capitals in continent 'Asia' ===");
        r.printCapitals(r.getCapitalCitiesByContinent("Asia"));

        System.out.println("\n=== Capitals in region 'Northern Europe' ===");
        r.printCapitals(r.getCapitalCitiesByRegion("Northern Europe"));

        System.out.println("\n=== Top 5 capital cities in world ===");
        r.printCapitals(r.getTopNCapitalCitiesInWorld(5));

        System.out.println("\n=== Top 5 capital cities in continent 'Asia' ===");
        r.printCapitals(r.getTopNCapitalCitiesInContinent("Asia", 5));

        System.out.println("\n=== Top 5 capital cities in region 'Southern Europe' ===");
        r.printCapitals(r.getTopNCapitalCitiesInRegion("Southern Europe", 5));

        // ---------------- Populations ----------------
        System.out.println("\n=== Population by Continent ===");
        r.printPopulations(r.getPopulationByContinent());

        System.out.println("\n=== Population by Region ===");
        r.printPopulations(r.getPopulationByRegion());

        System.out.println("\n=== Population by Country ===");
        // printing top 20 countries by population (full list is large)
        ArrayList<Population> popCountries = r.getPopulationByCountry();
        if (popCountries.size() > 20) {
            ArrayList<Population> top20 = new ArrayList<>(popCountries.subList(0, 20));
            r.printPopulations(top20);
        } else r.printPopulations(popCountries);

        // single population access examples
        System.out.println("\nWorld population: " + r.getWorldPopulation());
        System.out.println("Population of continent Asia: " + r.getPopulationOfContinent("Asia"));
        System.out.println("Population of region 'Eastern Asia': " + r.getPopulationOfRegion("Eastern Asia"));
        System.out.println("Population of country 'China': " + r.getPopulationOfCountry("China"));
        System.out.println("Population of district 'California': " + r.getPopulationOfDistrict("California"));
        System.out.println("Population of city 'Shanghai': " + r.getPopulationOfCity("Shanghai"));

        // ---------------- Languages ----------------
        System.out.println("\n=== Language report (speakers and % of world) ===");
        r.printLanguages(r.getLanguageReport());

        // disconnect
        r.disconnect();
    }
}

