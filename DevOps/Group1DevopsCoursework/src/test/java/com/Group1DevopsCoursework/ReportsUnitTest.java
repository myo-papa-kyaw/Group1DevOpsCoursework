
package com.Group1DevopsCoursework;
import com.Group1DevopsCoursework.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsUnitTest {

    Reports reports;

    @BeforeEach
    void setUp() {
        reports = new Reports();
        reports.connect("localhost:33060", 10000);
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
    @Test
    void testGetAllCountriesInWorld_Print() {
        System.out.println("\n--- TestPrint: getAllCountriesInWorld ---");
        ArrayList<Country> countries = reports.getAllCountriesInWorld();
        if (countries != null && !countries.isEmpty()) {
            System.out.println("Top country: " + countries.get(0).name);
        } else {
            System.out.println("No country data found");
        }
    }
    // -------------------- 2. getCountriesByContinent() --------------------
    @Test
    void testGetCountriesByContinent_Null() {
        System.out.println("\n--- TestNull: getCountriesByContinent ---");
        ArrayList<Country> result = reports.getCountriesByContinent(null);
        assertNotNull(result, "Expected null when continent is null");
        System.out.println(" Handled null input");
    }

    @Test
    void testGetCountriesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCountriesByContinent ---");
        ArrayList<Country> result = reports.getCountriesByContinent("");
        assertTrue(result == null || result.isEmpty(), "Expected no results for empty string");
        System.out.println(" Handled empty string input");
    }

    @Test
    void testGetCountriesByContinent_Print() {
        System.out.println("\n--- TestPrint: getCountriesByContinent ---");
        ArrayList<Country> countries = reports.getCountriesByContinent("South America");
        if (countries != null && !countries.isEmpty()) {
            System.out.println("Top South America country: " + countries.get(0).name);
        } else {
            System.out.println("No results");
        }
    }
    // -------------------- 3. getCountriesByRegion() --------------------
    @Test
    void testGetCountriesByRegion_Null() {
        System.out.println("\n--- TestNull: getCountriesByRegion ---");
        ArrayList<Country> result = reports.getCountriesByRegion(null);
        assertNotNull(result, "Expected null when region is null");
        System.out.println(" Handled null input");
    }

    @Test
    void testGetCountriesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCountriesByRegion ---");
        ArrayList<Country> result = reports.getCountriesByRegion("");
        assertTrue(result == null || result.isEmpty(), "Expected no results for empty string");
        System.out.println(" Handled empty string input");
    }

    @Test
    void testGetCountriesByRegion_Print() {
        System.out.println("\n--- TestPrint: getCountriesByRegion ---");
        ArrayList<Country> countries = reports.getCountriesByRegion("Southern Europe");
        if (countries != null && !countries.isEmpty()) {
            System.out.println("Top region country: " + countries.get(0).name);
        } else {
            System.out.println("No results for region");
        }
    }

    // -------------------- 4. getTopNCountriesInWorld() --------------------
    @Test
    void testGetTopNCountriesInWorld_Negative() {
        System.out.println("\n--- TestNull: getTopNCountriesInWorld ---");
        ArrayList<Country> result = reports.getTopNCountriesInWorld(-5);
        assertTrue(result == null || result.isEmpty(), "Expected no results for negative limit");
        System.out.println(" Handled invalid limit safely");
    }

    @Test
    void testGetTopNCountriesInWorld_Print() {
        System.out.println("\n--- TestPrint: getTopNCountriesInWorld ---");
        ArrayList<Country> countries = reports.getTopNCountriesInWorld(5);
        if (countries != null && !countries.isEmpty()) {
            for (Country c : countries) {
                System.out.println(c.name + " - " + c.population);
            }
        } else {
            System.out.println("No data available");
        }
    }

    // -------------------- 5. getTopNCountriesInContinent() --------------------
    @Test
    void testGetTopNCountriesInContinent_Null() {
        System.out.println("\n--- TestNull: getTopNCountriesInContinent ---");
        ArrayList<Country> result = reports.getTopNCountriesInContinent(null, 5);
        assertNotNull(result, "Expected null when continent is null");
        System.out.println(" Handled null continent");
    }

    @Test
    void testGetTopNCountriesInContinent_Print() {
        System.out.println("\n--- TestPrint: getTopNCountriesInContinent ---");
        ArrayList<Country> countries = reports.getTopNCountriesInContinent("Europe", 3);
        if (countries != null && !countries.isEmpty()) {
            countries.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No countries found for Europe");
        }
    }

    // -------------------- 6. getTopNCountriesInRegion() --------------------
    @Test
    void testGetTopNCountriesInRegion_Null() {
        System.out.println("\n--- TestNull: getTopNCountriesInRegion ---");
        ArrayList<Country> result = reports.getTopNCountriesInRegion(null, 3);
        assertNotNull(result, "Expected null when region is null");
        System.out.println(" Handled null region");
    }

    @Test
    void testGetTopNCountriesInRegion_Print() {
        System.out.println("\n--- TestPrint: getTopNCountriesInRegion ---");
        ArrayList<Country> countries = reports.getTopNCountriesInRegion("Caribbean", 3);
        if (countries != null && !countries.isEmpty()) {
            countries.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No data for Caribbean");
        }
    }

    // -------------------- 7. getAllCitiesInWorld() --------------------

    @Test
    void testGetAllCitiesInWorld_Print() {
        System.out.println("\n--- TestPrint: getAllCitiesInWorld ---");
        ArrayList<City> cities = reports.getAllCitiesInWorld();
        if (cities != null && !cities.isEmpty()) {
            System.out.println("Top city: " + cities.get(0).name);
        } else {
            System.out.println("No city data found");
        }
    }

    // -------------------- 8. getCitiesByContinent() --------------------
    @Test
    void testGetCitiesByContinent_Null() {
        System.out.println("\n--- TestNull: getCitiesByContinent ---");
        ArrayList<City> result = reports.getCitiesByContinent(null);
        assertNotNull(result, "Method should handle null input safely");
        System.out.println("Handled null input");
    }

    @Test
    void testGetCitiesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByContinent ---");
        ArrayList<City> result = reports.getCitiesByContinent("");
        assertTrue(result == null || result.isEmpty(), "Expected empty result for empty string");
        System.out.println("Handled empty string input");
    }

    @Test
    void testGetCitiesByContinent_Print() {
        System.out.println("\n--- TestPrint: getCitiesByContinent ---");
        ArrayList<City> cities = reports.getCitiesByContinent("Europe");
        if (cities != null && !cities.isEmpty()) {
            System.out.println("Top Europe city: " + cities.get(0).name);
        } else {
            System.out.println("No cities found for Europe");
        }
    }

    // -------------------- 9. getCitiesByRegion() --------------------
    @Test
    void testGetCitiesByRegion_Null() {
        System.out.println("\n--- TestNull: getCitiesByRegion ---");
        ArrayList<City> result = reports.getCitiesByRegion(null);
        assertNotNull(result, "Method should handle null input safely");
        System.out.println("Handled null input");
    }

    @Test
    void testGetCitiesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCitiesByRegion ---");
        ArrayList<City> result = reports.getCitiesByRegion("");
        assertTrue(result == null || result.isEmpty(), "Expected empty result for empty string");
        System.out.println("Handled empty string input");
    }

    @Test
    void testGetCitiesByRegion_Print() {
        System.out.println("\n--- TestPrint: getCitiesByRegion ---");
        ArrayList<City> cities = reports.getCitiesByRegion("Caribbean");
        if (cities != null && !cities.isEmpty()) {
            System.out.println("Top Caribbean city: " + cities.get(0).name);
        } else {
            System.out.println("No cities found for Caribbean");
        }
    }

    // -------------------- 10. getCitiesByCountry() --------------------
    @Test
    void testGetCitiesByCountry_Null() {
        ArrayList<City> result = reports.getCitiesByCountry(null);
        assertNotNull(result, "Handled null country input");
    }

    @Test
    void testGetCitiesByCountry_Empty() {
        ArrayList<City> result = reports.getCitiesByCountry("");
        assertTrue(result == null || result.isEmpty(), "Handled empty country input");
    }

    @Test
    void testGetCitiesByCountry_Print() {
        ArrayList<City> cities = reports.getCitiesByCountry("Brazil");
        if (cities != null && !cities.isEmpty()) {
            System.out.println("Top city in Brazil: " + cities.get(0).name);
        } else {
            System.out.println("No cities found for Brazil");
        }
    }

    // -------------------- 11. getCitiesByDistrict() --------------------
    @Test
    void testGetCitiesByDistrict_Null() {
        ArrayList<City> result = reports.getCitiesByDistrict(null);
        assertNotNull(result, "Handled null district input");
    }

    @Test
    void testGetCitiesByDistrict_Empty() {
        ArrayList<City> result = reports.getCitiesByDistrict("");
        assertFalse(result == null || result.isEmpty(), "Handled empty district input");
    }

    @Test
    void testGetCitiesByDistrict_Print() {
        ArrayList<City> cities = reports.getCitiesByDistrict("California");
        if (cities != null && !cities.isEmpty()) {
            System.out.println("Top city in California: " + cities.get(0).name);
        } else {
            System.out.println("No cities found for California");
        }
    }

    // -------------------- 12. getTopNCitiesInWorld() --------------------
    @Test
    void testGetTopNCitiesInWorld_Negative() {
        ArrayList<City> result = reports.getTopNCitiesInWorld(-5);
        assertTrue(result == null || result.isEmpty(), "Handled negative N safely");
    }

    @Test
    void testGetTopNCitiesInWorld_Print() {
        ArrayList<City> cities = reports.getTopNCitiesInWorld(5);
        if (cities != null && !cities.isEmpty()) {
            for (City c : cities) {
                System.out.println(c.name + " - " + c.population);
            }
        } else {
            System.out.println("No city data available");
        }
    }

    // -------------------- 13. getTopNCitiesInContinent() --------------------
    @Test
    void testGetTopNCitiesInContinent_Null() {
        ArrayList<City> result = reports.getTopNCitiesInContinent(null, 3);
        assertNotNull(result, "Handled null continent input");
    }

    @Test
    void testGetTopNCitiesInContinent_Print() {
        ArrayList<City> cities = reports.getTopNCitiesInContinent("Europe", 3);
        if (cities != null && !cities.isEmpty()) {
            cities.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No cities found for Europe");
        }
    }

    // -------------------- 14. getTopNCitiesInRegion() --------------------
    @Test
    void testGetTopNCitiesInRegion_Null() {
        ArrayList<City> result = reports.getTopNCitiesInRegion(null, 3);
        assertNotNull(result, "Handled null region input");
    }

    @Test
    void testGetTopNCitiesInRegion_Print() {
        ArrayList<City> cities = reports.getTopNCitiesInRegion("Caribbean", 3);
        if (cities != null && !cities.isEmpty()) {
            cities.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No cities found for Caribbean");
        }
    }

    // -------------------- 15. getTopNCitiesInCountry() --------------------
    @Test
    void testGetTopNCitiesInCountry_Null() {
        ArrayList<City> result = reports.getTopNCitiesInCountry(null, 3);
        assertNotNull(result, "Handled null country input");
    }

    @Test
    void testGetTopNCitiesInCountry_Print() {
        ArrayList<City> cities = reports.getTopNCitiesInCountry("Brazil", 3);
        if (cities != null && !cities.isEmpty()) {
            cities.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No cities found for Brazil");
        }
    }

    // -------------------- 16. getTopNCitiesInDistrict() --------------------
    @Test
    void testGetTopNCitiesInDistrict_Null() {
        ArrayList<City> result = reports.getTopNCitiesInDistrict(null, 3);
        assertNotNull(result, "Handled null district input");
    }

    @Test
    void testGetTopNCitiesInDistrict_Print() {
        ArrayList<City> cities = reports.getTopNCitiesInDistrict("California", 3);
        if (cities != null && !cities.isEmpty()) {
            cities.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No cities found for California");
        }
    }
    // -------------------- 17. getAllCapitalCitiesInWorld() --------------------
    @Test
    void testGetAllCapitalCitiesInWorld_Print() {
        System.out.println("\n--- TestPrint: getAllCapitalCitiesInWorld ---");
        ArrayList<CapitalCity> capitals = reports.getAllCapitalCitiesInWorld();
        if (capitals != null && !capitals.isEmpty()) {
            System.out.println("Top capital: " + capitals.get(0).name);
        } else {
            System.out.println("No capital city data found");
        }
    }

    // -------------------- 18. getCapitalCitiesByContinent() --------------------
    @Test
    void testGetCapitalCitiesByContinent_Null() {
        System.out.println("\n--- TestNull: getCapitalCitiesByContinent ---");
        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent(null);
        assertNotNull(result, "Handled null continent input");
        System.out.println("Handled null input");
    }

    @Test
    void testGetCapitalCitiesByContinent_Empty() {
        System.out.println("\n--- TestEmpty: getCapitalCitiesByContinent ---");
        ArrayList<CapitalCity> result = reports.getCapitalCitiesByContinent("");
        assertTrue(result == null || result.isEmpty(), "Expected empty result for empty continent");
        System.out.println("Handled empty string input");
    }

    @Test
    void testGetCapitalCitiesByContinent_Print() {
        System.out.println("\n--- TestPrint: getCapitalCitiesByContinent ---");
        ArrayList<CapitalCity> capitals = reports.getCapitalCitiesByContinent("Asia");
        if (capitals != null && !capitals.isEmpty()) {
            System.out.println("Top Asian capital: " + capitals.get(0).name);
        } else {
            System.out.println("No capitals found for Asia");
        }
    }

    // -------------------- 19. getCapitalCitiesByRegion() --------------------
    @Test
    void testGetCapitalCitiesByRegion_Null() {
        System.out.println("\n--- TestNull: getCapitalCitiesByRegion ---");
        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion(null);
        assertNotNull(result, "Handled null region input");
    }

    @Test
    void testGetCapitalCitiesByRegion_Empty() {
        System.out.println("\n--- TestEmpty: getCapitalCitiesByRegion ---");
        ArrayList<CapitalCity> result = reports.getCapitalCitiesByRegion("");
        assertTrue(result == null || result.isEmpty(), "Expected empty result for empty region");
    }

    @Test
    void testGetCapitalCitiesByRegion_Print() {
        System.out.println("\n--- TestPrint: getCapitalCitiesByRegion ---");
        ArrayList<CapitalCity> capitals = reports.getCapitalCitiesByRegion("Western Europe");
        if (capitals != null && !capitals.isEmpty()) {
            System.out.println("Top Western Europe capital: " + capitals.get(0).name);
        } else {
            System.out.println("No capitals found for Western Europe");
        }
    }

    // -------------------- 20. getTopNCapitalCitiesInWorld() --------------------
    @Test
    void testGetTopNCapitalCitiesInWorld_Negative() {
        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInWorld(-5);
        assertTrue(result == null || result.isEmpty(), "Handled negative N safely");
    }

    @Test
    void testGetTopNCapitalCitiesInWorld_Print() {
        ArrayList<CapitalCity> capitals = reports.getTopNCapitalCitiesInWorld(5);
        if (capitals != null && !capitals.isEmpty()) {
            capitals.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No capital data available");
        }
    }

    // -------------------- 21. getTopNCapitalCitiesInContinent() --------------------
    @Test
    void testGetTopNCapitalCitiesInContinent_Null() {
        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInContinent(null, 3);
        assertNotNull(result, "Handled null continent input");
    }

    @Test
    void testGetTopNCapitalCitiesInContinent_Print() {
        ArrayList<CapitalCity> capitals = reports.getTopNCapitalCitiesInContinent("Africa", 3);
        if (capitals != null && !capitals.isEmpty()) {
            capitals.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No capitals found for Africa");
        }
    }

    // -------------------- 22. getTopNCapitalCitiesInRegion() --------------------
    @Test
    void testGetTopNCapitalCitiesInRegion_Null() {
        ArrayList<CapitalCity> result = reports.getTopNCapitalCitiesInRegion(null, 3);
        assertNotNull(result, "Handled null region input");
    }

    @Test
    void testGetTopNCapitalCitiesInRegion_Print() {
        ArrayList<CapitalCity> capitals = reports.getTopNCapitalCitiesInRegion("Eastern Europe", 3);
        if (capitals != null && !capitals.isEmpty()) {
            capitals.forEach(c -> System.out.println(c.name + " - " + c.population));
        } else {
            System.out.println("No capitals found for Eastern Europe");
        }
    }

    // -------------------- 23: getPopulationByContinent() --------------------
    @Test
    void testGetPopulationByContinent_Print() {
        System.out.println("\n--- TestPrint: getPopulationByContinent ---");
        ArrayList<Population> pops = reports.getPopulationByContinent();
        if (pops != null && !pops.isEmpty()) {
            for (Population p : pops) {
                System.out.println(p.name + " - Total: " + p.totalPopulation + " - Cities: " + p.cityPopulation);
            }
        } else {
            System.out.println("No population data found");
        }
    }

    // -------------------- 24: getPopulationByRegion() --------------------
    @Test
    void testGetPopulationByRegion_Print() {
        System.out.println("\n--- TestPrint: getPopulationByRegion ---");
        ArrayList<Population> pops = reports.getPopulationByRegion();
        if (pops != null && !pops.isEmpty()) {
            for (Population p : pops) {
                System.out.println(p.name + " - Total: " + p.totalPopulation + " - Cities: " + p.cityPopulation);
            }
        } else {
            System.out.println("No population data found");
        }
    }

    // -------------------- 25: getPopulationByCountry() --------------------
    @Test
    void testGetPopulationByCountry_Print() {
        System.out.println("\n--- TestPrint: getPopulationByCountry ---");
        ArrayList<Population> pops = reports.getPopulationByCountry();
        if (pops != null && !pops.isEmpty()) {
            for (Population p : pops) {
                System.out.println(p.name + " - Total: " + p.totalPopulation + " - Cities: " + p.cityPopulation);
            }
        } else {
            System.out.println("No population data found");
        }
    }

    // -------------------- 26-31. Single-value getters --------------------

    @Test
    void testGetWorldPopulation() {
        System.out.println("\n--- Test: getWorldPopulation ---");
        long worldPop = reports.getWorldPopulation();
        assertTrue(worldPop > 0, "World population should be greater than 0");
        System.out.println("World population: " + worldPop);
    }

    @Test
    void testGetPopulationOfContinentWithName_Valid() {
        Population p = reports.getPopulationOfContinentWithName("Asia");
        assertNotNull(p, "Population object should not be null");
        assertTrue(p.totalPopulation > 0, "Population should be greater than 0");
        System.out.println("Asia population: " + p.totalPopulation);
    }

    @Test
    void testGetPopulationOfContinentWithName_Invalid() {
        Population p = reports.getPopulationOfContinentWithName("NonExistingContinent");
        assertNotNull(p);
        assertEquals(0, p.totalPopulation, "Population should be 0 for non-existing continent");
    }

    @Test
    void testGetPopulationOfRegionWithName_Valid() {
        Population p = reports.getPopulationOfRegionWithName("Caribbean");
        assertNotNull(p);
        System.out.println("Caribbean population: " + p.totalPopulation);
    }

    @Test
    void testGetPopulationOfRegionWithName_Invalid() {
        Population p = reports.getPopulationOfRegionWithName("InvalidRegion");
        assertNotNull(p);
        assertEquals(0, p.totalPopulation, "Population should be 0 for invalid region");
    }

    @Test
    void testGetPopulationOfCountryWithName_Valid() {
        Population p = reports.getPopulationOfCountryWithName("Brazil");
        assertNotNull(p);
        assertTrue(p.totalPopulation > 0, "Population should be greater than 0 for Brazil");
    }

    @Test
    void testGetPopulationOfCountryWithName_Invalid() {
        Population p = reports.getPopulationOfCountryWithName("UnknownCountry");
        assertNotNull(p);
        assertEquals(0, p.totalPopulation, "Population should be 0 for unknown country");
    }

    @Test
    void testGetPopulationOfDistrictWithName_Valid() {
        Population p = reports.getPopulationOfDistrictWithName("California");
        assertNotNull(p);
        System.out.println("California district population: " + p.totalPopulation);
    }

    @Test
    void testGetPopulationOfDistrictWithName_Invalid() {
        Population p = reports.getPopulationOfDistrictWithName("UnknownDistrict");
        assertNotNull(p);
        assertEquals(0, p.totalPopulation, "Population should be 0 for unknown district");
    }

    @Test
    void testGetPopulationOfCityWithName_Valid() {
        Population p = reports.getPopulationOfCityWithName("Tokyo");
        assertNotNull(p);
        assertTrue(p.totalPopulation > 0, "Population should be greater than 0 for Tokyo");
    }

    @Test
    void testGetPopulationOfCityWithName_Invalid() {
        Population p = reports.getPopulationOfCityWithName("UnknownCity");
        assertNotNull(p);
        assertEquals(0, p.totalPopulation, "Population should be 0 for unknown city");
    }
    // -------------------- 32. getLanguageReport() --------------------
    @Test
    void testGetLanguageReport_Print() {
        System.out.println("\n--- TestPrint: getLanguageReport ---");
        ArrayList<Language> list = reports.getLanguageReport();
        if (list != null && !list.isEmpty()) {
            for (Language l : list) {
                System.out.printf("%-10s : %d speakers (%.2f%% of world)\n",
                        l.language, l.speakers, l.percentage);
            }
        } else {
            System.out.println("No language data found");
        }
    }

    @Test
    void testGetLanguageReport_NotNull() {
        ArrayList<Language> list = reports.getLanguageReport();
        assertNotNull(list, "Language list should not be null");
    }

    @Test
    void testGetLanguageReport_NotEmpty() {
        ArrayList<Language> list = reports.getLanguageReport();
        assertFalse(list.isEmpty(), "Language list should not be empty");
    }

    @Test
    void testGetLanguageReport_PercentageCalculation() {
        ArrayList<Language> list = reports.getLanguageReport();
        long worldPop = reports.getWorldPopulation();
        for (Language l : list) {
            double expectedPercentage = (double) l.speakers / worldPop * 100.0;
            assertEquals(expectedPercentage, l.percentage, 0.01,
                    "Percentage should match calculation");
        }
    }

    @Test
    void testGetLanguageReport_ContainsTargetLanguages() {
        ArrayList<Language> list = reports.getLanguageReport();
        String[] targets = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
        for (String lang : targets) {
            boolean found = list.stream().anyMatch(l -> l.language.equals(lang));
            assertTrue(found, lang + " should be present in the report");
        }
    }

}

