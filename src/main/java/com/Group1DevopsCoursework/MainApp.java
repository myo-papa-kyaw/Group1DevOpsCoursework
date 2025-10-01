package com.Group1DevopsCoursework;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();

        Reports reports = new Reports(db);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== General Reports Menu =====");
            System.out.println("1. Population of the World");
            System.out.println("2. Population of a Continent");
            System.out.println("3. Population of a Region");
            System.out.println("4. Population of a Country");
            System.out.println("5. Population of a District");
            System.out.println("6. Population of a City");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                sc.nextLine(); // clear invalid input
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> reports.getWorldPopulation();
                case 2 -> reports.getContinentPopulation();
                case 3 -> reports.getRegionPopulation();
                case 4 -> reports.getCountryPopulation();
                case 5 -> reports.getDistrictPopulation();
                case 6 -> reports.getCityPopulation();
                case 0 -> {
                    System.out.println("Exiting... Goodbye!");
                    db.disconnect();
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice, please select a valid option (0-6).");
            }
        }
    }
}
