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
            System.out.println("\n******  REPORTS MENU  *******");
            System.out.println("| 1. Country Reports        |");
            System.out.println("| 2. City Reports           |");
            System.out.println("| 3. Capital City Reports   |");
            System.out.println("| 4. Population Reports     |");
            System.out.println("| 5. Language Reports       |");
            System.out.println("| 6. Exit                   |");
            System.out.println("|___________________________|");
            System.out.print("Enter your choice (1-6): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number (1-6).");
                scanner.next();
            }

            mainChoice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (mainChoice) {
                case 1:
                    showCountryReports(reports, scanner);
                    break;
                case 2:
                    showCityReports(reports, scanner);
                    break;
                case 3:
                    showCapitalCitiesReports(reports, scanner);
                    break;
                case 4:
                    showPopulationReports(reports, scanner);
                    break;
                case 5:
                    showLanguageReports(reports, scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }

        } while (mainChoice != 6);

        scanner.close();
        db.disconnect();
    }

    private static void showCountryReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== COUNTRY REPORTS ===");
            System.out.println("1. All countries in the world by population");
            System.out.println("2. All countries in a continent by population");
            System.out.println("3. All countries in a region by population");
            System.out.println("4. Top N countries in the world");
            System.out.println("5. Top N countries in a continent");
            System.out.println("6. Top N countries in a region");
            System.out.println("7. Country Report (detailed)");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice (1-8): ");

            choice = getValidChoice(scanner, 8);

            switch (choice) {
                case 1:
                    reports.getAllCountries();
                    break;
                case 2:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    String continent = scanner.nextLine();
                    if (!continent.trim().isEmpty()) {
                        reports.getCountriesByContinent(continent);
                    } else {
                        System.out.println("Continent name cannot be empty.");
                    }
                    break;
                case 3:
                    System.out.print("Enter region name (e.g., Western Europe): ");
                    String region = scanner.nextLine();
                    if (!region.trim().isEmpty()) {
                        reports.getCountriesByRegion(region);
                    } else {
                        System.out.println("Region name cannot be empty.");
                    }
                    break;
                case 4:
                    System.out.print("Enter number of countries (N): ");
                    int nWorld = getValidInt(scanner);
                    if (nWorld > 0) {
                        reports.getTopNCountries(nWorld);
                    } else {
                        System.out.println("Number must be greater than 0.");
                    }
                    break;
                case 5:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    String continentN = scanner.nextLine();
                    System.out.print("Enter number of countries (N): ");
                    int nContinent = getValidInt(scanner);
                    if (!continentN.trim().isEmpty() && nContinent > 0) {
                        reports.getTopNCountriesByContinent(continentN, nContinent);
                    } else {
                        System.out.println("Continent name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 6:
                    System.out.print("Enter region name (e.g., Western Europe): ");
                    String regionN = scanner.nextLine();
                    System.out.print("Enter number of countries (N): ");
                    int nRegion = getValidInt(scanner);
                    if (!regionN.trim().isEmpty() && nRegion > 0) {
                        reports.getTopNCountriesByRegion(regionN, nRegion);
                    } else {
                        System.out.println("Region name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 7:
                    reports.getCountryReport();
                    break;
                case 8:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 8);
    }

    private static void showCityReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== CITY REPORTS ===");
            System.out.println("1. All cities in the world by population");
            System.out.println("2. All cities in a continent by population");
            System.out.println("3. All cities in a region by population");
            System.out.println("4. All cities in a country by population");
            System.out.println("5. All cities in a district by population");
            System.out.println("6. Top N cities in the world");
            System.out.println("7. Top N cities in a continent");
            System.out.println("8. Top N cities in a region");
            System.out.println("9. Top N cities in a country");
            System.out.println("10. Top N cities in a district");
            System.out.println("11. City Report (detailed)");
            System.out.println("12. Back to Main Menu");
            System.out.print("Enter your choice (1-12): ");

            choice = getValidChoice(scanner, 12);

            switch (choice) {
                case 1:
                    reports.getAllCities();
                    break;
                case 2:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    String continent = scanner.nextLine();
                    if (!continent.trim().isEmpty()) {
                        reports.getCitiesByContinent(continent);
                    } else {
                        System.out.println("Continent name cannot be empty.");
                    }
                    break;
                case 3:
                    System.out.print("Enter region name (e.g., Western Europe): ");
                    String region = scanner.nextLine();
                    if (!region.trim().isEmpty()) {
                        reports.getCitiesByRegion(region);
                    } else {
                        System.out.println("Region name cannot be empty.");
                    }
                    break;
                case 4:
                    System.out.print("Enter country code (e.g., USA) or country name: ");
                    String countryInput = scanner.nextLine();
                    if (!countryInput.trim().isEmpty()) {
                        // Try as code first, then as name
                        if (countryInput.length() <= 3) {
                            reports.getCitiesByCountry(countryInput.toUpperCase());
                        } else {
                            // For country name, we need to get the code first
                            String countryCode = getCountryCodeFromName(reports, countryInput);
                            if (countryCode != null) {
                                reports.getCitiesByCountry(countryCode);
                            } else {
                                System.out.println("Country not found: " + countryInput);
                            }
                        }
                    } else {
                        System.out.println("Country code/name cannot be empty.");
                    }
                    break;
                case 5:
                    System.out.print("Enter district name: ");
                    String district = scanner.nextLine();
                    if (!district.trim().isEmpty()) {
                        reports.getCitiesByDistrict(district);
                    } else {
                        System.out.println("District name cannot be empty.");
                    }
                    break;
                case 6:
                    System.out.print("Enter number of cities (N): ");
                    int n = getValidInt(scanner);
                    if (n > 0) {
                        reports.getTopNCities(n);
                    } else {
                        System.out.println("Number must be greater than 0.");
                    }
                    break;
                case 7:
                    System.out.print("Enter continent name: ");
                    String cont = scanner.nextLine();
                    System.out.print("Enter number of cities (N): ");
                    int nCont = getValidInt(scanner);
                    if (!cont.trim().isEmpty() && nCont > 0) {
                        reports.getTopNCitiesByContinent(cont, nCont);
                    } else {
                        System.out.println("Continent name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 8:
                    System.out.print("Enter region name: ");
                    String reg = scanner.nextLine();
                    System.out.print("Enter number of cities (N): ");
                    int nReg = getValidInt(scanner);
                    if (!reg.trim().isEmpty() && nReg > 0) {
                        reports.getTopNCitiesByRegion(reg, nReg);
                    } else {
                        System.out.println("Region name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 9:
                    System.out.print("Enter country code (e.g., USA) or country name: ");
                    String countryCodeInput = scanner.nextLine();
                    System.out.print("Enter number of cities (N): ");
                    int nCountry = getValidInt(scanner);
                    if (!countryCodeInput.trim().isEmpty() && nCountry > 0) {
                        if (countryCodeInput.length() <= 3) {
                            reports.getTopNCitiesByCountry(countryCodeInput.toUpperCase(), nCountry);
                        } else {
                            String countryCode = getCountryCodeFromName(reports, countryCodeInput);
                            if (countryCode != null) {
                                reports.getTopNCitiesByCountry(countryCode, nCountry);
                            } else {
                                System.out.println("Country not found: " + countryCodeInput);
                            }
                        }
                    } else {
                        System.out.println("Country code/name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 10:
                    System.out.print("Enter district name: ");
                    String dist = scanner.nextLine();
                    System.out.print("Enter number of cities (N): ");
                    int nDist = getValidInt(scanner);
                    if (!dist.trim().isEmpty() && nDist > 0) {
                        reports.getTopNCitiesByDistrict(dist, nDist);
                    } else {
                        System.out.println("District name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 11:
                    reports.getCityReport();
                    break;
                case 12:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 12);
    }

    private static void showCapitalCitiesReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== CAPITAL CITIES REPORTS ===");
            System.out.println("1. All capital cities in the world by population");
            System.out.println("2. All capital cities in a continent by population");
            System.out.println("3. All capital cities in a region by population");
            System.out.println("4. Top N capital cities in the world");
            System.out.println("5. Top N capital cities in a continent");
            System.out.println("6. Top N capital cities in a region");
            System.out.println("7. Capital City Report (detailed)");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice (1-8): ");

            choice = getValidChoice(scanner, 8);

            switch (choice) {
                case 1:
                    reports.getAllCapitalCities();
                    break;
                case 2:
                    System.out.print("Enter continent name (e.g., Asia): ");
                    String continent = scanner.nextLine();
                    if (!continent.trim().isEmpty()) {
                        reports.getCapitalCitiesByContinent(continent);
                    } else {
                        System.out.println("Continent name cannot be empty.");
                    }
                    break;
                case 3:
                    System.out.print("Enter region name (e.g., Western Europe): ");
                    String region = scanner.nextLine();
                    if (!region.trim().isEmpty()) {
                        reports.getCapitalCitiesByRegion(region);
                    } else {
                        System.out.println("Region name cannot be empty.");
                    }
                    break;
                case 4:
                    System.out.print("Enter number of capital cities (N): ");
                    int n = getValidInt(scanner);
                    if (n > 0) {
                        reports.getTopNCapitalCities(n);
                    } else {
                        System.out.println("Number must be greater than 0.");
                    }
                    break;
                case 5:
                    System.out.print("Enter continent name: ");
                    String cont = scanner.nextLine();
                    System.out.print("Enter number of capital cities (N): ");
                    int nCont = getValidInt(scanner);
                    if (!cont.trim().isEmpty() && nCont > 0) {
                        reports.getTopNCapitalCitiesByContinent(cont, nCont);
                    } else {
                        System.out.println("Continent name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 6:
                    System.out.print("Enter region name: ");
                    String reg = scanner.nextLine();
                    System.out.print("Enter number of capital cities (N): ");
                    int nReg = getValidInt(scanner);
                    if (!reg.trim().isEmpty() && nReg > 0) {
                        reports.getTopNCapitalCitiesByRegion(reg, nReg);
                    } else {
                        System.out.println("Region name cannot be empty and number must be greater than 0.");
                    }
                    break;
                case 7:
                    reports.getCapitalCityReport();
                    break;
                case 8:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 8);
    }

    private static void showPopulationReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== POPULATION REPORTS ===");
            System.out.println("1. Population summary by continent");
            System.out.println("2. Population summary by region");
            System.out.println("3. Population summary by country");
            System.out.println("4. Get population of world/continent/region/country/district/city");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");

            choice = getValidChoice(scanner, 5);

            switch (choice) {
                case 1:
                    reports.getPopulationSummaryByContinent();
                    break;
                case 2:
                    reports.getPopulationSummaryByRegion();
                    break;
                case 3:
                    reports.getPopulationSummaryByCountry();
                    break;
                case 4:
                    System.out.print("Choose: world/continent/region/country/district/city : ");
                    String choiceType = scanner.nextLine().trim().toLowerCase();
                    switch (choiceType) {
                        case "world":
                            System.out.println("World population: " + reports.getWorldPopulation());
                            break;
                        case "continent":
                            System.out.print("Enter continent name: ");
                            String continent = scanner.nextLine();
                            if (!continent.trim().isEmpty()) {
                                System.out.println("Population: " + reports.getPopulationOfContinent(continent));
                            } else {
                                System.out.println("Continent name cannot be empty.");
                            }
                            break;
                        case "region":
                            System.out.print("Enter region name: ");
                            String region = scanner.nextLine();
                            if (!region.trim().isEmpty()) {
                                System.out.println("Population: " + reports.getPopulationOfRegion(region));
                            } else {
                                System.out.println("Region name cannot be empty.");
                            }
                            break;
                        case "country":
                            System.out.print("Enter country code or name: ");
                            String countryInput = scanner.nextLine();
                            if (!countryInput.trim().isEmpty()) {
                                if (countryInput.length() <= 3) {
                                    System.out.println("Population: " + reports.getPopulationOfCountry(countryInput.toUpperCase()));
                                } else {
                                    String countryCode = getCountryCodeFromName(reports, countryInput);
                                    if (countryCode != null) {
                                        System.out.println("Population: " + reports.getPopulationOfCountry(countryCode));
                                    } else {
                                        System.out.println("Country not found: " + countryInput);
                                    }
                                }
                            } else {
                                System.out.println("Country code/name cannot be empty.");
                            }
                            break;
                        case "district":
                            System.out.print("Enter district name: ");
                            String district = scanner.nextLine();
                            if (!district.trim().isEmpty()) {
                                System.out.println("Population: " + reports.getPopulationOfDistrict(district));
                            } else {
                                System.out.println("District name cannot be empty.");
                            }
                            break;
                        case "city":
                            System.out.print("Enter city name: ");
                            String city = scanner.nextLine();
                            if (!city.trim().isEmpty()) {
                                System.out.println("Population: " + reports.getPopulationOfCity(city));
                            } else {
                                System.out.println("City name cannot be empty.");
                            }
                            break;
                        default:
                            System.out.println("Unknown type. Please choose from: world, continent, region, country, district, city");
                    }
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 5);
    }

    private static void showLanguageReports(Reports reports, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== LANGUAGE REPORTS ===");
            System.out.println("1. Top languages speakers (Chinese, English, Hindi, Spanish, Arabic)");
            System.out.println("2. Back to Main Menu");
            System.out.print("Enter your choice (1-2): ");

            choice = getValidChoice(scanner, 2);

            switch (choice) {
                case 1:
                    reports.getTopLanguages();
                    break;
                case 2:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 2);
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

    // Helper method to get country code from country name
    private static String getCountryCodeFromName(Reports reports, String countryName) {
        // This is a simplified version - you might need to implement this properly
        // based on your database structure
        System.out.println("Note: Country name lookup not fully implemented. Using first 3 letters as code.");
        if (countryName.length() >= 3) {
            return countryName.substring(0, 3).toUpperCase();
        } else {
            return countryName.toUpperCase();
        }
    }
}