package com.Group1DevopsCoursework;

import java.sql.*;
import java.util.ArrayList;

/**
 * Reports.java
 *  - contains DB connect/disconnect
 *  - implements 32 report queries
 *  - contains print methods for results
 */
public class Reports {

    // Database connection
    private Connection con = null;

    /**
     * Check if database connection is available and valid
     */
    private boolean isConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Safe method to check connection before executing queries
     */
    private boolean checkConnection() {
        if (!isConnected()) {
            System.out.println("Database not connected - cannot execute query");
            return false;
        }
        return true;
    }

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
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root", "example"
                );
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
                System.out.println(" Disconnected from database.");
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

    public Population getPopulationOfContinentWithName(String continent) {
        Population p = new Population();
        p.name = continent; // Set the name column
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Continent = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p.totalPopulation = rs.getLong("pop"); // Set the population
                } else {
                    p.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of continent: " + e.getMessage());
            p.totalPopulation = 0L;
        }
        return p;
    }

    public Population getPopulationOfRegionWithName(String region) {
        Population p = new Population();
        p.name = region;
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Region = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p.totalPopulation = rs.getLong("pop");
                } else {
                    p.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of region: " + e.getMessage());
            p.totalPopulation = 0L;
        }
        return p;
    }
    public Population getPopulationOfCountryWithName(String country) {
        Population p = new Population();
        p.name = country;
        String sql = "SELECT Population AS pop FROM country WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, country);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p.totalPopulation = rs.getLong("pop");
                } else {
                    p.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of country: " + e.getMessage());
            p.totalPopulation = 0L;
        }
        return p;
    }
    public Population getPopulationOfDistrictWithName(String district) {
        Population p = new Population();
        p.name = district;
        String sql = "SELECT SUM(Population) AS pop FROM city WHERE District = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p.totalPopulation = rs.getLong("pop");
                } else {
                    p.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of district: " + e.getMessage());
            p.totalPopulation = 0L;
        }
        return p;
    }
    public Population getPopulationOfCityWithName(String city) {
        Population p = new Population();
        p.name = city;
        String sql = "SELECT Population AS pop FROM city WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, city);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p.totalPopulation = rs.getLong("pop");
                } else {
                    p.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of city: " + e.getMessage());
            p.totalPopulation = 0L;
        }
        return p;
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
//    public void printSinglePopulation(Population p) {
//        System.out.println(String.format("%-20s %-15s", "Name", "Population"));
//        System.out.println(String.format("%-20s %-15d", p.name, p.totalPopulation));
//    }


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

            String output = String.format("%-20s %-20d %-20.2f%%", l.language, l.speakers, l.percentage);
            System.out.println(output);

        }


    }
    public void addCountry(Country country) {
        String sql = """
        INSERT INTO country (Code, Name, Continent, Region, Population)
        VALUES (?, ?, ?, ?, ?);
        """;
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, country.code);
            pstmt.setString(2, country.name);
            pstmt.setString(3, country.continent);
            pstmt.setString(4, country.region);
            pstmt.setInt(5, country.population);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding country: " + e.getMessage());
        }
    }

    public Country getCountryByCode(String code) {
        String sql = "SELECT Code, Name, Continent, Region, Population FROM country WHERE Code = ? LIMIT 1;";
        Country country = null;
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = new Country();
                    country.code = rs.getString("Code");
                    country.name = rs.getString("Name");
                    country.continent = rs.getString("Continent");
                    country.region = rs.getString("Region");
                    country.population = rs.getInt("Population");

                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting country: " + e.getMessage());
        }
        return country;
    }

    public static void main(String[] args) {
        Reports r = new Reports();

        // Connect to database
        if(args.length < 1){
            r.connect("localhost:33060", 0);
        }else{
            r.connect("world-db:3306", 10000);
        }


        // ---------------- Countries ----------------
        System.out.println("1. === All countries in world (top by population) ===");
        r.printCountries(r.getAllCountriesInWorld());

        System.out.println("2. === Countries in continent 'Asia' ===");
        r.printCountries(r.getCountriesByContinent("Asia"));

        System.out.println("3. === Countries in region 'Eastern Europe' ===");
        r.printCountries(r.getCountriesByRegion("Eastern Europe"));

        System.out.println("4. === Top 5 countries in world ===");
        r.printCountries(r.getTopNCountriesInWorld(5));

        System.out.println("5. === Top 5 countries in continent 'Asia' ===");
        r.printCountries(r.getTopNCountriesInContinent("Asia", 5));

        System.out.println("6. === Top 5 countries in region 'Southern Asia' ===");
        r.printCountries(r.getTopNCountriesInRegion("Southern Asia", 5));

// ---------------- Cities ----------------
        System.out.println("7. === All cities in world (top by population) ===");
        r.printCities(r.getAllCitiesInWorld());

        System.out.println("8. === Cities in continent 'Asia' ===");
        r.printCities(r.getCitiesByContinent("Asia"));

        System.out.println("9. === Cities in region 'Western Europe' ===");
        r.printCities(r.getCitiesByRegion("Western Europe"));

        System.out.println("10. === Cities in country 'China' ===");
        r.printCities(r.getCitiesByCountry("China"));

        System.out.println("11. === Cities in district 'California' ===");
        r.printCities(r.getCitiesByDistrict("California"));

        System.out.println("12. === Top 5 cities in world ===");
        r.printCities(r.getTopNCitiesInWorld(5));

        System.out.println("13. === Top 5 cities in continent 'Asia' ===");
        r.printCities(r.getTopNCitiesInContinent("Asia", 5));

        System.out.println("14. === Top 5 cities in region 'Southern Asia' ===");
        r.printCities(r.getTopNCitiesInRegion("Southern Asia", 5));

        System.out.println("15. === Top 5 cities in country 'India' ===");
        r.printCities(r.getTopNCitiesInCountry("India", 5));

        System.out.println("16. === Top 5 cities in district 'Maharashtra' ===");
        r.printCities(r.getTopNCitiesInDistrict("Maharashtra", 5));

// ---------------- Capitals ----------------
        System.out.println("17. === All capital cities in world (top by population) ===");
        r.printCapitals(r.getAllCapitalCitiesInWorld());

        System.out.println("18. === Capitals in continent 'Asia' ===");
        r.printCapitals(r.getCapitalCitiesByContinent("Asia"));

        System.out.println("19. === Capitals in region 'Northern Europe' ===");
        r.printCapitals(r.getCapitalCitiesByRegion("Northern Europe"));

        System.out.println("20. === Top 5 capital cities in world ===");
        r.printCapitals(r.getTopNCapitalCitiesInWorld(5));

        System.out.println("21. === Top 5 capital cities in continent 'Asia' ===");
        r.printCapitals(r.getTopNCapitalCitiesInContinent("Asia", 5));

        System.out.println("22. === Top 5 capital cities in region 'Southern Europe' ===");
        r.printCapitals(r.getTopNCapitalCitiesInRegion("Southern Europe", 5));

// ---------------- Populations ----------------
        System.out.println("23. === Population by Continent ===");
        r.printPopulations(r.getPopulationByContinent());

        System.out.println("24. === Population by Region ===");
        r.printPopulations(r.getPopulationByRegion());

        System.out.println("25. === Population by Country ===");
//        ArrayList<Population> popCountries = r.getPopulationByCountry();
//        if (popCountries.size() > 20) {
//            ArrayList<Population> top20 = new ArrayList<>(popCountries.subList(0, 20));
//            r.printPopulations(top20);
//        } else r.printPopulations(popCountries);
        r.printPopulations(r.getPopulationByCountry());

// single population access examples
        System.out.println("26. World population: " + r.getWorldPopulation());
//
        System.out.println("27. === Population of continent Asia ===");
        Population asiaPop = r.getPopulationOfContinentWithName("Asia");
        System.out.println(String.format("%-20s %-15s", "Continent", "Population"));
        System.out.println(String.format("%-20s %-15d", asiaPop.name, asiaPop.totalPopulation));

// 28. Region population
        System.out.println("28. === Population of region Eastern Asia ===");
        Population regionPop = r.getPopulationOfRegionWithName("Eastern Asia");
        System.out.println(String.format("%-20s %-15s", "Region", "Population"));
        System.out.println(String.format("%-20s %-15d", regionPop.name, regionPop.totalPopulation));

// 29. Country population
        System.out.println("29. === Population of country Brazil ===");
        Population countryPop = r.getPopulationOfCountryWithName("Brazil");
        System.out.println(String.format("%-20s %-15s", "Country", "Population"));
        System.out.println(String.format("%-20s %-15d", countryPop.name, countryPop.totalPopulation));

// 30. District population
        System.out.println("30. === Population of district São Paulo ===");
        Population districtPop = r.getPopulationOfDistrictWithName("São Paulo");
        System.out.println(String.format("%-20s %-15s", "District", "Population"));
        System.out.println(String.format("%-20s %-15d", districtPop.name, districtPop.totalPopulation));

// 31. City population
        System.out.println("31. === Population of city Shanghai ===");
        Population cityPop = r.getPopulationOfCityWithName("Shanghai");
        System.out.println(String.format("%-20s %-15s", "City", "Population"));
        System.out.println(String.format("%-20s %-15d", cityPop.name, cityPop.totalPopulation));


// ---------------- Languages ----------------
        System.out.println("32. === Language report (speakers and % of world) ===");
        r.printLanguages(r.getLanguageReport());

        // disconnect
        r.disconnect();
    }
}

