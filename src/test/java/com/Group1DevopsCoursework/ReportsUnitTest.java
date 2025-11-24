package com.Group1DevopsCoursework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Reports class
 * Tests various report printing methods with different input scenarios
 */
class ReportsUnitTest {

    Reports reports;
    // Capture System.out output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Set up test environment before each test method
     * Redirects System.out to capture console output
     * Initializes Reports instance
     */
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));  // Capture console output
        reports = new Reports();

    }

    /**
     * Clean up after each test method
     * Restores original System.out stream
     */
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);  // Restore original System.out
    }

    // ===== Country Report Tests =====

    /**
     * Test printing countries with null input
     * Verifies method handles null input without crashing
     */
    @Test
    void printCountriesNull() {
        reports.printCountries(null);
    }

    /**
     * Test printing countries with empty list
     * Verifies method handles empty collections properly
     */
    @Test
    void printCountriesEmpty() {
        ArrayList<Country> countries = new ArrayList<>();
        reports.printCountries(countries);
    }

    /**
     * Test printing countries list containing null element
     * Verifies method handles null elements gracefully
     */
    @Test
    void printCountriesContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        reports.printCountries(countries);
    }

    /**
     * Test printing countries with valid data
     * Verifies country information is displayed correctly
     */
    @Test
    void printCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        Country c = new Country();
        c.code = "MMR";
        c.name = "Myanmar";
        c.continent = "Asia";
        c.region = "Southeast Asia";
        c.population = 54409800;
        c.capital = "Naypyidaw";
        countries.add(c);
        reports.printCountries(countries);
    }

    // ===== City Report Tests =====

    /**
     * Test printing cities with null input
     * Verifies method handles null input without crashing
     */
    @Test
    void printCitiesNull() {
        reports.printCities(null);
    }

    /**
     * Test printing cities with empty list
     * Verifies method handles empty collections properly
     */
    @Test
    void printCitiesEmpty() {
        ArrayList<City> cities = new ArrayList<>();
        reports.printCities(cities);
    }

    /**
     * Test printing cities list containing null element
     * Verifies method handles null elements gracefully
     */
    @Test
    void printCitiesContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        reports.printCities(cities);
    }

    /**
     * Test printing cities with valid data
     * Verifies city information is displayed correctly
     */
    @Test
    void printCities() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.name = "Yangon";
        city.country = "Myanmar";
        city.district = "Yangon";
        city.population = 7350000;
        cities.add(city);
        reports.printCities(cities);
    }

    // ===== Capital Report Tests =====

    /**
     * Test printing capitals with null input
     * Verifies method handles null input without crashing
     */
    @Test
    void printCapitalsNull() {
        reports.printCapitals(null);
    }

    /**
     * Test printing capitals with empty list
     * Verifies method handles empty collections properly
     */
    @Test
    void printCapitalsEmpty() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        reports.printCapitals(caps);
    }

    /**
     * Test printing capitals list containing null element
     * Verifies method handles null elements gracefully
     */
    @Test
    void printCapitalsContainsNull() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        caps.add(null);
        reports.printCapitals(caps);
    }

    /**
     * Test printing capitals with valid data
     * Verifies capital city information is displayed correctly
     */
    @Test
    void printCapitals() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        CapitalCity cap = new CapitalCity();
        cap.name = "Naypyidaw";
        cap.country = "Myanmar";
        cap.population = 1160242;
        caps.add(cap);
        reports.printCapitals(caps);
    }

    // ===== Population Report Tests =====

    /**
     * Test printing populations with null input
     * Verifies method handles null input without crashing
     */
    @Test
    void printPopulationsNull() {
        reports.printPopulations(null);
    }

    /**
     * Test printing populations with empty list
     * Verifies method handles empty collections properly
     */
    @Test
    void printPopulationsEmpty() {
        ArrayList<Population> pops = new ArrayList<>();
        reports.printPopulations(pops);
    }

    /**
     * Test printing populations list containing null element
     * Verifies method handles null elements gracefully
     */
    @Test
    void printPopulationsContainsNull() {
        ArrayList<Population> pops = new ArrayList<>();
        pops.add(null);
        reports.printPopulations(pops);
    }

    /**
     * Test printing populations with valid data
     * Verifies population statistics are displayed correctly
     */
    @Test
    void printPopulations() {
        ArrayList<Population> pops = new ArrayList<>();
        Population pop = new Population();
        pop.name = "Asia";
        pop.totalPopulation = 4600000000L;
        pop.cityPopulation = 2900000000L;
        pop.cityPercentage = 63.0;
        pop.nonCityPercentage = 37.0;
        pops.add(pop);
        reports.printPopulations(pops);
    }

    // ===== Language Report Tests =====

    /**
     * Test printing languages with null input
     * Verifies method handles null input without crashing
     */
    @Test
    void printLanguagesNull() {
        reports.printLanguages(null);
    }

    /**
     * Test printing languages with empty list
     * Verifies method handles empty collections properly
     */
    @Test
    void printLanguagesEmpty() {
        ArrayList<Language> langs = new ArrayList<>();
        reports.printLanguages(langs);
    }

    /**
     * Test printing languages list containing null element
     * Verifies method handles null elements gracefully
     */
    @Test
    void printLanguagesContainsNull() {
        ArrayList<Language> langs = new ArrayList<>();
        langs.add(null);
        reports.printLanguages(langs);
    }

    /**
     * Test printing languages with valid data
     * Verifies language statistics are displayed correctly
     */
    @Test
    void printLanguages() {
        ArrayList<Language> langs = new ArrayList<>();
        Language lang = new Language();
        lang.language = "English";
        lang.speakers = 1200000000;
        lang.percentage = 15.0;
        langs.add(lang);
        reports.printLanguages(langs);
    }

// =================== MAIN-LIKE OUTPUT TESTS ===================

    /**
     * Test country report output format and content
     * Verifies that country reports display all required fields correctly
     */
    @Test
    void outputCountriesSections() {
        ArrayList<Country> list = new ArrayList<>();
        Country c = new Country();
        c.code = "IND";
        c.name = "India";
        c.continent = "Asia";
        c.region = "Southern Asia";
        c.population = 1300000000;
        c.capital = "New Delhi";
        list.add(c);

        reports.printCountries(list);
        String output = outContent.toString();

        originalOut.println(output);

        assertTrue(output.contains("Code"));
        assertTrue(output.contains("India"));
        assertTrue(output.contains("Asia"));
        assertTrue(output.contains("New Delhi"));
    }

    /**
     * Test city report output format and content
     * Verifies that city reports display all required fields correctly
     */
    @Test
    void outputCitiesSections() {
        ArrayList<City> list = new ArrayList<>();
        City ci = new City();
        ci.name = "Mumbai";
        ci.country = "India";
        ci.district = "Maharashtra";
        ci.population = 12400000;
        list.add(ci);

        reports.printCities(list);
        String output = outContent.toString();
        originalOut.println(output);

        assertTrue(output.contains("Name"));
        assertTrue(output.contains("Mumbai"));
        assertTrue(output.contains("Maharashtra"));
    }

    /**
     * Test capital city report output format and content
     * Verifies that capital city reports display all required fields correctly
     */
    @Test
    void outputCapitalsSections() {
        ArrayList<CapitalCity> list = new ArrayList<>();
        CapitalCity cap = new CapitalCity();
        cap.name = "New Delhi";
        cap.country = "India";
        cap.population = 21800000;
        list.add(cap);

        reports.printCapitals(list);
        String output = outContent.toString();
        originalOut.println(output);

        assertTrue(output.contains("Country"));
        assertTrue(output.contains("New Delhi"));
    }

    /**
     * Test population report output format and content
     * Verifies that population reports display demographic statistics correctly
     */
    @Test
    void outputPopulationReports() {
        ArrayList<Population> pops = new ArrayList<>();
        Population p = new Population();
        p.name = "Asia";
        p.totalPopulation = 4600000000L;
        p.cityPopulation = 2900000000L;
        p.cityPercentage = 63.0;
        p.nonCityPercentage = 37.0;
        pops.add(p);

        reports.printPopulations(pops);
        String output = outContent.toString();
        originalOut.println(output);

        assertTrue(output.contains("Total Population"));
        assertTrue(output.contains("Asia"));
    }

    /**
     * Test multiple population entries in report
     * Verifies that reports can handle and display multiple population records
     */
    @Test
    void outputSinglePopulationReports() {
        Population asia = new Population();
        asia.name = "Asia";
        asia.totalPopulation = 4600000000L;

        Population country = new Population();
        country.name = "Brazil";
        country.totalPopulation = 212000000;

        reports.printPopulations(new ArrayList<>() {{
            add(asia);
            add(country);
        }});
        String output = outContent.toString();
        originalOut.println(output);
        assertTrue(output.contains("Asia"));
        assertTrue(output.contains("Brazil"));
    }

    /**
     * Test language report output format and content
     * Verifies that language reports display linguistic statistics correctly
     */
    @Test
    void outputLanguageReports() {
        ArrayList<Language> langs = new ArrayList<>();
        Language l = new Language();
        l.language = "English";
        l.speakers = 1200000000;
        l.percentage = 15.0;
        langs.add(l);

        reports.printLanguages(langs);
        String output = outContent.toString();
        originalOut.println(output);

        assertTrue(output.contains("Language"));
        assertTrue(output.contains("English"));
        assertTrue(output.contains("%"));
    }

}
