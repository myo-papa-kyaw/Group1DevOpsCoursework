package com.Group1DevopsCoursework;

public class MainApp {
    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();
        Reports reports = new Reports(db);

        System.out.println("\n========== WORLD POPULATION SYSTEM ==========\n");

        System.out.println("\n1 - All countries (world) by population");
        reports.getAllCountries();

        System.out.println("\n2 - All countries in a continent by population (Asia)");
        reports.getCountriesByContinent("Asia");

        System.out.println("\n3 - All countries in a region by population (Example: Southern Europe)");
        reports.getCountriesByRegion("Southern Europe");

        System.out.println("\n4 - Top 10 countries (world)");
        reports.getTopNCountries(10);

        System.out.println("\n5 - Top 10 countries in a continent (Asia)");
        reports.getTopNCountriesByContinent("Asia", 10);

        System.out.println("\n6 - Top 10 countries in a region (Example: Western Africa)");
        reports.getTopNCountriesByRegion("Western Africa", 10);

        System.out.println("\n7 - All cities (world) by population");
        reports.getAllCities();

        System.out.println("\n8 - All cities in a continent by population (Europe)");
        reports.getCitiesByContinent("Europe");

        System.out.println("\n9 - All cities in a region by population (Caribbean)");
        reports.getCitiesByRegion("Caribbean");

        System.out.println("\n10 - All cities in a country by population (USA)");
        reports.getCitiesByCountry("USA");

        System.out.println("\n11 - All cities in a district by population (California)");
        reports.getCitiesByDistrict("California");

        System.out.println("\n12 - Top 10 cities (world)");
        reports.getTopNCities(10);

        System.out.println("\n13 - Top 10 cities in a continent (Asia)");
        reports.getTopNCitiesByContinent("Asia", 10);

        System.out.println("\n14 - Top 10 populated cities in a region (Caribbean)");
        reports.getTopNCitiesByRegion("Caribbean", 10);

        System.out.println("\n15 - Top 10 populated cities in a country (USA)");
        reports.getTopNCitiesByCountry("USA", 10);

        System.out.println("\n16 - Top 10 populated cities in a district (California)");
        reports.getTopNCitiesByDistrict("California", 10);

        System.out.println("\n17 - All capital cities (world) by population");
        reports.getAllCapitalCities();

        System.out.println("\n18 - All capital cities in a continent by population (Asia)");
        reports.getCapitalCitiesByContinent("Asia");

        System.out.println("\n19 - All capital cities in a region by population (Eastern Africa)");
        reports.getCapitalCitiesByRegion("Eastern Africa");

        System.out.println("\n20 - Top 10 capital cities (world)");
        reports.getTopNCapitalCities(10);

        System.out.println("\n21 - Top 10 capital cities in a continent (Europe)");
        reports.getTopNCapitalCitiesByContinent("Europe", 10);

        System.out.println("\n22 - Top 10 capital cities in a region (Caribbean)");
        reports.getTopNCapitalCitiesByRegion("Caribbean", 10);

        System.out.println("\n23 - Population of people, people living in cities, and people not living in cities in each continent");
        reports.getPopulationSplitByContinent();

        System.out.println("\n24 - Population of people, people living in cities, and people not living in cities in each region");
        reports.getPopulationSplitByRegion();

        System.out.println("\n25 - Population of people, people living in cities, and people not living in cities in each country");
        reports.getPopulationSplitByCountry();

        System.out.println("\n26 - Population of the world");
        System.out.println(reports.getWorldPopulation());

        System.out.println("\n26b - Population of a continent (Asia)");
        System.out.println(reports.getPopulationOfContinent("Asia"));

        System.out.println("\n26c - Population of a region (Caribbean)");
        System.out.println(reports.getPopulationOfRegion("Caribbean"));

        System.out.println("\n26d - Population of a country (USA)");
        System.out.println(reports.getPopulationOfCountry("USA"));

        System.out.println("\n26e - Population of a district (California)");
        System.out.println(reports.getPopulationOfDistrict("California"));

        System.out.println("\n26f - Population of a city (New York)");
        System.out.println(reports.getPopulationOfCity("New York"));

        System.out.println("\n27 - Top languages by speakers (Chinese, English, Hindi, Spanish, Arabic)");
        reports.getTopLanguages();

        System.out.println("\n28 - Country Report");
        reports.getCountryReport();

        System.out.println("\n29 - City Report");
        reports.getCityReport();

        System.out.println("\n30 - Capital City Report");
        reports.getCapitalCityReport();
        System.out.println("\n31 - Population Reports");
        System.out.println("=== Population Report by Continent ===");
        reports.getPopulationReportForContinent();

        System.out.println("=== Population Report by Region ===");
        reports.getPopulationReportForRegion();

        System.out.println("=== Population Report by Country ===");
        reports.getPopulationReportForCountry();


        System.out.println("\n========== END OF ALL REPORTS ==========\n");

        db.disconnect();
        System.out.println("Program terminated successfully.");
    }
}
