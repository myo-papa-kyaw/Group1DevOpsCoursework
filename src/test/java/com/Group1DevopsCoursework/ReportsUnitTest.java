package com.Group1DevopsCoursework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsUnitTest {

    Reports reports;
    // Capture System.out output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));  // Capture console output
        reports = new Reports();

    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);  // Restore original System.out
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
// =================== MAIN-LIKE OUTPUT TESTS ===================

    // Country Reports
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

    //  City Reports
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

    // 17–22: Capital Reports
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

    // 23–25: Population Lists
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

    // 26–31: Single Population Access
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

    // 32: Language Report
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

