package com.Group1DevopsCoursework;

public class MainApp {
    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();

        // Try connecting to the database
        db.connect();

        if (db.con == null) {
            System.err.println("Could not establish a database connection. Exiting...");
            return;
        }

        Reports reports = new Reports(db);
        int N = 10; // Fixed value for all Top N reports

        System.out.println("\n========== AUTO REPORT RUN ==========");
        System.out.println("All reports will use N = " + N + "\n");

        try {
            // Run all reports automatically
            reports.getCityVsNonCityPopulationByContinent();
            reports.getCityVsNonCityPopulationByRegion();
            reports.getCityVsNonCityPopulationByCountry();

            reports.getTopNPopulatedCitiesWorld(N);
            reports.getTopNPopulatedCitiesContinent("Asia", N);
            reports.getTopNPopulatedCitiesRegion("Western Europe", N);
            reports.getTopNPopulatedCitiesCountry("United Kingdom", N);
            reports.getTopNPopulatedCitiesDistrict("California", N);

            System.out.println("\n All reports generated successfully.\n");

        } catch (Exception e) {
            System.err.println(" Error running reports: " + e.getMessage());
        } finally {
            db.disconnect();
            System.out.println("Database connection closed.");
        }
    }
}
