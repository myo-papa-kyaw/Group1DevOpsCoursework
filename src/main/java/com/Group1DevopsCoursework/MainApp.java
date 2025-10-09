package com.Group1DevopsCoursework;

public class MainApp {

    public static void main(String[] args) {

        // Connect to the database
        Database_Connection dbConnection = new Database_Connection();
        dbConnection.connect();

        Reports reports = new Reports(dbConnection);

        // ================== COUNTRY REPORTS ==================
        System.out.println("\n================== COUNTRY REPORTS ==================\n");

        System.out.println("1. All countries in the world (largest to smallest population):");
        reports.getAllCountriesInWorld();

        System.out.println("\n2. All countries in Asia (largest to smallest population):");
        reports.getCountriesByContinent("Asia");

        System.out.println("\n3. All countries in Eastern Asia (largest to smallest population):");
        reports.getCountriesByRegion("Eastern Asia");

        System.out.println("\n4. Top 10 populated countries in the world:");
        reports.getTopNCountriesInWorld(10);

        System.out.println("\n5. Top 5 populated countries in Asia:");
        reports.getTopNCountriesInContinent("Asia", 5);

        System.out.println("\n6. Top 5 populated countries in Eastern Asia:");
        reports.getTopNCountriesInRegion("Eastern Asia", 5);

        // ================== CITY REPORTS ==================
        System.out.println("\n================== CITY REPORTS ==================\n");

        System.out.println("7. All cities in the world (largest to smallest population):");
        reports.getAllCitiesInWorld();

        System.out.println("\n8. All cities in Asia (largest to smallest population):");
        reports.getCitiesByContinent("Asia");

        System.out.println("\n9. All cities in Eastern Asia (largest to smallest population):");
        reports.getCitiesByRegion("Eastern Asia");

        System.out.println("\n10. All cities in Korea (largest to smallest population):");
        reports.getCitiesByCountry("Korea");

        System.out.println("\n11. All cities in Seoul district (largest to smallest population):");
        reports.getCitiesByDistrict("Seoul");

        System.out.println("\n12. Top 10 populated cities in the world:");
        reports.getTopNCitiesInWorld(10);

        System.out.println("\n13. Top 5 populated cities in Asia:");
        reports.getTopNCitiesInContinent("Asia", 5);

        System.out.println("\n14. Top 5 populated cities in Eastern Asia:");
        reports.getTopNCitiesInRegion("Eastern Asia", 5);

        System.out.println("\n15. Top 5 populated cities in Korea:");
        reports.getTopNCitiesInCountry("South Korea", 5);

        System.out.println("\n16. Top 5 populated cities in Seoul district:");
        reports.getTopNCitiesInDistrict("Seoul", 5);

        // ================== CAPITAL CITY REPORTS ==================
        System.out.println("\n================== CAPITAL CITY REPORTS ==================\n");

        System.out.println("17. All capital cities in the world (largest to smallest population):");
        reports.getAllCapitalCitiesInWorld();

        System.out.println("\n18. All capital cities in Asia (largest to smallest population):");
        reports.getCapitalCitiesByContinent("Asia");

        System.out.println("\n19. All capital cities in Eastern Asia (largest to smallest population):");
        reports.getCapitalCitiesByRegion("Eastern Asia");

        System.out.println("\n20. Top 10 populated capital cities in the world:");
        reports.getTopNCapitalCitiesInWorld(10);

        System.out.println("\n21. Top 5 populated capital cities in Asia:");
        reports.getTopNCapitalCitiesInContinent("Asia", 5);

        System.out.println("\n22. Top 5 populated capital cities in Eastern Asia:");
        reports.getTopNCapitalCitiesInRegion("Eastern Asia", 5);

        // ================== POPULATION REPORTS ==================
        System.out.println("\n================== POPULATION REPORTS ==================\n");

        System.out.println("23. Population by continent:");
        reports.getPopulationByContinent();

        System.out.println("\n24. Population by region:");
        reports.getPopulationByRegion();

        System.out.println("\n25. Population by country:");
        reports.getPopulationByCountry();

        System.out.println("\n26. Total population of the world:");
        System.out.println(reports.getWorldPopulation());

        System.out.println("\n27. Population of Asia:");
        System.out.println(reports.getPopulationOfContinent("Asia"));

        System.out.println("\n28. Population of Eastern Asia:");
        System.out.println(reports.getPopulationOfRegion("Eastern Asia"));

        System.out.println("\n29. Population of Korea:");
        System.out.println(reports.getPopulationOfCountry("South Korea"));

        System.out.println("\n30. Population of Seoul district:");
        System.out.println(reports.getPopulationOfDistrict("Seoul"));

        System.out.println("\n31. Population of Seoul city:");
        System.out.println(reports.getPopulationOfCity("Seoul"));
        // ================== LANGUAGE REPORTS ==================
        System.out.println("\n================== LANGUAGE REPORTS ==================\n");

        System.out.println("32. Number of speakers of selected languages (Chinese, English, Hindi, Spanish, Arabic):");
        reports.getLanguageSpeakersReport();

        // Disconnect from the database
        dbConnection.disconnect();
    }
}
