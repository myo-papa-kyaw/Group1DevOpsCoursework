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
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number (1-3).");
                scanner.next();
            }

            mainChoice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (mainChoice) {
                case 1:
                    showCountryReports(reports, scanner);
                    break;
                case 2:
                    showCapitalCitiesReports(reports, scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
            }

        } while (mainChoice != 3);

        scanner.close();
        db.disconnect();
    }

    private static void showCountryReports(Reports reports, Scanner scanner) {
        System.out.println("\n=== COUNTRY REPORTS ===");
        System.out.println("1. All countries in the world");
        System.out.println("2. All countries in a continent");
        System.out.println("3. All countries in a region");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice (1-4): ");

        int choice = getValidChoice(scanner, 4);

        switch (choice) {
            case 1:
                reports.getAllCountries();
                break;
            case 2:
                System.out.print("Enter continent name (e.g., Asia) : ");
                String continent = scanner.nextLine();
                reports.getCountriesByContinent(continent);
                break;
            case 3:
                System.out.print("Enter region name (e.g., Western Europe) : ");
                String region = scanner.nextLine();
                reports.getCountriesByRegion(region);
                break;
            case 4:
                System.out.println("Returning to Main Menu...");
                break;
        }
    }

    private static void showCapitalCitiesReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== CAPITAL CITIES REPORTS ===");
            System.out.println("1. All capital cities in the world (by population)");
            System.out.println("2. All capital cities in a continent (by population)");
            System.out.println("3. All capital cities in a region (by population)");
            System.out.println("4. Top N populated capital cities in the world");
            System.out.println("5. Top N populated capital cities in a continent");
            System.out.println("6. Top N populated capital cities in a region");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice (1-7): ");

            choice = getValidChoice(scanner, 7);

            switch (choice) {
                case 1:
                    reports.getAllCapitalCitiesWorld();
                    break;
                case 2:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    String continent = scanner.nextLine();
                    reports.getCapitalCitiesByContinent(continent);
                    break;
                case 3:
                    System.out.print("Enter region name (e.g., Western Europe) : ");
                    String region = scanner.nextLine();
                    reports.getCapitalCitiesByRegion(region);
                    break;
                case 4:
                    System.out.print("Enter number of cities (N) : ");
                    int nWorld = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesWorld(nWorld);
                    break;
                case 5:
                    System.out.print("Enter continent name (e.g., Asia) : ");
                    String continentN = scanner.nextLine();
                    System.out.print("Enter number of cities (N) : ");
                    int nContinent = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesContinent(continentN, nContinent);
                    break;
                case 6:
                    System.out.print("Enter region name (e.g., Western Europe) : ");
                    String regionN = scanner.nextLine();
                    System.out.print("Enter number of cities (N) : ");
                    int nRegion = getValidInt(scanner);
                    reports.getTopNPopulatedCapitalCitiesRegion(regionN, nRegion);
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 7);
    }

    private static int getValidChoice(Scanner scanner, int maxChoice) {
        int choice;
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            if (choice >= 1 && choice <= maxChoice) {
                break;
            } else {
                System.out.println("Please enter a number between 1 and " + maxChoice + ".");
            }
        }
        return choice;
    }

    private static int getValidInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        return number;
    }
}