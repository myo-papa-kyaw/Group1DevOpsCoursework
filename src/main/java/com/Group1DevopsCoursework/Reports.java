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

    // -------------------------
    // Country Reports (1-6)
    // -------------------------

    // 1. All countries in the world ordered by population descending
    public void getAllCountries() {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC";
        printCountryReport(sql);
    }

    // 2. All countries in a continent ordered by population descending
    public void getCountriesByContinent(String continent) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? ORDER BY c.Population DESC";
        printCountryReport(sql, continent);
    }

    // 3. All countries in a region ordered by population descending
    public void getCountriesByRegion(String region) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? ORDER BY c.Population DESC";
        printCountryReport(sql, region);
    }

    // 4. Top N populated countries in the world
    public void getTopNCountries(int n) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC LIMIT ?";
        printCountryReportWithLimit(sql, n);
    }

    // 5. Top N populated countries in a continent
    public void getTopNCountriesByContinent(String continent, int n) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? ORDER BY c.Population DESC LIMIT ?";
        printCountryReportWithLimit(sql, continent, n);
    }

    // 6. Top N populated countries in a region
    public void getTopNCountriesByRegion(String region, int n) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS CapitalName " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? ORDER BY c.Population DESC LIMIT ?";
        printCountryReportWithLimit(sql, region, n);
    }

    // -------------------------
    // City Reports (7-16)
    // -------------------------

    // 7. All the cities in the world organised by largest population to smallest.
    public void getAllCities() {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "ORDER BY ci.Population DESC";
        printCityReport(sql);
    }

    // 8. All the cities in a continent organised by largest population to smallest.
    public void getCitiesByContinent(String continent) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Continent = ? ORDER BY ci.Population DESC";
        printCityReport(sql, continent);
    }

    // 9. All the cities in a region organised by largest population to smallest.
    public void getCitiesByRegion(String region) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Region = ? ORDER BY ci.Population DESC";
        printCityReport(sql, region);
    }

    // 10. All the cities in a country organised by largest population to smallest.
    public void getCitiesByCountry(String countryCode) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE ci.CountryCode = ? ORDER BY ci.Population DESC";
        printCityReport(sql, countryCode);
    }

    // 11. All the cities in a district organised by largest population to smallest.
    public void getCitiesByDistrict(String district) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE ci.District = ? ORDER BY ci.Population DESC";
        printCityReport(sql, district);
    }

    // 12. Top N populated cities in the world
    public void getTopNCities(int n) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "ORDER BY ci.Population DESC LIMIT ?";
        printCityReportWithLimit(sql, n);
    }

    // 13. Top N populated cities in a continent
    public void getTopNCitiesByContinent(String continent, int n) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, " +
                "c.Continent, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Continent = ? " +
                "ORDER BY ci.Population DESC LIMIT ?";
        printCityReportWithLimit(sql, continent, n);
    }

    // 14. Top N populated cities in a region
    public void getTopNCitiesByRegion(String region, int n) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Region = ? ORDER BY ci.Population DESC LIMIT ?";
        printCityReportWithLimit(sql, region, n);
    }

    // 15. Top N populated cities in a country
    public void getTopNCitiesByCountry(String countryCode, int n) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE ci.CountryCode = ? ORDER BY ci.Population DESC LIMIT ?";
        printCityReportWithLimit(sql, countryCode, n);
    }

    // 16. Top N populated cities in a district
    public void getTopNCitiesByDistrict(String district, int n) {
        String sql = "SELECT ci.ID, ci.Name AS CityName, ci.District, c.Name AS CountryName, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE ci.District = ? ORDER BY ci.Population DESC LIMIT ?";
        printCityReportWithLimit(sql, district, n);
    }

    // -------------------------
    // Capital City Reports (17-22)
    // -------------------------

    // 17. All the capital cities in the world organised by largest population to smallest.
    public void getAllCapitalCities() {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY ci.Population DESC";
        printCapitalReport(sql);
    }

    // 18. All the capital cities in a continent organised by largest population to smallest.
    public void getCapitalCitiesByContinent(String continent) {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? ORDER BY ci.Population DESC";
        printCapitalReport(sql, continent);
    }

    // 19. All the capital cities in a region organised by largest to smallest.
    public void getCapitalCitiesByRegion(String region) {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? ORDER BY ci.Population DESC";
        printCapitalReport(sql, region);
    }

    // 20. Top N populated capital cities in the world
    public void getTopNCapitalCities(int n) {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY ci.Population DESC LIMIT ?";
        printCapitalReportWithLimit(sql, n);
    }

    // 21. Top N populated capital cities in a continent
    public void getTopNCapitalCitiesByContinent(String continent, int n) {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? ORDER BY ci.Population DESC LIMIT ?";
        printCapitalReportWithLimit(sql, continent, n);
    }

    // 22. Top N populated capital cities in a region
    public void getTopNCapitalCitiesByRegion(String region, int n) {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? ORDER BY ci.Population DESC LIMIT ?";
        printCapitalReportWithLimit(sql, region, n);
    }

    // -------------------------
    // Population summaries (23-26)
    // -------------------------

    // 23. The population of people, people living in cities, and people not living in cities in each continent.
    public void getPopulationSummaryByContinent() {
        String sql = "SELECT Continent, SUM(Population) AS TotalPopulation FROM country GROUP BY Continent";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-20s %-15s %-20s %-20s%n", "Continent", "TotalPop", "InCities (num, %)", "NotInCities (num, %)");
            while (rs.next()) {
                String continent = rs.getString("Continent");
                long total = rs.getLong("TotalPopulation");
                long inCities = getCityPopulationForContinent(continent);
                long notInCities = total - inCities;
                double inPct = total == 0 ? 0 : (inCities * 100.0 / total);
                double notPct = total == 0 ? 0 : (notInCities * 100.0 / total);
                System.out.printf("%-20s %-15d %-10d (%.2f%%) %-10d (%.2f%%)%n", continent, total, inCities, inPct, notInCities, notPct);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching continent population summary: " + e.getMessage());
        }
    }

    // 24. By region
    public void getPopulationSummaryByRegion() {
        String sql = "SELECT Region, SUM(Population) AS TotalPopulation FROM country GROUP BY Region";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-40s %-15s %-20s %-20s%n", "Region", "TotalPop", "InCities (num, %)", "NotInCities (num, %)");
            while (rs.next()) {
                String region = rs.getString("Region");
                long total = rs.getLong("TotalPopulation");
                long inCities = getCityPopulationForRegion(region);
                long notInCities = total - inCities;
                double inPct = total == 0 ? 0 : (inCities * 100.0 / total);
                double notPct = total == 0 ? 0 : (notInCities * 100.0 / total);
                System.out.printf("%-40s %-15d %-10d (%.2f%%) %-10d (%.2f%%)%n", region, total, inCities, inPct, notInCities, notPct);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching region population summary: " + e.getMessage());
        }
    }

    // 25. By country
    public void getPopulationSummaryByCountry() {
        String sql = "SELECT Code, Name, Population FROM country ORDER BY Population DESC";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-10s %-40s %-15s %-20s %-20s%n", "Code", "Country", "TotalPop", "InCities (num, %)", "NotInCities (num, %)");
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                long total = rs.getLong("Population");
                long inCities = getCityPopulationForCountry(code);
                long notInCities = total - inCities;
                double inPct = total == 0 ? 0 : (inCities * 100.0 / total);
                double notPct = total == 0 ? 0 : (notInCities * 100.0 / total);
                System.out.printf("%-10s %-40s %-15d %-10d (%.2f%%) %-10d (%.2f%%)%n", code, name, total, inCities, inPct, notInCities, notPct);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching country population summary: " + e.getMessage());
        }
    }

    // 26. Additional single-entity population fetches
    public long getWorldPopulation() {
        String sql = "SELECT SUM(Population) AS WorldPop FROM country";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getLong("WorldPop");
        } catch (SQLException e) {
            System.out.println("Error getting world population: " + e.getMessage());
        }
        return 0;
    }

    public long getPopulationOfContinent(String continent) {
        String sql = "SELECT SUM(Population) AS Pop FROM country WHERE Continent = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("Pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting continent population: " + e.getMessage());
        }
        return 0;
    }

    public long getPopulationOfRegion(String region) {
        String sql = "SELECT SUM(Population) AS Pop FROM country WHERE Region = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, region);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("Pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting region population: " + e.getMessage());
        }
        return 0;
    }

    public long getPopulationOfCountry(String countryCode) {
        String sql = "SELECT Population FROM country WHERE Code = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, countryCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("Population");
            }
        } catch (SQLException e) {
            System.out.println("Error getting country population: " + e.getMessage());
        }
        return 0;
    }

    public long getPopulationOfDistrict(String district) {
        String sql = "SELECT SUM(Population) AS Pop FROM city WHERE District = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, district);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("Pop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting district population: " + e.getMessage());
        }
        return 0;
    }

    public long getPopulationOfCity(String cityName) {
        String sql = "SELECT Population FROM city WHERE Name = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, cityName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("Population");
            }
        } catch (SQLException e) {
            System.out.println("Error getting city population: " + e.getMessage());
        }
        return 0;
    }

    // -------------------------
    // Language speaker counts (27)
    // -------------------------
    public void getTopLanguages() {
        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
        long worldPop = getWorldPopulation();
        System.out.printf("%-10s %-20s %-10s%n", "Language", "Speakers", "% of World");
        for (String lang : languages) {
            String sql = "SELECT SUM(c.Population * (cl.Percentage/100.0)) AS Speakers " +
                    "FROM countrylanguage cl JOIN country c ON cl.CountryCode = c.Code " +
                    "WHERE cl.Language = ?";
            try (PreparedStatement ps = db.con.prepareStatement(sql)) {
                ps.setString(1, lang);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        long speakers = Math.round(rs.getDouble("Speakers"));
                        double pct = worldPop == 0 ? 0 : (speakers * 100.0 / worldPop);
                        System.out.printf("%-10s %-20d %-10.2f%%%n", lang, speakers, pct);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error fetching language data for " + lang + ": " + e.getMessage());
            }
        }
    }

    // -------------------------
    // Additional Reports (28-31)
    // -------------------------
    public void getCountryReport() {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-8s %-35s %-15s %-25s %-15s %-30s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");

            while (rs.next()) {
                System.out.printf("%-8s %-35s %-15s %-25s %-15d %-30s%n",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getString("Capital"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCityReport() {
        String sql = "SELECT ci.Name AS CityName, c.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "ORDER BY ci.Population DESC";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-35s %-30s %-25s %-15s%n",
                    "City", "Country", "District", "Population");

            while (rs.next()) {
                System.out.printf("%-35s %-30s %-25s %-15d%n",
                        rs.getString("CityName"),
                        rs.getString("CountryName"),
                        rs.getString("District"),
                        rs.getInt("Population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCapitalCityReport() {
        String sql = "SELECT ci.Name AS CapitalName, c.Name AS CountryName, ci.Population " +
                "FROM country c JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY ci.Population DESC";
        try (PreparedStatement ps = db.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-35s %-30s %-15s%n",
                    "Capital City", "Country", "Population");

            while (rs.next()) {
                System.out.printf("%-35s %-30s %-15d%n",
                        rs.getString("CapitalName"),
                        rs.getString("CountryName"),
                        rs.getInt("Population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Population report for a single continent
    // Population report for a single continent
    public void getPopulationReportForContinent(String continent) {
        long total = getPopulationOfContinent(continent);
        long inCities = getCityPopulationForContinent(continent);
        long notInCities = total - inCities;
        double inPct = total == 0 ? 0 : (inCities * 100.0 / total);
        double notPct = total == 0 ? 0 : (notInCities * 100.0 / total);

        System.out.printf("%-20s %-15s %-25s %-25s%n", "Continent", "TotalPop", "CityPop (% of total)", "NonCityPop (% of total)");
        System.out.printf("%-20s %-15d %-25s %-25s%n",
                continent,
                total,
                inCities + " (" + String.format("%.2f", inPct) + "%)",
                notInCities + " (" + String.format("%.2f", notPct) + "%)");
    }

    // -------------------------
    // Helper methods for population calculations
    // -------------------------

    private long getCityPopulationForContinent(String continent) {
        String sql = "SELECT SUM(ci.Population) AS CityPop " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Continent = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("CityPop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting city population for continent: " + e.getMessage());
        }
        return 0;
    }

    private long getCityPopulationForRegion(String region) {
        String sql = "SELECT SUM(ci.Population) AS CityPop " +
                "FROM city ci JOIN country c ON ci.CountryCode = c.Code " +
                "WHERE c.Region = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, region);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("CityPop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting city population for region: " + e.getMessage());
        }
        return 0;
    }

    private long getCityPopulationForCountry(String countryCode) {
        String sql = "SELECT SUM(Population) AS CityPop FROM city WHERE CountryCode = ?";
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            ps.setString(1, countryCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("CityPop");
            }
        } catch (SQLException e) {
            System.out.println("Error getting city population for country: " + e.getMessage());
        }
        return 0;
    }

    // -------------------------
    // Print helper methods
    // -------------------------

    private void printCountryReport(String sql, Object... params) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-8s %-35s %-15s %-25s %-15s %-30s%n",
                        "Code", "Name", "Continent", "Region", "Population", "Capital");
                while (rs.next()) {
                    System.out.printf("%-8s %-35s %-15s %-25s %-15d %-30s%n",
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            rs.getString("CapitalName"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching country report: " + e.getMessage());
        }
    }

    private void printCountryReportWithLimit(String sql, Object... params) {
        printCountryReport(sql, params);
    }

    private void printCityReport(String sql, Object... params) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-5s %-35s %-25s %-30s %-15s%n",
                        "ID", "City Name", "District", "Country", "Population");
                while (rs.next()) {
                    System.out.printf("%-5d %-35s %-25s %-30s %-15d%n",
                            rs.getInt("ID"),
                            rs.getString("CityName"),
                            rs.getString("District"),
                            rs.getString("CountryName"),
                            rs.getLong("Population"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching city report: " + e.getMessage());
        }
    }

    private void printCityReportWithLimit(String sql, Object... params) {
        printCityReport(sql, params);
    }

    private void printCapitalReport(String sql, Object... params) {
        try (PreparedStatement ps = db.con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-35s %-30s %-15s%n",
                        "Capital City", "Country", "Population");
                while (rs.next()) {
                    System.out.printf("%-35s %-30s %-15d%n",
                            rs.getString("CapitalName"),
                            rs.getString("CountryName"),
                            rs.getLong("Population"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching capital city report: " + e.getMessage());
        }
    }

    private void printCapitalReportWithLimit(String sql, Object... params) {
        printCapitalReport(sql, params);
    }
}




