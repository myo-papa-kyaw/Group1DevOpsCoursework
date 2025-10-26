//package com.Group1DevopsCoursework;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//
///**
// * Comprehensive test for all Reports - works with or without database
// */
//public class ReportsUnitTest {
//
//    Reports reports;
//    Database_Connection db;
//
//    @BeforeEach
//    void setUp() {
//        System.out.println("🚀 Starting Reports Test Suite");
//        System.out.println("=" .repeat(60));
//
//        db = new Database_Connection();
//        db.connect();
//        reports = new Reports(db);
//
//        if (db.isConnected()) {
//            System.out.println("✅ DATABASE: Connected - Running live queries");
//        } else {
//            System.out.println("⚠️ DATABASE: Not connected - Testing error handling");
//        }
//        System.out.println("-".repeat(60));
//    }
//
//    @AfterEach
//    void tearDown() {
//        db.disconnect();
//        System.out.println("=" .repeat(60));
//    }
//
//    // ==================== COUNTRY REPORTS ====================
//
//    @Test
//    void testAllCountriesWorld() {
//        System.out.println("\n1. 🌍 ALL COUNTRIES IN WORLD");
//        reports.getAllCountriesInWorld();
//    }
//
//    @Test
//    void testCountriesByContinent() {
//        System.out.println("\n2. 🌏 COUNTRIES IN ASIA");
//        reports.getCountriesByContinent("Asia");
//    }
//
//    @Test
//    void testCountriesByRegion() {
//        System.out.println("\n3. 🗺️ COUNTRIES IN SOUTHEAST ASIA");
//        reports.getCountriesByRegion("Southeast Asia");
//    }
//
//    @Test
//    void testTopCountriesWorld() {
//        System.out.println("\n4. 🏆 TOP 5 COUNTRIES IN WORLD");
//        reports.getTopNCountriesInWorld(5);
//    }
//
//    // ==================== CITY REPORTS ====================
//
//    @Test
//    void testAllCitiesWorld() {
//        System.out.println("\n5. 🏙️ ALL CITIES IN WORLD");
//        reports.getAllCitiesInWorld();
//    }
//
//    @Test
//    void testCitiesByContinent() {
//        System.out.println("\n6. 🏞️ CITIES IN ASIA");
//        reports.getCitiesByContinent("Asia");
//    }
//
//    @Test
//    void testCitiesByRegion() {
//        System.out.println("\n7. 🌆 CITIES IN SOUTHEAST ASIA");
//        reports.getCitiesByRegion("Southeast Asia");
//    }
//
//    @Test
//    void testCitiesByCountry() {
//        System.out.println("\n8. 🇲🇲 CITIES IN MYANMAR");
//        reports.getCitiesByCountry("Myanmar");
//    }
//
//    @Test
//    void testCitiesByDistrict() {
//        System.out.println("\n9. 🏘️ CITIES IN YANGON DISTRICT");
//        reports.getCitiesByDistrict("Yangon");
//    }
//
//    @Test
//    void testTopCitiesWorld() {
//        System.out.println("\n10. 🏆 TOP 10 CITIES IN WORLD");
//        reports.getTopNCitiesInWorld(10);
//    }
//
//    // ==================== CAPITAL CITY REPORTS ====================
//
//    @Test
//    void testAllCapitalCities() {
//        System.out.println("\n11. 🏛️ ALL CAPITAL CITIES IN WORLD");
//        reports.getAllCapitalCitiesInWorld();
//    }
//
//    @Test
//    void testCapitalCitiesByContinent() {
//        System.out.println("\n12. 🌏 CAPITAL CITIES IN ASIA");
//        reports.getCapitalCitiesByContinent("Asia");
//    }
//
//    @Test
//    void testCapitalCitiesByRegion() {
//        System.out.println("\n13. 🗺️ CAPITAL CITIES IN SOUTHEAST ASIA");
//        reports.getCapitalCitiesByRegion("Southeast Asia");
//    }
//
//    // ==================== POPULATION REPORTS ====================
//
//    @Test
//    void testWorldPopulation() {
//        System.out.println("\n14. 📊 WORLD POPULATION");
//        long population = reports.getWorldPopulation();
//        System.out.println("World Population: " + population);
//    }
//
//    @Test
//    void testPopulationByContinent() {
//        System.out.println("\n15. 📊 POPULATION IN ASIA");
//        reports.getPopulationByContinent("Asia");
//    }
//
//    @Test
//    void testPopulationByRegion() {
//        System.out.println("\n16. 📊 POPULATION IN SOUTHEAST ASIA");
//        reports.getPopulationByRegion("Southeast Asia");
//    }
//
//    @Test
//    void testPopulationByCountry() {
//        System.out.println("\n17. 📊 POPULATION IN MYANMAR");
//        reports.getPopulationByCountry("Myanmar");
//    }
//
//    // ==================== LANGUAGE REPORT ====================
//
//    @Test
//    void testLanguageSpeakers() {
//        System.out.println("\n18. 🗣️ LANGUAGE SPEAKERS REPORT");
//        reports.getLanguageSpeakersReport();
//    }
//
//    // ==================== ERROR HANDLING TESTS ====================
//
//    @Test
//    void testNullConnection() {
//        System.out.println("\n19. ⚠️ NULL DATABASE CONNECTION HANDLING");
//        Reports nullReports = new Reports(null);
//        nullReports.getAllCountriesInWorld();
//    }
//
//    @Test
//    void testNullContinent() {
//        System.out.println("\n20. ⚠️ NULL CONTINENT INPUT HANDLING");
//        reports.getCountriesByContinent(null);
//    }
//
//    @Test
//    void testNullCountry() {
//        System.out.println("\n21. ⚠️ NULL COUNTRY INPUT HANDLING");
//        reports.getCitiesByCountry(null);
//    }
//
//    // ==================== COMPREHENSIVE TEST ====================
//
//    @Test
//    void testAllReportsComprehensive() {
//        System.out.println("\n" + "⭐".repeat(20));
//        System.out.println("COMPREHENSIVE TEST SUITE");
//        System.out.println("⭐".repeat(20));
//
//        if (!db.isConnected()) {
//            System.out.println("Running in offline mode - testing error handling");
//        }
//
//        // Test a sample of each report type
//        System.out.println("\n📋 SAMPLE COUNTRY REPORT:");
//        reports.getAllCountriesInWorld();
//
//        System.out.println("\n🏙️ SAMPLE CITY REPORT:");
//        reports.getAllCitiesInWorld();
//
//        System.out.println("\n🏛️ SAMPLE CAPITAL CITY REPORT:");
//        reports.getAllCapitalCitiesInWorld();
//
//        System.out.println("\n📊 SAMPLE POPULATION REPORT:");
//        reports.getPopulationByContinent("Asia");
//
//        System.out.println("\n✅ COMPREHENSIVE TEST COMPLETED");
//    }
//}