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

    // Database connection object
    private Connection con = null;


    /**
     * @param location host:port of the MySQL instance (e.g., "localhost:3306")
     * @param delay    initial delay before the first attempt (currently unused)
     */

    public void connect(final String location, final int delay) {

        // Attempt to load MySQL JDBC driver so the application can communicate with MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);  // Fatal condition: driver missing
        }

        // Number of retry attempts if database is not ready
        final int retries = 15;

        for (int i = 0; i < retries; ++i) {
            try {
                // Inform the user of current retry attempt
                System.out.println("Waiting for database to be ready... attempt " + (i + 1));

                // Delay before attempting to connect (hardcoded 10 seconds)
                // Useful for Docker where DB startup may lag
                Thread.sleep(10000);

                // Attempt actual connection to the database
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root", "example"
                );

                System.out.println("✅ Successfully connected to database!");
                break; // Exit retry loop on success

            } catch (SQLException sqle) {
                // Database unreachable or still starting up
                System.out.println("❌ Database not ready yet (" + (i + 1) + "/" + retries + ")");
                System.out.println(sqle.getMessage());

            } catch (InterruptedException ie) {
                // Rare condition: thread sleep interrupted
                System.out.println("Thread interrupted during wait.");
            }
        }
    }

    /**
     * Safely closes the database connection if it is currently open.
     * <p>
     * This method prevents resource leaks by ensuring the application does not hold
     * open database connections after finishing its operations.
     */

    public void disconnect() {
        if (con != null) {
            try {
                con.close();  // Release DB connection
                System.out.println("Disconnected from database.");
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // ======================================================
    // ================ QUERY / REPORT METHODS =============
    // ======================================================

    // ----------------- COUNTRY REPORTS --------------------

    /**
     * Retrieves all countries in the world, ordered by population (highest first).
     * Includes country code, name, continent, region, population, and capital city name.
     *
     * @return a list of Country objects; an empty list if no results found.
     */

    public ArrayList<Country> getAllCountriesInWorld() {
        // Query returns all countries joined with their capital city name (LEFT JOIN keeps all countries)
        final String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            ORDER BY c.Population DESC;
            """;

        return runCountryQuery(sql);
    }

    /**
     * Retrieves countries belonging to a specific continent.
     * Results are sorted from largest to smallest population.
     *
     * @param continent the name of the continent
     * @return a filtered list of Country objects in that continent
     */

    public ArrayList<Country> getCountriesByContinent(final String continent) {
        final String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            WHERE c.Continent = ?
            ORDER BY c.Population DESC;
            """;

        // Use helper to bind continent parameter safely
        return runCountryQueryWithString(sql, continent);
    }

    /**
     * Retrieves countries belonging to a specific region.
     * Sorted by descending population.
     *
     * @param region the region name
     * @return a list of Country objects matching the region
     */

    public ArrayList<Country> getCountriesByRegion(final String region) {
        final String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            WHERE c.Region = ?
            ORDER BY c.Population DESC;
            """;

        return runCountryQueryWithString(sql, region);
    }

    /**
     * Retrieves the top N most populated countries in the world.
     *
     * @param toplimitednumber the maximum number of countries to return
     * @return the top N Country objects sorted by population
     */

    public ArrayList<Country> getTopNCountriesInWorld(final int toplimitednumber) {
        final String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            ORDER BY c.Population DESC
            LIMIT ?;
            """;


        return runCountryQueryWithInt(sql, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated countries in a specific continent.
     *
     * @param continent the continent to filter by
     * @param toplimitednumber number of results to return
     * @return list of Country objects limited to N entries
     */

    public ArrayList<Country> getTopNCountriesInContinent(final String continent, final int toplimitednumber) {
        final String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            WHERE c.Continent = ?
            ORDER BY c.Population DESC
            LIMIT ?;
            """;

        return runCountryQueryWithStringAndInt(sql, continent, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated countries in a specific region.
     *
     * @param region the region to filter
     * @param toplimitednumber the maximum number of results
     * @return a list of the top N countries within the region
     */

    public ArrayList<Country> getTopNCountriesInRegion(final String region, final int toplimitednumber) {
        final String sql = """
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

    /**
     * Retrieves all cities in the world, ordered by population (descending).
     *
     * @return list of City objects
     */

    public ArrayList<City> getAllCitiesInWorld() {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            ORDER BY ci.Population DESC;
            """;
        return runCityQuery(sql);
    }

    /**
     * Retrieves all cities located in a specific continent.
     *
     * @param continent continent name (e.g., "Asia")
     * @return list of cities within that continent
     */

    public ArrayList<City> getCitiesByContinent(final String continent) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC;
            """;
        return runCityQueryWithString(sql, continent);
    }

    /**
     * Retrieves all cities located in a specific region.
     *
     * @param region region name (e.g., "Western Europe")
     * @return list of cities within that region
     */

    public ArrayList<City> getCitiesByRegion(final String region) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Region = ?
            ORDER BY ci.Population DESC;
            """;
        return runCityQueryWithString(sql, region);
    }

    /**
     * Retrieves all cities within a specific country.
     *
     * @param countryName the name of the country
     * @return list of cities belonging to the specified country
     */

    public ArrayList<City> getCitiesByCountry(final String countryName) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Name = ?
            ORDER BY ci.Population DESC;
            """;
        return runCityQueryWithString(sql, countryName);
    }

    /**
     * Retrieves all cities located in a specific district.
     *
     * @param district district name
     * @return list of cities in that district
     */

    public ArrayList<City> getCitiesByDistrict(final String district) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE ci.District = ?
            ORDER BY ci.Population DESC;
            """;
        return runCityQueryWithString(sql, district);
    }

    /**
     * Retrieves the top N most populated cities in the world.
     *
     * @param toplimitednumber number of cities to return
     * @return list of the top N cities in the world
     */

    public ArrayList<City> getTopNCitiesInWorld(final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCityQueryWithInt(sql, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated cities within a continent.
     *
     * @param continent continent name
     * @param toplimitednumber limit of returned rows
     * @return list of top N cities in the continent
     */

    public ArrayList<City> getTopNCitiesInContinent(final String continent, final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCityQueryWithStringAndInt(sql, continent, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated cities within a region.
     *
     * @param region  region name
     * @param toplimitednumber number of cities to return
     * @return list of top N cities in the region
     */

    public ArrayList<City> getTopNCitiesInRegion(final String region, final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCityQueryWithStringAndInt(sql, region, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated cities within a specific country.
     *
     * @param countryName      country to filter by
     * @param toplimitednumber number of cities to return
     * @return list of top N cities in that country
     */

    public ArrayList<City> getTopNCitiesInCountry(final String countryName, final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Name = ?
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCityQueryWithStringAndInt(sql, countryName, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated cities within a specific district.
     *
     * @param district district name
     * @param toplimitednumber number of cities to return
     * @return list of top N cities in that district
     */

    public ArrayList<City> getTopNCitiesInDistrict(final String district, final int toplimitednumber) {
        final String sql = """
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

    /**
     * Retrieves all capital cities in the world, ordered by population (descending).
     * Each entry includes capital name, country name, and population.
     *
     * @return list of CapitalCity objects
     */

    public ArrayList<CapitalCity> getAllCapitalCitiesInWorld() {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            ORDER BY ci.Population DESC;
            """;
        return runCapitalQuery(sql);
    }

    /**
     * Retrieves all capital cities in a specific continent, ordered by population.
     *
     * @param continent continent name
     * @return list of capital cities in the continent
     */

    public ArrayList<CapitalCity> getCapitalCitiesByContinent(final String continent) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC;
            """;
        return runCapitalQueryWithString(sql, continent);
    }

    /**
     * Retrieves all capital cities in a specific region, sorted by population.
     *
     * @param region region name
     * @return list of capital cities in the region
     */

    public ArrayList<CapitalCity> getCapitalCitiesByRegion(final String region) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Region = ?
            ORDER BY ci.Population DESC;
            """;
        return runCapitalQueryWithString(sql, region);
    }

    /**
     * Retrieves the top N most populated capital cities in the world.
     *
     * @param toplimitednumber number of capitals to return
     * @return list of top N capital cities globally
     */

    public ArrayList<CapitalCity> getTopNCapitalCitiesInWorld(final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCapitalQueryWithInt(sql, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated capital cities within a continent.
     *
     * @param continent  continent name
     * @param toplimitednumber max number of results
     * @return list of top N capital cities in the continent
     */

    public ArrayList<CapitalCity> getTopNCapitalCitiesInContinent(final String continent, final int toplimitednumber) {
        final String sql = """
            SELECT ci.Name, co.Name AS Country, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
            LIMIT ?;
            """;
        return runCapitalQueryWithStringAndInt(sql, continent, toplimitednumber);
    }

    /**
     * Retrieves the top N most populated capital cities within a region.
     *
     * @param region region name
     * @param toplimitednumber number of capitals to return
     * @return list of top N capital cities in the region
     */

    public ArrayList<CapitalCity> getTopNCapitalCitiesInRegion(final String region, final int toplimitednumber) {
        final String sql = """
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

    /**
     * Retrieves aggregated population statistics for each continent.
     * Includes total population, city population, and computed non-city values.
     *
     * @return list of Population objects grouped by continent
     */

    public ArrayList<Population> getPopulationByContinent() {
        final String sql = """
            SELECT c.Continent AS name,
                   SUM(c.Population) AS totalPop,
                   IFNULL(SUM(ci.Population), 0) AS cityPop
            FROM country c
            LEFT JOIN city ci ON c.Code = ci.CountryCode
            GROUP BY c.Continent
            ORDER BY totalPop DESC;
            """;
        return runPopulationAggregationQuery(sql, "continent");
    }

    /**
     * Retrieves aggregated population statistics for each region.
     *
     * @return list of Population objects grouped by region
     */

    public ArrayList<Population> getPopulationByRegion() {
        final String sql = """
            SELECT c.Region AS name,
                   SUM(c.Population) AS totalPop,
                   IFNULL(SUM(ci.Population), 0) AS cityPop
            FROM country c
            LEFT JOIN city ci ON c.Code = ci.CountryCode
            GROUP BY c.Region
            ORDER BY totalPop DESC;
            """;
        return runPopulationAggregationQuery(sql, "region");
    }

    /**
     * Retrieves aggregated population statistics for each country.
     *
     * @return list of Population objects grouped by country
     */

    public ArrayList<Population> getPopulationByCountry() {
        final String sql = """
            SELECT c.Name AS name,
                   c.Population AS totalPop,
                   IFNULL(SUM(ci.Population), 0) AS cityPop
            FROM country c
            LEFT JOIN city ci ON c.Code = ci.CountryCode
            GROUP BY c.Code, c.Name, c.Population
            ORDER BY totalPop DESC;
            """;
        return runPopulationAggregationQuery(sql, "country");
    }

    /**
     * Retrieves the total population of the world.
     *
     * @return the sum of all country populations
     */

    public long getWorldPopulation() {
        final String sql = "SELECT SUM(Population) AS worldPop FROM country;";
        long worldPopulation = 0L;

        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {

            if (result.next()) {
                worldPopulation = result.getLong("worldPop");
            }

        } catch (SQLException e) {
            System.out.println("Error getting world population: " + e.getMessage());
        }

        return worldPopulation;
    }

    /**
     * Retrieves total population for a specific continent.
     *
     * @param continent the continent name
     * @return Population object with total population value
     */

    public Population getPopulationOfContinentWithName(final String continent) {
        final Population totalpopulation = new Population();
        totalpopulation.name = continent;

        final String sql = "SELECT SUM(Population) AS pop FROM country WHERE Continent = ?;";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet result = pstmt.executeQuery()) {
                totalpopulation.totalPopulation = result.next() ?
                        result.getLong("pop") : 0L;
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of continent: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }

        return totalpopulation;
    }

    /**
     * Retrieves total population for a specific region.
     *
     * @param region region name
     * @return Population object for that region
     */

    public Population getPopulationOfRegionWithName(final String region) {
        final Population totalpopulation = new Population();
        totalpopulation.name = region;

        final String sql = "SELECT SUM(Population) AS pop FROM country WHERE Region = ?;";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
            try (ResultSet result = pstmt.executeQuery()) {
                totalpopulation.totalPopulation = result.next() ?
                        result.getLong("pop") : 0L;
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of region: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }

        return totalpopulation;
    }

    /**
     * Retrieves the population of a specific country.
     *
     * @param country country name
     * @return Population object representing that country
     */

    public Population getPopulationOfCountryWithName(final String country) {
        final Population totalpopulation = new Population();
        totalpopulation.name = country;

        final String sql = "SELECT Population AS pop FROM country WHERE Name = ? LIMIT 1;";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, country);
            try (ResultSet result = pstmt.executeQuery()) {
                totalpopulation.totalPopulation = result.next() ?
                        result.getLong("pop") : 0L;
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of country: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }

        return totalpopulation;
    }

    /**
     * Retrieves the total population of a specific district.
     *
     * @param district district name
     * @return Population object containing district population
     */

    public Population getPopulationOfDistrictWithName(final String district) {
        final Population totalpopulation = new Population();
        totalpopulation.name = district;

        final String sql = "SELECT SUM(Population) AS pop FROM city WHERE District = ?;";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet result = pstmt.executeQuery()) {
                totalpopulation.totalPopulation = result.next() ?
                        result.getLong("pop") : 0L;
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of district: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }

        return totalpopulation;
    }

    /**
     * Retrieves the population of a specific city.
     *
     * @param city city name
     * @return Population object containing city population
     */

    public Population getPopulationOfCityWithName(final String city) {
        final Population totalpopulation = new Population();
        totalpopulation.name = city;

        final String sql = "SELECT Population AS pop FROM city WHERE Name = ? LIMIT 1;";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, city);
            try (ResultSet result = pstmt.executeQuery()) {
                totalpopulation.totalPopulation = result.next() ?
                        result.getLong("pop") : 0L;
            }
        } catch (SQLException e) {
            System.out.println("Error getting population of city: " + e.getMessage());
            totalpopulation.totalPopulation = 0L;
        }

        return totalpopulation;
    }

    // ----------------- LANGUAGE REPORT ---------------------

    /**
     * @return list of Language objects containing name, total speakers, and world percentage
     */
    public ArrayList<Language> getLanguageReport() {
        final String sql = """
            SELECT cl.Language AS language,
                   SUM((c.Population * cl.Percentage) / 100.0) AS speakers
            FROM countrylanguage cl
            JOIN country c ON cl.CountryCode = c.Code
            WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
            GROUP BY cl.Language
            ORDER BY speakers DESC;
            """;

        final ArrayList<Language> list = new ArrayList<>();
        final long worldPop = getWorldPopulation();

        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {

            while (result.next()) {
                final Language language = new Language();

                language.language = result.getString("language");

                // Raw speakers value may include fractional people; convert to whole number
                final double speakersD = result.getDouble("speakers");
                language.speakers = Math.round(speakersD);

                // Calculate percentage of world population
                if (worldPop > 0) {
                    language.percentage = (speakersD / (double) worldPop) * 100.0;
                } else {
                    language.percentage = 0.0;
                }

                list.add(language);
            }
        } catch (SQLException e) {
            System.out.println("Error getting language report: " + e.getMessage());
        }

        return list;
    }

    /**
     * Generates a complete Markdown report containing all country, city, capital,
     * population, and language reports.
     * @param filename the name of the Markdown file to write
     */

    public void outputAllReportsMarkdown(final String filename) {

        // Main Markdown builder used to accumulate the entire report content
        final StringBuilder stringbuilder = new StringBuilder();

        // Report header and introductory information
        stringbuilder.append("# World Population Reports\n\n");
        stringbuilder.append("### Group 1 — DevOps Coursework Team Project\n\n");
        stringbuilder.append("This report was collaboratively created by **Group 1** as part of our DevOps coursework.");

        try {
            // Ensure the output directory exists
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

            // ---------------- Population Aggregates ----------------
            stringbuilder.append("## 23. Population by Continent\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByContinent());

            stringbuilder.append("## 24. Population by Region\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByRegion());

            stringbuilder.append("## 25. Population by Country\n\n");
            appendPopulationsMarkdown(stringbuilder, getPopulationByCountry());

            // ---------------- Individual Population Reports ----------------

            stringbuilder.append("## 26. World Population\n\n");
            stringbuilder.append("| Area | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| World | ").append(getWorldPopulation()).append(" |\r\n\n");

            stringbuilder.append("## 27. Population of Continent: Asia\n\n");
            stringbuilder.append("| Continent | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Asia | ").append(getPopulationOfContinentWithName("Asia").totalPopulation).append(" |\r\n\n");

            stringbuilder.append("## 28. Population of Region: Eastern Asia\n\n");
            stringbuilder.append("| Region | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Eastern Asia | ").append(getPopulationOfRegionWithName("Eastern Asia").totalPopulation).append(" |\r\n\n");

            stringbuilder.append("## 29. Population of Country: Brazil\n\n");
            stringbuilder.append("| Country | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Brazil | ").append(getPopulationOfCountryWithName("Brazil").totalPopulation).append(" |\r\n\n");

            stringbuilder.append("## 30. Population of District: São Paulo\n\n");
            stringbuilder.append("| District | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| São Paulo | ").append(getPopulationOfDistrictWithName("São Paulo").totalPopulation).append(" |\r\n\n");

            stringbuilder.append("## 31. Population of City: Shanghai\n\n");
            stringbuilder.append("| City | Population |\r\n| --- | --- |\r\n");
            stringbuilder.append("| Shanghai | ").append(getPopulationOfCityWithName("Shanghai").totalPopulation).append(" |\r\n\n");

            // ---------------- Language Report ----------------
            stringbuilder.append("## 32. Language Report\n\n");
            appendLanguagesMarkdown(stringbuilder, getLanguageReport());

            // ---------------- Save File ----------------
            final BufferedWriter writer =
                    new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(stringbuilder.toString());
            writer.close();

            System.out.println("Markdown report written to ./reports/" + filename);

        } catch (IOException e) {
            System.out.println("Error writing markdown report: " + e.getMessage());
        }
    }
// ---------- Helper methods for markdown formatting ----------

    /**
     * Appends a Markdown-formatted table of countries to the report builder.
     * Each row represents a single country with its code, name, continent,
     * region, population, and capital.
     *
     * @param stringbuilder the active Markdown builder to append to
     * @param countries     list of Country objects to format
     */

    public void appendCountriesMarkdown(final StringBuilder stringbuilder, final ArrayList<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            stringbuilder.append("_No countries found._\n\n");
            return;
        }
        stringbuilder.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        stringbuilder.append("| --- | --- | --- | --- | --- | --- |\r\n");

        for (final Country c : countries) {
            if (c == null) continue;
            stringbuilder.append("| ").append(c.code).append(" | ").append(c.name)
                    .append(" | ").append(c.continent).append(" | ").append(c.region)
                    .append(" | ").append(c.population).append(" | ").append(c.capital).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /**
     * Appends a Markdown-formatted table of cities to the report builder.
     * Each row displays a city with its name, country, district, and population.
     *
     * @param stringbuilder the active Markdown builder to append to
     * @param cities        list of City objects to format
     */

    public void appendCitiesMarkdown(final StringBuilder stringbuilder, final ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            stringbuilder.append("_No cities found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Country | District | Population |\r\n");
        stringbuilder.append("| --- | --- | --- | --- |\r\n");

        for (final City ci : cities) {
            if (ci == null) continue;
            stringbuilder.append("| ").append(ci.name).append(" | ").append(ci.country)
                    .append(" | ").append(ci.district).append(" | ").append(ci.population).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /**
     * Appends a Markdown-formatted table of capital cities to the report builder.
     * Each row represents a capital city along with its country and population.
     *
     * @param stringbuilder the active Markdown builder to append to
     * @param capitals      list of CapitalCity objects to format
     */

    public void appendCapitalsMarkdown(final StringBuilder stringbuilder, final ArrayList<CapitalCity> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            stringbuilder.append("_No capitals found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Country | Population |\r\n");
        stringbuilder.append("| --- | --- | --- |\r\n");

        for (final CapitalCity cap : capitals) {
            if (cap == null) continue;
            stringbuilder.append("| ").append(cap.name).append(" | ").append(cap.country)
                    .append(" | ").append(cap.population).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /**
     * Appends a Markdown-formatted table of population statistics to the report builder.
     * Each row includes total population, city population, and percentage breakdowns
     * for the specified geographic level (continent, region, or country).
     *
     * @param stringbuilder the active Markdown builder to append to
     * @param populations   list of Population objects to format
     */

    public void appendPopulationsMarkdown(final StringBuilder stringbuilder, final ArrayList<Population> populations) {
        if (populations == null || populations.isEmpty()) {
            stringbuilder.append("_No population data found._\n\n");
            return;
        }
        stringbuilder.append("| Name | Total Population | City Population | % in Cities | % not in Cities |\r\n");
        stringbuilder.append("| --- | --- | --- | --- | --- |\r\n");

        for (final Population p : populations) {
            if (p == null) continue;
            stringbuilder.append("| ").append(p.name).append(" | ")
                    .append(p.totalPopulation).append(" | ").append(p.cityPopulation)
                    .append(" | ").append(String.format("%.2f", p.cityPercentage))
                    .append(" | ").append(String.format("%.2f", p.nonCityPercentage)).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    /**
     * Appends a Markdown-formatted table of languages, including total speakers
     * and the percentage of the world population for each language.
     *
     * @param stringbuilder the active Markdown builder to append to
     * @param languages     list of Language objects to format
     */

    public void appendLanguagesMarkdown(final StringBuilder stringbuilder, final ArrayList<Language> languages) {
        if (languages == null || languages.isEmpty()) {
            stringbuilder.append("_No language data found._\n\n");
            return;
        }
        stringbuilder.append("| Language | Speakers | % of World Population |\r\n");
        stringbuilder.append("| --- | --- | --- |\r\n");

        for (final Language l : languages) {
            if (l == null) continue;
            stringbuilder.append("| ").append(l.language).append(" | ").append(l.speakers)
                    .append(" | ").append(String.format("%.2f%%", l.percentage)).append(" |\r\n");
        }
        stringbuilder.append("\n");
    }

    // ======================================================
    // ================ RUNNER & PRINT HELPERS =============
    // ======================================================

    /**
     * Executes a country-related SQL query without parameters and maps all results
     * into Country objects.
     *
     * @param sql the SQL query to execute
     * @return a list of populated Country objects
     */

    private ArrayList<Country> runCountryQuery(final String sql) {
        final ArrayList<Country> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                final Country country = new Country();
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

    /**
     * Executes a country-related SQL query that contains one string parameter,
     * such as continent or region filters.
     *
     * @param sql the SQL query to execute
     * @param param string parameter to bind to the query
     * @return a filtered list of Country objects
     */

    private ArrayList<Country> runCountryQueryWithString(final String sql, final String param) {
        final ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final Country country = new Country();
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

    /**
     * Executes a country query that expects a single integer parameter
     * (e.g., LIMIT for top-N queries).
     *
     * @param sql the SQL query to execute
     * @param toplimitednumber  integer parameter for limiting results
     * @return a list of Country objects
     */

    private ArrayList<Country> runCountryQueryWithInt(final String sql, final int toplimitednumber) {
        final ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final Country country = new Country();
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

    /**
     * Executes a country query that includes both a string filter and an integer
     * limit parameter, used for queries such as “top N countries in a region”.
     *
     * @param sql the SQL query to execute
     * @param string filter parameter (continent, region, etc.)
     * @param toplimitednumber  number of results to return
     * @return a list of Country objects
     */

    private ArrayList<Country> runCountryQueryWithStringAndInt(final String sql, final String string, final int toplimitednumber) {
        final ArrayList<Country> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final Country country = new Country();
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

    /**
     * Executes a city query without parameters and maps results to City objects.
     *
     * @param sql the SQL query to execute
     * @return list of City objects
     */

    private ArrayList<City> runCityQuery(final String sql) {
        final ArrayList<City> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                final City city = new City();
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

    /**
     * Executes a city query that includes one string parameter (e.g., region or country filters).
     *
     * @param sql the SQL query
     * @param param filter to apply
     * @return list of City objects
     */

    private ArrayList<City> runCityQueryWithString(final String sql, final String param) {
        final ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final City city = new City();
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
    /**
     * Executes a city query that applies an integer parameter such as LIMIT.
     *
     * @param sql the SQL query
     * @param toplimitednumber limit on returned rows
     * @return list of City objects
     */
    private ArrayList<City> runCityQueryWithInt(final String sql, final int toplimitednumber) {
        final ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final City city = new City();
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

    /**
     * Executes a city query that includes both a string filter and an integer limit.
     *
     * @param sql  the SQL query
     * @param string filter value
     * @param toplimitednumber limit value
     * @return list of City objects
     */
    private ArrayList<City> runCityQueryWithStringAndInt(final String sql, final String string, final int toplimitednumber) {
        final ArrayList<City> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final City city = new City();
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

    /**
     * Executes a capital city query without parameters.
     *
     * @param sql the SQL query
     * @return list of CapitalCity objects
     */
    private ArrayList<CapitalCity> runCapitalQuery(final String sql) {
        final ArrayList<CapitalCity> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                final CapitalCity capitalcity = new CapitalCity();
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

    /**
     * Executes a capital query with a string filter (continent or region).
     *
     * @param sql the SQL query
     * @param param filter value
     * @return list of CapitalCity objects
     */
    private ArrayList<CapitalCity> runCapitalQueryWithString(final String sql, final String param) {
        final ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, param);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final CapitalCity capitalcity = new CapitalCity();
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
    /**
     * Executes a capital query with an integer limit parameter.
     *
     * @param sql the SQL query
     * @param toplimitednumber maximum number of rows
     * @return list of CapitalCity objects
     */
    private ArrayList<CapitalCity> runCapitalQueryWithInt(final String sql, final int toplimitednumber) {
        final ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final CapitalCity capitalcity = new CapitalCity();
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

    /**
     * Executes a capital query combining a string filter and an integer limit.
     *
     * @param sql the SQL query
     * @param string filter parameter
     * @param toplimitednumber limit parameter
     * @return list of CapitalCity objects
     */
    private ArrayList<CapitalCity> runCapitalQueryWithStringAndInt(final String sql, final String string, final int toplimitednumber) {
        final ArrayList<CapitalCity> list = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, string);
            pstmt.setInt(2, toplimitednumber);
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    final CapitalCity capitalcity = new CapitalCity();
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

    /**
     * Executes population aggregation queries for continents, regions, or countries.
     * The method calculates total population, population living in cities, and
     * percentages for each grouping level.
     *
     * @param sql   the SQL aggregation query
     * @param level a descriptive label for the query context (continent, region, etc.)
     * @return list of Population objects containing aggregated results
     */
    private ArrayList<Population> runPopulationAggregationQuery(final String sql, final String level) {
        final ArrayList<Population> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            while (result.next()) {
                final Population population = new Population();
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
    public void printCountries(final ArrayList<Country> countries) {
        if (countries == null) {
            System.out.println("No countries");
            return;
        }
        System.out.println(String.format("%-10s %-20s %-20s %-20s %-15s %-10s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));
        for (final Country c : countries) {
            if (c == null) {
                continue;
            }
            final String output = String.format("%-10s %-20s %-20s %-20s %-15d %-10s",
                    c.code, c.name, c.continent, c.region, c.population, c.capital);
            System.out.println(output);
        }
    }


    /**
     * Prints a list of City reports.
     *
     * @param cities The list of cities to print.
     */
    public void printCities(final ArrayList<City> cities) {
        if (cities == null) {
            System.out.println("No cities");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-20s %-15s",
                "Name", "Country", "District", "Population"));
        for (final City ci : cities) {
            if (ci == null) {
                continue;
            }
            final String output = String.format("%-20s %-20s %-20s %-15d",
                    ci.name, ci.country, ci.district, ci.population);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of Capital City reports.
     *
     * @param capitals The list of capital cities to print.
     */
    public void printCapitals(final ArrayList<CapitalCity> capitals) {
        if (capitals == null) {
            System.out.println("No capital cities");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-15s",
                "Name", "Country", "Population"));
        for (final CapitalCity cap : capitals) {
            if (cap == null) {
                continue;
            }
            final String output = String.format("%-20s %-20s %-15d",
                    cap.name, cap.country, cap.population);
            System.out.println(output);
        }
    }

    /**
     * Prints a list of Population reports.
     *
     * @param populations The list of populations to print.
     */
    public void printPopulations(final ArrayList<Population> populations) {
        if (populations == null) {
            System.out.println("No population data");
            return;
        }
        System.out.println(String.format("%-30s %-15s %-15s %-10s %-10s",
                "Name", "Total Population", "City Population", "% in Cities", "% not in Cities"));
        for (final Population p : populations) {
            if (p == null) {
                continue;
            }
            final String output = String.format("%-30s %-15d %-15d %-10.2f %-10.2f",
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
    public void printLanguages(final ArrayList<Language> languages) {
        if (languages == null) {
            System.out.println("No languages");
            return;
        }
        System.out.println(String.format("%-20s %-20s %-20s",
                "Language", "Speakers", "% of World Population"));
        for (final Language l : languages) {
            if (l == null) {
                continue;
            }
            final String output = String.format("%-20s %-20d %-20.2f%%", l.language, l.speakers, l.percentage);
            System.out.println(output);

        }


    }

    public void addCountry(final Country country) {
        final String sql = """
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

    public Country getCountryByCode(final String code) {
        final String sql = "SELECT Code, Name, Continent, Region, Population FROM country WHERE Code = ? LIMIT 1;";
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

    public static void main(final String[] args) {
        final Reports reports = new Reports();

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
        final Population asiaPop = reports.getPopulationOfContinentWithName("Asia");
        System.out.println(String.format("%-20s %-15s", "Continent", "Population"));
        System.out.println(String.format("%-20s %-15d", asiaPop.name, asiaPop.totalPopulation));

// 28. Region population
        System.out.println("28. === Population of region Eastern Asia ===");
        final Population regionPop = reports.getPopulationOfRegionWithName("Eastern Asia");
        System.out.println(String.format("%-20s %-15s", "Region", "Population"));
        System.out.println(String.format("%-20s %-15d", regionPop.name, regionPop.totalPopulation));

// 29. Country population
        System.out.println("29. === Population of country Brazil ===");
        final Population countryPop = reports.getPopulationOfCountryWithName("Brazil");
        System.out.println(String.format("%-20s %-15s", "Country", "Population"));
        System.out.println(String.format("%-20s %-15d", countryPop.name, countryPop.totalPopulation));

// 30. District population
        System.out.println("30. === Population of district São Paulo ===");
        final Population districtPop = reports.getPopulationOfDistrictWithName("São Paulo");
        System.out.println(String.format("%-20s %-15s", "District", "Population"));
        System.out.println(String.format("%-20s %-15d", districtPop.name, districtPop.totalPopulation));

// 31. City population
        System.out.println("31. === Population of city Shanghai ===");
        final Population cityPop = reports.getPopulationOfCityWithName("Shanghai");
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

