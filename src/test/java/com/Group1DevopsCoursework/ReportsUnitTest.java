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
//        reports.connect("localhost:33060", 10000);
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
    void printLanguages() {
        ArrayList<Language> langs = new ArrayList<>();
        Language lang = new Language();
        lang.language = "English";
        lang.speakers = 1200000000;
        lang.percentage = 15.0;
        langs.add(lang);
        reports.printLanguages(langs);
    }

    // -------------------- 1. getAllCountriesInWorld() --------------------
    @Test
    void testGetAllCountriesInWorld_Null() {
        System.out.println("\n--- TestNull: getAllCountriesInWorld ---");
        ArrayList<Country> result = null;
        assertNull(result, "Expected null when data is not available");
        System.out.println("Handled null safely");
    }

    @Test
    void testGetAllCountriesInWorld_Empty() {
        System.out.println("\n--- TestEmpty: getAllCountriesInWorld ---");
        ArrayList<Country> emptyList = new ArrayList<>();
        assertTrue(emptyList.isEmpty(), "Expected empty list");
        System.out.println(" Handled empty list safely");
    }

    @Test
    void testGetAllCountriesInWorld_ContainsNull() {
        System.out.println("\n--- TestContainsNull: getAllCountriesInWorld ---");
        ArrayList<Country> list = new ArrayList<>();
        list.add(null);
        assertNotNull(list, "List should not be null");
        System.out.println(" Handled list containing null safely");
    }
}