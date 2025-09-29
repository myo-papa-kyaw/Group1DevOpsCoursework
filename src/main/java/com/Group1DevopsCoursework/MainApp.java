package com.Group1DevopsCoursework;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();

        Reports reports = new Reports(db);
        Scanner scanner = new Scanner(System.in);
        int mainChoice;

        do {
            System.out.println("\n=== REPORTS MENU ===");
            System.out.println("1. Country Reports");
            System.out.println("2. Capital City Reports");
            System.out.println("3. Population Reports");
            System.out.println("4. City Reports");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            mainChoice = getValidChoice(scanner, 5);

            switch (mainChoice) {
                case 1 -> showCountryReports(reports, scanner);
                case 2 -> showCapitalCitiesReports(reports, scanner);
                case 3 -> showPopulationReports(reports);
                case 4 -> showCityReports(reports, scanner);
                case 5 -> System.out.println("Exiting...");
            }

        } while (mainChoice != 5);

        scanner.close();
        db.disconnect();
    }

    // -------- COUNTRY REPORTS --------
    private static void showCountryReports(Reports reports, Scanner scanner) {
        System.out.println("\n=== COUNTRY REPORTS ===");
        System.out.println("1. All countries in the world");
        System.out.println("2. All countries in a continent");
        System.out.println("3. All countries in a region");
        System.out.println("4. Back to Main Menu");

        int choice = getValidChoice(scanner, 4);
        switch (choice) {
            case 1 -> reports.getAllCountries();
            case 2 -> {
                System.out.print("Enter continent name: ");
                String continent = scanner.nextLine();
                reports.getCountriesByContinent(continent);
            }
            case 3 -> {
                System.out.print("Enter region name: ");
                String region = scanner.nextLine();
                reports.getCountriesByRegion(region);
            }
            case 4 -> System.out.println("Returning...");
        }
    }

    // -------- CAPITAL CITY REPORTS --------
    private static void showCapitalCitiesReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== CAPITAL CITIES REPORTS ===");
            System.out.println("1. All capital cities in the world");
            System.out.println("2. All capital cities in a continent");
            System.out.println("3. All capital cities in a region");
            System.out.println("4. Top N capital cities in the world");
            System.out.println("5. Top N capital cities in a continent");
            System.out.println("6. Top N capital cities in a region");
            System.out.println("7. Back to Main Menu");

            choice = getValidChoice(scanner, 7);
            switch (choice) {
                case 1 -> reports.getAllCapitalCitiesWorld();
                case 2 -> {
                    System.out.print("Enter continent: ");
                    String continent = scanner.nextLine();
                    reports.getCapitalCitiesByContinent(continent);
                }
                case 3 -> {
                    System.out.print("Enter region: ");
                    String region = scanner.nextLine();
                    reports.getCapitalCitiesByRegion(region);
                }
                case 4 -> {
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesWorld(n);
                }
                case 5 -> {
                    System.out.print("Enter continent: ");
                    String continent = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesContinent(continent, n);
                }
                case 6 -> {
                    System.out.print("Enter region: ");
                    String region = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesRegion(region, n);
                }
                case 7 -> System.out.println("Returning...");
            }
        } while (choice != 7);
    }

    // -------- POPULATION REPORTS --------
    private static void showPopulationReports(Reports reports) {
        System.out.println("\n=== POPULATION REPORTS ===");
        reports.getPopulationByContinent();
        reports.getPopulationByRegion();
        reports.getPopulationByCountry();
    }

    // -------- CITY REPORTS --------
    private static void showCityReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== CITY REPORTS ===");
            System.out.println("1. Top N cities in the world");
            System.out.println("2. Top N cities in a continent");
            System.out.println("3. Top N cities in a region");
            System.out.println("4. Top N cities in a country");
            System.out.println("5. Back to Main Menu");

            choice = getValidChoice(scanner, 5);
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCitiesWorld(n);
                }
                case 2 -> {
                    System.out.print("Enter continent: ");
                    String continent = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCitiesContinent(continent, n);
                }
                case 3 -> {
                    System.out.print("Enter region: ");
                    String region = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCitiesRegion(region, n);
                }
                case 4 -> {
                    System.out.print("Enter country: ");
                    String country = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = getValidInt(scanner);
                    reports.getTopNPopulatedCitiesCountry(country, n);
                }
                case 5 -> System.out.println("Returning...");
            }
        } while (choice != 5);
    }

    // -------- INPUT VALIDATION --------
    private static int getValidChoice(Scanner scanner, int maxChoice) {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > maxChoice) {
            System.out.println("Invalid choice. Try again.");
            return getValidChoice(scanner, maxChoice);
        }
        return choice;
    }

    private static int getValidInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }
}
