package com.Group1DevopsCoursework;

import java.sql.*;

public class Reports {
    private final Database_Connection db;

    public Reports(Database_Connection db) {
        this.db = db;
    }

    // ====================== COUNTRY REPORTS ======================

    // UC1: All countries in the world by population
    public void getAllCountries() {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country ORDER BY Population DESC");
    }

    // UC2: All countries in a continent
    public void getCountriesByContinent(String continent) {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country WHERE Continent = '" + continent + "' ORDER BY Population DESC");
    }

    // UC3: All countries in a region
    public void getCountriesByRegion(String region) {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country WHERE Region = '" + region + "' ORDER BY Population DESC");
    }

    // UC4: Top N countries (world)
    public void getTopNCountries(int n) {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country ORDER BY Population DESC LIMIT " + n);
    }

    // UC5: Top N countries in a continent
    public void getTopNCountriesByContinent(String continent, int n) {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country WHERE Continent = '" + continent + "' ORDER BY Population DESC LIMIT " + n);
    }

    // UC6: Top N countries in a region
    public void getTopNCountriesByRegion(String region, int n) {
        runCountryQuery("SELECT Code, Name, Continent, Region, Population FROM country WHERE Region = '" + region + "' ORDER BY Population DESC LIMIT " + n);
    }

    // ====================== CITY REPORTS ======================

    // UC7: All cities in the world
    public void getAllCities() {
        runCityQuery("SELECT Name, CountryCode, District, Population FROM city ORDER BY Population DESC");
    }

    // UC8: All cities in a continent
    public void getCitiesByContinent(String continent) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = '" + continent + "' ORDER BY city.Population DESC");
    }

    // UC9: All cities in a region
    public void getCitiesByRegion(String region) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = '" + region + "' ORDER BY city.Population DESC");
    }

    // UC10: All cities in a country
    public void getCitiesByCountry(String country) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = '" + country + "' ORDER BY city.Population DESC");
    }

    // UC11: All cities in a district
    public void getCitiesByDistrict(String district) {
        runCityQuery("SELECT Name, CountryCode, District, Population FROM city " +
                "WHERE District = '" + district + "' ORDER BY Population DESC");
    }

    // UC12: Top N cities (world)
    public void getTopNCities(int n) {
        runCityQuery("SELECT Name, CountryCode, District, Population FROM city ORDER BY Population DESC LIMIT " + n);
    }

    // UC13: Top N cities in a continent
    public void getTopNCitiesByContinent(String continent, int n) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = '" + continent + "' ORDER BY city.Population DESC LIMIT " + n);
    }

    // UC14: Top N cities in a region
    public void getTopNCitiesByRegion(String region, int n) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = '" + region + "' ORDER BY city.Population DESC LIMIT " + n);
    }

    // UC15: Top N cities in a country
    public void getTopNCitiesByCountry(String country, int n) {
        runCityQuery("SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = '" + country + "' ORDER BY city.Population DESC LIMIT " + n);
    }

    // UC16: Top N cities in a district
    public void getTopNCitiesByDistrict(String district, int n) {
        runCityQuery("SELECT Name, CountryCode, District, Population FROM city " +
                "WHERE District = '" + district + "' ORDER BY Population DESC LIMIT " + n);
    }

    // ====================== CAPITAL CITY REPORTS ======================

    // UC17: All capital cities (world)
    public void getAllCapitalCities() {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC");
    }

    // UC18: All capital cities in a continent
    public void getCapitalCitiesByContinent(String continent) {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "WHERE country.Continent = '" + continent + "' ORDER BY city.Population DESC");
    }

    // UC19: All capital cities in a region
    public void getCapitalCitiesByRegion(String region) {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "WHERE country.Region = '" + region + "' ORDER BY city.Population DESC");
    }

    // UC20: Top N capital cities (world)
    public void getTopNCapitalCities(int n) {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC LIMIT " + n);
    }

    // UC21: Top N capital cities in a continent
    public void getTopNCapitalCitiesByContinent(String continent, int n) {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "WHERE country.Continent = '" + continent + "' ORDER BY city.Population DESC LIMIT " + n);
    }

    // UC22: Top N capital cities in a region
    public void getTopNCapitalCitiesByRegion(String region, int n) {
        runCapitalCityQuery("SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON country.Capital = city.ID " +
                "WHERE country.Region = '" + region + "' ORDER BY city.Population DESC LIMIT " + n);
    }

    // ====================== POPULATION REPORTS ======================

    // 23. Population of people, people living in cities, and not in cities in each continent
    public void getPopulationSplitByContinent() {
        String sql =
                "SELECT c.Continent AS Name, " +
                        "SUM(c.Population) AS TotalPop, " +
                        "SUM(ci.Population) AS CityPop, " +
                        "SUM(c.Population) - SUM(ci.Population) AS NonCityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Continent ORDER BY TotalPop DESC";
        runPopulationSplitQuery(sql, "Continent");
    }

    //24. Population of people, people living in cities, and not in cities in each region
    public void getPopulationSplitByRegion() {
        String sql =
                "SELECT c.Region AS Name, " +
                        "SUM(c.Population) AS TotalPop, " +
                        "SUM(ci.Population) AS CityPop, " +
                        "SUM(c.Population) - SUM(ci.Population) AS NonCityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Region ORDER BY TotalPop DESC";
        runPopulationSplitQuery(sql, "Region");
    }

    //25. Population of people, people living in cities, and not in cities in each country
    public void getPopulationSplitByCountry() {
        String sql =
                "SELECT c.Name AS Name, " +
                        "c.Population AS TotalPop, " +
                        "SUM(ci.Population) AS CityPop, " +
                        "c.Population - SUM(ci.Population) AS NonCityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Code, c.Name, c.Population " +
                        "ORDER BY TotalPop DESC";
        runPopulationSplitQuery(sql, "Country");
    }

    // UC26a: Population of the world
    public long getWorldPopulation() {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country");
    }

    // UC26b: Population of a continent
    public long getPopulationOfContinent(String continent) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Continent = '" + continent + "'");
    }

    // UC26c: Population of a region
    public long getPopulationOfRegion(String region) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM country WHERE Region = '" + region + "'");
    }

    // UC26d: Population of a country
    public long getPopulationOfCountry(String country) {
        return getSinglePopulation("SELECT Population AS pop FROM country WHERE Name = '" + country + "'");
    }

    // UC26e: Population of a district
    public long getPopulationOfDistrict(String district) {
        return getSinglePopulation("SELECT SUM(Population) AS pop FROM city WHERE District = '" + district + "'");
    }

    // UC26f: Population of a city
    public long getPopulationOfCity(String city) {
        return getSinglePopulation("SELECT Population AS pop FROM city WHERE Name = '" + city + "'");
    }

    //27 Population of top  languages (Chinese, English, Hindi, Spanish, Arabic)
    public void getTopLanguages() {
        String sql =
                "SELECT cl.Language, " +
                        "       SUM(c.Population * cl.Percentage / 100) AS Speakers " +
                        "FROM countrylanguage cl " +
                        "JOIN country c ON cl.CountryCode = c.Code " +
                        "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                        "GROUP BY cl.Language " +
                        "ORDER BY Speakers DESC";

        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // First, get world population
            long worldPop = getWorldPopulations();

            System.out.printf("%-10s %-20s %-20s%n", "Language", "Speakers", "% of World Population");
            while (rs.next()) {
                String language = rs.getString("Language");
                long speakers = rs.getLong("Speakers");
                double percent = (speakers * 100.0) / worldPop;

                System.out.printf("%-10s %-20d %-20.2f%n",
                        language, speakers, percent);
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // UC28: Country report
    public void getCountryReport() {
        getAllCountries();
    }

    // UC29: City report
    public void getCityReport() {
        getAllCities();
    }

    // UC30: Capital city report
    public void getCapitalCityReport() {
        getAllCapitalCities();
    }
    // Population report for each continent
    public void getPopulationReportForContinent() {
        String sql =
                "SELECT c.Continent AS Name, " +
                        "       SUM(c.Population) AS TotalPop, " +
                        "       SUM(ci.Population) AS CityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Continent ORDER BY TotalPop DESC";
        runPopulationReport(sql, "Continent");
    }

    // Population report for each region
    public void getPopulationReportForRegion() {
        String sql =
                "SELECT c.Region AS Name, " +
                        "       SUM(c.Population) AS TotalPop, " +
                        "       SUM(ci.Population) AS CityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Region ORDER BY TotalPop DESC";
        runPopulationReport(sql, "Region");
    }

    // Population report for each country
    public void getPopulationReportForCountry() {
        String sql =
                "SELECT c.Name AS Name, " +
                        "       c.Population AS TotalPop, " +
                        "       SUM(ci.Population) AS CityPop " +
                        "FROM country c " +
                        "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                        "GROUP BY c.Code, c.Name, c.Population " +
                        "ORDER BY TotalPop DESC";
        runPopulationReport(sql, "Country");
    }

    // ====================== HELPER METHODS ======================

    private void runCountryQuery(String sql) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-5s %-40s %-20s %-25s %-15s%n", "Code", "Name", "Continent", "Region", "Population");
            while (rs.next()) {
                System.out.printf("%-5s %-40s %-20s %-25s %-15d%n",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void runCityQuery(String sql) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-25s %-10s %-20s %-15s%n", "Name", "Country", "District", "Population");
            while (rs.next()) {
                System.out.printf("%-25s %-10s %-20s %-15d%n",
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void runCapitalCityQuery(String sql) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-25s %-30s %-15s%n", "Capital City", "Country", "Population");
            while (rs.next()) {
                System.out.printf("%-25s %-30s %-15d%n",
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getInt("Population"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private long getSinglePopulation(String sql) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong("pop");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    private void runPopulationSplitQuery(String sql, String type) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-20s %-15s %-15s %-15s%n", type, "Total", "City", "Non-City");
            while (rs.next()) {
                System.out.printf("%-20s %-15d %-15d %-15d%n",
                        rs.getString("Name"),
                        rs.getLong("TotalPop"),
                        rs.getLong("CityPop"),
                        rs.getLong("NonCityPop"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public long getWorldPopulations() {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(Population) AS WorldPop FROM country");
            if (rs.next()) {
                return rs.getLong("WorldPop");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    private void runPopulationReport(String sql, String type) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-20s %-15s %-20s %-20s %-20s %-20s%n",
                    type, "TotalPop", "CityPop", "City %", "NonCityPop", "NonCity %");

            while (rs.next()) {
                String name = rs.getString("Name");
                long total = rs.getLong("TotalPop");
                long city = rs.getLong("CityPop");
                long nonCity = total - city;

                double cityPct = (total > 0) ? (city * 100.0 / total) : 0.0;
                double nonCityPct = (total > 0) ? (nonCity * 100.0 / total) : 0.0;

                System.out.printf("%-20s %-15d %-20d %-20.2f %-20d %-20.2f%n",
                        name, total, city, cityPct, nonCity, nonCityPct);
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
