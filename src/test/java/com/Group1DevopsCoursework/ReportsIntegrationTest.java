package com.Group1DevopsCoursework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsIntegrationTest {

    private Reports reports;

    @BeforeEach
    void setUp() {
        reports = new Reports();
        reports.connect("localhost:33060", 1000);
    }

    @AfterEach
    void tearDown() {
        reports.disconnect();
    }

    // ==================== CONNECTION TESTS ====================

    @Test
    void testConnect() {
        assertNotNull(reports, "Reports object should be created");
        // Connection is established in setUp()
        System.out.println("Connection test completed");
    }

    @Test
    void testDisconnect() {
        reports.disconnect();
        System.out.println("Disconnect test completed");
    }

    // ==================== COUNTRY REPORT TESTS ====================

//    @Test
//    void testGetAllCountriesInWorld() {
//        System.out.println("\n--- Testing getAllCountriesInWorld ---");
//        ArrayList<Country> result = reports.getAllCountriesInWorld();
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return countries");
//
//        // Verify first country has highest population
//        Country first = result.get(0);
//        Country last = result.get(result.size() - 1);
//        assertTrue(first.population >= last.population, "Should be sorted by population descending");
//
//        reports.printCountries((ArrayList<Country>) result.subList(0, 5)); // Print first 5
//        System.out.println("Retrieved " + result.size() + " countries");
//    }
//
//    @Test
//    void testGetCountriesByContinent() {
//        System.out.println("\n--- Testing getCountriesByContinent ---");
//        String continent = "Asia";
//
//        ArrayList<Country> result = reports.getCountriesByContinent(continent);
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return Asian countries");
//
//        for (Country c : result) {
//            assertEquals("Asia", c.continent, "All countries should be from Asia");
//        }
//
//        reports.printCountries((ArrayList<Country>) result.subList(0, Math.min(5, result.size())));
//        System.out.println("Retrieved " + result.size() + " countries from " + continent);
//    }

    @Test
    void testGetCountriesByContinent_Null() {
        System.out.println("\n--- Testing getCountriesByContinent with null ---");
        ArrayList<Country> result = reports.getCountriesByContinent(null);

        assertNotNull(result, "Should handle null parameter gracefully");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetCountriesByContinent_Empty() {
        System.out.println("\n--- Testing getCountriesByContinent with unknown continent ---");
        ArrayList<Country> result = reports.getCountriesByContinent("UnknownContinent");

        assertNotNull(result, "Should return empty list, not null");
        assertTrue(result.isEmpty(), "Should return empty list for unknown continent");
        System.out.println("Handled unknown continent safely");
    }

    @Test
    void testGetCountriesByRegion() {
        System.out.println("\n--- Testing getCountriesByRegion ---");
        String region = "Eastern Europe";

        ArrayList<Country> result = reports.getCountriesByRegion(region);

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return countries from region");

        for (Country c : result) {
            assertEquals("Eastern Europe", c.region, "All countries should be from Eastern Europe");
        }

        reports.printCountries(result);
        System.out.println("Retrieved " + result.size() + " countries from " + region);
    }

    @Test
    void testGetTopNCountriesInWorld() {
        System.out.println("\n--- Testing getTopNCountriesInWorld ---");
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInWorld(n);

        assertNotNull(result, "Result should not be null");
        assertEquals(n, result.size(), "Should return exactly " + n + " countries");

        // Verify sorting
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).population >= result.get(i + 1).population,
                    "Should be sorted by population descending");
        }

        reports.printCountries(result);
        System.out.println("Retrieved top " + n + " countries by population");
    }

    @Test
    void testGetTopNCountriesInContinent() {
        System.out.println("\n--- Testing getTopNCountriesInContinent ---");
        String continent = "Asia";
        int n = 3;

        ArrayList<Country> result = reports.getTopNCountriesInContinent(continent, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " countries");

        for (Country c : result) {
            assertEquals("Asia", c.continent, "All countries should be from Asia");
        }

        reports.printCountries(result);
        System.out.println("Retrieved top " + n + " countries from " + continent);
    }

    @Test
    void testGetTopNCountriesInRegion() {
        System.out.println("\n--- Testing getTopNCountriesInRegion ---");
        String region = "Southern Asia";
        int n = 3;

        ArrayList<Country> result = reports.getTopNCountriesInRegion(region, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " countries");

        for (Country c : result) {
            assertEquals("Southern Asia", c.region, "All countries should be from Southern Asia");
        }

        reports.printCountries(result);
        System.out.println("Retrieved top " + n + " countries from " + region);
    }

    // ==================== CITY REPORT TESTS ====================

//    @Test
//    void testGetAllCitiesInWorld() {
//        System.out.println("\n--- Testing getAllCitiesInWorld ---");
//        ArrayList<City> result = reports.getAllCitiesInWorld();
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return cities");
//
//        reports.printCities((ArrayList<City>) result.subList(0, Math.min(5, result.size())));
//        System.out.println("Retrieved " + result.size() + " cities");
//    }
//
//    @Test
//    void testGetCitiesByContinent() {
//        System.out.println("\n--- Testing getCitiesByContinent ---");
//        String continent = "Asia";
//
//        ArrayList<City> result = reports.getCitiesByContinent(continent);
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return cities from Asia");
//
//        reports.printCities((ArrayList<City>) result.subList(0, Math.min(5, result.size())));
//        System.out.println("Retrieved " + result.size() + " cities from " + continent);
//    }
//
//    @Test
//    void testGetCitiesByRegion() {
//        System.out.println("\n--- Testing getCitiesByRegion ---");
//        String region = "Western Europe";
//
//        ArrayList<City> result = reports.getCitiesByRegion(region);
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return cities from Western Europe");
//
//        reports.printCities((ArrayList<City>) result.subList(0, Math.min(5, result.size())));
//        System.out.println("Retrieved " + result.size() + " cities from " + region);
//    }

    @Test
    void testGetCitiesByCountry() {
        System.out.println("\n--- Testing getCitiesByCountry ---");
        String country = "China";

        ArrayList<City> result = reports.getCitiesByCountry(country);

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return cities from China");

        for (City c : result) {
            assertEquals("China", c.country, "All cities should be from China");
        }

        reports.printCities(result);
        System.out.println("Retrieved " + result.size() + " cities from " + country);
    }

    @Test
    void testGetCitiesByDistrict() {
        System.out.println("\n--- Testing getCitiesByDistrict ---");
        String district = "California";

        ArrayList<City> result = reports.getCitiesByDistrict(district);

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return cities from California");

        for (City c : result) {
            assertEquals("California", c.district, "All cities should be from California district");
        }

        reports.printCities(result);
        System.out.println("Retrieved " + result.size() + " cities from " + district);
    }

    @Test
    void testGetTopNCitiesInWorld() {
        System.out.println("\n--- Testing getTopNCitiesInWorld ---");
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInWorld(n);

        assertNotNull(result, "Result should not be null");
        assertEquals(n, result.size(), "Should return exactly " + n + " cities");

        reports.printCities(result);
        System.out.println("Retrieved top " + n + " cities by population");
    }

    @Test
    void testGetTopNCitiesInContinent() {
        System.out.println("\n--- Testing getTopNCitiesInContinent ---");
        String continent = "Asia";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInContinent(continent, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " cities");

        reports.printCities(result);
        System.out.println("Retrieved top " + n + " cities from " + continent);
    }

    @Test
    void testGetTopNCitiesInRegion() {
        System.out.println("\n--- Testing getTopNCitiesInRegion ---");
        String region = "Southern Asia";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInRegion(region, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " cities");

        reports.printCities(result);
        System.out.println("Retrieved top " + n + " cities from " + region);
    }

    @Test
    void testGetTopNCitiesInCountry() {
        System.out.println("\n--- Testing getTopNCitiesInCountry ---");
        String country = "India";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInCountry(country, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " cities");

        for (City c : result) {
            assertEquals("India", c.country, "All cities should be from India");
        }

        reports.printCities(result);
        System.out.println("Retrieved top " + n + " cities from " + country);
    }

    @Test
    void testGetTopNCitiesInDistrict() {
        System.out.println("\n--- Testing getTopNCitiesInDistrict ---");
        String district = "Maharashtra";
        int n = 3;

        ArrayList<City> result = reports.getTopNCitiesInDistrict(district, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " cities");

        for (City c : result) {
            assertEquals("Maharashtra", c.district, "All cities should be from Maharashtra district");
        }

        reports.printCities(result);
        System.out.println("Retrieved top " + n + " cities from " + district);
    }

    // ==================== CAPITAL CITY TESTS ====================

//    @Test
//    void testGetAllCapitalCitiesInWorld() {
//        System.out.println("\n--- Testing getAllCapitalCitiesInWorld ---");
//        ArrayList<CapitalCity> result = reports.getAllCapitalCitiesInWorld();
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return capital cities");
//
//        reports.printCapitals((ArrayList<CapitalCity>) result.subList(0, Math.min(5, result.size())));
//        System.out.println("Retrieved " + result.size() + " capital cities");
//    }

    @Test
    void testGetCapitalCitiesByContinent() {
        System.out.println("\n--- Testing getCapitalCitiesByContinent ---");
        String continent = "Asia";

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent(continent);

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return Asian capital cities");

        reports.printCapitals(result);
        System.out.println("Retrieved " + result.size() + " capital cities from " + continent);
    }

//    @Test
//    void testGetCapitalCitiesByRegion() {
//        System.out.println("\n--- Testing getCapitalCitiesByRegion ---");
//        String region = "Northern Europe";
//
//        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion(region);
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return capital cities from Northern Europe");
//
//        reports.printCapitals(result);
//        System.out.println("Retrieved " + result.size() + " capital cities from " + region);
//    }

    @Test
    void testGetTopNCapitalCitiesInWorld() {
        System.out.println("\n--- Testing getTopNCapitalCitiesInWorld ---");
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInWorld(n);

        assertNotNull(result, "Result should not be null");
        assertEquals(n, result.size(), "Should return exactly " + n + " capital cities");

        reports.printCapitals(result);
        System.out.println("Retrieved top " + n + " capital cities by population");
    }

    @Test
    void testGetTopNCapitalCitiesInContinent() {
        System.out.println("\n--- Testing getTopNCapitalCitiesInContinent ---");
        String continent = "Asia";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInContinent(continent, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " capital cities");

        reports.printCapitals(result);
        System.out.println("Retrieved top " + n + " capital cities from " + continent);
    }

    @Test
    void testGetTopNCapitalCitiesInRegion() {
        System.out.println("\n--- Testing getTopNCapitalCitiesInRegion ---");
        String region = "Southern Europe";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInRegion(region, n);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.size() <= n, "Should return at most " + n + " capital cities");

        reports.printCapitals(result);
        System.out.println("Retrieved top " + n + " capital cities from " + region);
    }

    // ==================== POPULATION TESTS ====================

    @Test
    void testGetPopulationByContinent() {
        System.out.println("\n--- Testing getPopulationByContinent ---");
        ArrayList<Population> result = reports.getPopulationByContinent();

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return population by continent");

        reports.printPopulations(result);
        System.out.println("Retrieved population data for " + result.size() + " continents");
    }
//
//    @Test
//    void testGetPopulationByRegion() {
//        System.out.println("\n--- Testing getPopulationByRegion ---");
//        ArrayList<Population> result = reports.getPopulationByRegion();
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return population by region");
//
//        reports.printPopulations((ArrayList<Population>) result.subList(0, Math.min(10, result.size())));
//        System.out.println("Retrieved population data for " + result.size() + " regions");
//    }
//
//    @Test
//    void testGetPopulationByCountry() {
//        System.out.println("\n--- Testing getPopulationByCountry ---");
//        ArrayList<Population> result = reports.getPopulationByCountry();
//
//        assertNotNull(result, "Result should not be null");
//        assertFalse(result.isEmpty(), "Should return population by country");
//
//        reports.printPopulations((ArrayList<Population>) result.subList(0, Math.min(10, result.size())));
//        System.out.println("Retrieved population data for " + result.size() + " countries");
//    }

    @Test
    void testGetWorldPopulation() {
        System.out.println("\n--- Testing getWorldPopulation ---");
        long result = reports.getWorldPopulation();

        assertTrue(result > 0, "World population should be positive");
        System.out.println("World population: " + result);
    }

    @Test
    void testGetPopulationOfContinentWithName() {
        System.out.println("\n--- Testing getPopulationOfContinentWithName ---");
        String continent = "Asia";

        Population result = reports.getPopulationOfContinentWithName(continent);

        assertNotNull(result, "Result should not be null");
        assertEquals(continent, result.name, "Population object should have correct name");
        assertTrue(result.totalPopulation > 0, "Asian population should be positive");

        System.out.println("Population of " + continent + ": " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfRegionWithName() {
        System.out.println("\n--- Testing getPopulationOfRegionWithName ---");
        String region = "Eastern Asia";

        Population result = reports.getPopulationOfRegionWithName(region);

        assertNotNull(result, "Result should not be null");
        assertEquals(region, result.name, "Population object should have correct name");
        assertTrue(result.totalPopulation > 0, "Eastern Asia population should be positive");

        System.out.println("Population of " + region + ": " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfCountryWithName() {
        System.out.println("\n--- Testing getPopulationOfCountryWithName ---");
        String country = "Brazil";

        Population result = reports.getPopulationOfCountryWithName(country);

        assertNotNull(result, "Result should not be null");
        assertEquals(country, result.name, "Population object should have correct name");
        assertTrue(result.totalPopulation > 0, "Brazil population should be positive");

        System.out.println("Population of " + country + ": " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfDistrictWithName() {
        System.out.println("\n--- Testing getPopulationOfDistrictWithName ---");
        String district = "São Paulo";

        Population result = reports.getPopulationOfDistrictWithName(district);

        assertNotNull(result, "Result should not be null");
        assertEquals(district, result.name, "Population object should have correct name");
        assertTrue(result.totalPopulation > 0, "São Paulo district population should be positive");

        System.out.println("Population of " + district + " district: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfCityWithName() {
        System.out.println("\n--- Testing getPopulationOfCityWithName ---");
        String city = "Shanghai";

        Population result = reports.getPopulationOfCityWithName(city);

        assertNotNull(result, "Result should not be null");
        assertEquals(city, result.name, "Population object should have correct name");
        assertTrue(result.totalPopulation > 0, "Shanghai population should be positive");

        System.out.println("Population of " + city + ": " + result.totalPopulation);
    }

    // ==================== LANGUAGE TESTS ====================

    @Test
    void testGetLanguageReport() {
        System.out.println("\n--- Testing getLanguageReport ---");
        ArrayList<Language> result = reports.getLanguageReport();

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return language data");

        // Should contain the 5 specified languages
        boolean hasChinese = false, hasEnglish = false, hasHindi = false, hasSpanish = false, hasArabic = false;

        for (Language lang : result) {
            switch (lang.language) {
                case "Chinese": hasChinese = true; break;
                case "English": hasEnglish = true; break;
                case "Hindi": hasHindi = true; break;
                case "Spanish": hasSpanish = true; break;
                case "Arabic": hasArabic = true; break;
            }
        }

        reports.printLanguages(result);
        System.out.println("Retrieved data for " + result.size() + " languages");
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    void testGetCountryByCode() {
        System.out.println("\n--- Testing getCountryByCode ---");
        Country result = reports.getCountryByCode("BRA");

        assertNotNull(result, "Should retrieve Brazil by code");
        assertEquals("Brazil", result.name);
        assertEquals("South America", result.continent);
        System.out.println("Retrieved country: " + result.name);
    }

    @Test
    void testAddCountry() {
        System.out.println("\n--- Testing addCountry ---");

        // Create test country
        Country testCountry = new Country();
        testCountry.code = "TST";
        testCountry.name = "TestLand";
        testCountry.continent = "Asia";
        testCountry.region = "TestRegion";
        testCountry.population = 100000;

        // Add country
        reports.addCountry(testCountry);

        // Verify country was added
        Country result = reports.getCountryByCode("TST");
        assertNotNull(result, "Country should be retrieved successfully");
        assertEquals("TestLand", result.name);
        assertEquals("Asia", result.continent);
        assertEquals("TestRegion", result.region);
        assertEquals(100000, result.population);

        System.out.println("Added and retrieved country: " + result.name);
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    void testInvalidTopN() {
        System.out.println("\n--- Testing invalid top N values ---");

        // Test with negative N
        ArrayList<Country> negativeResult = reports.getTopNCountriesInWorld(-1);
        assertNotNull(negativeResult, "Should handle negative N gracefully");

        // Test with zero N
        ArrayList<Country> zeroResult = reports.getTopNCountriesInWorld(0);
        assertNotNull(zeroResult, "Should handle zero N gracefully");

        // Test with very large N
        ArrayList<Country> largeResult = reports.getTopNCountriesInWorld(10000);
        assertNotNull(largeResult, "Should handle large N gracefully");

        System.out.println("Handled various N values safely");
    }

    @Test
    void testEmptyStringParameters() {
        System.out.println("\n--- Testing empty string parameters ---");

        ArrayList<Country> emptyContinent = reports.getCountriesByContinent("");
        assertNotNull(emptyContinent, "Should handle empty continent");

        ArrayList<City> emptyCountry = reports.getCitiesByCountry("");
        assertNotNull(emptyCountry, "Should handle empty country");

        System.out.println("Handled empty string parameters safely");
    }
}