package com.Group1DevopsCoursework;
/**
 * --------------------------------------------------------------
 * Class: MainApp
 * Description:
 *     This is the main entry point of the DevOps Coursework project.
 *     It connects to the world database, generates, and displays
 *     32 different types of reports such as Country, City, Capital City,
 *     Population, and Language reports.
 * --------------------------------------------------------------
 */
public class MainApp {

    public static void main(String[] args) {

        // Connect to the database
        Database_Connection dbConnection = new Database_Connection();
        dbConnection.connect();

        // Create a Reports object to run all report queries
        Reports reports = new Reports(dbConnection);

        // ================== COUNTRY REPORTS ==================
        System.out.println("\n================== COUNTRY REPORTS ==================\n");

        // 1. Display all countries in the world, ordered by population (descending)
        System.out.println("1. All countries in world (largest to smallest population):");
        reports.getAllCountriesInWorld();

        // 2. Display all countries in Asia, ordered by population (descending)
        System.out.println("\n2. All countries in Asia (largest to smallest population):");
        reports.getCountriesByContinent("Asia");

        // 3. Display all countries in Eastern Asia
        System.out.println("\n3. All countries in Eastern Asia (largest to smallest population):");
        reports.getCountriesByRegion("Eastern Asia");

        // 4–6: Display top N countries by population globally, by continent, and by region
        System.out.println("\n4. Top 10 populated countries in the world:");
        reports.getTopNCountriesInWorld(10);

        System.out.println("\n5. Top 5 populated countries in Asia:");
        reports.getTopNCountriesInContinent("Asia", 5);

        System.out.println("\n6. Top 5 populated countries in Eastern Asia:");
        reports.getTopNCountriesInRegion("Eastern Asia", 5);


        // ================== CITY REPORTS ==================
        System.out.println("\n================== CITY REPORTS ==================\n");

        // 7–11: Display city reports grouped by world, continent, region, country, and district
        System.out.println("7. All cities in the world (largest to smallest population):");
        reports.getAllCitiesInWorld();

        System.out.println("\n8. All cities in Asia (largest to smallest population):");
        reports.getCitiesByContinent("Asia");

        System.out.println("\n9. All cities in Eastern Asia (largest to smallest population):");
        reports.getCitiesByRegion("Eastern Asia");

        System.out.println("\n10. All cities in India (largest to smallest population):");
        reports.getCitiesByCountry("India");

        System.out.println("\n11. All cities in Maharashtra district (largest to smallest population):");
        reports.getCitiesByDistrict("Maharashtra");

        // 12–16: Display top N populated cities for world, continent, region, country, and district
        System.out.println("\n12. Top 10 populated cities in the world:");
        reports.getTopNCitiesInWorld(10);

        System.out.println("\n13. Top 5 populated cities in Asia:");
        reports.getTopNCitiesInContinent("Asia", 5);

        System.out.println("\n14. Top 5 populated cities in Eastern Asia:");
        reports.getTopNCitiesInRegion("Eastern Asia", 5);

        System.out.println("\n15. Top 5 populated cities in Japan:");
        reports.getTopNCitiesInCountry("Japan", 5);

        System.out.println("\n16. Top 5 populated cities in São Paulo district:");
        reports.getTopNCitiesInDistrict("São Paulo", 5);


        // ================== CAPITAL CITY REPORTS ==================
        System.out.println("\n================== CAPITAL CITY REPORTS ==================\n");

        // 17–19: Display all capital cities grouped by world, continent, and region
        System.out.println("17. All capital cities in the world (largest to smallest population):");
        reports.getAllCapitalCitiesInWorld();

        System.out.println("\n18. All capital cities in Asia (largest to smallest population):");
        reports.getCapitalCitiesByContinent("Asia");

        System.out.println("\n19. All capital cities in Eastern Asia (largest to smallest population):");
        reports.getCapitalCitiesByRegion("Eastern Asia");

        // 20–22: Display top N populated capital cities for world, continent, and region
        System.out.println("\n20. Top 10 populated capital cities in the world:");
        reports.getTopNCapitalCitiesInWorld(10);

        System.out.println("\n21. Top 5 populated capital cities in Asia:");
        reports.getTopNCapitalCitiesInContinent("Asia", 5);

        System.out.println("\n22. Top 5 populated capital cities in Eastern Asia:");
        reports.getTopNCapitalCitiesInRegion("Eastern Asia", 5);


        // ================== POPULATION REPORTS ==================
        System.out.println("\n================== POPULATION REPORTS ==================\n");

        // 23–25: Display population distribution by continent, region, and country
        System.out.println("23. Population by continent:");
        reports.getPopulationByContinent();

        System.out.println("\n24. Population by region:");
        reports.getPopulationByRegion();

        System.out.println("\n25. Population by country:");
        reports.getPopulationByCountry();

        // 26–31: Display total or specific population statistics
        System.out.println("\n26. Total population of the world:");
        System.out.println(reports.getWorldPopulation());

        System.out.println("\n27. Population of Asia:");
        System.out.println(reports.getPopulationOfContinent("Asia"));

        System.out.println("\n28. Population of Eastern Asia:");
        System.out.println(reports.getPopulationOfRegion("Eastern Asia"));

        System.out.println("\n29. Population of Brazil:");
        System.out.println(reports.getPopulationOfCountry("Brazil"));

        System.out.println("\n30. Population of São Paulo district:");
        System.out.println(reports.getPopulationOfDistrict("São Paulo"));

        System.out.println("\n31. Population of São Paulo city:");
        System.out.println(reports.getPopulationOfCity("São Paulo"));


        // ================== LANGUAGE REPORTS ==================
        System.out.println("\n================== LANGUAGE REPORTS ==================\n");

        // 32: Display number of speakers for selected global languages
        System.out.println("32. Number of speakers of selected languages (Chinese, English, Hindi, Spanish, Arabic):");
        reports.getLanguageSpeakersReport();

        // Disconnect from the database
        dbConnection.disconnect();
    }
}
