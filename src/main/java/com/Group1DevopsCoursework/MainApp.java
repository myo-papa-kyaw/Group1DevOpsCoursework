package com.Group1DevopsCoursework;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();
        db.connect();
        Reports reports = new Reports(db);
        Scanner scanner = new Scanner(System.in);

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("0. Exit");
            System.out.println("1. All countries (world) by population");
            System.out.println("2. All countries in a continent by population");
            System.out.println("3. All countries in a region by population");
            System.out.println("4. Top N countries (world)");
            System.out.println("5. Top N countries in a continent");
            System.out.println("6. Top N countries in a region");
            System.out.println("7. All cities (world) by population");
            System.out.println("8. All cities in a continent by population");
            System.out.println("9. All cities in a region by population");
            System.out.println("10. All cities in a country by population");
            System.out.println("11. All cities in a district by population");
            System.out.println("12. Top N cities (world)");
            System.out.println("13. Top N cities in a continent");
            System.out.println("14. Top N cities in a region");
            System.out.println("15. Top N cities in a country");
            System.out.println("16. Top N cities in a district");
            System.out.println("17. All capital cities (world) by population");
            System.out.println("18. All capital cities in a continent by population");
            System.out.println("19. All capital cities in a region by population");
            System.out.println("20. Top N capital cities (world)");
            System.out.println("21. Top N capital cities in a continent");
            System.out.println("22. Top N capital cities in a region");
            System.out.println("23. Population summary by continent");
            System.out.println("24. Population summary by region");
            System.out.println("25. Population summary by country");
            System.out.println("26. Get population of world/continent/region/country/district/city");
            System.out.println("27. Top languages speakers (Chinese, English, Hindi, Spanish, Arabic)");
            System.out.println("28. Country Report");
            System.out.println("29. City Report");
            System.out.println("30. Capital City Report");
            System.out.println("31. Population Report (Continent / Region / Country)");
            System.out.print("Enter choice: ");

            choice = readInt(scanner); // safe integer input

            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    reports.getAllCountries();
                    break;
                case 2:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    reports.getCountriesByContinent(readLine(scanner));
                    break;
                case 3:
                    System.out.print("Enter region name (e.g., Southern Europe): ");
                    reports.getCountriesByRegion(readLine(scanner));
                    break;
                case 4:
                    System.out.print("Enter N: ");
                    reports.getTopNCountries(readInt(scanner));
                    break;
                case 5:
                    System.out.print("Enter continent name: ");
                    String cont = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCountriesByContinent(cont, readInt(scanner));
                    break;
                case 6:
                    System.out.print("Enter region name: ");
                    String reg = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCountriesByRegion(reg, readInt(scanner));
                    break;
                case 7:
                    reports.getAllCities();
                    break;
                case 8:
                    System.out.print("Enter continent name: ");
                    reports.getCitiesByContinent(readLine(scanner));
                    break;
                case 9:
                    System.out.print("Enter region name: ");
                    reports.getCitiesByRegion(readLine(scanner));
                    break;
                case 10:
                    System.out.print("Enter country code (e.g., USA): ");
                    reports.getCitiesByCountry(readLine(scanner));
                    break;
                case 11:
                    System.out.print("Enter district name: ");
                    reports.getCitiesByDistrict(readLine(scanner));
                    break;
                case 12:
                    System.out.print("Enter N: ");
                    reports.getTopNCities(readInt(scanner));
                    break;
                case 13:
                    System.out.print("Enter continent name: ");
                    cont = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCitiesByContinent(cont, readInt(scanner));
                    break;
                case 14:
                    System.out.print("Enter region name: ");
                    reg = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCitiesByRegion(reg, readInt(scanner));
                    break;
                case 15:
                    System.out.print("Enter country code (e.g., USA): ");
                    String countryCode = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCitiesByCountry(countryCode, readInt(scanner));
                    break;
                case 16:
                    System.out.print("Enter district name: ");
                    String district = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCitiesByDistrict(district, readInt(scanner));
                    break;
                case 17:
                    reports.getAllCapitalCities();
                    break;
                case 18:
                    System.out.print("Enter continent name: ");
                    reports.getCapitalCitiesByContinent(readLine(scanner));
                    break;
                case 19:
                    System.out.print("Enter region name: ");
                    reports.getCapitalCitiesByRegion(readLine(scanner));
                    break;
                case 20:
                    System.out.print("Enter N: ");
                    reports.getTopNCapitalCities(readInt(scanner));
                    break;
                case 21:
                    System.out.print("Enter continent name: ");
                    cont = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCapitalCitiesByContinent(cont, readInt(scanner));
                    break;
                case 22:
                    System.out.print("Enter region name: ");
                    reg = readLine(scanner);
                    System.out.print("Enter N: ");
                    reports.getTopNCapitalCitiesByRegion(reg, readInt(scanner));
                    break;
                case 23:
                    reports.getPopulationSummaryByContinent();
                    break;
                case 24:
                    reports.getPopulationSummaryByRegion();
                    break;
                case 25:
                    reports.getPopulationSummaryByCountry();
                    break;
                case 26:
                    System.out.print("Choose: world/continent/region/country/district/city : ");
                    String choiceType = readLine(scanner).trim().toLowerCase();
                    switch (choiceType) {
                        case "world":
                            System.out.println("World population: " + reports.getWorldPopulation());
                            break;
                        case "continent":
                            System.out.print("Enter continent name: ");
                            System.out.println(reports.getPopulationOfContinent(readLine(scanner)));
                            break;
                        case "region":
                            System.out.print("Enter region name: ");
                            System.out.println(reports.getPopulationOfRegion(readLine(scanner)));
                            break;
                        case "country":
                            System.out.print("Enter country code: ");
                            System.out.println(reports.getPopulationOfCountry(readLine(scanner)));
                            break;
                        case "district":
                            System.out.print("Enter district name: ");
                            System.out.println(reports.getPopulationOfDistrict(readLine(scanner)));
                            break;
                        case "city":
                            System.out.print("Enter city name: ");
                            System.out.println(reports.getPopulationOfCity(readLine(scanner)));
                            break;
                        default:
                            System.out.println("Unknown type");
                    }
                    break;
                case 27:
                    reports.getTopLanguages();
                    break;
                case 28:
                    reports.getCountryReport();
                    break;
                case 29:
                    reports.getCityReport();
                    break;
                case 30:
                    reports.getCapitalCityReport();
                    break;
                case 31:
                    // ---- FIXED: robust handling for Population Report (user types a number then text)
                    System.out.println("Population Reports:");
                    System.out.println("1. By Continent (enter continent name)");
                    System.out.println("2. By Region (enter region name)");
                    System.out.println("3. By Country (enter country name)");
                    System.out.print("Enter choice (1/2/3): ");
                    int sub = readInt(scanner);
                    if (sub == 1) {
                        System.out.print("Enter Continent Name (e.g., Asia): ");
                        String continent = readLine(scanner);
                        reports.getPopulationReportForContinent(continent);
                    } else if (sub == 2) {
                        System.out.print("Enter Region Name (e.g., Caribbean): ");
                        String region = readLine(scanner);
                        reports.getPopulationReportForRegion(region);
                    } else if (sub == 3) {
                        System.out.print("Enter Country Name (e.g., France): ");
                        String country = readLine(scanner);
                        reports.getPopulationReportForCountry(country);
                    } else {
                        System.out.println("Invalid choice for population report.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        db.disconnect();
        scanner.close();
        System.out.println("Program terminated.");
    }

    // helper that keeps asking until a valid integer is supplied
    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.print("Please enter a number: ");
                continue;
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    // helper that reads a non-empty trimmed line (returns empty if user allows it)
    private static String readLine(Scanner sc) {
        String line = sc.nextLine();
        return line == null ? "" : line.trim();
    }
}
