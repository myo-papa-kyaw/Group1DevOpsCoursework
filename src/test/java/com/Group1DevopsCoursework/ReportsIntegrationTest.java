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
    void testDisconnect() {
        reports.disconnect();
        System.out.println("Disconnected from server testing complete.");
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

}
