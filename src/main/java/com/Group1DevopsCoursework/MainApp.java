package com.Group1DevopsCoursework;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();

        Reports reports = new Reports(db);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== REPORT MENU =====");
            System.out.println("1. Population in cities vs non-cities by Continent");
            System.out.println("2. Population in cities vs non-cities by Region");
            System.out.println("3. Population in cities vs non-cities by Country");
            System.out.println("4. Top N populated cities in the World");
            System.out.println("5. Top N populated cities in a Continent");
            System.out.println("6. Top N populated cities in a Region");
            System.out.println("7. Top N populated cities in a Country");
            System.out.println("8. Top N populated cities in a District");
            System.out.println("0. Exit");
            System.out.print("Choose a report: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> reports.getCityVsNonCityPopulationByContinent();
                case 2 -> reports.getCityVsNonCityPopulationByRegion();
                case 3 -> reports.getCityVsNonCityPopulationByCountry();
                case 4 -> {
                    System.out.print("Enter N: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    reports.getTopNPopulatedCitiesWorld(n);
                }
                case 5 -> {
                    System.out.print("Enter Continent: ");
                    String continent = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    reports.getTopNPopulatedCitiesContinent(continent, n);
                }
                case 6 -> {
                    System.out.print("Enter Region: ");
                    String region = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    reports.getTopNPopulatedCitiesRegion(region, n);
                }
                case 7 -> {
                    System.out.print("Enter Country: ");
                    String country = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    reports.getTopNPopulatedCitiesCountry(country, n);
                }
                case 8 -> {
                    System.out.print("Enter District: ");
                    String district = scanner.nextLine();
                    System.out.print("Enter N: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    reports.getTopNPopulatedCitiesDistrict(district, n);
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    db.disconnect();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
