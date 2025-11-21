package com.Group1DevopsCoursework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Reports.java
 * - contains DB connect/disconnect
 * - implements 32 report queries
 * - contains print methods for results
 */
public class Reports {

    public Reports() {
        // default constructor
    }

    // Database connection
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     *
     * @param location host:port (e.g. "localhost:3306")
     * @param delay    milliseconds to wait before first attempt (can be 0)
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
            try {
                System.out.println("Waiting for database to be ready... attempt " + (i + 1));
                Thread.sleep(10000); // Wait 10 seconds before each attempt

                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root", "example"
                );

                System.out.println("✅ Successfully connected to database!");
                break;
            } catch (SQLException sqle) {
                System.out.println("❌ Database not ready yet (" + (i + 1) + "/" + retries + ")");
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
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                ORDER BY c.Population DESC;
                """;

        return runCountryQuery(sql);
    }

    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = """
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                WHERE c.Continent = ?
                ORDER BY c.Population DESC;
                """;
        return runCountryQueryWithString(sql, continent);
    }


    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = """
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                WHERE c.Region = ?
                ORDER BY c.Population DESC;
                """;
        return runCountryQueryWithString(sql, region);
    }


    public ArrayList<Country> getTopNCountriesInWorld(int toplimitednumber) {
        String sql = """
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                ORDER BY c.Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithInt(sql, toplimitednumber);
    }


    public ArrayList<Country> getTopNCountriesInContinent(String continent, int toplimitednumber) {
        String sql = """
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                WHERE c.Continent = ?
                ORDER BY c.Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithStringAndInt(sql, continent, toplimitednumber);
    }


    public ArrayList<Country> getTopNCountriesInRegion(String region, int toplimitednumber) {
        String sql = """
                SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci ON c.Capital = ci.ID
                WHERE c.Region = ?
                ORDER BY c.Population DESC
                LIMIT ?;
                """;
        return runCountryQueryWithStringAndInt(sql, region, toplimitednumber);
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

    public ArrayList<City> getTopNCitiesInWorld(int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithInt(sql, toplimitednumber);
    }

    public ArrayList<City> getTopNCitiesInContinent(String continent, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, continent, toplimitednumber);
    }

    public ArrayList<City> getTopNCitiesInRegion(String region, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, region, toplimitednumber);
    }

    public ArrayList<City> getTopNCitiesInCountry(String countryName, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Name = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, countryName, toplimitednumber);
    }

    public ArrayList<City> getTopNCitiesInDistrict(String district, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE ci.District = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCityQueryWithStringAndInt(sql, district, toplimitednumber);
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

    public ArrayList<CapitalCity> getTopNCapitalCitiesInWorld(int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithInt(sql, toplimitednumber);
    }

    public ArrayList<CapitalCity> getTopNCapitalCitiesInContinent(String continent, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithStringAndInt(sql, continent, toplimitednumber);
    }

    public ArrayList<CapitalCity> getTopNCapitalCitiesInRegion(String region, int toplimitednumber) {
        String sql = """
                SELECT ci.Name, co.Name AS Country, ci.Population
                FROM city ci
                JOIN country co ON ci.ID = co.Capital
                WHERE co.Region = ?
                ORDER BY ci.Population DESC
                LIMIT ?;
                """;
        return runCapitalQueryWithStringAndInt(sql, region, toplimitednumber);
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
             ResultSet result = stmt.executeQuery(sql)) {

            if (result.next()) {
                return result.getLong("worldPop");
            }

        } catch (SQLException e) {
            System.out.println("Error getting world population: " + e.getMessage());
        }
        return 0L;
    }

    public Population getPopulationOfContinentWithName(String continent) {
        Population totalpopulation = new Population();
        totalpopulation.name = continent; // Set the name column
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Continent = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    totalpopulation.totalPopulation = result.getLong("pop"); // Set the population
                } else {
                    totalpopulation.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of continent: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }
        return totalpopulation;
    }

    public Population getPopulationOfRegionWithName(String region) {
        Population totalpopulation = new Population();
        totalpopulation.name = region;
        String sql = "SELECT SUM(Population) AS pop FROM country WHERE Region = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    totalpopulation.totalPopulation = result.getLong("pop");
                } else {
                    totalpopulation.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of region: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }
        return totalpopulation;
    }

    public Population getPopulationOfCountryWithName(String country) {
        Population totalpopulation = new Population();
        totalpopulation.name = country;
        String sql = "SELECT Population AS pop FROM country WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, country);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    totalpopulation.totalPopulation = result.getLong("pop");
                } else {
                    totalpopulation.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of country: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }
        return totalpopulation;
    }

    public Population getPopulationOfDistrictWithName(String district) {
        Population totalpopulation = new Population();
        totalpopulation.name = district;
        String sql = "SELECT SUM(Population) AS pop FROM city WHERE District = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    totalpopulation.totalPopulation = result.getLong("pop");
                } else {
                    totalpopulation.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of district: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }
        return totalpopulation;
    }

    public Population getPopulationOfCityWithName(String city) {
        Population totalpopulation = new Population();
        totalpopulation.name = city;
        String sql = "SELECT Population AS pop FROM city WHERE Name = ? LIMIT 1;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, city);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    totalpopulation.totalPopulation = result.getLong("pop");
                } else {
                    totalpopulation.totalPopulation = 0L;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of city: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }
        return totalpopulation;
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
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                Language language = new Language();
                language.language = result.getString("language");
                // speakers may be fractional; round to long
                double speakersD = result.getDouble("speakers");
                language.speakers = Math.round(speakersD);
                if (worldPop > 0) language.percentage = (speakersD / (double) worldPop) * 100.0;
                else language.percentage = 0.0;
                list.add(language);
            }
        } catch (SQLException e) {
            System.out.println("Error getting language report: " + e.getMessage());
        }
        return list;
    }


    public void outputAllReportsMarkdown(String filename) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("# World Popullation Reports\n\n");
        stringbuilder.append("### Group 1 — DevOps Coursework Team Project\n\n");
        stringbuilder.append("This report was collaboratively created by **Group 1** as part of our DevOps coursework.");

        try {
            new File("./reports/").mkdirs();

            // ---------------- Countries ----------------
            stringbuilder.append("## 1. All Countries in the World\n\n");
            appendCountriesMarkdown(stringbuilder, getAllCountriesInWorld());

            stringbuilder.append("## 2. Countries by Continent: Asia\n\n");
            appendCountriesMarkdown(stringbuilder, getCountriesByContinent("Asia"));

            stringbuilder.append("## 3. Countries by Region: Eastern Europe\n\n");
            appendCountriesMarkdown(stringbuilder, getCountriesByRegion("Eastern Europe"));

            stringbuilder.append("## 4. Top 5 Countries in World\n\n");
            appendCountriesMarkdown(stringbuilder, getTopNCountriesInWorld(5));

            stringbuilder.append("## 5. Top 5 Countries in Asia\n\n");
            appendCountriesMarkdown(stringbuilder, getTopNCountriesInContinent("Asia", 5));

            stringbuilder.append("## 6. Top 5 Countries in Middle East\n\n");
            appendCountriesMarkdown(stringbuilder, getTopNCountriesInRegion("Middle East", 5));

            // ---------------- Cities ----------------
            stringbuilder.append("## 7. All Cities in World\n\n");
            appendCitiesMarkdown(stringbuilder, getAllCitiesInWorld());

            stringbuilder.append("## 8. Cities in Continent: Asia\n\n");
            appendCitiesMarkdown(stringbuilder, getCitiesByContinent("Asia"));

            stringbuilder.append("## 9. Cities in Region: Western Europe\n\n");
            appendCitiesMarkdown(stringbuilder, getCitiesByRegion("Western Europe"));

            stringbuilder.append("## 10. Cities in Country: China\n\n");
            appendCitiesMarkdown(stringbuilder, getCitiesByCountry("China"));

            stringbuilder.append("## 11. Cities in District: California\n\n");
            appendCitiesMarkdown(stringbuilder, getCitiesByDistrict("California"));

            stringbuilder.append("## 12. Top 5 Cities in the World\n\n");
            appendCitiesMarkdown(stringbuilder, getTopNCitiesInWorld(5));

            stringbuilder.append("## 13. Top 5 Cities in Continent: Asia\n\n");
            appendCitiesMarkdown(stringbuilder, getTopNCitiesInContinent("Asia", 5));

            stringbuilder.append("## 14. Top 5 Cities in Region: Eastern Asia\n\n");
            appendCitiesMarkdown(stringbuilder, getTopNCitiesInRegion("Eastern Asia", 5));

            stringbuilder.append("## 15. Top 5 Cities in Country: India\n\n");
            appendCitiesMarkdown(stringbuilder, getTopNCitiesInCountry("India", 5));

            stringbuilder.append("## 16. Top 5 Cities in District: Maharashtra\n\n");
            appendCitiesMarkdown(stringbuilder, getTopNCitiesInDistrict("Maharashtra", 5));

            // ---------------- Capitals ----------------
            stringbuilder.append("## 17. All Capital Cities in World\n\n");
            appendCapitalsMarkdown(stringbuilder, getAllCapitalCitiesInWorld());

            stringbuilder.append("## 18. Capitals in Continent: Asia\n\n");
            appendCapitalsMarkdown(stringbuilder, getCapitalCitiesByContinent("Asia"));

            stringbuilder.append("## 19. Capitals in Region: Eastern Asia\n\n");
            appendCapitalsMarkdown(stringbuilder, getCapitalCitiesByRegion("Eastern Asia"));

            stringbuilder.append("## 20. Top 5 Capital Cities in the World\n\n");
            appendCapitalsMarkdown(stringbuilder, getTopNCapitalCitiesInWorld(5));

            stringbuilder.append("## 21. Top 5 Capital Cities in Continent: Asia\n\n");
            appendCapitalsMarkdown(stringbuilder, getTopNCapitalCitiesInContinent("Asia", 5));

            stringbuilder.append("## 22. Top 5 Capital Cities in Region: Southern Europe\n\n");
            appendCapitalsMarkdown(stringbuilder, getTopNCapitalCitiesInRegion("Southern Europe", 5));


            // ---------------- Population ----------------
            stringbuilder.append("## 23. Population by Continent\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByContinent());

            stringbuilder.append("## 24. Population by Region\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByRegion());

            stringbuilder.append("## 25. Population by Country\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByCountry());

            // ---------------- Population (Individual Reports) ----------------

            // 26. World population
            stringbuilder.append("## 26. World Population\n\n");
            stringbuilder.append("| Area | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| World | ").append(getWorldPopulation()).append(" |\r\n\n");

            // 27. Population of Continent: Asia
            stringbuilder.append("## 27. Population of Continent: Asia\n\n");
            stringbuilder.append("| Continent | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Asia | ").append(getPopulationOfContinentWithName("Asia").totalPopulation).append(" |\r\n\n");

            // 28. Population of Region: Eastern Asia
            stringbuilder.append("## 28. Population of Region: Eastern Asia\n\n");
            stringbuilder.append("| Region | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Eastern Asia | ").append(getPopulationOfRegionWithName("Eastern Asia").totalPopulation).append(" |\r\n\n");

            // 29. Population of Country: Brazil
            stringbuilder.append("## 29. Population of Country: Brazil\n\n");
            stringbuilder.append("| Country | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Brazil | ").append(getPopulationOfCountryWithName("Brazil").totalPopulation).append(" |\r\n\n");

            // 30. Population of District: São Paulo
            stringbuilder.append("## 30. Population of District: São Paulo\n\n");
            stringbuilder.append("| District | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| São Paulo | ").append(getPopulationOfDistrictWithName("São Paulo").totalPopulation).append(" |\r\n\n");

            // 31. Population of City: Shanghai
            stringbuilder.append("## 31. Population of City: Shanghai\n\n");
            stringbuilder.append("| City | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Shanghai | ").append(getPopulationOfCityWithName("Shanghai").totalPopulation).append(" |\r\n\n");

            // ---------------- Languages ----------------
            stringbuilder.append("## 32. Language Report\n\n");
            appendLanguagesMarkdown(stringbuilder, getLanguageReport());

            // Write to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(stringbuilder.toString());
            writer.close();
            System.out.println("Markdown report written to ./reports/" + filename);
        } catch (IOException e) {
            System.out.println("Error writing markdown report: " + e.getMessage());
        }
    }

// ---------- Helper methods for markdown formatting ----------

    /* default */ public void appendCountriesMarkdown(StringBuilder stringbuilder, ArrayList<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            stringbuilder.append("_No countries found._\n\n");
            {
                return;
            }
        }
        stringbuilder.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        stringbuilder.append("| --- | --- | --- | --- | --- | --- |\r\n");
        for (Country c : countries) {
            if (c == null) continue;
            stringbuilder.append("| ").append(c.code).append(" | ").append(c.name)
                    .append(" | ").append(c.continent).append(" | ").append(c.region)
                    .append(" | ").append(c.population).append(" | ").append(c.capital).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /* default */ public void appendCitiesMarkdown(StringBuilder stringbuilder, ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            stringbuilder.append("_No cities found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Country | District | Population |\r\n");
        stringbuilder.append("| --- | --- | --- | --- |\r\n");
        for (City ci : cities) {
            if (ci == null) continue;
            stringbuilder.append("| ").append(ci.name).append(" | ").append(ci.country)
                    .append(" | ").append(ci.district).append(" | ").append(ci.population).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /* default */ public void appendCapitalsMarkdown(StringBuilder stringbuilder, ArrayList<CapitalCity> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            stringbuilder.append("_No capitals found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Country | Population |\r\n");
        stringbuilder.append("| --- | --- | --- |\r\n");
        for (CapitalCity cap : capitals) {
            if (cap == null) continue;
            stringbuilder.append("| ").append(cap.name).append(" | ").append(cap.country)
                    .append(" | ").append(cap.population).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /* default */ public void appendPopulationsMarkdown(StringBuilder stringbuilder, ArrayList<Population> populations) {
        if (populations == null || populations.isEmpty()) {
            stringbuilder.append("_No population data found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Total Population | City Population | % in Cities | % not in Cities |\r\n");
        stringbuilder.append("| --- | --- | --- | --- | --- |\r\n");
        for (Population p : populations) {
            if (p == null) continue;
            stringbuilder.append("| ").append(p.name).append(" | ")
                    .append(p.totalPopulation).append(" | ").append(p.cityPopulation)
                    .append(" | ").append(String.format("%.2f", p.cityPercentage))
                    .append(" | ").append(String.format("%.2f", p.nonCityPercentage)).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /* default */ public void appendLanguagesMarkdown(StringBuilder stringbuilder, ArrayList<Language> languages) {
        if (languages == null || languages.isEmpty()) {
            stringbuilder.append("_No language data found._\n\n");
            return;
        }
        stringbuilder.append("| Language | Speakers | % of World Population |\r\n");
        stringbuilder.append("| --- | --- | --- |\r\n");
        for (Language l : languages) {
            if (l == null) continue;
            stringbuilder.append("| ").append(l.language).append(" | ").append(l.speakers)
                    .append(" | ").append(String.format("%.2f%%", l.percentage)).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    // ======================================================
    // ================ RUNNER & PRINT HELPERS =============
    // ======================================================

    // country helpers
    private ArrayList<Country> runCountryQuery(String sql) {
        ArrayList<Country> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                Country country = new Country();
                country.code = result.getString("Code");
                country.name = result.getString("Name");
                country.continent = result.getString("Continent");
                country.region = result.getString("Region");
                country.population = result.getInt("Population");
                // Capital stored as city id in world db; convert to string id
                country.capital = result.getString("Capital");
                list.add(country);
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
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    Country country = new Country();
                    country.code = result.getString("Code");
                    country.name = result.getString("Name");
                    country.continent = result.getString("Continent");
                    country.region = result.getString("Region");
                    country.population = result.getInt("Population");
                    country.capital = result.getString("Capital");
                    list.add(country);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running country query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<Country> runCountryQueryWithInt(String sql, int toplimitednumber) {
        ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    Country country = new Country();
                    country.code = result.getString("Code");
                    country.name = result.getString("Name");
                    country.continent = result.getString("Continent");
                    country.region = result.getString("Region");
                    country.population = result.getInt("Population");
                    country.capital = result.getString("Capital");
                    list.add(country);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running country query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<Country> runCountryQueryWithStringAndInt(String sql, String string, int toplimitednumber) {
        ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    Country country = new Country();
                    country.code = result.getString("Code");
                    country.name = result.getString("Name");
                    country.continent = result.getString("Continent");
                    country.region = result.getString("Region");
                    country.population = result.getInt("Population");
                    country.capital = result.getString("Capital");
                    list.add(country);
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
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                City city = new City();
                city.name = result.getString("Name");
                city.country = result.getString("Country");
                city.district = result.getString("District");
                city.population = result.getInt("Population");
                list.add(city);
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
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    City city = new City();
                    city.name = result.getString("Name");
                    city.country = result.getString("Country");
                    city.district = result.getString("District");
                    city.population = result.getInt("Population");
                    list.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running city query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<City> runCityQueryWithInt(String sql, int toplimitednumber) {
        ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    City city = new City();
                    city.name = result.getString("Name");
                    city.country = result.getString("Country");
                    city.district = result.getString("District");
                    city.population = result.getInt("Population");
                    list.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running city query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<City> runCityQueryWithStringAndInt(String sql, String string, int toplimitednumber) {
        ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    City city = new City();
                    city.name = result.getString("Name");
                    city.country = result.getString("Country");
                    city.district = result.getString("District");
                    city.population = result.getInt("Population");
                    list.add(city);
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
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                CapitalCity capitalcity = new CapitalCity();
                capitalcity.name = result.getString("Name");
                capitalcity.country = result.getString("Country");
                capitalcity.population = result.getInt("Population");
                list.add(capitalcity);
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
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    CapitalCity capitalcity = new CapitalCity();
                    capitalcity.name = result.getString("Name");
                    capitalcity.country = result.getString("Country");
                    capitalcity.population = result.getInt("Population");
                    list.add(capitalcity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query with param: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<CapitalCity> runCapitalQueryWithInt(String sql, int toplimitednumber) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    CapitalCity capitalcity = new CapitalCity();
                    capitalcity.name = result.getString("Name");
                    capitalcity.country = result.getString("Country");
                    capitalcity.population = result.getInt("Population");
                    list.add(capitalcity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error running capital query with int: " + e.getMessage());
        }
        return list;
    }

    private ArrayList<CapitalCity> runCapitalQueryWithStringAndInt(String sql, String string, int toplimitednumber) {
        ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    CapitalCity capitalcity = new CapitalCity();
                    capitalcity.name = result.getString("Name");
                    capitalcity.country = result.getString("Country");
                    capitalcity.population = result.getInt("Population");
                    list.add(capitalcity);
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
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                Population population = new Population();
                population.name = result.getString("name");
                population.totalPopulation = result.getLong("totalPop");
                population.cityPopulation = result.getLong("cityPop");
                population.nonCityPopulation = population.totalPopulation - population.cityPopulation;
                if (population.totalPopulation > 0) {
                    population.cityPercentage = (population.cityPopulation * 100.0) / (double) population.totalPopulation;
                    population.nonCityPercentage = 100.0 - population.cityPercentage;
                } else {
                    population.cityPercentage = 0.0;
                    population.nonCityPercentage = 0.0;
                }
                list.add(population);
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
     *
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
     *
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
     *
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
     *
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
     *
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
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    country = new Country();
                    country.code = result.getString("Code");
                    country.name = result.getString("Name");
                    country.continent = result.getString("Continent");
                    country.region = result.getString("Region");
                    country.population = result.getInt("Population");

                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting country: " + e.getMessage());
        }
        return country;
    }

    public static void main(String[] args) {
        Reports reports = new Reports();

        // Connect to database
        if (args.length < 1) {
            reports.connect("localhost:33060", 0);
        } else {
            reports.connect("world-db:3306", 10000);
        }


        // ---------------- Countries ----------------
        System.out.println("1. === All countries in world (top by population) ===");
        reports.printCountries(reports.getAllCountriesInWorld());

        System.out.println("2. === Countries in continent 'Asia' ===");
        reports.printCountries(reports.getCountriesByContinent("Asia"));

        System.out.println("3. === Countries in region 'Eastern Europe' ===");
        reports.printCountries(reports.getCountriesByRegion("Eastern Europe"));

        System.out.println("4. === Top 5 countries in world ===");
        reports.printCountries(reports.getTopNCountriesInWorld(5));

        System.out.println("5. === Top 5 countries in continent 'Asia' ===");
        reports.printCountries(reports.getTopNCountriesInContinent("Asia", 5));

        System.out.println("6. === Top 5 countries in region 'Middle East' ===");
        reports.printCountries(reports.getTopNCountriesInRegion("Middle East", 5));

// ---------------- Cities ----------------
        System.out.println("7. === All cities in world (top by population) ===");
        reports.printCities(reports.getAllCitiesInWorld());

        System.out.println("8. === Cities in continent 'Asia' ===");
        reports.printCities(reports.getCitiesByContinent("Asia"));

        System.out.println("9. === Cities in region 'Western Europe' ===");
        reports.printCities(reports.getCitiesByRegion("Western Europe"));

        System.out.println("10. === Cities in country 'China' ===");
        reports.printCities(reports.getCitiesByCountry("China"));

        System.out.println("11. === Cities in district 'California' ===");
        reports.printCities(reports.getCitiesByDistrict("California"));

        System.out.println("12. === Top 5 cities in world ===");
        reports.printCities(reports.getTopNCitiesInWorld(5));

        System.out.println("13. === Top 5 cities in continent 'Asia' ===");
        reports.printCities(reports.getTopNCitiesInContinent("Asia", 5));

        System.out.println("14. === Top 5 cities in region 'Southern Asia' ===");
        reports.printCities(reports.getTopNCitiesInRegion("Southern Asia", 5));

        System.out.println("15. === Top 5 cities in country 'India' ===");
        reports.printCities(reports.getTopNCitiesInCountry("India", 5));

        System.out.println("16. === Top 5 cities in district 'Maharashtra' ===");
        reports.printCities(reports.getTopNCitiesInDistrict("Maharashtra", 5));

// ---------------- Capitals ----------------
        System.out.println("17. === All capital cities in world (top by population) ===");
        reports.printCapitals(reports.getAllCapitalCitiesInWorld());

        System.out.println("18. === Capitals in continent 'Asia' ===");
        reports.printCapitals(reports.getCapitalCitiesByContinent("Asia"));

        System.out.println("19. === Capitals in region 'Eastern Asia' ===");
        reports.printCapitals(reports.getCapitalCitiesByRegion("Eastern Asia"));

        System.out.println("20. === Top 5 capital cities in world ===");
        reports.printCapitals(reports.getTopNCapitalCitiesInWorld(5));

        System.out.println("21. === Top 5 capital cities in continent 'Asia' ===");
        reports.printCapitals(reports.getTopNCapitalCitiesInContinent("Asia", 5));

        System.out.println("22. === Top 5 capital cities in region 'Southern Europe' ===");
        reports.printCapitals(reports.getTopNCapitalCitiesInRegion("Southern Europe", 5));

// ---------------- Populations ----------------
        System.out.println("23. === Population by Continent ===");
        reports.printPopulations(reports.getPopulationByContinent());

        System.out.println("24. === Population by Region ===");
        reports.printPopulations(reports.getPopulationByRegion());

        System.out.println("25. === Population by Country ===");
//        ArrayList<Population> popCountries = r.getPopulationByCountry();
//        if (popCountries.size() > 20) {
//            ArrayList<Population> top20 = new ArrayList<>(popCountries.subList(0, 20));
//            r.printPopulations(top20);
//        } else r.printPopulations(popCountries);
        reports.printPopulations(reports.getPopulationByCountry());

// single population access examples
        System.out.println("26. World population: " + reports.getWorldPopulation());
//
        System.out.println("27. === Population of continent Asia ===");
        Population asiaPop = reports.getPopulationOfContinentWithName("Asia");
        System.out.println(String.format("%-20s %-15s", "Continent", "Population"));
        System.out.println(String.format("%-20s %-15d", asiaPop.name, asiaPop.totalPopulation));

// 28. Region population
        System.out.println("28. === Population of region Eastern Asia ===");
        Population regionPop = reports.getPopulationOfRegionWithName("Eastern Asia");
        System.out.println(String.format("%-20s %-15s", "Region", "Population"));
        System.out.println(String.format("%-20s %-15d", regionPop.name, regionPop.totalPopulation));

// 29. Country population
        System.out.println("29. === Population of country Brazil ===");
        Population countryPop = reports.getPopulationOfCountryWithName("Brazil");
        System.out.println(String.format("%-20s %-15s", "Country", "Population"));
        System.out.println(String.format("%-20s %-15d", countryPop.name, countryPop.totalPopulation));

// 30. District population
        System.out.println("30. === Population of district São Paulo ===");
        Population districtPop = reports.getPopulationOfDistrictWithName("São Paulo");
        System.out.println(String.format("%-20s %-15s", "District", "Population"));
        System.out.println(String.format("%-20s %-15d", districtPop.name, districtPop.totalPopulation));

// 31. City population
        System.out.println("31. === Population of city Shanghai ===");
        Population cityPop = reports.getPopulationOfCityWithName("Shanghai");
        System.out.println(String.format("%-20s %-15s", "City", "Population"));
        System.out.println(String.format("%-20s %-15d", cityPop.name, cityPop.totalPopulation));


// ---------------- Languages ----------------
        System.out.println("32. === Language report (speakers and % of world) ===");
        reports.printLanguages(reports.getLanguageReport());

        // ---------------- Markdown Output ----------------
        System.out.println("Generating full Markdown report...");
        reports.outputAllReportsMarkdown("WorldReports.md");


        // disconnect
        reports.disconnect();
    }
}

