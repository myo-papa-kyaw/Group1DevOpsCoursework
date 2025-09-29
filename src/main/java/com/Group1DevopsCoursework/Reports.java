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

    // 1. Population living in cities vs not in cities in each continent
    public void getCityVsNonCityPopulationByContinent() {
        String sql = """
            SELECT country.Continent,
                   SUM(city.Population) AS CityPopulation,
                   SUM(country.Population - city.Population) AS NonCityPopulation
            FROM country
            JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Continent
        """;

        executePopulationSplitQuery(sql, "Population in Cities vs Non-Cities by Continent");
    }

    // 2. Population living in cities vs not in cities in each region
    public void getCityVsNonCityPopulationByRegion() {
        String sql = """
            SELECT country.Region,
                   SUM(city.Population) AS CityPopulation,
                   SUM(country.Population - city.Population) AS NonCityPopulation
            FROM country
            JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Region
        """;

        executePopulationSplitQuery(sql, "Population in Cities vs Non-Cities by Region");
    }

    // 3. Population living in cities vs not in cities in each country
    public void getCityVsNonCityPopulationByCountry() {
        String sql = """
            SELECT country.Name AS Country,
                   SUM(city.Population) AS CityPopulation,
                   SUM(country.Population - city.Population) AS NonCityPopulation
            FROM country
            JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Name
        """;

        executePopulationSplitQuery(sql, "Population in Cities vs Non-Cities by Country");
    }

    // Helper method for population split queries
    private void executePopulationSplitQuery(String sql, String title) {
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- " + title + " -----");
            System.out.printf("%-30s %-20s %-20s%n", "Location", "City Population", "Non-City Population");
            System.out.println("-".repeat(75));

            while (rs.next()) {
                String location = rs.getString(1);
                long cityPop = rs.getLong("CityPopulation");
                long nonCityPop = rs.getLong("NonCityPopulation");
                System.out.printf("%-30s %-20d %-20d%n", location, cityPop, nonCityPop);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching population split: " + e.getMessage());
        }
    }

    // 4. Top N populated cities in the world
    public void getTopNPopulatedCitiesWorld(int n) {
        String sql = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC
            LIMIT ?
        """;
        executeTopNCityQuery(sql, "Top " + n + " Populated Cities in the World", n);
    }

    // 5. Top N populated cities in a continent
    public void getTopNPopulatedCitiesContinent(String continent, int n) {
        String sql = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            LIMIT ?
        """;
        executeTopNCityQuery(sql, "Top " + n + " Populated Cities in " + continent, n, continent);
    }

    // 6. Top N populated cities in a region
    public void getTopNPopulatedCitiesRegion(String region, int n) {
        String sql = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?
        """;
        executeTopNCityQuery(sql, "Top " + n + " Populated Cities in " + region, n, null, region);
    }

    // 7. Top N populated cities in a country
    public void getTopNPopulatedCitiesCountry(String countryName, int n) {
        String sql = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC
            LIMIT ?
        """;
        executeTopNCityQuery(sql, "Top " + n + " Populated Cities in " + countryName, n, null, null, countryName);
    }

    // 8. Top N populated cities in a district
    public void getTopNPopulatedCitiesDistrict(String district, int n) {
        String sql = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC
            LIMIT ?
        """;
        executeTopNCityQuery(sql, "Top " + n + " Populated Cities in District: " + district, n, null, null, null, district);
    }

    // Helper for Top N city queries (with variable parameters)
    private void executeTopNCityQuery(String sql, String title, int n, String... params) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            int index = 1;
            for (String p : params) {
                if (p != null) {
                    ps.setString(index++, p);
                }
            }
            ps.setInt(index, n);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- " + title + " -----");
                System.out.printf("%-30s %-30s %-20s %-15s%n", "City", "Country", "District", "Population");
                System.out.println("-".repeat(95));

                while (rs.next()) {
                    System.out.printf("%-30s %-30s %-20s %-15d%n",
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getString("District"),
                            rs.getInt("Population"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching top N cities: " + e.getMessage());
        }
    }


}
