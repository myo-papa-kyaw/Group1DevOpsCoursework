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
            System.out.println("2. Population of all Continents");
            System.out.println("3. Population of all Regions");
            System.out.println("4. Population of all Countries");
            System.out.println("5. Population of all Districts");
            System.out.println("6. Population of all Cities");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                sc.nextLine(); // clear invalid input
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    reports.getWorldPopulation();
                    break;
                case 2:
                    reports.getContinentPopulation();
                    break;
                case 3:
                    reports.getRegionPopulation();
                    break;
                case 4:
                    reports.getCountryPopulation();
                    break;
                case 5:
                    reports.getDistrictPopulation();
                    break;
                case 6:
                    reports.getCityPopulation();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    db.disconnect();
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice, please select a valid option (0-6).");
            }
        }
    }
}
