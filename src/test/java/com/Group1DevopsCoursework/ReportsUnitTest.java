package com.Group1DevopsCoursework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsUnitTest {

    Reports reports;

    @BeforeEach
    void setUp() {
        reports = new Reports();
    }

    // ===== Country Report Tests =====
    @Test
    void printCountriesNull() {
        reports.printCountries(null);
    }

    @Test
    void printCountriesEmpty() {
        ArrayList<Country> countries = new ArrayList<>();
        reports.printCountries(countries);
    }

    @Test
    void printCountriesContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        reports.printCountries(countries);
    }

    @Test
    void printCountriesWithNullFields() {
        ArrayList<Country> countries = new ArrayList<>();
        Country c = new Country();
        c.code = null;
        c.name = null;
        c.continent = null;
        c.region = null;
        c.population = 0;
        c.capital = null;
        countries.add(c);
        reports.printCountries(countries);
    }

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
    @Test
    void printCitiesNull() {
        reports.printCities(null);
    }

    @Test
    void printCitiesEmpty() {
        ArrayList<City> cities = new ArrayList<>();
        reports.printCities(cities);
    }

    @Test
    void printCitiesContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        reports.printCities(cities);
    }

    @Test
    void printCitiesWithNullFields() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.name = null;
        city.country = null;
        city.district = null;
        city.population = 0;
        cities.add(city);
        reports.printCities(cities);
    }

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
    @Test
    void printCapitalsNull() {
        reports.printCapitals(null);
    }

    @Test
    void printCapitalsEmpty() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        reports.printCapitals(caps);
    }

    @Test
    void printCapitalsContainsNull() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        caps.add(null);
        reports.printCapitals(caps);
    }

    @Test
    void printCapitalsWithNullFields() {
        ArrayList<CapitalCity> caps = new ArrayList<>();
        CapitalCity cap = new CapitalCity();
        cap.name = null;
        cap.country = null;
        cap.population = 0;
        caps.add(cap);
        reports.printCapitals(caps);
    }

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
    @Test
    void printPopulationsNull() {
        reports.printPopulations(null);
    }

    @Test
    void printPopulationsEmpty() {
        ArrayList<Population> pops = new ArrayList<>();
        reports.printPopulations(pops);
    }

    @Test
    void printPopulationsContainsNull() {
        ArrayList<Population> pops = new ArrayList<>();
        pops.add(null);
        reports.printPopulations(pops);
    }

    @Test
    void printPopulationsWithNullFields() {
        ArrayList<Population> pops = new ArrayList<>();
        Population pop = new Population();
        pop.name = null;
        pop.totalPopulation = 0;
        pop.cityPopulation = 0;
        pop.nonCityPopulation = 0;
        pop.cityPercentage = 0.0;
        pop.nonCityPercentage = 0.0;
        pops.add(pop);
        reports.printPopulations(pops);
    }

    @Test
    void printPopulationsWithZeroTotalPopulation() {
        ArrayList<Population> pops = new ArrayList<>();
        Population pop = new Population();
        pop.name = "Test Area";
        pop.totalPopulation = 0;
        pop.cityPopulation = 0;
        pop.nonCityPopulation = 0;
        pop.cityPercentage = 0.0;
        pop.nonCityPercentage = 0.0;
        pops.add(pop);
        reports.printPopulations(pops);
    }

    @Test
    void printPopulations() {
        ArrayList<Population> pops = new ArrayList<>();
        Population pop = new Population();
        pop.name = "Asia";
        pop.totalPopulation = 4600000000L;
        pop.cityPopulation = 2900000000L;
        pop.nonCityPopulation = 1700000000L;
        pop.cityPercentage = 63.0;
        pop.nonCityPercentage = 37.0;
        pops.add(pop);
        reports.printPopulations(pops);
    }

    // ===== Language Report Tests =====
    @Test
    void printLanguagesNull() {
        reports.printLanguages(null);
    }

    @Test
    void printLanguagesEmpty() {
        ArrayList<Language> langs = new ArrayList<>();
        reports.printLanguages(langs);
    }

    @Test
    void printLanguagesContainsNull() {
        ArrayList<Language> langs = new ArrayList<>();
        langs.add(null);
        reports.printLanguages(langs);
    }

    @Test
    void printLanguagesWithNullFields() {
        ArrayList<Language> langs = new ArrayList<>();
        Language lang = new Language();
        lang.language = null;
        lang.speakers = 0;
        lang.percentage = 0.0;
        langs.add(lang);
        reports.printLanguages(langs);
    }

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

    // ===== Connection Tests =====
    @Test
    void testDisconnectWhenNotConnected() {
        // Should not throw exception when disconnecting without connection
        reports.disconnect();
    }

    @Test
    void testConnectWithInvalidLocation() {
        // Test connection failure handling
        reports.connect("invalid:9999", 0);
        // Should not crash, just output error messages
    }

    // ===== Population Calculation Edge Cases =====
    @Test
    void testPopulationObjectCreation() {
        Population pop = new Population();
        assertNotNull(pop);

        // Test default values
        assertEquals(0, pop.totalPopulation);
        assertEquals(0, pop.cityPopulation);
        assertEquals(0, pop.nonCityPopulation);
        assertEquals(0.0, pop.cityPercentage);
        assertEquals(0.0, pop.nonCityPercentage);
        assertNull(pop.name);
    }

    @Test
    void testCountryObjectCreation() {
        Country country = new Country();
        assertNotNull(country);

        // Test default values
        assertNull(country.code);
        assertNull(country.name);
        assertNull(country.continent);
        assertNull(country.region);
        assertEquals(0, country.population);
        assertNull(country.capital);
    }

    @Test
    void testCityObjectCreation() {
        City city = new City();
        assertNotNull(city);

        // Test default values
        assertNull(city.name);
        assertNull(city.country);
        assertNull(city.district);
        assertEquals(0, city.population);
    }

    @Test
    void testCapitalCityObjectCreation() {
        CapitalCity capital = new CapitalCity();
        assertNotNull(capital);

        // Test default values
        assertNull(capital.name);
        assertNull(capital.country);
        assertEquals(0, capital.population);
    }

    @Test
    void testLanguageObjectCreation() {
        Language language = new Language();
        assertNotNull(language);

        // Test default values
        assertNull(language.language);
        assertEquals(0, language.speakers);
        assertEquals(0.0, language.percentage);
    }
}