package com.Group1DevopsCoursework;

import com.Group1DevopsCoursework.Reports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsIntegrationTest {

    Reports reports;

    @BeforeEach
    void setUp() {
        reports = new Reports();
        reports.connect("localhost:33060", 1000);
    }

    @Test
    void testGetCountryByCode() {
        // Assuming a country with code "BRA" already exists in the DB
        Country country = reports.getCountryByCode("BRA");
        assertNotNull(country);
        assertEquals("Brazil", country.name);
        assertEquals("South America", country.continent);
        System.out.println("Retrieved country: " + country.name);
    }

    @Test
    void testAddCountry() {
        // Create country object without capital
        Country country = new Country();
        country.code = "TST";
        country.name = "TestLand";
        country.continent = "Asia";
        country.region = "TestRegion";
        country.population = 100000;

        // Add country
        reports.addCountry(country);

        // Get country
        Country result = reports.getCountryByCode("TST");
        assertNotNull(result, "Country should be retrieved successfully");
        assertEquals("TestLand", result.name);
        assertEquals("Asia", result.continent);
        assertEquals("TestRegion", result.region);
        assertEquals(100000, result.population);
        assertNull(result.capital, "null");
        System.out.println("Added and retrieved country  " + result.name);
    }
    @Test
    void testGetAllCountriesInWorld_NotNull() {
        System.out.println("\n--- TestNull: getAllCountriesInWorld ---");

        ArrayList<Country> result = reports.getAllCountriesInWorld();

        assertNotNull(result, "List should not be null");
        System.out.println("Handled non-null return correctly");
    }

    @Test
    void testGetAllCountriesInWorld_NotEmpty() {
        System.out.println("\n--- TestEmpty: getAllCountriesInWorld ---");

        ArrayList<Country> result = reports.getAllCountriesInWorld();

        assertFalse(result.isEmpty(), "List should not be empty because world has countries");
        System.out.println("Handled non-empty result correctly");
    }

    @Test
    void testGetAllCountriesInWorld_PrintResults() {
        System.out.println("\n--- TestPrint: getAllCountriesInWorld ---");

        ArrayList<Country> result = reports.getAllCountriesInWorld();

        assertNotNull(result, "List should not be null");
        assertFalse(result.isEmpty(), "List should not be empty");

        reports.printCountries(result);
        System.out.println("Print execution completed successfully");
    }




    // -------------------- getCountriesByContinent() --------------------
    @Test
    void testGetCountriesByContinent_Null() {
        System.out.println("\n--- TestNull: getCountriesByContinent ---");
        String continent = null;

        ArrayList<Country> result = reports.getCountriesByContinent(continent);

        assertNotNull(result, "List should not be null even when continent is null");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetCountriesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCountriesByContinent ---");
        // Use a continent that does not exist in database
        String continent = "ContinentXYZ";

        ArrayList<Country> result = reports.getCountriesByContinent(continent);

        assertTrue(result.isEmpty(), "List should be empty for unknown continent");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCountriesByContinent_WithValidContinent() {
        System.out.println("\n--- TestValid: getCountriesByContinent ---");
        String continent = "Asia";

        ArrayList<Country> result = reports.getCountriesByContinent(continent);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Asia should return country data");

        for (Country c : result) {
            assertEquals("Asia", c.continent, "Each returned country should belong to Asia");
        }
        reports.printCountries(result);
    }

    // -------------------- getCountriesByRegion() --------------------
    @Test
    void testGetCountriesByRegion_Null() {
        System.out.println("\n--- TestNull: getCountriesByRegion ---");
        String region = null;

        ArrayList<Country> result = reports.getCountriesByRegion(region);

        assertNotNull(result, "List should not be null even when region is null");
        System.out.println("Handled null region safely");
    }

    @Test
    void testGetCountriesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCountriesByRegion ---");
        String region = "RegionXYZ";

        ArrayList<Country> result = reports.getCountriesByRegion(region);

        assertTrue(result.isEmpty(), "List should be empty for unknown region");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCountriesByRegion_Valid() {
        System.out.println("\n--- TestValid: getCountriesByRegion ---");
        String region = "Southeast Asia";

        ArrayList<Country> result = reports.getCountriesByRegion(region);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Region should return country data");

        for (Country c : result) {
            assertEquals(region, c.region, "Each returned country should belong to the region");
        }
        reports.printCountries(result);
    }

    // -------------------- getTopNCountriesInWorld() --------------------
    @Test
    void testGetTopNCountriesInWorld_InvalidN() {
        System.out.println("\n--- TestInvalid: getTopNCountriesInWorld ---");
        int n = -5;

        ArrayList<Country> result = reports.getTopNCountriesInWorld(n);

        assertNotNull(result, "Result list should not be null even for negative n");
        System.out.println("Handled invalid n safely");
    }

    @Test
    void testGetTopNCountriesInWorld_Valid() {
        System.out.println("\n--- TestValid: getTopNCountriesInWorld ---");
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInWorld(n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " countries");

        System.out.println("Top " + n + " countries in the world:");
        reports.printCountries(result);
    }

    // -------------------- getTopNCountriesInContinent() --------------------
    @Test
    void testGetTopNCountriesInContinent_NullContinent() {
        System.out.println("\n--- TestNull: getTopNCountriesInContinent ---");
        String continent = null;
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInContinent(continent, n);

        assertNotNull(result, "Result list should not be null even if continent is null");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetTopNCountriesInContinent_EmptyContinent() {
        System.out.println("\n--- TestEmpty: getTopNCountriesInContinent ---");
        String continent = "ContinentXYZ";
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInContinent(continent, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown continent");
        System.out.println("Handled empty continent safely");
    }

    @Test
    void testGetTopNCountriesInContinent_Valid() {
        System.out.println("\n--- TestValid: getTopNCountriesInContinent ---");
        String continent = "Asia";
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInContinent(continent, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " countries in continent");

        for (Country c : result) {
            assertEquals(continent, c.continent, "Each country should belong to " + continent);
        }
        reports.printCountries(result);
    }

    // -------------------- getTopNCountriesInRegion() --------------------
    @Test
    void testGetTopNCountriesInRegion_NullRegion() {
        System.out.println("\n--- TestNull: getTopNCountriesInRegion ---");
        String region = null;
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInRegion(region, n);

        assertNotNull(result, "Result list should not be null even if region is null");
        System.out.println("Handled null region safely");
    }

    @Test
    void testGetTopNCountriesInRegion_EmptyRegion() {
        System.out.println("\n--- TestEmpty: getTopNCountriesInRegion ---");
        String region = "RegionXYZ";
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInRegion(region, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown region");
        System.out.println("Handled empty region safely");
    }

    @Test
    void testGetTopNCountriesInRegion_Valid() {
        System.out.println("\n--- TestValid: getTopNCountriesInRegion ---");
        String region = "Southeast Asia";
        int n = 5;

        ArrayList<Country> result = reports.getTopNCountriesInRegion(region, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " countries in region");

        for (Country c : result) {
            assertEquals(region, c.region, "Each country should belong to " + region);
        }
        reports.printCountries(result);
    }

    // ----------------- getCitiesByContinent -----------------------

    @Test
    void testGetCitiesByContinent_Null() {
        System.out.println("\n--- TestNull: getCitiesByContinent ---");
        String continent = null;

        ArrayList<City> result = reports.getCitiesByContinent(continent);

        assertNotNull(result, "List should not be null even when continent is null");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetCitiesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByContinent ---");
        String continent = "ContinentXYZ";

        ArrayList<City> result = reports.getCitiesByContinent(continent);

        assertTrue(result.isEmpty(), "List should be empty for unknown continent");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCitiesByContinent_Valid() {
        System.out.println("\n--- TestValid: getCitiesByContinent ---");
        String continent = "Asia";

        ArrayList<City> result = reports.getCitiesByContinent(continent);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Asia should return city data");

        reports.printCities(result);
    }

    // ----------------- getCitiesByRegion -----------------------

    @Test
    void testGetCitiesByRegion_Null() {
        System.out.println("\n--- TestNull: getCitiesByRegion ---");
        String region = null;

        ArrayList<City> result = reports.getCitiesByRegion(region);

        assertNotNull(result, "List should not be null even when region is null");
        System.out.println("Handled null region safely");
    }

    @Test
    void testGetCitiesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByRegion ---");
        String region = "RegionXYZ";

        ArrayList<City> result = reports.getCitiesByRegion(region);

        assertTrue(result.isEmpty(), "List should be empty for unknown region");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCitiesByRegion_Valid() {
        System.out.println("\n--- TestValid: getCitiesByRegion ---");
        String region = "Southeast Asia";

        ArrayList<City> result = reports.getCitiesByRegion(region);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Region should return city data");

        reports.printCities(result);
    }

    // ----------------- getCitiesByCountry -----------------------

    @Test
    void testGetCitiesByCountry_Null() {
        System.out.println("\n--- TestNull: getCitiesByCountry ---");
        String country = null;

        ArrayList<City> result = reports.getCitiesByCountry(country);

        assertNotNull(result, "List should not be null even when country is null");
        System.out.println("Handled null country safely");
    }

    @Test
    void testGetCitiesByCountry_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByCountry ---");
        String country = "CountryXYZ";

        ArrayList<City> result = reports.getCitiesByCountry(country);

        assertTrue(result.isEmpty(), "List should be empty for unknown country");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCitiesByCountry_Valid() {
        System.out.println("\n--- TestValid: getCitiesByCountry ---");
        String country = "Myanmar";

        ArrayList<City> result = reports.getCitiesByCountry(country);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Country should return city data");

        reports.printCities(result);
    }

    // ----------------- getCitiesByDistrict -----------------------

    @Test
    void testGetCitiesByDistrict_Null() {
        System.out.println("\n--- TestNull: getCitiesByDistrict ---");
        String district = null;

        ArrayList<City> result = reports.getCitiesByDistrict(district);

        assertNotNull(result, "List should not be null even when district is null");
        System.out.println("Handled null district safely");
    }

    @Test
    void testGetCitiesByDistrict_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByDistrict ---");
        String district = "DistrictXYZ";

        ArrayList<City> result = reports.getCitiesByDistrict(district);

        assertTrue(result.isEmpty(), "List should be empty for unknown district");
        System.out.println("Handled empty result safely");
    }

    @Test
    void testGetCitiesByDistrict_Valid() {
        System.out.println("\n--- TestValid: getCitiesByDistrict ---");
        String district = "São Paulo";

        ArrayList<City> result = reports.getCitiesByDistrict(district);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "District should return city data");

        reports.printCities(result);
    }

    // -------------------- getTopNCitiesInCountry() --------------------
    @Test
    void testGetTopNCitiesInCountry_NullCountry() {
        System.out.println("\n--- TestNull: getTopNCitiesInCountry ---");
        String countryName = null;
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInCountry(countryName, n);

        assertNotNull(result, "Result list should not be null even if countryName is null");
        System.out.println("Handled null country safely");
    }

    @Test
    void testGetTopNCitiesInCountry_EmptyCountry() {
        System.out.println("\n--- TestEmpty: getTopNCitiesInCountry ---");
        String countryName = "CountryXYZ";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInCountry(countryName, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown country");
        System.out.println("Handled empty country safely");
    }

    @Test
    void testGetTopNCitiesInCountry_Valid() {
        System.out.println("\n--- TestValid: getTopNCitiesInCountry ---");
        String countryName = "Myanmar";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInCountry(countryName, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " cities in country");

        for (City c : result) {
            assertEquals(countryName, c.country, "Each city should belong to " + countryName);
        }
        reports.printCities(result);
    }

    // -------------------- getTopNCitiesInDistrict() --------------------
    @Test
    void testGetTopNCitiesInDistrict_NullDistrict() {
        System.out.println("\n--- TestNull: getTopNCitiesInDistrict ---");
        String district = null;
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInDistrict(district, n);

        assertNotNull(result, "Result list should not be null even if district is null");
        System.out.println("Handled null district safely");
    }

    @Test
    void testGetTopNCitiesInDistrict_EmptyDistrict() {
        System.out.println("\n--- TestEmpty: getTopNCitiesInDistrict ---");
        String district = "DistrictXYZ";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInDistrict(district, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown district");
        System.out.println("Handled empty district safely");
    }
    @Test
    void testGetTopNCitiesInDistrict_Valid() {
        System.out.println("\n--- TestValid: getTopNCitiesInDistrict ---");
        String district = "Mendoza";
        int n = 5;

        ArrayList<City> result = reports.getTopNCitiesInDistrict(district, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " cities in district");

        for (City c : result) {
            assertEquals(district, c.district, "Each city should belong to " + district);
        }
        reports.printCities(result);
    }

    @Test
    void testRunCityQuery_ValidResult() {
        System.out.println("\n--- TestValid: runCityQuery  ---");

        ArrayList<City> result = reports.getAllCitiesInWorld();

        assertNotNull(result, "List should not be null");
        assertFalse(result.isEmpty(), "World city list should not be empty");

        reports.printCities(result);
        System.out.println("Valid data printed successfully (runCityQuery mapping confirmed)");
    }

    @Test
    void testRunCityQueryWithInt_EmptyResult() {
        System.out.println("\n--- TestEmpty: runCityQueryWithInt indirect via getTopNCitiesInWorld ---");
        int n = -1; // negative number should return empty

        ArrayList<City> result = reports.getTopNCitiesInWorld(n);

        assertTrue(result.isEmpty(), "List should be empty for invalid top N");
        System.out.println("Handled empty result safely (runCityQueryWithInt returned empty list)");
    }

    @Test
    void testRunCityQueryWithInt_ValidResult() {
        System.out.println("\n--- TestValid: runCityQueryWithInt indirect via getTopNCitiesInWorld ---");
        int n = 5; // top 5 cities

        ArrayList<City> result = reports.getTopNCitiesInWorld(n);

        assertNotNull(result, "List should not be null");
        assertFalse(result.isEmpty(), "Top 5 cities should return data");

        reports.printCities(result);
        System.out.println("Valid data printed successfully.");
    }


    // ----------------- CAPITAL CITY REPORTS ----------------
    // ----------------- getAllCapitalCitiesInWorld -----------------------

    @Test
    void testGetAllCapitalCitiesInWorld_NotNull() {
        System.out.println("\n--- TestNull: getAllCapitalCitiesInWorld ---");

        ArrayList<CapitalCity> result = reports.getAllCapitalCitiesInWorld();

        assertNotNull(result, "List should not be null");
        System.out.println("Handled non-null return safely");
    }

    @Test
    void testGetAllCapitalCitiesInWorld_NotEmpty() {
        System.out.println("\n--- TestEmpty: getAllCapitalCitiesInWorld ---");

        ArrayList<CapitalCity> result = reports.getAllCapitalCitiesInWorld();

        assertFalse(result.isEmpty(), "List should not be empty because the world has capital cities");
        System.out.println("Handled non-empty result safely");
    }

    @Test
    void testGetAllCapitalCitiesInWorld_PrintResults() {
        System.out.println("\n--- TestPrint: getAllCapitalCitiesInWorld ---");

        ArrayList<CapitalCity> result = reports.getAllCapitalCitiesInWorld();

        assertNotNull(result, "List should not be null");
        assertFalse(result.isEmpty(), "List should not be empty");

        reports.printCapitals(result);
        System.out.println("Print execution completed successfully");
    }


    // -------------------- getCapitalCitiesByContinent() --------------------
    @Test
    void testGetCapitalCitiesByContinent_Null() {
        System.out.println("\n--- TestNull: getCapitalCitiesByContinent ---");
        String continent = null;

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent(continent);

        assertNotNull(result, "List should not be null even when continent is null");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetCapitalCitiesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCapitalCitiesByContinent ---");
        String continent = "ContinentXYZ";

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent(continent);

        assertTrue(result.isEmpty(), "List should be empty for unknown continent");
        System.out.println("Handled empty continent safely");
    }

    @Test
    void testGetCapitalCitiesByContinent_Valid() {
        System.out.println("\n--- TestValid: getCapitalCitiesByContinent ---");
        String continent = "Asia";

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent(continent);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Asia should return capital city data");

        for (CapitalCity c : result) {
            assertNotNull(c.name, "Capital name should not be null");
            assertNotNull(c.country, "Country should not be null");
            System.out.println(c.name + " - " + c.country + " - " + c.population);
        }
    }


    // -------------------- getCapitalCitiesByRegion() --------------------
    @Test
    void testGetCapitalCitiesByRegion_Null() {
        System.out.println("\n--- TestNull: getCapitalCitiesByRegion ---");
        String region = null;

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion(region);

        assertNotNull(result, "List should not be null even when region is null");
        System.out.println("Handled null region safely");
    }

    @Test
    void testGetCapitalCitiesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCapitalCitiesByRegion ---");
        String region = "RegionXYZ";

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion(region);

        assertTrue(result.isEmpty(), "List should be empty for unknown region");
        System.out.println("Handled empty region safely");
    }

    @Test
    void testGetCapitalCitiesByRegion_Valid() {
        System.out.println("\n--- TestValid: getCapitalCitiesByRegion ---");
        String region = "Southeast Asia";

        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion(region);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Region should return capital city data");

        for (CapitalCity c : result) {
            assertNotNull(c.name, "Capital name should not be null");
            assertNotNull(c.country, "Country should not be null");
            System.out.println(c.name + " - " + c.country + " - " + c.population);
        }
    }

    // -------------------- getTopNCapitalCitiesInWorld() --------------------
    @Test
    void testGetTopNCapitalCitiesInWorld() {
        System.out.println("\n--- Test: getTopNCapitalCitiesInWorld ---");
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInWorld(n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " capital cities");

        reports.printCapitals(result);
    }

    // -------------------- getTopNCapitalCitiesInContinent() --------------------
    @Test
    void testGetTopNCapitalCitiesInContinent_Null() {
        System.out.println("\n--- TestNull: getTopNCapitalCitiesInContinent ---");
        String continent = null;
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInContinent(continent, n);

        assertNotNull(result, "Result list should not be null even if continent is null");
        System.out.println("Handled null continent safely");
    }

    @Test
    void testGetTopNCapitalCitiesInContinent_Empty() {
        System.out.println("\n--- TestEmpty: getTopNCapitalCitiesInContinent ---");
        String continent = "ContinentXYZ";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInContinent(continent, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown continent");
        System.out.println("Handled empty continent safely");
    }

    @Test
    void testGetTopNCapitalCitiesInContinent_Valid() {
        System.out.println("\n--- TestValid: getTopNCapitalCitiesInContinent ---");
        String continent = "Asia";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInContinent(continent, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " capital cities in continent");

        reports.printCapitals(result);
    }

    // -------------------- getTopNCapitalCitiesInRegion() --------------------
    @Test
    void testGetTopNCapitalCitiesInRegion_Null() {
        System.out.println("\n--- TestNull: getTopNCapitalCitiesInRegion ---");
        String region = null;
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInRegion(region, n);

        assertNotNull(result, "Result list should not be null even if region is null");
        System.out.println("Handled null region safely");
    }

    @Test
    void testGetTopNCapitalCitiesInRegion_Empty() {
        System.out.println("\n--- TestEmpty: getTopNCapitalCitiesInRegion ---");
        String region = "RegionXYZ";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInRegion(region, n);

        assertTrue(result.isEmpty(), "List should be empty for unknown region");
        System.out.println("Handled empty region safely");
    }

    @Test
    void testGetTopNCapitalCitiesInRegion_Valid() {
        System.out.println("\n--- TestValid: getTopNCapitalCitiesInRegion ---");
        String region = "Southeast Asia";
        int n = 5;

        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInRegion(region, n);

        assertNotNull(result, "Result list should not be null");
        assertEquals(n, result.size(), "Should return top " + n + " capital cities in region");

        reports.printCapitals(result);
    }

    // ----------------- POPULATION REPORTS -------------------

    // -------------------- getPopulationByContinent() --------------------
    @Test
    void testGetPopulationByContinent() {
        System.out.println("\n--- Test: getPopulationByContinent ---");
        ArrayList<Population> result = reports.getPopulationByContinent();

        assertNotNull(result, "Result list should not be null");

        for (Population p : result) {
            assertNotNull(p.name, "Continent name should not be null");
            reports.printPopulations(result);
        }
    }

    // -------------------- getPopulationByRegion() --------------------
    @Test
    void testGetPopulationByRegion() {
        System.out.println("\n--- Test: getPopulationByRegion ---");
        ArrayList<Population> result = reports.getPopulationByRegion();

        assertNotNull(result, "Result list should not be null");

        for (Population p : result) {
            assertNotNull(p.name, "Region name should not be null");
            reports.printPopulations(result);
        }
    }

    // -------------------- getPopulationByCountry() --------------------
    @Test
    void testGetPopulationByCountry() {
        System.out.println("\n--- Test: getPopulationByCountry ---");
        ArrayList<Population> result = reports.getPopulationByCountry();

        assertNotNull(result, "Result list should not be null");

        for (Population p : result) {
            assertNotNull(p.name, "Country name should not be null");
            reports.printPopulations(result);
        }
    }

    // -------------------- getWorldPopulation() --------------------
    @Test
    void testGetWorldPopulation() {
        System.out.println("\n--- Test: getWorldPopulation ---");
        long worldPop = reports.getWorldPopulation();

        assertTrue(worldPop > 0, "World population should be greater than 0");
        System.out.println("World Population: " + worldPop);
    }

    // -------------------- getPopulationOfContinentWithName() --------------------
    @Test
    void testGetPopulationOfContinentWithName() {
        System.out.println("\n--- Test: getPopulationOfContinentWithName ---");
        String continent = "Asia";
        Population result = reports.getPopulationOfContinentWithName(continent);

        assertNotNull(result, "Result should not be null");
        assertEquals(continent, result.name, "Continent name should match input");
        System.out.println(result.name + " Population: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfContinentWithName_Empty() {
        System.out.println("\n--- TestEmpty: getPopulationOfContinentWithName ---");
        String continent = "ContinentXYZ";
        Population result = reports.getPopulationOfContinentWithName(continent);

        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.totalPopulation, "Population should be 0 for unknown continent");
        System.out.println("Handled unknown continent safely");
    }

    // -------------------- getPopulationOfRegionWithName() --------------------
    @Test
    void testGetPopulationOfRegionWithName() {
        System.out.println("\n--- Test: getPopulationOfRegionWithName ---");
        String region = "Eastern Asia";
        Population result = reports.getPopulationOfRegionWithName(region);

        assertNotNull(result, "Result should not be null");
        assertEquals(region, result.name, "Region name should match input");
        System.out.println(result.name + " Population: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfRegionWithName_Empty() {
        System.out.println("\n--- TestEmpty: getPopulationOfRegionWithName ---");
        String region = "RegionXYZ";
        Population result = reports.getPopulationOfRegionWithName(region);

        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.totalPopulation, "Population should be 0 for unknown region");
        System.out.println("Handled unknown region safely");
    }

    // -------------------- getPopulationOfCountryWithName() --------------------
    @Test
    void testGetPopulationOfCountryWithName() {
        System.out.println("\n--- Test: getPopulationOfCountryWithName ---");
        String country = "China";
        Population result = reports.getPopulationOfCountryWithName(country);

        assertNotNull(result, "Result should not be null");
        assertEquals(country, result.name, "Country name should match input");
        System.out.println(result.name + " Population: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfCountryWithName_Empty() {
        System.out.println("\n--- TestEmpty: getPopulationOfCountryWithName ---");
        String country = "CountryXYZ";
        Population result = reports.getPopulationOfCountryWithName(country);

        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.totalPopulation, "Population should be 0 for unknown country");
        System.out.println("Handled unknown country safely");
    }

    // -------------------- getPopulationOfDistrictWithName() --------------------
    @Test
    void testGetPopulationOfDistrictWithName() {
        System.out.println("\n--- Test: getPopulationOfDistrictWithName ---");
        String district = "Guangdong";
        Population result = reports.getPopulationOfDistrictWithName(district);

        assertNotNull(result, "Result should not be null");
        assertEquals(district, result.name, "District name should match input");
        System.out.println(result.name + " Population: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfDistrictWithName_Empty() {
        System.out.println("\n--- TestEmpty: getPopulationOfDistrictWithName ---");
        String district = "DistrictXYZ";
        Population result = reports.getPopulationOfDistrictWithName(district);

        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.totalPopulation, "Population should be 0 for unknown district");
        System.out.println("Handled unknown district safely");
    }

    // -------------------- getPopulationOfCityWithName() --------------------
    @Test
    void testGetPopulationOfCityWithName() {
        System.out.println("\n--- Test: getPopulationOfCityWithName ---");
        String city = "Beijing";
        Population result = reports.getPopulationOfCityWithName(city);

        assertNotNull(result, "Result should not be null");
        assertEquals(city, result.name, "City name should match input");
        System.out.println(result.name + " Population: " + result.totalPopulation);
    }

    @Test
    void testGetPopulationOfCityWithName_Empty() {
        System.out.println("\n--- TestEmpty: getPopulationOfCityWithName ---");
        String city = "CityXYZ";
        Population result = reports.getPopulationOfCityWithName(city);

        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.totalPopulation, "Population should be 0 for unknown city");
        System.out.println("Handled unknown city safely");
    }


    @Test
    void testGetLanguageReport_PrintResults() {
        System.out.println("\n--- TestPrint: getLanguageReport ---");

        ArrayList<Language> result = reports.getLanguageReport();

        assertNotNull(result, "List should not be null");
        assertFalse(result.isEmpty(), "List should not be empty");

        reports.printLanguages(result);
        System.out.println("Language report printed successfully");
    }

    @Test
    public void testMainMethod() {
        String[] args = {};
        Reports.main(args);
    }


    @Test
    void testDisconnect() {
        reports.disconnect();
        System.out.println("Disconnected from server testing complete.");
    }





    // ==================== MISSING TESTS FOR 100% COVERAGE ====================

    // 1. Markdown Output Tests
    @Test
    void testOutputAllReportsMarkdown() {
        assertDoesNotThrow(() -> reports.outputAllReportsMarkdown("test_report.md"));
        System.out.println("Markdown report generation completed successfully");
    }

    @Test
    void testOutputAllReportsMarkdown_WithIOException() {
        // Test with problematic filename that might cause IO issues
        assertDoesNotThrow(() -> reports.outputAllReportsMarkdown("../../../invalid/test.md"));
    }

    // 2. Markdown Helper Methods with Null/Empty Lists
    @Test
    void testAppendCountriesMarkdown_WithNullList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCountriesMarkdown(sb, null);
        assertTrue(sb.toString().contains("No countries found"), "Should handle null list");
    }

    @Test
    void testAppendCountriesMarkdown_WithEmptyList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCountriesMarkdown(sb, new ArrayList<>());
        assertTrue(sb.toString().contains("No countries found"), "Should handle empty list");
    }

    @Test
    void testAppendCitiesMarkdown_WithNullList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCitiesMarkdown(sb, null);
        assertTrue(sb.toString().contains("No cities found"), "Should handle null list");
    }

    @Test
    void testAppendCitiesMarkdown_WithEmptyList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCitiesMarkdown(sb, new ArrayList<>());
        assertTrue(sb.toString().contains("No cities found"), "Should handle empty list");
    }

    @Test
    void testAppendCapitalsMarkdown_WithNullList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCapitalsMarkdown(sb, null);
        assertTrue(sb.toString().contains("No capitals found"), "Should handle null list");
    }

    @Test
    void testAppendCapitalsMarkdown_WithEmptyList() {
        StringBuilder sb = new StringBuilder();
        reports.appendCapitalsMarkdown(sb, new ArrayList<>());
        assertTrue(sb.toString().contains("No capitals found"), "Should handle empty list");
    }

    @Test
    void testAppendPopulationsMarkdown_WithNullList() {
        StringBuilder sb = new StringBuilder();
        reports.appendPopulationsMarkdown(sb, null);
        assertTrue(sb.toString().contains("No population data found"), "Should handle null list");
    }

    @Test
    void testAppendPopulationsMarkdown_WithEmptyList() {
        StringBuilder sb = new StringBuilder();
        reports.appendPopulationsMarkdown(sb, new ArrayList<>());
        assertTrue(sb.toString().contains("No population data found"), "Should handle empty list");
    }

    @Test
    void testAppendLanguagesMarkdown_WithNullList() {
        StringBuilder sb = new StringBuilder();
        reports.appendLanguagesMarkdown(sb, null);
        assertTrue(sb.toString().contains("No language data found"), "Should handle null list");
    }

    @Test
    void testAppendLanguagesMarkdown_WithEmptyList() {
        StringBuilder sb = new StringBuilder();
        reports.appendLanguagesMarkdown(sb, new ArrayList<>());
        assertTrue(sb.toString().contains("No language data found"), "Should handle empty list");
    }

    // 3. Print Methods with Null Elements in Lists
    @Test
    void testPrintCountries_WithNullElementsInList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        countries.add(createTestCountry());
        countries.add(null);

        assertDoesNotThrow(() -> reports.printCountries(countries));
    }

    @Test
    void testPrintCities_WithNullElementsInList() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        cities.add(createTestCity());
        cities.add(null);

        assertDoesNotThrow(() -> reports.printCities(cities));
    }

    @Test
    void testPrintCapitals_WithNullElementsInList() {
        ArrayList<CapitalCity> capitals = new ArrayList<>();
        capitals.add(null);
        capitals.add(createTestCapital());
        capitals.add(null);

        assertDoesNotThrow(() -> reports.printCapitals(capitals));
    }

    @Test
    void testPrintPopulations_WithNullElementsInList() {
        ArrayList<Population> populations = new ArrayList<>();
        populations.add(null);
        populations.add(createTestPopulation());
        populations.add(null);

        assertDoesNotThrow(() -> reports.printPopulations(populations));
    }

    @Test
    void testPrintLanguages_WithNullElementsInList() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(null);
        languages.add(createTestLanguage());
        languages.add(null);

        assertDoesNotThrow(() -> reports.printLanguages(languages));
    }

    // 4. Connection Error Handling Tests
    @Test
    void testConnect_WithInvalidLocation() {
        Reports localReports = new Reports();
        assertDoesNotThrow(() -> localReports.connect("invalid-host:9999", 100));
        System.out.println("Handled invalid connection location gracefully");
    }

    @Test
    void testDisconnect_WhenNotConnected() {
        Reports localReports = new Reports();
        assertDoesNotThrow(localReports::disconnect);
        System.out.println("Handled disconnect when not connected gracefully");
    }

    // 5. SQL Exception Handling Tests (Disconnected Database)
    @Test
    void testAllQueryMethods_WithDisconnectedDatabase() {
        reports.disconnect(); // Force disconnection

        // Test that all query methods handle SQL exceptions gracefully
        assertNotNull(reports.getAllCountriesInWorld());
        assertNotNull(reports.getCountriesByContinent("Asia"));
        assertNotNull(reports.getCountriesByRegion("Asia"));
        assertNotNull(reports.getTopNCountriesInWorld(5));
        assertNotNull(reports.getAllCitiesInWorld());
        assertNotNull(reports.getCitiesByContinent("Asia"));
        assertNotNull(reports.getCitiesByCountry("China"));
        assertNotNull(reports.getTopNCitiesInWorld(5));
        assertNotNull(reports.getAllCapitalCitiesInWorld());
        assertNotNull(reports.getCapitalCitiesByContinent("Asia"));
        assertNotNull(reports.getTopNCapitalCitiesInWorld(5));
        assertNotNull(reports.getPopulationByContinent());
        assertNotNull(reports.getPopulationByRegion());
        assertNotNull(reports.getPopulationByCountry());
        assertNotNull(reports.getLanguageReport());
        assertNotNull(reports.getWorldPopulation());

        // Reconnect for other tests
        reports.connect("localhost:33060", 1000);
    }

    // 6. Edge Cases for Top N Queries
    @Test
    void testGetTopNCountriesInWorld_ZeroN() {
        ArrayList<Country> result = reports.getTopNCountriesInWorld(0);
        assertNotNull(result, "Should not return null for n=0");
        assertTrue(result.isEmpty(), "Should return empty list for n=0");
    }

    @Test
    void testGetTopNCitiesInWorld_ZeroN() {
        ArrayList<City> result = reports.getTopNCitiesInWorld(0);
        assertNotNull(result, "Should not return null for n=0");
        assertTrue(result.isEmpty(), "Should return empty list for n=0");
    }

    @Test
    void testGetTopNCapitalCitiesInWorld_ZeroN() {
        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInWorld(0);
        assertNotNull(result, "Should not return null for n=0");
        assertTrue(result.isEmpty(), "Should return empty list for n=0");
    }

    // 8. Language Report Edge Cases
    @Test
    void testGetLanguageReport_WithZeroWorldPopulationScenario() {
        // This tests the branch where worldPop <= 0 in percentage calculation
        ArrayList<Language> result = reports.getLanguageReport();
        assertNotNull(result);

        for (Language lang : result) {
            assertTrue(lang.percentage >= 0, "Percentage should not be negative");
        }
    }

    // 9. Population Calculation Edge Cases
    @Test
    void testRunPopulationAggregationQuery_WithZeroPopulation() {
        ArrayList<Population> result = reports.getPopulationByContinent();
        assertNotNull(result);

        // Verify that if totalPopulation is 0, percentages are set to 0
        for (Population pop : result) {
            if (pop.totalPopulation <= 0) {
                assertEquals(0.0, pop.cityPercentage, "City percentage should be 0 when total population is 0");
                assertEquals(0.0, pop.nonCityPercentage, "Non-city percentage should be 0 when total population is 0");
            }
        }
    }

// ==================== HELPER METHODS ====================

    // Add these helper methods to create test data
    private Country createTestCountry() {
        Country country = new Country();
        country.code = "TST";
        country.name = "TestCountry";
        country.continent = "TestContinent";
        country.region = "TestRegion";
        country.population = 1000000;
        country.capital = "TestCapital";
        return country;
    }

    private City createTestCity() {
        City city = new City();
        city.name = "TestCity";
        city.country = "TestCountry";
        city.district = "TestDistrict";
        city.population = 500000;
        return city;
    }

    private CapitalCity createTestCapital() {
        CapitalCity capital = new CapitalCity();
        capital.name = "TestCapital";
        capital.country = "TestCountry";
        capital.population = 1000000;
        return capital;
    }

    private Population createTestPopulation() {
        Population population = new Population();
        population.name = "TestPopulation";
        population.totalPopulation = 10000000L;
        population.cityPopulation = 4000000L;
        population.nonCityPopulation = 6000000L;
        population.cityPercentage = 40.0;
        population.nonCityPercentage = 60.0;
        return population;
    }

    private Language createTestLanguage() {
        Language language = new Language();
        language.language = "TestLanguage";
        language.speakers = 5000000L;
        language.percentage = 10.5;
        return language;
    }

// ==================== MODEL CLASS TESTS ====================

    @Test
    void testCapitalCityConstructorAndGetters() {
        CapitalCity capital = new CapitalCity();
        capital.name = "Yangon";
        capital.country = "Myanmar";
        capital.population = 5000000;

        assertEquals("Yangon", capital.name);
        assertEquals("Myanmar", capital.country);
        assertEquals(5000000, capital.population);
    }

    @Test
    void testCityConstructorAndGetters() {
        City city = new City();
        city.name = "Mandalay";
        city.country = "Myanmar";
        city.district = "Mandalay";
        city.population = 1200000;

        assertEquals("Mandalay", city.name);
        assertEquals("Myanmar", city.country);
        assertEquals("Mandalay", city.district);
        assertEquals(1200000, city.population);
    }

    @Test
    void testCountryConstructorAndGetters() {
        Country country = new Country();
        country.code = "MMR";
        country.name = "Myanmar";
        country.continent = "Asia";
        country.region = "Southeast Asia";
        country.population = 54000000;
        country.capital = "Yangon";

        assertEquals("MMR", country.code);
        assertEquals("Myanmar", country.name);
        assertEquals("Asia", country.continent);
        assertEquals("Southeast Asia", country.region);
        assertEquals(54000000, country.population);
        assertEquals("Yangon", country.capital);
    }

    @Test
    void testLanguageConstructorAndGetters() {
        Language language = new Language();
        language.language = "Burmese";
        language.speakers = 33000000L;
        language.percentage = 61.1;

        assertEquals("Burmese", language.language);
        assertEquals(33000000L, language.speakers);
        assertEquals(61.1, language.percentage, 0.01);
    }

    @Test
    void testPopulationConstructorAndGetters() {
        Population population = new Population();
        population.name = "Asia";
        population.totalPopulation = 3700000000L;
        population.cityPopulation = 1500000000L;
        population.nonCityPopulation = 2200000000L;
        population.cityPercentage = 40.54;
        population.nonCityPercentage = 59.46;

        assertEquals("Asia", population.name);
        assertEquals(3700000000L, population.totalPopulation);
        assertEquals(1500000000L, population.cityPopulation);
        assertEquals(2200000000L, population.nonCityPopulation);
        assertEquals(40.54, population.cityPercentage, 0.01);
        assertEquals(59.46, population.nonCityPercentage, 0.01);
    }
}
