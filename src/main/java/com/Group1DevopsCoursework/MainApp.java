package com.Group1DevopsCoursework;

public class MainApp {

    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();

        Reports reports = new Reports(db);

        System.out.println("\n===== Running General Reports Automatically =====");

        // Automatically call all report methods
        System.out.println("\n1. Population of the World:");
        reports.getWorldPopulation();

        System.out.println("\n2. Population of a Continent:");
        reports.getContinentPopulation();

        System.out.println("\n3. Population of a Region:");
        reports.getRegionPopulation();

        System.out.println("\n4. Population of a Country:");
        reports.getCountryPopulation();

        System.out.println("\n5. Population of a District:");
        reports.getDistrictPopulation();

        System.out.println("\n6. Population of a City:");
        reports.getCityPopulation();

        // Disconnect from the database
        db.disconnect();
        System.out.println("\nAll reports completed. Goodbye!");
    }
}
