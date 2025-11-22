package com.Group1DevopsCoursework;

/**
 * Represents a capital city within the world database.
 * Each CapitalCity object stores:
 * - The name of the capital city
 * - The country it belongs to
 * - Its population
 * This class is primarily used to support reporting queries that retrieve
 * capital cities in various scopes (world, continent, region, filtered lists, etc.).
 */

public class CapitalCity {

    /** The name of the capital city. */
    public String name;

    /** The country to which the capital city belongs. */
    public String country;

    /** The population of the capital city. */
    public int population;
}
